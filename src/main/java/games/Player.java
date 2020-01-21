package games;

import javafx.scene.image.ImageView;

/**
 * 
 * <b>Jugador</b>
 * </br></br>
 * Personaje jugable del juego que contiene información básica
 * del cliente en el juego
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 * 
 */
public class Player {

	/**
	 * Puntuación del jugador
	 */
	private int score;
	
	/**
	 * Nombre del jugador
	 */
	private String playerName;
	
	/**
	 * Icono del jugador en el juego
	 */
	ImageView playerIcon;
	
	public Player() {}

	
	public Player(int puntuacion, String nombreJugador, ImageView playerIcon) {
		this.score = puntuacion;
		this.playerName = nombreJugador;
		this.playerIcon = playerIcon;
	}


	public int getPuntuacion() {
		return score;
	}

	public void setPuntuacion(int puntuacion) {
		this.score = puntuacion;
	}

	public String getNombreJugador() {
		return playerName;
	}

	public void setNombreJugador(String nombreJugador) {
		this.playerName = nombreJugador;
	}

	public ImageView getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(ImageView playerIcon) {
		this.playerIcon = playerIcon;
	}
	
	
}