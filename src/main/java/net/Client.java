package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import games.Card;
import games.Game;
import games.GameRules;
import games.Player;
import games.PlayerInfo;
import gameslib.Dos;
import javafx.application.Platform;
import main.LudopatApp;
import net.objects.NET_GameRules;
import net.objects.NET_PlayerInfo;

public class Client implements Runnable {

	private LudopatApp app;
	private String ip;
	private PlayerInfo clientInfo;
	private int clientID;
	private Socket socket;
	
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
		   
		    dataIn = new ObjectInputStream(socket.getInputStream());
		    dataOut = new ObjectOutputStream(socket.getOutputStream());
		    
		    NET_PlayerInfo netInfo = new NET_PlayerInfo(clientInfo);
		    InfoPackage infoPkg = new InfoPackage(InfoPackage.CLIENT_CONNECT, netInfo);
		    dataOut.writeObject(infoPkg);	    
		    
		    while( true ) {
		    	
		    	InfoPackage inPkg = (InfoPackage)dataIn.readObject();
		    	
		    	if( inPkg.getInfoByte() == InfoPackage.CLIENT_SERVERROOMINFO ) {
		    		
		    		
		    		// Visualizamos la interfaz y la actuallizamos
					// Avisamos de que se necesita crear la interfaz
				    Platform.runLater(new Runnable() {	
						@Override
						public void run() {
							
							@SuppressWarnings("unchecked")
							ArrayList<NET_PlayerInfo> netPlayers = (ArrayList<NET_PlayerInfo>)inPkg.getInfoObject();
							ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
							for( NET_PlayerInfo np : netPlayers ) {
								players.add(new PlayerInfo(np));
							}
						
							app.refreshRoomView(players);	
						}
					});
		    	}
		    }
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			
			if( socket != null && !socket.isClosed() ) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
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

}
