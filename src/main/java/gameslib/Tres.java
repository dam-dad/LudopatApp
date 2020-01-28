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
		// TODO Auto-generated method stub
		
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
	}
	
	private void generateHand(int numCards) {
		
	}
	
	private void showHand() {
		
	}
	
	private void hideHand() {
		
	}
	
	private void nextTurn() {
		hideHand();
	}
	
	private void drawCard() {
		
	}
	
	private void iniciarTurno() {
		showHand();
		checkTable();
	}
	
	private void checkTable() {
		

	}
	// -----------------------------------------------------
}
