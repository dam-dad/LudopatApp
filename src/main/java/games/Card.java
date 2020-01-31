package games;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
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
	 * Imagen de la carta en el juego
	 */
	private Image cardImage;
	
	/**
	 * Palo o color de la carta
	 */
	private Suit suit;

	/**
	 * Si la carta es jugable o no jugable
	 */
	private BooleanProperty playable ;
	
	public Card() {}
	
	
	public Card(int cardValue, Image cardImage) {
		super();
		this.cardValue = cardValue;
		this.cardImage = cardImage;
	}

	
	public Card(int cardValue, Image cardImage, Suit suit) {
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

	public Image getCardImage() {
		return cardImage;
	}

	public void setCardImage(Image cardImage) {
		this.cardImage = cardImage;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}


	public final BooleanProperty playableProperty() {
		return this.playable;
	}
	

	public final boolean isPlayable() {
		return this.playableProperty().get();
	}
	

	public final void setPlayable(final boolean playable) {
		this.playableProperty().set(playable);
	}
	
	
}
