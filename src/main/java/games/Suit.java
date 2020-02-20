package games;

import java.io.Serializable;

/**
 * 
 * <b>Suit</b>
 * </br></br>
 * Palo dentro de una baraja en el juego
 * 
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 * 
 */
public class Suit implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Nombre del palo
	 */
	private String name;
	/**
	 * Prefijo usado para la asignacion de imagen
	 */
	private String imgPrefix;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPrefix() {
		return imgPrefix;
	}

	public void setImgPrefix(String imgPrefix) {
		this.imgPrefix = imgPrefix;
	}
}
