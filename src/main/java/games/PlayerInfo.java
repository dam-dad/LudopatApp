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

	String[][] avatarsReferences = {
			{ getClass().getResource("/ui/images/avatar_carpenter.png").toString(), "Carpintero" },
			{ getClass().getResource("/ui/images/avatar_doctor.png").toString(), "Doctor" },
			{ getClass().getResource("/ui/images/avatar_lawyer.png").toString(), "Abogado" },
			{ getClass().getResource("/ui/images/avatar_programmer.png").toString(), "Hacker" },
			{ getClass().getResource("/ui/images/avatar_dab.png").toString(), "Dab niño" },
			{ getClass().getResource("/ui/images/avatar_potat.png").toString(), "Potat" },
			{ getClass().getResource("/ui/images/avatar_travolta.png").toString(), "Travolta" },
			{ getClass().getResource("/ui/images/avatar_davIA.png").toString(), "Dav_IA.sad" },
			{ getClass().getResource("/ui/images/userNull.png").toString(), "" },
			{ getClass().getResource("/ui/images/avatar_not_davIA.png").toString(), "Not_Dav_IA" },
			{ getClass().getResource("/ui/images/avatar_francoisIA.png").toString(), "François_IA" },
			{ getClass().getResource("/ui/images/avatar_pirateIA.png").toString(), "Pirate_IA" }
	};
	
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
	
	/**
	 * Índice del avatar
	 */
	private int avatarIndex;
	
	public PlayerInfo() {}
	
	/**
	 * Construimos loas datos del jugador a partir de su versión online
	 * @param netPlayer Datos jugador para pasar por sockets
	 */
	public PlayerInfo(NET_PlayerInfo netPlayer) {
		setPlayerName(netPlayer.getPlayerName());
		setAvatarIndex(netPlayer.getAvatarIndex());
		setPlayerIcon(new Image(avatarsReferences[netPlayer.getAvatarIndex()][0]));
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

	public int getAvatarIndex() {
		return avatarIndex;
	}

	public void setAvatarIndex(int avatarIndex) {
		this.avatarIndex = avatarIndex;
	}
}
