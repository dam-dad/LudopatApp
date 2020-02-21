package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import games.Card;
import games.Player;
import games.PlayerInfo;
import gameslib.Dos;
import javafx.application.Platform;
import main.LudopatApp;
import net.objects.InfoPackage;
import net.objects.InitialInfoPackage;
import net.objects.NET_Card;
import net.objects.NET_GameRules;
import net.objects.NET_Player;
import net.objects.NET_PlayerInfo;
import net.objects.ServerNewCard;

/**
 * 
 * Cliente asociado a la máquina que se conecta al servidor
 * a través de la red, se conecta a su hilo correspondiente
 * en el servidor a través de ServerClient.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Client implements Runnable {

	private LudopatApp app;
	private String ip;
	private PlayerInfo clientInfo;
	private int clientID;
	private Socket socket;
	private boolean serverExit;
	private boolean endGame;
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
		
		if (socket != null && !socket.isClosed()) {

			// Desconectamos a este cliente
			try {

				dataIn.close();
				dataOut.close();
				socket.close();

			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * Ejecuta la lectura de los mensajes enviados por 
	 * el servidor
	 */
	@SuppressWarnings("unchecked")
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
		    
		    while( true ) {
		    	
		    	
		    	InfoPackage inPkg = (InfoPackage)dataIn.readObject();
		    	
		    	if( inPkg.getInfoByte() == InfoPackage.CLIENT_DISCONNECT ) {
		    		serverExit = true;
		    		break;
		    	}
		    	
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
		    	
		    	else if( inPkg.getInfoByte() == InfoPackage.SERVER_ENDGAME ) {
		    		
		    		ArrayList<NET_Player> netPlayers = (ArrayList<NET_Player>)inPkg.getInfoObject();
		    		ArrayList<Player> players = new ArrayList<Player>();
		    		
		    		for( NET_Player nPlayer : netPlayers ) {
		    			players.add(new Player(nPlayer));
		    		}
		    		
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							((Dos)app.getCurrentGame()).client_receiveEndGame(players); // El cliente recibe una notificación
						}
					});		
					
					// El hilo ha acabado su función
		    		break;
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
		    		Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							String message  = (String)inPkg.getInfoObject();
				    		int senderID = inPkg.getUserID();
				    		((Dos)app.getCurrentGame()).getNETHud().getChat().getMessage(message, senderID);
						}
					});
		    		
		    	} else {
		    		checkSpecificCases(inPkg);
		    	}
		    	
		    	
		    }
			
		} catch( SocketException e ) {
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					app.client_failConnection();
				}
			});
		} catch (IOException | ClassNotFoundException e) {
		} finally {
			
			
			if( socket != null && !socket.isClosed() ) {
				
				try {
					
					socket.close();
					
				} catch (IOException e) {
				}
			}
			
			if( serverExit ) {
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						
						if( app.getCurrentGame() == null )
							app.goMenu();	 // No estamos en el juego, estamos en la sala de espera
						else 
							((Dos)app.getCurrentGame()).client_receiveDisconnect(); // El cliente recibe una notificación
					}
				});
	    		
			}
		}
	}

	// Métodos exclusivos de cada juego
	//----------------------------------------------------------------------------
	
	/**
	 * Revisa las acciones a tomar por parte del mensaje recibido
	 * que sean específicas de cada juego.
	 * 
	 * @param pkg Paquete recibido del servidor
	 */
	private void checkSpecificCases(InfoPackage pkg) {
		
		switch( pkg.getInfoByte() ) {
		
			case InfoPackage.CLIENT_THROWCARD:
				
				Platform.runLater(new Runnable() {	
					@Override
					public void run() {
						
						ServerNewCard newInfo = (ServerNewCard)pkg.getInfoObject();
						
						ArrayList<Card> newHand = new ArrayList<Card>();
						for( NET_Card nCard : newInfo.getCards()) {
							newHand.add(new Card(nCard));
						}
						
						((Dos)app.getCurrentGame()).client_receiveThrowedCard(newHand, new Card(newInfo.getCardInTable()));
					}
				});
				
				break;
				
			case InfoPackage.CLIENT_DRAWCARD:
				
				Platform.runLater(new Runnable() {
					
					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						
						ArrayList<Card> newHand = new ArrayList<Card>();
						for( NET_Card nCard : ((ArrayList<NET_Card>)pkg.getInfoObject()) ) {
							newHand.add(new Card(nCard));
						}
						
						((Dos)app.getCurrentGame()).client_receiveDrawCard(newHand);
					}
				});
				
				break;
				
			case InfoPackage.CLIENT_NEXTTURN:
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						
						int draws = (int)pkg.getInfoObject();
						int id = pkg.getUserID();
						
						((Dos)app.getCurrentGame()).client_receiveNextTurn(id, draws);	
					}
				});
				
			default:
					break;
		}
	}
	
	//----------------------------------------------------------------------------

	// Envios al servidor
	//----------------------------------------------------------------------------
	
	/**
	 * Solicitud del cliente para robar cartas
	 */
	public void sendDrawCard() {
	
		InfoPackage outPkg = new InfoPackage(InfoPackage.CLIENT_DRAWCARD, null);
		
		try {
			
			dataOut.writeObject(outPkg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Solicitud del cliente para tirar la carta
	 * @param indexOfCard Índice de la carta seleccionada de su mano
	 */ 
	public void sendThrowCard(int indexOfCard) {
		
		InfoPackage outPkg = new InfoPackage(InfoPackage.CLIENT_THROWCARD, indexOfCard);
		
		try {
			dataOut.writeObject(outPkg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Envío de mensaje por parte del chat
	 * @param text Contenido del mensaje
	 */
	public void sendMessage(String text) {
		InfoPackage message = new InfoPackage(InfoPackage.CLIENT_SENDMESSAGE, text);
		try {
			dataOut.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Notifica de el paso de turno a un jugador
	 */
	public void sendNextTurn() {
		InfoPackage nextTPkg = new InfoPackage(InfoPackage.CLIENT_NEXTTURN, null);
		try {
			dataOut.writeObject(nextTPkg);
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	//----------------------------------------------------------------------------
	

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

}
