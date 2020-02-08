package gamesAI;

import java.util.ArrayList;

import games.Card;
import games.Game;
import games.Player;
import gameslib.Dos;

/**
 * <b>IA</b>
 * <br><br>
 * 
 * Inteligencia artifical que representa a un jugador en el juego. Su
 * implementación es básica. Está adecuado al juego del DOS
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Dav_IA_DOS {

	/**
	 * Jugador que representa esta IA
	 */
	private Player player;
	
	/**
	 * Juego base
	 */
	private Dos baseGame;
	
	public Dav_IA_DOS(Player player, Dos baseGame) {
		this.player = player;
	}
	
	public void initTurn() {
		
		// Tenemos que añadir un retardo
		ArrayList<Card> cards = new ArrayList<Card>();
		
		// Comprobamos las cartas que puede jugar
		for( Card card : player.getHand() ) {
			
			if( card.isPlayable() ) {
				cards.add(card);
			}
		}
		
		// Vemos si tenemos cartas para jugar
		if( cards.size() > 0 ) {
			int r = (int)(Math.random() * cards.size() );
			baseGame.throwCard(cards.get(r));
			
		} else {
			
			// Entonces tenemos que robar
			baseGame.drawCard();
		}
		
	}
}
