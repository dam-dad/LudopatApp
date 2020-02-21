package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import games.PlayerInfo;
import main.LudopatApp;
import net.objects.InfoPackage;
import net.objects.InitialInfoPackage;
import net.objects.NET_Card;
import net.objects.NET_GameRules;
import net.objects.NET_Player;
import net.objects.NET_PlayerInfo;
import net.objects.ServerNewCard;

/**
 * Hilo del cliente del servidor que escuchará y enviará la
 * máquina a la que está conectado
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class ServerClient implements Runnable {

	private Socket socket;
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	private Server roomServer;
	private int userID;
	private PlayerInfo playerInfo;
	private boolean exit;
	private GameServer gameServer;
	


	public ServerClient(Socket socket, Server roomServer, LudopatApp app) {
		this.socket = socket;
		this.roomServer = roomServer;
		
		try {
			
			this.dataIn = new ObjectInputStream(socket.getInputStream());
			this.dataOut = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Lee información que le proporciona el cliente mientras está
	 * conectado con el servidor
	 */
	@Override
	public void run() {
		
		// Este cliente escuchará todo el tiempo lo que le llega del usuario
		// El cierre de la conexión se hace por parte del cliente al 
		// cerrar el socket
		try {
			
			while(true) {
				
				InfoPackage pkg = (InfoPackage)dataIn.readObject();	
				checkByteID(pkg);
			}
			
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		} finally {
			
			if( !exit ) {
				
				// Debemos cerrar la conexión
				if( gameServer != null ) {
					gameServer.sendClientDisconnected(this);
				}
				
				else {
					roomServer.clientDisconnected(this);
				}
				
			}
			
			try {
				
				if( socket != null && !socket.isClosed() ) {
					socket.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Identificación del paquete recibido y procesamiento
	 * @param pkgIn Paquete recibido
	 */
	private void checkByteID(InfoPackage pkgIn) {
		
		switch(pkgIn.getInfoByte()) {
				
			case InfoPackage.CLIENT_CONNECT:
				// Ponemos la información de este usuario
				if( this.playerInfo == null ) {
					// Creamos al usuario
					NET_PlayerInfo netInfo = (NET_PlayerInfo)pkgIn.getInfoObject();
					PlayerInfo player = new PlayerInfo(netInfo);
					player.setUserID(userID);
					setPlayerInfo(player);
					notify_clientConnected();
				}
				break;
			case InfoPackage.CLIENT_SENDMESSAGE:
				gameServer.broadcastMessage((String) pkgIn.getInfoObject(), userID);
				break;
				default:
					// Reservados para casos propios de cada juego
					break;
					
			case InfoPackage.CLIENT_THROWCARD:
					gameServer.clientThrowedCard((int)pkgIn.getInfoObject());
					break;
			
			case InfoPackage.CLIENT_DRAWCARD:
				gameServer.clientTakenCard();
				break;
			
			case InfoPackage.CLIENT_NEXTTURN:
				gameServer.ClientNextTurn();
				break;
			
		}
		
	}
	
	
	// Notificaciones al servidor
	//---------------------------------------------------------------
	
	/**
	 * Notificación al servidor de que este cliente ha establecido
	 * la conexión
	 */ 
	private void notify_clientConnected() { roomServer.clientConnected(this); }
	
	//---------------------------------------------------------------
	
	// Envio a los clientes
	//---------------------------------------------------------------
	/**
	 * Desconectamos al cliente
	 */
	public void sendDisconnected() {
	
		try {
			
			if( !socket.isClosed() ) {
				exit = true;
				dataOut.writeObject( new InfoPackage(InfoPackage.CLIENT_DISCONNECT, null));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void receiveMessage(String message, int id) {
		
		InfoPackage packageMessage = new InfoPackage(InfoPackage.CLIENT_SENDMESSAGE, message, id);
		try {
			dataOut.writeObject(packageMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameSend_nextTurn(int id,int draws) {
		
		InfoPackage packageNextTurn = new InfoPackage(InfoPackage.CLIENT_NEXTTURN, draws, id);
		
		try {
			dataOut.writeObject(packageNextTurn);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	/**
	 * Envio del paquete de información de la sala del servidor al cliente
	 * @param rules Reglas del servidor
	 */
	public void send_clientsInfo(NET_GameRules rules) {
		
		// Enviamos a este cliente información que tiene el servidor acerca
		// de los jugadores conectados y de la sala
		try {
			
			InfoPackage outPkg = new InfoPackage(InfoPackage.CLIENT_SERVERROOMINFO, rules );
			dataOut.writeObject(outPkg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Mandamos la señal de desconexión, por lo que
	 * este cliente va al menú principal.
	 */
	public void send_disconnectClient() {
		
		try {
			dataOut.writeObject(new InfoPackage(InfoPackage.CLIENT_DISCONNECT, null));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envio de los datos de inicio de juego al cliente
	 * @param currentPlayers
	 * @param tableCard
	 * @param activePlayer
	 */
	public void gameSend_initialInfo(ArrayList<NET_Player> currentPlayers, NET_Card tableCard, int activePlayer, String gameType ) {
		
		try {	
			
			// Construimos el paquete de turno
			InitialInfoPackage initialInfo = new InitialInfoPackage(currentPlayers, tableCard, activePlayer, gameType);

			// Tenemos que enviar 3 paquetes
			InfoPackage outPkg = new InfoPackage(InfoPackage.CLIENT_INITIALINFO, initialInfo, userID);
			dataOut.writeObject(outPkg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Envío a los clientes de la nueva carta en la mesa
	 * @param newHand Nueva mano para el jugador que lanzó la carta
	 * @param cardInTable Nueva carta sobre la mesa
	 */
	public void gameSend_newTableCard(ArrayList<NET_Card> newHand, NET_Card cardInTable) {
		
		ServerNewCard newCardPkg = new ServerNewCard(newHand, cardInTable);
		
		try {
			
			dataOut.writeObject(new InfoPackage(InfoPackage.CLIENT_THROWCARD, newCardPkg));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * El cliente ha robado cartas
	 * @param newHand Nueva mano para este cliente
	 */
	public void gameSend_clientCardTaken(ArrayList<NET_Card> newHand) {
		
		try {
			
			dataOut.writeObject(new InfoPackage(InfoPackage.CLIENT_DRAWCARD, newHand));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}


	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}


	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
	public GameServer getGameServer() {
		return gameServer;
	}


	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;
	}

}
