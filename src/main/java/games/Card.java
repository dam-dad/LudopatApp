package games;

import javafx.scene.image.ImageView;

/**
 * <b>Carta</b>
 * <br><br>
 * 
 * Elemento principal del juego que tiene distintas propiedades
 * tales como el valor, el palo, imagen,......
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */

public class Card {

	/**
	 * Valor de la carta en el juego
	 */
	private int cardValue;
	
	/**
	 * Imagen de la caarta en el juego
	 */
	private ImageView cardImage;
	
	/**
	 * Palo o color de la carta
	 */
	private int suit;

	public Card() {}
	
	
	public Card(int cardValue, ImageView cardImage) {
		super();
		this.cardValue = cardValue;
		this.cardImage = cardImage;
	}

	
	public Card(int cardValue, ImageView cardImage, int suit) {
		super();
		this.cardValue = cardValue;
		this.cardImage = cardImage;
		this.suit = suit;
	}
	
	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

	public ImageView getCardImage() {
		return cardImage;
	}

	public void setCardImage(ImageView cardImage) {
		this.cardImage = cardImage;
	}

	public int getSuits() {
		return suit;
	}

	public void setSuits(int suits) {
		this.suit = suits;
	}
	
	
	
	
}