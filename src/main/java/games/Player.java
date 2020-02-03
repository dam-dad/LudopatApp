package games;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private IntegerProperty score = new SimpleIntegerProperty();
	
	/**
	 * Nombre del jugador
	 */
	private StringProperty playerName = new SimpleStringProperty();
	
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




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public final IntegerProperty scoreProperty() {
		return this.score;
	}
	


	public final int getScore() {
		return this.scoreProperty().get();
	}
	


	public final void setScore(final int score) {
		this.scoreProperty().set(score);
	}
	


	public final StringProperty playerNameProperty() {
		return this.playerName;
	}
	


	public final String getPlayerName() {
		return this.playerNameProperty().get();
	}
	


	public final void setPlayerName(final String playerName) {
		this.playerNameProperty().set(playerName);
	}
	
	
	
}
