package games;

import java.util.ArrayList;

import gamesAI.Dav_AI;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.objects.NET_Card;
import net.objects.NET_Player;
import statistics.PlayerStatistic;

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
	 * Jugador manejado por la inteligencia artificial
	 */
	private boolean isAI;
	
	/**
	 * Controlador de la inteligencia artifical
	 */
	private Dav_AI AIController;
	
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
	
	/**
	 * Estadísticas del jugador
	 */
	private PlayerStatistic playerStatistics;
	
	public Player() {}
	
	public Player(NET_Player nPlayer) {
		
		for( NET_Card card : nPlayer.getHand() ) {
			hand.add(new Card(card));
		}
		
		setId(nPlayer.getId());
		setPlayerInfo(new PlayerInfo(nPlayer.getPlayerInfo()));
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
	
	public final ObjectProperty<PlayerInfo> playerInfoProperty() {
		return this.playerInfo;
	}
	
	public final PlayerInfo getPlayerInfo() {
		return this.playerInfoProperty().get();
	}
	
	public final void setPlayerInfo(final PlayerInfo playerInfo) {
		this.playerInfoProperty().set(playerInfo);
		//Creamos el playerStatistics del jugador
		this.playerStatistics = new PlayerStatistic("#" + this.id, this.playerInfo.get().getPlayerName(), this.playerInfo.get().getPlayerIcon().getUrl());
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}

	public Dav_AI getAIController() {
		return AIController;
	}

	public void setAIController(Dav_AI aIController) {
		AIController = aIController;
	}
	
	public void setStatistics(int points) {
		this.playerStatistics.setPoints(points);
	}
	
	public PlayerStatistic getStatistics() {
		return this.playerStatistics;
	}
	
}
