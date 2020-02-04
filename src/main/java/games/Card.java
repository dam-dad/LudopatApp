package games;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;


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
	private ObjectProperty<Image> cardImage = new SimpleObjectProperty<Image>();
	
	/**
	 * Palo o color de la carta
	 */
	private Suit suit;

	/**
	 * Si la carta es jugable o no jugable
	 */
	private BooleanProperty playable = new SimpleBooleanProperty() ;
	
	public Card() {}
	
	
	public Card(int cardValue, Image cardImage) {
		super();
		this.cardValue = cardValue;
		this.cardImage.set(cardImage);
	}

	
	public Card(int cardValue, Image cardImage, Suit suit) {
		super();
		this.cardValue = cardValue;
		this.cardImage.set(cardImage);
		this.suit = suit;
	}
	
	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
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


	public final ObjectProperty<Image> cardImageProperty() {
		return this.cardImage;
	}
	


	public final Image getCardImage() {
		return this.cardImageProperty().get();
	}
	


	public final void setCardImage(final Image cardImage) {
		this.cardImageProperty().set(cardImage);
	}
	
	
	
}
