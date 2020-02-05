package games;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

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
	 * Información personal del jugador
	 */
	private ObjectProperty<PlayerInfo> playerInfo = new SimpleObjectProperty<>();
	
	/**
	 * Actual mano del jugador
	 */
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	/**
	 * Identificación de turno del jugador
	 */
	private int id;
	
	public Player() {}


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


	public final ObjectProperty<PlayerInfo> playerInfoProperty() {
		return this.playerInfo;
	}
	


	public final PlayerInfo getPlayerInfo() {
		return this.playerInfoProperty().get();
	}
	


	public final void setPlayerInfo(final PlayerInfo playerInfo) {
		this.playerInfoProperty().set(playerInfo);
	}
	
	
	
	
	
}
