package games;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

/**
 * 
 * <b>Icono del jugador</b>
 * <br><br>
 * 
 * Iconos de los distintos jugadores
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class PlayerIcon {

	/**
	 * Iconos de los jugadores
	 */
	private static ArrayList<ImageView> playerIcons;
	
	/**
	 * Obtener la carta correspondiente
	 * @param index Índice de la carta
	 * @return
	 */
	public static ImageView getImageByIndex(int index) {
		return playerIcons.get(index);
	}
}
