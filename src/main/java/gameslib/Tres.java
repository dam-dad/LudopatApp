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

	private final static int MAX_CARDS = 10;
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
		ordenarJugadores();
		dealCards();
		// Virar
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void throwCard(Card card) {
		
		// ¿ Es especial ?
		if( card.getCardValue() >= SPECIAL_CARDS ) {
			
			// 11 - Cambio verde, 12 - amarillo - 13 - azul, 14 - blanco
			// 15 - Invertir
			// 16 - Pasar turno
			switch (card.getCardValue()) {

			case 15:
				inverse = true;
				break;
			case 16:
				nextTurn = true;
				break;
			case 17:
				nextDraw += 1;
				break;
			default:
				break;
			}
		} else {
			
			// Mismo color o número
			if( currentCard.getSuit() == card.getSuit() ||
				currentCard.getCardValue() == card.getCardValue() ) {
				// .........
			}
			
		}
	}

	@Override
	public void dealCards() {
		
		int numCartas = gameRules.getDeckType().getNumCards();
		/* Falta comprobar si es baraja doble */
		
		for( Player p : currentPlayers ) {
			
			ArrayList<Card> mano = new ArrayList<Card>(numCartas);
			
			for( int i = 0; i < numCartas; i++ ) {
				
				// Añadimos carta al usuario y se lo quitamos a la baraja
				Card card = deck.getCards().remove(deck.getCards().size()-1);
				mano.add(card);
			}
		}
		
	}

	// -----------------------------------------------------
	
	// Métodos personalizados
	// -----------------------------------------------------
	
	private void ordenarJugadores() {
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
