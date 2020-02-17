package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import games.Card;
import games.Player;
import games.PlayerInfo;
import gameslib.Dos;
import javafx.application.Platform;
import main.LudopatApp;
import net.objects.NET_GameRules;
import net.objects.NET_Player;
import net.objects.NET_PlayerInfo;

public class Client implements Runnable {

	private LudopatApp app;
	private String ip;
	private PlayerInfo clientInfo;
	private int clientID;
	private Socket socket;
	private boolean exit;
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	
	/**
	 * Cliente online, usado en el host
	 * @param app Aplicación principal
	 * @param clientInfo Información del usuario
	 */
	public Client(LudopatApp app, PlayerInfo clientInfo) {
		this.app = app;
		this.clientInfo = clientInfo;
	}
	
	/**
	 * Cliente online, usado cuando se accede de forma remota
	 * @param app Aplicación principal
	 * @param ip IP del servidor destino
	 * @param clientInfo Información del usuario
	 */
	public Client(LudopatApp app, String ip, PlayerInfo clientInfo) {
		this.clientInfo = clientInfo;
		this.app = app;
		this.ip = ip;
	}
	
	/**
	 * Desconexión de este cliente con el servidor
	 */
	public void disconnectClient() {
		exit = true;
		
		if( socket != null && !socket.isClosed() ) {
			
			try {
				
				// Notificamos al servidor de que nos desconectamos
				dataOut.writeObject(new InfoPackage(InfoPackage.CLIENT_DISCONNECT, null));
				
			} catch (IOException e1) {
			}
			
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	public void run() {
		
		// En esta parte nos encargaremos de escuchar
		socket = new Socket();
		InetSocketAddress addr;
		
		// Debemos conectar con el servidor
		if( ip == null )
			addr = new InetSocketAddress(Server.PORT); // Nuestro host
		else 
			addr = new InetSocketAddress(ip, Server.PORT); // La ip del servidor
		
		try {
			
			socket.connect(addr);
		   
		    dataOut = new ObjectOutputStream(socket.getOutputStream());
		    
		    NET_PlayerInfo netInfo = new NET_PlayerInfo(clientInfo);
		    InfoPackage infoPkg = new InfoPackage(InfoPackage.CLIENT_CONNECT, netInfo);
		    dataOut.writeObject(infoPkg);	    
		    
		    dataIn = new ObjectInputStream(socket.getInputStream());
		    
		    while( !exit ) {
		    	
		    	
		    	InfoPackage inPkg = (InfoPackage)dataIn.readObject();
		    	
		    	if( inPkg.getInfoByte() == InfoPackage.CLIENT_SERVERROOMINFO ) {
		    		
		    		
		    		// Visualizamos la interfaz y la actuallizamos
					// Avisamos de que se necesita crear la interfaz
				    Platform.runLater(new Runnable() {	
						@Override
						public void run() {
							
							NET_GameRules rules = (NET_GameRules)inPkg.getInfoObject();
							ArrayList<NET_PlayerInfo> netPlayers = rules.getPlayersInfo();
							ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
							for( NET_PlayerInfo np : netPlayers ) {
								players.add(new PlayerInfo(np));
							}
						
							app.refreshRoomView(players, rules.getIp());	
						}
					});
		    	} 
		    	
		    	else if( inPkg.getInfoByte() == InfoPackage.CLIENT_INITIALINFO ) {
		    		
		    		setClientID(inPkg.getUserID());
		    		
		    		Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							
							// Entonces podemos empezar con el juego
							InitialInfoPackage iPkg = (InitialInfoPackage)inPkg.getInfoObject();
							
							ArrayList<Player> players = new ArrayList<Player>();
							for( NET_Player p : iPkg.getPlayers() ) {
								players.add(new Player(p));
							}
							
							app.client_startGame(players,
												new Card(iPkg.getCardInTable()),
												iPkg.getActivePlayer(), 
												inPkg.getUserID(),
												iPkg.getGameType());
						}
					});
		    	}else if (inPkg.getInfoByte() == InfoPackage.CLIENT_SENDMESSAGE ) {
		    		String message  = (String)inPkg.getInfoObject();
		    		int senderID = inPkg.getUserID();
		    		((Dos)app.getCurrentGame()).getNETHud().getChat().getMessage(message, senderID);
		    	}
		    	
		    	
		    }
			
		} catch (IOException | ClassNotFoundException e) {
			
			if( !exit ) {
				disconnectClient();	
			}
			
		} finally {
			
			if( socket != null && !socket.isClosed() ) {
				try {
					socket.close();
					
				} catch (IOException e) {
				}
			}
		}
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public void sendMessage(String text) {
		InfoPackage message = new InfoPackage(InfoPackage.CLIENT_SENDMESSAGE, text);
		try {
			dataOut.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
