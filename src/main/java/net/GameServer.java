package net;

import java.util.ArrayList;

import games.Card;
import games.Player;
import gameslib.Dos;
import javafx.application.Platform;
import main.LudopatApp;
import net.objects.NET_Card;
import net.objects.NET_Player;
/**
 * <b>GameServer</b> <br>
 * <br>
 * 
 * Servidor de partida donde se aloja la informacion
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class GameServer {

	private ArrayList<ServerClient> clients;
	private LudopatApp app;
	
	public GameServer(ArrayList<ServerClient> clients, LudopatApp app) {
		this.clients = clients;
		this.app = app;
		
		for(ServerClient serverClient : clients) {
			serverClient.setGameServer(this);
		}
	}
	
	protected ArrayList<NET_Player> getNETPlayersConversion(ArrayList<Player> players)  {
		
		ArrayList<NET_Player> netPlayers = new ArrayList<NET_Player>();
		for( Player p : players ) {
			NET_Player nPlayer = new NET_Player(p);
			netPlayers.add(nPlayer);
		}
		
		return netPlayers;
	}
	
	/**
	 * El host ha cerrado el servidor, enviamos
	 * a los clientes la desconexión con el servidor
	 */
	public void closeServer() {
		
		for( ServerClient c : clients ) {
			c.sendDisconnected();
		}
	}
	
	// Envios por parte del servidor
	//-----------------------------------------------------------------------------
	
	/**
	 * Envío de información inicial de la mesa
	 * @param players Jugadores actuales en la mesa
	 * @param currentCard Carta actual sobre la mesa
	 * @param currentPlayer Primer turno
	 * @param gameType Modo de juego
	 */
	public synchronized void sendInitialInfo(ArrayList<Player> players, Card currentCard, int currentPlayer, String gameType) {
		
		NET_Card currentNETCard = new NET_Card(currentCard);
		for( ServerClient c : clients ) {
			c.gameSend_initialInfo(getNETPlayersConversion(players), currentNETCard, currentPlayer, gameType);
		}
	}

	/**
	 * Replica el mensaje enviado por un cliente a los demás
	 * clientes
	 * @param message Mensaje recibido del cliente
	 * @param id ID del cliente que envió el mensaje
	 */
	public synchronized void broadcastMessage(String message, int id) {
		for(ServerClient client : clients) {
			client.receiveMessage(message, id);
		}
	}
	
	/**
	 * Envío a los clientes la nueva carta sobre la mesa
	 * y al cliente que lanzó la carta su nueva mano
	 * @param newHand Nueva mano del cliente que lanzó la carta
	 * @param newCard Nueva carta sobre la mesa
	 */
	public synchronized void sendNewCardTable(ArrayList<Card> newHand, Card newCard) {
		
		ArrayList<NET_Card> newNETHand = new ArrayList<NET_Card>();
		for( Card card : newHand ) {
			newNETHand.add(new NET_Card(card));
		}
		
		for( ServerClient c : clients ) {
			c.gameSend_newTableCard(newNETHand, new NET_Card(newCard));
		}
	}
	
	/**
	 * El cliente ha podido robar cartas
	 * @param newHand Nueva mano para el cliente que ha robado cartas
	 */
	public synchronized void sendCardTaken(ArrayList<Card> newHand) {
		
		
		ArrayList<NET_Card> newNETHand = new ArrayList<NET_Card>();
		for( Card card : newHand ) {
			newNETHand.add(new NET_Card(card));
		}
		
		for( ServerClient c : clients ) {
			c.gameSend_clientCardTaken(newNETHand);
		}
	}
	
	public synchronized void sendNextTurn(int playerId,int cardDraws) {
		
		for(ServerClient c : clients) {
			c.gameSend_nextTurn(playerId, cardDraws);
		}
	}
	
	//-----------------------------------------------------------------------------
	
	// Recepciones en el servidor
	//-----------------------------------------------------------------------------
	public synchronized void sendClientDisconnected(ServerClient client) {
		
		clients.remove(client);
		
		for( ServerClient c : clients ) {
			c.sendDisconnected();
		}
	}
	
	public synchronized void ClientNextTurn() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				((Dos)app.getServerCurrentGame()).server_nextTurn();
				
			}
		});
	}
	/**
	 * Carta lanzada por el cliente
	 * @param cardInHand Índice de la carta en su mano
	 */
	public synchronized void clientThrowedCard(int cardInHand) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				((Dos)app.getServerCurrentGame()).server_throwCard(cardInHand);
			}
		});
		
	}
	
	public synchronized void clientTakenCard() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				((Dos)app.getServerCurrentGame()).sever_drawCard();
				
			}
		});
	}
	
	//-----------------------------------------------------------------------------
}
