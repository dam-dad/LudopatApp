package net;

import java.util.ArrayList;

import games.Card;
import games.Player;
import net.objects.NET_Card;
import net.objects.NET_Player;

public class GameServer {

	private ArrayList<ServerClient> clients;

	public GameServer(ArrayList<ServerClient> clients) {
		this.clients = clients;
	}
	
	protected ArrayList<NET_Player> getNETPlayersConversion(ArrayList<Player> players)  {
		
		ArrayList<NET_Player> netPlayers = new ArrayList<NET_Player>();
		for( Player p : players ) {
			NET_Player nPlayer = new NET_Player(p);
			netPlayers.add(nPlayer);
		}
		
		return netPlayers;
	}
	
	public synchronized void sendInitialInfo(ArrayList<Player> players, Card currentCard, int currentPlayer, String gameType) {
		
		NET_Card currentNETCard = new NET_Card(currentCard);
		for( ServerClient c : clients ) {
			c.gameSend_initialInfo(getNETPlayersConversion(players), currentNETCard, currentPlayer, gameType);
		}
	}
}
