package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import games.PlayerInfo;
import main.LudopatApp;
import net.objects.NET_Card;
import net.objects.NET_GameRules;
import net.objects.NET_Player;
import net.objects.NET_PlayerInfo;

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
	
//	private LudopatApp app;
	
	public ServerClient(Socket socket, Server roomServer, LudopatApp app) {
		this.socket = socket;
		this.roomServer = roomServer;
	//	this.app = app;
		
		try {
			this.dataIn = new ObjectInputStream(socket.getInputStream());
			this.dataOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	/**
	 * Desconexión de este cliente con el servidor.
	 * Método llamado desde el propio servidor
	 */
	public void disconnectClient() {
		
		exit = true;
		
		if( socket != null && !socket.isClosed() ) {
			
			try {
				socket.close();
				
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * Lee información que le proporciona el cliente mientras está
	 * conectado con el servidor
	 */
	@Override
	public void run() {
		
		// Este cliente escuchará todo el tiempo lo que le llega del usuario
		try {
			
			while(!exit) {
				
				InfoPackage pkg = (InfoPackage)dataIn.readObject();
				
				// Ahora necesitamos ver que nos ha enviado el cliente
				if( !checkByteID(pkg) ) {
					// Si ha llegado hasta aquí indica que debemos terminar
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		} finally {
			
			if( !exit ) {
				// Debemos cerrar la conexión
				roomServer.clientDisconnected(this);
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
	 * @return True si es correcto, False implica el cierre de conexión
	 */
	private boolean checkByteID(InfoPackage pkgIn) {
		
		switch(pkgIn.getInfoByte()) {
		// El usuario se ha desconectado
		case InfoPackage.CLIENT_DISCONNECT:
			return false;
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
			default:
				// Reservados para casos propios de cada juego
				break;
		}
		
		return true;
		
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

}
