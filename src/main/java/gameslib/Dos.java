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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Dos extends Game {

	private int nextDraw = 1;
	private Player activePlayer;
	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}


	private boolean inverse = false;

	private final int SPECIAL_CARDS = 11; // Empezamos por 11 las especiales

	public final static int SPECIAL_INVERSE = 15;
	public final static int SPECIAL_BLOCK = 16;
	public final static int SPECIAL_PLUSONE = 17;
	public final static int SPECIAL_CHANGE_BLUE = 11;
	public final static int SPECIAL_CHANGE_YELLOW = 12;
	public final static int SPECIAL_CHANGE_WHITE = 13;
	public final static int SPECIAL_CHANGE_GREEN = 14;
	
	
	/**
	 * Actual valor de la carta en la mesa
	 */
	private int currentValue;
	private StringProperty currentColor = new SimpleStringProperty();
	private ObjectProperty<Card> lastCard = new SimpleObjectProperty<Card>();

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	

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
		// Virar
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
		lastCard.set(card);
		// ¿ Es especial ?
		if (card.getCardValue() >= SPECIAL_CARDS) {

			// 11 - Cambio verde, 12 - amarillo - 13 - azul, 14 - blanco
			// 15 - Invertir
			// 16 - Pasar turno
			switch (card.getCardValue()) {
			case 11:
				setCurrentColor("green");
				break;
			case 12:
				setCurrentColor("yellow");
				break;
			case 13:
				setCurrentColor("blue");
				break;
			case 14:
				setCurrentColor("white");
				break;
			case 15:
				inverse = true;
				break;
			case 16:
				nextTurn();
				break;
			case 17:
				nextDraw += 1;
				break;
			default:
				break;
			}
		} else {

			// Mismo color o número
			if (currentColor.get() == card.getSuit().getName() || currentValue == card.getCardValue()) {
				currentColor.set(card.getSuit().getName());;
				currentValue = card.getCardValue();
			}

		}
	}

	// -----------------------------------------------------

	// Métodos personalizados
	// -----------------------------------------------------

	private void sortPlayers() {
		Collections.shuffle(this.currentPlayers);
	}


	public void nextTurn() {
		if (inverse) {
			if (currentPlayers.indexOf(activePlayer) < 1) {
				activePlayer = currentPlayers.get(currentPlayers.size() - 1);
			} else {
				activePlayer = currentPlayers.get(currentPlayers.indexOf(activePlayer) - 1);
			}

		} else {
			if (currentPlayers.indexOf(activePlayer) == currentPlayers.size() - 1) {
				activePlayer = currentPlayers.get(0);
			} else {
				activePlayer = currentPlayers.get(currentPlayers.indexOf(activePlayer) + 1);
			}
		}
	}

	public void drawCard() {
		int i = 0;
		while (deck.getCards().size() > 0 && activePlayer.getHand().size() < 10 && i < nextDraw) {
			Card card = deck.getCards().get(deck.getCards().size() - 1);
			deck.getCards().remove(deck.getCards().size() - 1);
			activePlayer.getHand().add(card);
			i++;
		}
		if (deck.getCards().size() <= 0){
			endGame();
		}
	}

	public void startTurn() {
		//Comprobar carta a carta y reemplazarla.
		for( Card card : activePlayer.getHand() ) {
			checkTable(card);
		}
	}

	public void checkTable(Card currentCard) {
		
		if (currentValue != currentCard.getCardValue()) {
			currentCard.setPlayable(true);
		}else {
			if (currentColor.get() == currentCard.getSuit().getName()) {
				currentCard.setPlayable(true);
			}else {
				if (currentCard.getCardValue() >= this.SPECIAL_CARDS) {
					currentCard.setPlayable(true);
				}else {
					currentCard.setPlayable(false);
				}
			}
		}
	}
	// -----------------------------------------------------

	@Override
	public void dealCards() {

		int numCartas = gameRules.getDeckType().getNumCards();
		/* Falta comprobar si es baraja doble */

		for (Player p : currentPlayers) {

			ArrayList<Card> mano = new ArrayList<Card>(numCartas);

			for (int i = 0; i < numCartas; i++) {

				// Añadimos carta al usuario y se lo quitamos a la baraja
				Card card = deck.getCards().remove(deck.getCards().size() - 1);
				mano.add(card);
			}
		}
		Card card;
		while ((card = deck.getCards().remove(deck.getCards().size()-1)).getCardValue() >= SPECIAL_CARDS) {
			deck.getCards().add(0, card);
		}		
		currentValue = card.getCardValue();
		currentColor.set(card.getSuit().getName());
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
	
	
}
