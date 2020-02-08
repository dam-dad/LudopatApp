package gamesAI;

import java.util.ArrayList;

import games.Card;
import games.Player;
import gameslib.Dos;
import javafx.application.Platform;

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
public class Dav_IA_DOS extends Dav_AI implements Runnable {

	/**
	 * Jugador que representa esta IA
	 */
	private Player player;
	
	
	public Dav_IA_DOS(Player player) {
		this.player = player;
	}
	
	public void disconnectAI() {
		setStopAI(true);
	}
	
	@Override
	public void run() {
		
		Dos baseGame = (Dos)getBaseGame();
		
		while(!isStopAI()) {
			
			try {
				
				Thread.sleep(AI_SLEEP);
				// Necesitamos el check de si es nuestro turno o no
				if(isOurTurn()) {
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
						// Elegimos una aleatoria entre las jugables
						int r = (int)(Math.random() * cards.size() );
						Platform.runLater( new Runnable() {		
							@Override
							public void run() {
								baseGame.throwCard(cards.get(r));	
							}
						});
						
						
					} else {
						
						// Entonces tenemos que robar
						Platform.runLater( new Runnable() {
							@Override
							public void run() {
								baseGame.drawCard();
							}
						});
					}
					
					// Antes de pasar turno, esperamos un tiempo
					sleepAI();
					
					setOurTurn(false);
					
					// Pasamos turno
					Platform.runLater( new Runnable() {
						@Override
						public void run() {
							baseGame.nextTurn();			
						}
					});
				}
				
			} catch (InterruptedException e) {
			}
		}
		
	}
	
	public void initTurn() {
		setOurTurn(true);
	}

	@Override
	public void sleepAI() {
		
		try {
			Thread.sleep((long)(Math.random()*3000 + 500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
