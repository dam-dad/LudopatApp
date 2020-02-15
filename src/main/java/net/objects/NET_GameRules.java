package net.objects;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Reglas de juego del servidor para pasar a los clientes
 * con información de la ip del servidor y de los jugadores
 * conectados
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class NET_GameRules implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la versión online de las reglas de juego
	 * @param playersInfo Información de los jugadores
	 * @param ip IP del servidor
	 */
	public NET_GameRules(ArrayList<NET_PlayerInfo> playersInfo, String ip) {
		setPlayersInfo(playersInfo);
		setIp(ip);
	}
	
	public ArrayList<NET_PlayerInfo> getPlayersInfo() {
		return playersInfo;
	}

	public void setPlayersInfo(ArrayList<NET_PlayerInfo> playersInfo) {
		this.playersInfo = playersInfo;
	}

	private ArrayList<NET_PlayerInfo> playersInfo = new ArrayList<NET_PlayerInfo>();
	

	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	private String gameType;

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	
}
