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
	private int avatarIndex;
	
	public NET_PlayerInfo(String playerName, int avatarIndex) {
		this.playerName = playerName;
		this.avatarIndex = avatarIndex;
	}
	
	public NET_PlayerInfo(String playerName, int avatarIndex, int userID) {
		this.playerName = playerName;
		this.userID = userID;
		this.avatarIndex = avatarIndex;
	}
	
	public NET_PlayerInfo(PlayerInfo playerInfo) {
		setPlayerName(playerInfo.getPlayerName());
		setAvatarIndex(playerInfo.getAvatarIndex());
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

	public int getAvatarIndex() {
		return avatarIndex;
	}

	public void setAvatarIndex(int avatarIndex) {
		this.avatarIndex = avatarIndex;
	}
	
}
