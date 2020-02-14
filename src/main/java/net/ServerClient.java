package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import games.PlayerInfo;
import javafx.scene.image.Image;
import main.LudopatApp;
import net.objects.NET_GameRules;
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
//	private LudopatApp app;
	
	public ServerClient(Socket socket, Server roomServer, LudopatApp app) {
		this.socket = socket;
		this.roomServer = roomServer;
	//	this.app = app;
		
		try {
			
			this.dataIn = new ObjectInputStream(socket.getInputStream());
			this.dataOut = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		
		// Este cliente escuchará todo el tiempo lo que le llega del usuario
		try {
			
			while(true) {
				
				InfoPackage pkg = (InfoPackage)dataIn.readObject();
				
				// Ahora necesitamos ver que nos ha enviado el cliente
				if( !checkByteID(pkg) ) {
					// Si ha llegado hasta aquí indica que debemos terminar
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			// Debemos cerrar la conexión
			roomServer.clientDisconnected(this);
			
			try {
				
				if( socket != null && !socket.isClosed() ) {
					socket.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Métodos del propio cliente
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
				PlayerInfo player = new PlayerInfo();
				player.setUserID(userID);
				player.setUrlResourceImage(netInfo.getUrlImage());
				player.setPlayerIcon(new Image(getClass().getResource(netInfo.getUrlImage()).toString()));
				player.setPlayerName(netInfo.getPlayerName());
				setPlayerInfo(player);
				notify_clientConnected();
			}
			
			break;
			default:
				break;
		}
		
		return true;
		
	}
	
	private void notify_clientConnected() {
		
		// El cliente nos ha enviado su información, tenemos
		// que comunicarlo al resto de clientes
		roomServer.clientConnected(this);
	}
	
	// Métodos llamados del servidor
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
	
	public void send_clientDisconnect(int userID) {

		try {
			
			InfoPackage outPkg = new InfoPackage(InfoPackage.CLIENT_DISCONNECT, userID );
			dataOut.writeObject(outPkg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

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
