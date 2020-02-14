package net.objects;

import java.io.Serializable;

import games.PlayerInfo;

/**
 * Versión del PlayerInfo Serializable para comunicación online
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class NET_PlayerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String playerName;
	private String urlImage;
	private int userID;
	
	public NET_PlayerInfo(String playerName, String urlImage) {
		this.playerName = playerName;
		this.urlImage = urlImage;
	}
	
	public NET_PlayerInfo(String playerName, String urlImage, int userID) {
		this.playerName = playerName;
		this.urlImage = urlImage;
		this.userID = userID;
	}
	
	public NET_PlayerInfo(PlayerInfo playerInfo) {
		setPlayerName(playerInfo.getPlayerName());
		setUrlImage(playerInfo.getUrlResourceImage());
		setUserID(playerInfo.getUserID());
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getUrlImage() {
		return urlImage;
	}
	
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
