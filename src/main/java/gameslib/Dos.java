/**
 * 
 */
package gameslib;

import java.util.ArrayList;
import java.util.Collections;

import games.Card;
import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;
import games.Suit;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import main.LudopatApp;
import net.Client;
import net.GameServer;

/**
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Dos extends Game {

	public GameControllerDos controller;

	private IntegerProperty cardsToDraw = new SimpleIntegerProperty(1);
	private ObjectProperty<Player> activePlayer = new SimpleObjectProperty<Player>();

	private boolean inverse = false;

	private final int SPECIAL_CARDS = 11; // Empezamos por 11 las especiales

	public final static int SPECIAL_INVERSE = 15;
	public final static int SPECIAL_BLOCK = 16;
	public final static int SPECIAL_PLUSONE = 17;
	public final static int SPECIAL_CHANGE_BLUE = 11;
	public final static int SPECIAL_CHANGE_YELLOW = 12;
	public final static int SPECIAL_CHANGE_WHITE = 13;
	public final static int SPECIAL_CHANGE_GREEN = 14;

	private boolean isBlocked;

	/**
	 * Actual valor de la carta en la mesa
	 */
	private IntegerProperty currentValue = new SimpleIntegerProperty();
	private StringProperty currentColor = new SimpleStringProperty();
	private ObjectProperty<Card> lastCard = new SimpleObjectProperty<Card>();

	// NET
	@SuppressWarnings("unused")
	private LudopatApp app;
	private Player localPlayer;
	private GameServer gameServer;
	private Client clientThread;
	private GameControllerDosNET NETHud;

	/**
	 * Constructor del juego principal
	 * 
	 * @param deck           Mazo sobre la mesa
	 * @param gameRules      Reglas de juego
	 * @param currentPlayers Jugadores en el juego
	 */
	public Dos(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		super(deck, gameRules, currentPlayers);
	}

	// ***************** NET Related***********************
	// -----------------------------------------------------
	// -----------------------------------------------------
	/**
	 * Constructor del juego para el cliente
	 * 
	 * @param currentPlayers  Jugadores en el juego
	 * @param tableCard       Carta sobre la mesa
	 * @param currentPlayerID Actual turno del jugador
	 * @param localPlayerID   Juegador local, esto es, de esta máquina
	 * @param clientThread    Hilo de procesamiento del cliente de esta máquina
	 */
	public Dos(ArrayList<Player> currentPlayers, Card tableCard, int currentPlayerID, int localPlayerID,
			Client clientThread) {
		setCurrentPlayers(currentPlayers);
		setLastCard(tableCard);
		setClientThread(clientThread);
		setLocalPlayer(
				currentPlayers.stream().filter(p -> p.getPlayerInfo().getUserID() == localPlayerID).findFirst().get());

		// Si no ha salido una carta especial, cambiamos valor y color
		if (tableCard.getCardValue() < SPECIAL_CARDS) {
			setCurrentColor(tableCard.getSuit().getName());
			setCurrentValue(tableCard.getCardValue());
		}

		// Vemos a que jugador le toca jugar
		for (Player p : currentPlayers) {
			if (p.getPlayerInfo().getUserID() == currentPlayerID) {
				setActivePlayer(p);
				break;
			}
		}
	}

	/**
	 * Inicia el servidor de juego
	 * 
	 * @param app La propia app
	 */
	public void initGameServer(LudopatApp app) {
		this.app = app;

		gameServer = app.getGameServer();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				gameServer.sendInitialInfo(getCurrentPlayers(), lastCard.get(), activePlayer.get().getId(),
						app.getGameRules().getGameType());

			}
		});
	}

	/**
	 * El cliente quiere robar cartas
	 */
	public void client_drawCard() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				clientThread.sendDrawCard();

			}
		});
	}

	/**
	 * El cliente quiere lanzar carta
	 * 
	 * @param indexOfCard Índice de la carta en su mano
	 */
	public void client_throwCard(int indexOfCard) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clientThread.sendThrowCard(indexOfCard);
			}
		});
	}

	/**
	 * El cliente pasa turno
	 */
	public void client_nextTurn() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				clientThread.sendNextTurn();

			}
		});

	}

	/**
	 * El servidor avisa del paso de turno al siguiente jugador
	 */
	public void server_nextTurn() {
		nextTurn();
		if (gameServer != null) {
			gameServer.sendNextTurn(getActivePlayer().getPlayerInfo().getUserID(), cardsToDraw.get());
		}
	}

	/**
	 * Un jugador ha lanzado una carta
	 * 
	 * @param indexOfCard Índice de la carta en su mano
	 */
	public void server_throwCard(int indexOfCard) {

		// Modificamos la mano del jugador activo y lanzamos la carta
		Card card = getActivePlayer().getHand().get(indexOfCard);
		throwCard(card);

		// Noficiamos al resto de clientes de la nueva carta sobre la mesa
		if (gameServer != null) {

			if (getActivePlayer().getHand().size() <= 0) {
				gameServer.sendNewCardTable(getActivePlayer().getHand(), getLastCard());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				// Hemos terminado
				endGame();
			}

			else {
				gameServer.sendNewCardTable(getActivePlayer().getHand(), getLastCard());
			}
		}
	}

	/**
	 * Un jugador ha decidido robar cartas
	 */
	public void sever_drawCard() {
		drawCard();

		if (gameServer != null) {
			gameServer.sendCardTaken(getActivePlayer().getHand());
		}
	}

	/**
	 * Desconectamos a este cliente
	 */
	public void client_receiveDisconnect() {
		NETHud.notifyDisconnectDialog();
	}

	/**
	 * Se ha acabado el juego
	 * 
	 * @param players Jugadores ordenados según sus posiciones
	 */
	public void client_receiveEndGame(ArrayList<Player> players) {
		NETHud.updateCardCounters();
		NETHud.refreshHand();
		NETHud.endGame(players);
	}

	/**
	 * El cliente recibe el paso de turno del servidor
	 * 
	 * @param id    Identificador del jugador
	 * @param draws Numero de cartas a robar
	 */
	public void client_receiveNextTurn(int id, int draws) {

		setActivePlayer(
				getCurrentPlayers().stream().filter(p -> p.getPlayerInfo().getUserID() == id).findFirst().get());
		setCardsToDraw(draws);

		if (getActivePlayer() == getLocalPlayer())
			NETHud.nextTurn();

	}

	/**
	 * El cliente recibe la notificación de que ha robado cartas. En caso de ser el
	 * cliente que lo pidió actualizamos su mano, que será el jugador activo
	 * 
	 * @param newHand Nuevo mano del jugador activo
	 */
	public void client_receiveDrawCard(ArrayList<Card> newHand) {

		// Ahora actualizamos la mano del jugador activo
		getActivePlayer().setHand(newHand);

		// Actualizamos la interfaz
		if (getLocalPlayer().getPlayerInfo().getUserID() == getActivePlayer().getPlayerInfo().getUserID()) {
			NETHud.checkUpdatedHand(newHand); // Para el usuario activo con su nueva mano
		}

		// Para todos los usuarios actualizar el número de cartas
		NETHud.updateCardCounters();
	}

	/**
	 * El cliente recibe la notificación de una nueva carta lanzada
	 * 
	 * @param newHand     Nueva mano para el jugador actual
	 * @param cardInTable Nueva carta sobre la mesa
	 */
	public void client_receiveThrowedCard(ArrayList<Card> newHand, Card cardInTable) {

		// Cambiamos la carta sobre la mesa
		if (cardInTable.getCardValue() < SPECIAL_CARDS) {
			setCurrentValue(cardInTable.getCardValue());
		}

		if (cardInTable.getCardValue() <= SPECIAL_CHANGE_GREEN) {
			setCurrentColor(cardInTable.getSuit().getName());
		}

		setLastCard(cardInTable);

		// Ahora actualizamos la mano del jugador activo
		getActivePlayer().setHand(newHand);

		// Actualizamos la interfaz
		if (getLocalPlayer().getPlayerInfo().getUserID() == getActivePlayer().getPlayerInfo().getUserID()) {
			NETHud.checkUpdatedHand(newHand); // Para el usuario activo con su nueva mano
		}

		// Para todos los usuarios actualizar el número de cartas

		NETHud.updateCardCounters();
	}

	// ***************** NET Related***********************
	// -----------------------------------------------------
	// -----------------------------------------------------

	// Métodos genéricos
	// -----------------------------------------------------

	@Override
	public void initGame() {

		// Para la IA, necesitamos saber cuando se cambia de jugador
		activePlayer.addListener((o, ov, nv) -> onChangedPlayer(nv));

		// Ahora inicilaizamos la IA
		for (Player player : getCurrentPlayers()) {
			if (player.isAI()) {
				new Thread(player.getAIController()).start();
			}
		}

		deck.shuffle(); // Barajamos
		sortPlayers();
		dealCards();

	}

	private void onChangedPlayer(Player nv) {
		// Avisamos a la IA que es su turno
		if (nv != null && nv.isAI() && !isBlocked()) {
			nv.getAIController().initTurn();
		}
	}

	@Override
	public void endGame() {
		// Tenemos que desconectar a la IA
		for (Player player : getCurrentPlayers()) {
			if (player.isAI()) {
				player.getAIController().setStopAI(true);
			}
		}
		
		// Ordenar jugadores por numero de cartas y
		// lo pasa al dialogo.
		Collections.sort(currentPlayers, new ComparePlayers());

		if (gameServer != null) {
			gameServer.sendEndGame(currentPlayers);
		}
	}

	@Override
	public void throwCard(Card card) {

		// ¿Es especial?
		if (card.getCardValue() >= SPECIAL_CARDS) {

			switch (card.getCardValue()) {
			case SPECIAL_CHANGE_BLUE:
				setCurrentColor("blue");
				break;
			case SPECIAL_CHANGE_GREEN:
				setCurrentColor("green");
				break;
			case SPECIAL_CHANGE_WHITE:
				setCurrentColor("white");
				break;
			case SPECIAL_CHANGE_YELLOW:
				setCurrentColor("yellow");
				break;
			case SPECIAL_INVERSE:
				if (inverse) {
					inverse = false;
				} else {
					inverse = true;
				}
				break;
			case SPECIAL_BLOCK:
				isBlocked = true;
				break;
			case SPECIAL_PLUSONE:
				setCardsToDraw(getCardsToDraw() + 1);
				break;
			default:
				break;
			}
		} else {

			// Cambiamos las condiciones del tablero
			setCurrentColor(card.getSuit().getName());
			setCurrentValue(card.getCardValue());
		}

		// Ajustamos la última carta
		setLastCard(card);

		// Quitamos la carta de la mano del jugador
		getActivePlayer().getHand().remove(card);
		
		// Actualizamos las estadisticas del jugador
		getActivePlayer().setStatistics(getActivePlayer().getHand().size());
	}

	// -----------------------------------------------------

	// Métodos personalizados
	// -----------------------------------------------------
	/**
	 * Desordena a los jugadores
	 */
	private void sortPlayers() {
		Collections.shuffle(this.currentPlayers);
		setActivePlayer(currentPlayers.get(0));
	}

	/**
	 * Paso de turno
	 */
	public void nextTurn() {
		// Actualizamos las estadisticas del jugador
		getActivePlayer().setStatistics(getActivePlayer().getHand().size());

		if (inverse) {

			if (currentPlayers.indexOf(getActivePlayer()) == 0) {
				setActivePlayer(currentPlayers.get(currentPlayers.size() - 1));
			} else {
				setActivePlayer(currentPlayers.get(currentPlayers.indexOf(getActivePlayer()) - 1));
			}

		} else {

			if (currentPlayers.indexOf(getActivePlayer()) == currentPlayers.size() - 1) {
				setActivePlayer(currentPlayers.get(0));
			} else {
				setActivePlayer(currentPlayers.get(currentPlayers.indexOf(getActivePlayer()) + 1));
			}
		}

		if (isBlocked) {

			isBlocked = false;
			nextTurn();

		}
	}

	/**
	 * Robo de carta
	 */
	public void drawCard() {
		int i = 0;
		while (deck.getCards().size() > 0 && getActivePlayer().getHand().size() < 10 && i < getCardsToDraw()) {
			Card card = deck.getCards().remove(deck.getCards().size() - 1);
			getActivePlayer().getHand().add(card);
			i++;
		}

		setCardsToDraw(1);

		if (deck.getCards().size() <= 0) {
			endGame();
		}
	}

	/**
	 * Comienzo de turno
	 */
	public void startTurn() {
		// Comprobar carta a carta y reemplazarla.
		for (Card card : getActivePlayer().getHand()) {
			checkTable(card);
		}
	}

	/**
	 * Comprobacion de la mesa
	 * 
	 * @param currentCard Carta en mesa actualmente
	 */
	public void checkTable(Card currentCard) {

		if (currentCard.getCardValue() >= this.SPECIAL_CARDS) {
			currentCard.setPlayable(true);

		} else {

			// Si queda pendiente robo de +1, entonces sólo podemos usar cartas especiales
			if (getCardsToDraw() > 1) {
				currentCard.setPlayable(false);
			} else {
				if (currentCard.getCardValue() == getCurrentValue()) {
					currentCard.setPlayable(true);
				} else if (currentCard.getSuit().getName().equals(getCurrentColor())) {
					currentCard.setPlayable(true);
				} else {
					currentCard.setPlayable(false);
				}
			}

		}
	}

	// -----------------------------------------------------
	/**
	 * Reparto inicial de cartas
	 */
	@Override
	public void dealCards() {
		// TIP 1 para pruebas, 7 para partida
		int numCartas = 7;

		for (Player p : currentPlayers) {

			ArrayList<Card> mano = new ArrayList<Card>(numCartas);

			for (int i = 0; i < numCartas; i++) {

				// Añadimos carta al usuario y se lo quitamos a la baraja
				Card card = deck.getCards().remove(deck.getCards().size() - 1);
				mano.add(card);
			}

			p.setHand(mano);
		}
		Card card;
		while ((card = deck.getCards().remove(deck.getCards().size() - 1)).getCardValue() >= SPECIAL_CARDS) {
			deck.getCards().add(0, card);
		}

		setCurrentValue(card.getCardValue());
		currentColor.set(card.getSuit().getName());
		lastCard.set(card);

	}

	public int getPlayerPosition(Player p) {
		return currentPlayers.indexOf(p);
	}

	public final StringProperty currentColorProperty() {
		return this.currentColor;
	}

	public final String getCurrentColor() {
		return this.currentColorProperty().get();
	}

	public final void setCurrentColor(final String currentColor) {
		this.currentColorProperty().set(currentColor);
	}

	public final ObjectProperty<Card> lastCardProperty() {
		return this.lastCard;
	}

	public final Card getLastCard() {
		return this.lastCardProperty().get();
	}

	public final void setLastCard(final Card lastCard) {
		this.lastCardProperty().set(lastCard);
	}

	public final IntegerProperty currentValueProperty() {
		return this.currentValue;
	}

	public final int getCurrentValue() {
		return this.currentValueProperty().get();
	}

	public final void setCurrentValue(final int currentValue) {
		this.currentValueProperty().set(currentValue);
	}

	public final ObjectProperty<Player> activePlayerProperty() {
		return this.activePlayer;
	}

	public final Player getActivePlayer() {
		return this.activePlayerProperty().get();
	}

	public final void setActivePlayer(final Player activePlayer) {
		this.activePlayerProperty().set(activePlayer);
	}

	public final IntegerProperty cardsToDrawProperty() {
		return this.cardsToDraw;
	}

	public final int getCardsToDraw() {
		return this.cardsToDrawProperty().get();
	}

	public final void setCardsToDraw(final int cardsToDraw) {
		this.cardsToDrawProperty().set(cardsToDraw);
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isInverse() {
		return this.inverse;
	}

	public static void loadSpecialCards(Deck deck, Class<? extends LudopatApp> class1) {

		// Cargamos las cartas especiales
		ArrayList<Card> deckCards = deck.getCards();

		// Para las cartas especiales de cambio de color
		Suit s = new Suit();
		s.setName("blue");
		String urlImage = "/ui/images/dos/special/dos_special_changeblue.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_BLUE);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.setSuit(s);
			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		s = new Suit();
		s.setName("green");
		urlImage = "/ui/images/dos/special/dos_special_changegreen.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_GREEN);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.setSuit(s);
			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		s = new Suit();
		s.setName("white");
		urlImage = "/ui/images/dos/special/dos_special_changewhite.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_WHITE);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.setSuit(s);
			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		s = new Suit();
		s.setName("yellow");
		urlImage = "/ui/images/dos/special/dos_special_changeyellow.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_YELLOW);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.setSuit(s);
			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		urlImage = "/ui/images/dos/special/dos_special_block.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_BLOCK);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		urlImage = "/ui/images/dos/special/dos_special_inverse.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_INVERSE);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.getCardMap().put(card.getCardValue(), urlImage);
			deckCards.add(card);
		}

		urlImage = "/ui/images/dos/special/dos_special_plusone.png";
		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_PLUSONE);
			card.setCardImage(new Image(class1.getResource(urlImage).toString()));

			card.getCardMap().put(card.getCardValue(), urlImage);

			deckCards.add(card);
		}
	}

	public void setController(GameControllerDos controller) {
		this.controller = controller;
	}

	@Override
	public void refreshHand() {
		controller.refreshIAHand();
	}

	public Player getLocalPlayer() {
		return localPlayer;
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public Client getClientThread() {
		return clientThread;
	}

	public void setClientThread(Client clientThread) {
		this.clientThread = clientThread;
	}

	public GameControllerDosNET getNETHud() {
		return NETHud;
	}

	public void setNETHud(GameControllerDosNET nETHud) {
		NETHud = nETHud;
	}

}
