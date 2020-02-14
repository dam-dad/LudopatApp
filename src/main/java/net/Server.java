package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import games.GameRules;
import games.PlayerInfo;
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
	
	private ArrayList<ServerClient> clients;
	private ArrayList<PlayerInfo> currentPlayers = new ArrayList<PlayerInfo>();
	private String serverIP;
	
	private GameRules serverGameRules;
	
	private ServerSocket svSocket;
	
	private int clientID;
	
	private boolean exit;
	
	private LudopatApp app;
	
	public Server(LudopatApp app, String serverIP) {
		this.app = app;
		this.serverIP = serverIP;
		serverGameRules = app.getGameRules();
	}
	
	/**
	 * Cierre del servidor para no permitir más conexiones
	 */
	public void closeRoom() {
		this.exit = true;
		
		if( !svSocket.isClosed() ) {
			
			try {
				svSocket.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * Se aceptan las conexiones de los clientes
	 */
	@Override
	public void run() {
		
		// Arrancamos el servidor
		svSocket = null;
		InetSocketAddress addr = new InetSocketAddress(PORT);
		clients = new ArrayList<>();
	    clientID = 0; // Nos indica además el orden de este cliente
		
		try {
			
			svSocket = new ServerSocket();
			svSocket.bind(addr);
			
			while( !exit ) {
				
				// Esperamos a que alguien se conecte
				Socket clientSocket = svSocket.accept();
				
				// Creamos el enlace con este usuario
				ServerClient client = new ServerClient(clientSocket, this, app);
				clients.add(client);
				client.setUserID(clientID++);
				
				// Empezamos a escuchar a este cliente
				new Thread(client).start();
			}
			
		} catch (Exception e) {
		} finally {
			
			if( svSocket != null && !svSocket.isClosed() ) {
				try {
					svSocket.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	// Interacción con el cliente
	//---------------------------------------------------------------
	
	/**
	 * Se ha conectado un cliente
	 * @param client Cliente recién conectado
	 */
	public synchronized void clientConnected(ServerClient client) {
	
		// Añadimos la información de este jugador al servidor
		currentPlayers.add(client.getPlayerInfo());
		
		for (ServerClient c : clients) {

			// Refrescamos los datos de los clientes, volvemos a enviar la informacion
			// a todos para que puedan refrescar la interfaz.
			
			ArrayList<NET_PlayerInfo> netPlayers = new ArrayList<NET_PlayerInfo>();
			currentPlayers.stream().forEach(p -> {
					netPlayers.add(new NET_PlayerInfo(p.getPlayerName(), p.getUrlResourceImage(), p.getUserID()));
			});
			
			NET_GameRules rules = new NET_GameRules(netPlayers, serverIP);
			c.send_clientsInfo(rules);
		}
		
		// Cuando lleguemos al máximo de jugadores
		if( currentPlayers.size() == serverGameRules.getNumPlayers() ) {
			System.out.println("Sala llena");
		//	closeRoom();
			
			/*
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					app.room_startGame(currentPlayers, clients);		
				}
			});
			*/
			
		}
	}
	
	public synchronized void clientDisconnected(ServerClient client) {
		
		// Eliminamos el cliente de la lista
		clients.remove(client);

		currentPlayers.remove(client.getPlayerInfo());
		
		clientID--;
		
		// Verificamos que hay clientes
		if( clients.size() <= 0 ) {
			// ADD : Tenemos que cerrar las conexiones con los clientes
			// para avisar de que se ha cerrado la sala
			closeRoom(); // Cerramos la sala
			
		} else {
			
			// Informamos al resto de clientes
			for( ServerClient c : clients ) {
				c.send_clientDisconnect(client.getUserID());
			}
		}
	}
	
	//---------------------------------------------------------------

}
