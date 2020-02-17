package net;

import java.io.Serializable;

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
	
	private byte infoByte;
	
	private Object infoObject;

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
