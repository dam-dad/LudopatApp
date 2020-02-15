package net.objects;

import java.io.Serializable;

import games.Card;
import games.Suit;


public class NET_Card implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Valor de la carta en el juego
	 */
	private int cardValue;
	
	/**
	 * Palo o color de la carta
	 */
	private Suit suit;
	
	/**
	 * Localización de la imagen en la carpeta de recursos
	 */
	private String urlResourceImage;
	
	public NET_Card() {}
	
	
	public NET_Card(int cardValue, Suit suit) {
		this.cardValue = cardValue;
		this.suit = suit;
	}
	
	public NET_Card(Card card) {
		setUrlResourceImage(card.getCardMap().get(card.getCardValue()));
		setCardValue(card.getCardValue());
		setSuit(card.getSuit());
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


	public String getUrlResourceImage() {
		return urlResourceImage;
	}


	public void setUrlResourceImage(String urlResourceImage) {
		this.urlResourceImage = urlResourceImage;
	}
}
