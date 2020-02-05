package games;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	
	
	
}
