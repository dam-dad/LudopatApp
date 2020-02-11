package gamesAI;

import games.Game;

/**
 * <b>IA Base</b>
 * <br><br>
 * 
 * Inteligencia artifical que representa a un jugador en el juego.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */

public abstract class Dav_AI implements Runnable {
	
	/**
	 * Tiempo de decisión de la IA
	 */
	protected static final long AI_SLEEP = 2000;
	
	/**
	 * Valores de toma de decisión de la IA
	 */
	protected boolean ourTurn;
	
	/**
	 * Para desconectar a la inteligencia artifical
	 */
	protected boolean stopAI;
	
	/**
	 * Juego base
	 */
	protected Game baseGame;
	
	/**
	 * Turno del jugador, se especifica lo que debe hacer
	 */
	public abstract void initTurn();
	
	/**
	 * Ofrece un retardo en la ejecución de la acción
	 * en la IA
	 */
	public abstract void sleepAI();
	
	public Game getBaseGame() {
		return baseGame;
	}

	public void setBaseGame(Game baseGame) {
		this.baseGame = baseGame;
	}

	public void setStopAI(boolean stopAI) {
		this.stopAI = stopAI;
	}

	public boolean isStopAI() {
		return stopAI;
	}

	public boolean isOurTurn() {
		return ourTurn;
	}

	public void setOurTurn(boolean ourTurn) {
		this.ourTurn = ourTurn;
	}
}
