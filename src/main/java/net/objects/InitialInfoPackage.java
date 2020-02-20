package net.objects;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * <b>InitialInfoPackage</b> <br>
 * <br>
 * 
 * Paquete de informacion inicial, de principio de partida
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class InitialInfoPackage implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<NET_Player> players;
	private NET_Card cardInTable;
	private int activePlayer;
	private String gameType;
	
	public InitialInfoPackage(ArrayList<NET_Player> players, NET_Card cardInTable, int activePlayer, String gameType) {
		this.players = players;
		this.cardInTable = cardInTable;
		this.activePlayer = activePlayer;
		this.gameType = gameType;
	}

	public ArrayList<NET_Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<NET_Player> players) {
		this.players = players;
	}

	public NET_Card getCardInTable() {
		return cardInTable;
	}

	public void setCardInTable(NET_Card cardInTable) {
		this.cardInTable = cardInTable;
	}

	public int getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	
	
}
