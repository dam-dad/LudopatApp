package games;

import java.util.ArrayList;

import javafx.scene.image.Image;
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
	private Image playerIcon;
	
	/**
	 * Actual mano del jugador
	 */
	private ArrayList<Card> hand;
	
	/**
	 * Identificación de turno del jugador
	 */
	private int id;
	
	public Player() {}

	
	public Player(int puntuacion, String playerName, Image playerIcon) {
		this.score = puntuacion;
		this.playerName = playerName;
		this.playerIcon = playerIcon;
	}


	public Image getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(Image playerIcon) {
		this.playerIcon = playerIcon;
	}


	public ArrayList<Card> getHand() {
		return hand;
	}


	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
}
