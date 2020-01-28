/**
 * 
 */
package gameslib;

import java.util.ArrayList;

import games.Card;
import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;

/**
 * @author David Fernández Nieves
 *
 */
public class Tres extends Game {

	private int nextDraw = 1;
	private Player activePlayer;
	private boolean inverse = false;

	private final static int SPECIAL_CARDS = 11; // Empezamos por 11 las especiales

	/**
	 * Actual valor de la carta en la mesa
	 */
	private Card currentCard;

	public Tres(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
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

	}

	@Override
	public void throwCard(Card card) {

		// ¿ Es especial ?
		if (card.getCardValue() >= SPECIAL_CARDS) {

			// 11 - Cambio verde, 12 - amarillo - 13 - azul, 14 - blanco
			// 15 - Invertir
			// 16 - Pasar turno
			switch (card.getCardValue()) {

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
			if (currentCard.getSuit() == card.getSuit() || currentCard.getCardValue() == card.getCardValue()) {
				// .........
			}

		}
	}



	// -----------------------------------------------------

	// Métodos personalizados
	// -----------------------------------------------------

	private void sortPlayers() {
		// despues de ordenarlo iguala currentPlayer a el que salga primero kartoffel
	}

	private void mostrarMano() {

	}

	private void generateHand(int numCards) {

	}

	private void showHand() {

	}

	private void hideHand() {

	}

	private void nextTurn() {
		hideHand();
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
		showHand();
	}

	private void drawCard(Player player) {
		int i = 0;
		while (player.getHand().size() < 10 && i < nextDraw) {
			Card card = deck.getCards().get(deck.getCards().size() - 1);
			deck.getCards().remove(deck.getCards().size() - 1);
			i++;
		}
	}

	private void startTurn() {
		showHand();
		checkTable();
	}

	private void checkTable() {

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
		
	}
}
