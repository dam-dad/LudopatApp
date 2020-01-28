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
	
	private void ordenarJugadores() {
	}
	
	private void generarMano(int mumCartas) {
		
	}
	
	private void mostrarMano() {
		
	}
	
	private void ocultarMano() {
		
	}
	
	private void pasarTurno() {
		ocultarMano();
	}
	
	private void robar() {
		
	}
	
	private void iniciarTurno() {
		mostrarMano();
		comprobarMesa();
	}
	
	private void comprobarMesa() {
		

	}
	// -----------------------------------------------------
}
