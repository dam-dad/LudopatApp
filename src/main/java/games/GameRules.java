package games;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

/**
 * <b>Reglas del juego</b>
 * <br><br>
 * 
 * Reglas básicas del juego que se cargan a partir de un archivo XML propio
 * de cada juego.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class GameRules {

	/**
	 * Número de cartas total del juego
	 */
	
	private int numCards;
	/**
	 * Imágenes de cada carta en el juego
	 */
	private ArrayList<ImageView> cardImages;
	
	/**
	 * Valores de las distintas cartas del juego,
	 * cada juego interpretará este valor según
	 * su significado.
	 */
	private ArrayList<Integer> cardValues;
	
	public GameRules(int numCards, ArrayList<ImageView> cardImages, ArrayList<Integer> cardValues) {
		this.numCards = numCards;
		this.cardImages = cardImages;
		this.cardValues = cardValues;
	}

	public int getNumCards() {
		return numCards;
	}

	public void setNumCards(int numCards) {
		this.numCards = numCards;
	}

	public ArrayList<ImageView> getCardImages() {
		return cardImages;
	}

	public void setCardImages(ArrayList<ImageView> cardImages) {
		this.cardImages = cardImages;
	}

	public ArrayList<Integer> getCardValues() {
		return cardValues;
	}

	public void setCardValues(ArrayList<Integer> cardValues) {
		this.cardValues = cardValues;
	}
	
}
