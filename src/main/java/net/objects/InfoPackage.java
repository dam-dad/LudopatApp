package net.objects;

import java.io.Serializable;
/**
 * <b>InfoPackage</b> <br>
 * <br>
 * 
 * Paquete de información serializable enviado por la aplicación en red
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class InfoPackage implements Serializable {

	private static final long serialVersionUID = 1;
	
	// Mapa de bytes
	public static final byte CLIENT_CONNECT = 1;
	public static final byte CLIENT_DISCONNECT = 2;
	public static final byte CLIENT_SERVERROOMINFO = 3;
	public static final byte CLIENT_INITIALINFO = 4;
	public static final byte CLIENT_THROWCARD = 5;
	public static final byte CLIENT_NEXTTURN = 6;
	public static final byte CLIENT_DRAWCARD = 7;
	public static final byte CLIENT_SENDMESSAGE = 8;
	
	/**
	 * Byte utilizado para informar de que tipo de paquete se trata
	 */
	private byte infoByte;
	/**
	 * Objeto de información a enviar
	 */
	private Object infoObject;
	/*
	 * Identificacion del usuario que envía el paquete
	 */
	private int userID;
	
	public InfoPackage(byte infoByte, Object infoObject) {
		this.infoByte = infoByte;
		this.infoObject = infoObject;
	}
	
	public InfoPackage(byte infoByte, Object infoObject, int userID) {
		this.infoByte = infoByte;
		this.infoObject = infoObject;
		this.userID = userID;
	}

	public byte getInfoByte() {
		return infoByte;
	}

	public void setInfoByte(byte infoByte) {
		this.infoByte = infoByte;
	}

	public Object getInfoObject() {
		return infoObject;
	}

	public void setInfoObject(Object infoObject) {
		this.infoObject = infoObject;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
