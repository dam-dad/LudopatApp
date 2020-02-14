package net.objects;

import java.io.Serializable;


public class NET_Card implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Valor de la carta en el juego
	 */
	private int cardValue;
	
	/**
	 * URL de la imagen de la cartas
	 */
	private String urlImage;
	
	/**
	 * Palo o color de la carta
	 */
	private Suit suit;
	
	public NET_Card() {}
	
	
	public NET_Card(int cardValue, String urlImage, Suit suit) {
		this.cardValue = cardValue;
		this.suit = suit;
		this.urlImage = urlImage;
	}
	
	public NET_Card(Card card) {
		setCardValue(card.getCardValue());
		setSuit(card.getSuit());
		setUrlImage(card.getUrlResourceImage());
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


	public String getUrlImage() {
		return urlImage;
	}


	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}
