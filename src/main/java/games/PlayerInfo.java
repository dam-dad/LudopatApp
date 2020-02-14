package games;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import net.objects.NET_PlayerInfo;

/**
 * 
 * <b>Icono del jugador</b>
 * <br><br>
 * 
 * Información de los distintos jugadores
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class PlayerInfo {

	/**
	 * Icono del jugador
	 */
	private ObjectProperty<Image> playerIcon = new SimpleObjectProperty<Image>(
			new Image(getClass().getResourceAsStream("/ui/images/userNull.png")));
	
	/**
	 * Nombre del jugador
	 */
	private StringProperty playerName = new SimpleStringProperty();

	/**
	 * Ubicación de la imagen del jugador en recursos
	 */
	private String urlResourceImage;
	
	/** 
	 * ID del usuario online
	 */
	private int userID;
	
	public PlayerInfo() {}
	
	/**
	 * Construimos loas datos del jugador a partir de su versión online
	 * @param netPlayer Datos jugador para pasar por sockets
	 */
	public PlayerInfo(NET_PlayerInfo netPlayer) {
		setPlayerName(netPlayer.getPlayerName());
		setUrlResourceImage(netPlayer.getUrlImage());
		setPlayerIcon(new Image(getClass().getResource(netPlayer.getUrlImage()).toString()));
		setUserID(netPlayer.getUserID());
	}
	
	public final ObjectProperty<Image> playerIconProperty() {
		return this.playerIcon;
	}
	
	public final Image getPlayerIcon() {
		return this.playerIconProperty().get();
	}
	
	public final void setPlayerIcon(final Image playerIcon) {
		this.playerIconProperty().set(playerIcon);
	}
	
	public final StringProperty playerNameProperty() {
		return this.playerName;
	}
	
	public final String getPlayerName() {
		return this.playerNameProperty().get();
	}
	
	public final void setPlayerName(final String playerName) {
		this.playerNameProperty().set(playerName);
	}

	public String getUrlResourceImage() {
		return urlResourceImage;
	}

	public void setUrlResourceImage(String urlResourceImage) {
		this.urlResourceImage = urlResourceImage;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
