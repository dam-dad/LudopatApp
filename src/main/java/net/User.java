package net;

import games.PlayerInfo;

/**
 * Usuario on-line con información del cliente.
 * Usado principalmente para referenciar al jugador cuando
 * no está todavía conectado.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class User {

	private PlayerInfo playerInfo;

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
	
	
}
