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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import main.LudopatApp;

/**
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Dos extends Game {

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
	

	public Dos(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		super(deck, gameRules, currentPlayers);
	}

	// Métodos genéricos
	// -----------------------------------------------------

	@Override
	public void initGame() {

		deck.shuffle(); // Barajamos
		sortPlayers();
		dealCards();
	}

	@Override
	public void endGame() {
		// ordenar jugadores por numero de cartas, en caso de empate suma de valores,
		// pasarselo a dialogo
		Collections.sort(currentPlayers, new ComparePlayers());
		// dialogo

	}
	
	@Override
	public void throwCard(Card card) {
		
		// ¿ Es especial ?
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
				if( inverse ) {
					inverse = false;
				} else {
					inverse = true;
				}
				break;
			case SPECIAL_BLOCK:
				isBlocked = true;
				break;
			case SPECIAL_PLUSONE:
				setCardsToDraw(getCardsToDraw()+1);
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
	}

	// -----------------------------------------------------

	// Métodos personalizados
	// -----------------------------------------------------

	private void sortPlayers() {
		Collections.shuffle(this.currentPlayers);
		setActivePlayer(currentPlayers.get(0));
	}


	public void nextTurn() {
		
		if (inverse) {
			
			if (currentPlayers.indexOf(getActivePlayer()) == 0) {
				setActivePlayer( currentPlayers.get(currentPlayers.size() - 1));
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
	}

	public void drawCard() {
		int i = 0;
		while (deck.getCards().size() > 0 && getActivePlayer().getHand().size() < 10 && i < getCardsToDraw()) {
			Card card = deck.getCards().remove(deck.getCards().size() - 1);
			getActivePlayer().getHand().add(card);
			i++;
		}
		
		setCardsToDraw(1);
		
		if (deck.getCards().size() <= 0){
			endGame();
		}
	}

	public void startTurn() {
		//Comprobar carta a carta y reemplazarla.
		for( Card card : getActivePlayer().getHand() ) {
			checkTable(card);
		}
	}

	public void checkTable(Card currentCard) {
		
		if( currentCard.getCardValue() >= this.SPECIAL_CARDS ) {
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

	@Override
	public void dealCards() {
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
		while ((card = deck.getCards().remove(deck.getCards().size()-1)).getCardValue() >= SPECIAL_CARDS) {
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

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_BLUE);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_changeblue.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_GREEN);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_changegreen.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_WHITE);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_changewhite.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_YELLOW);
			card.setCardImage(new Image(
					class1.getResource("/ui/images/dos/special/dos_special_changeyellow.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_BLOCK);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_block.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_INVERSE);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_inverse.png").toString()));

			deckCards.add(card);
		}

		for (int i = 0; i < 2; i++) {

			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_PLUSONE);
			card.setCardImage(
					new Image(class1.getResource("/ui/images/dos/special/dos_special_plusone.png").toString()));

			deckCards.stream().forEach(node -> {

			});
			
			deckCards.add(card);
		}
	}
	
	
}
