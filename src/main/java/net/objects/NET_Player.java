package net.objects;

import java.io.Serializable;
import java.util.ArrayList;

import games.Card;
import games.Player;
/**
 * <b>NET_Player</b> <br>
 * <br>
 * 
 * Personaje jugable dentro del juego que tiene información basica sobre 
 * el estado de la partida
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class NET_Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private NET_PlayerInfo playerInfo;
	private ArrayList<NET_Card> hand = new ArrayList<NET_Card>();
	private int id;
	
	
	public NET_Player() {}

	public NET_Player(Player player) {
		
		setPlayerInfo(new NET_PlayerInfo(player.getPlayerInfo()));
		
		ArrayList<NET_Card> nCards = new ArrayList<NET_Card>();
		
		for( Card c : player.getHand() ) {
			nCards.add(new NET_Card(c));
		}
		
		setHand(nCards);
		setId(player.getId());
	}
	
	public NET_PlayerInfo getPlayerInfo() {
		return playerInfo;
	}


	public void setPlayerInfo(NET_PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}


	public ArrayList<NET_Card> getHand() {
		return hand;
	}


	public void setHand(ArrayList<NET_Card> hand) {
		this.hand = hand;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
