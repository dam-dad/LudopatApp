package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import games.GameRules;
import games.PlayerInfo;
import javafx.application.Platform;
import main.LudopatApp;
import net.objects.NET_GameRules;
import net.objects.NET_PlayerInfo;

/**
 * Servidor para coger las peticiones de conexión de
 * distintos clientes.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Server implements Runnable {

	public final static int PORT = 5555;
	
	private ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	private ArrayList<PlayerInfo> currentPlayers = new ArrayList<PlayerInfo>();
	private String serverIP;
	private GameRules serverGameRules;
	private ServerSocket svSocket;
	private int clientID;
	private boolean exit;
	private final int countdownTimer = 3000; // 3 segundos antes de empezar la partuda
	
	private LudopatApp app;
	
	
	public Server(LudopatApp app, String serverIP) {
		this.app = app;
		this.serverIP = serverIP;
		serverGameRules = app.getGameRules();
	}
	
	/**
	 * Cierre del servidor para no permitir más conexiones.
	 * @param bForce True cuando se ha producido un error
	 */
	public void closeRoom(boolean bForce) {
		
		this.exit = true;
		
		if( !svSocket.isClosed() ) {
			
			try {
				
				svSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if( bForce ) {
			
			for( ServerClient c : clients ) {
				c.sendDisconnected();
			}
		}
	}
	
	/**
	 * Si el servidor sigue aceptando conexiones o no
	 */
	public boolean isActive() {
		return !exit;
	}
	
	/**
	 * Se aceptan las conexiones de los clientes
	 */
	@Override
	public void run() {
		
		// Arrancamos el servidor
		svSocket = null;
		InetSocketAddress addr = new InetSocketAddress(PORT);
	    clientID = 0; // Nos indica además el orden de este cliente
		
		try {
			
			svSocket = new ServerSocket();
			svSocket.bind(addr);
			
			while( !exit ) {
					
				// Esperamos a que alguien se conecte
				Socket clientSocket = svSocket.accept();

				// Por si se queda arriba esperando....
				if( exit ) {
					break;
				}
				
				// Creamos el enlace con este usuario
				ServerClient client = new ServerClient(clientSocket, this, app);
				// Empezamos a escuchar a este cliente
				client.setUserID(clientID++);
				clients.add(client);
				

				new Thread(client).start();
			}
			
		} catch (Exception e) {

			
		} finally {
			
			if( svSocket != null && !svSocket.isClosed() ) {
				try {
					svSocket.close();
					exit = true;
				} catch (IOException e1) {
				}
			}
		}
		
	}
	
	// Interacción con los clientes
	//---------------------------------------------------------------
	
	/**
	 * Se ha conectado un cliente
	 * @param client Cliente recién conectado
	 */
	public synchronized void clientConnected(ServerClient client) {
	
		// Añadimos la información de este jugador al servidor
		currentPlayers.add(client.getPlayerInfo());
		
		NET_GameRules rules = getRoomInfo();
		
		for( ServerClient c : clients ) {
			c.send_clientsInfo(rules);
		}
		
		// Cuando lleguemos al máximo de jugadores
		if( currentPlayers.size() == serverGameRules.getNumPlayers() ) {
			closeRoom(false);
			
			try {
				
				Thread.sleep(countdownTimer);
				
				// Empezamos el servidor de juego
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						app.server_initGame(currentPlayers, clients);		
					}
				});
				
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * Desconexión de un cliente
	 * @param client Cliente que se ha desconectado
	 */
	public synchronized void clientDisconnected(ServerClient client) {
		
		// Eliminamos el cliente de la lista
		clients.remove(client);
		currentPlayers.remove(client.getPlayerInfo());
		clientID--;

		// Entonces hemos acabado
		if( clients.size() <= 0 ) {
			closeRoom(false);
			return;
		}
		
		// Refrescamos la información, se la enviamos a todos los clientes
		NET_GameRules rules = getRoomInfo();
		
		for( ServerClient c : clients ) {
			c.send_clientsInfo(rules);
		}
		
	}

	//---------------------------------------------------------------
	
	/**
	 * Información de la sala
	 * @return Información de la sala de espera del servidor
	 */
	private NET_GameRules getRoomInfo() {

		ArrayList<NET_PlayerInfo> netPlayers = new ArrayList<NET_PlayerInfo>();
		currentPlayers.stream().forEach(p -> {
			netPlayers.add(new NET_PlayerInfo(p.getPlayerName(), p.getAvatarIndex(), p.getUserID()));
		});

		return new NET_GameRules(netPlayers, serverIP);
	}

}
