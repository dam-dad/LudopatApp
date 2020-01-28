/**
 * 
 */
package gameslib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	public Tres(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		super(deck, gameRules, currentPlayers);
	}

	// Métodos genéricos
	// -----------------------------------------------------

	@Override
	public void initGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endGame() {
		//ordenar jugadores por numero de cartas, en caso de empate suma de valores, pasarselo a dialogo
		Collections.sort(currentPlayers, new ComparePlayers());
		//dialogo
		
	}

	@Override
	public void throwCard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dealCards() {
		// TODO Auto-generated method stub

	}

	// -----------------------------------------------------

	// Métodos personalizados
	// -----------------------------------------------------

	private void sortPlayers() {
		//despues de ordenarlo iguala currentPlayer a el que salga primero kartoffel
	}

	private void generateHand(int numCards) {

	}

	private void showHand() {

	}

	private void hideHand() {

	}

	private void nextTurn() {
		hideHand();
		if(inverse) {
			if(currentPlayers.indexOf(activePlayer) < 1) {
				activePlayer = currentPlayers.get(currentPlayers.size()-1);
			}
			else {
				activePlayer = currentPlayers.get(currentPlayers.indexOf(activePlayer)-1);
			}
			
		}else {
			if (currentPlayers.indexOf(activePlayer) == currentPlayers.size()-1) {
				activePlayer = currentPlayers.get(0);
			}
			else {
				activePlayer = currentPlayers.get(currentPlayers.indexOf(activePlayer)+1);
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
}
