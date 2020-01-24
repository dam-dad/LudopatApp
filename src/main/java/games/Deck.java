package games;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>Baraja</b>
 * <br><br>
 * 
 * Baraja de cartas que contiene instancias de objetos carta y 
 * que sirve de contenedor para dichos objetos
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Deck {

	/**
	 * Lista de cartas a jugar
	 */
	private ArrayList<Card> cards;
	
	/**
	 * Nombre identificativo de la baraja, usado
	 * para la implementación con la interfaz.
	 */
	private String deckType;
	
	/**
	 * Nombre de la baraja a visualizar en
	 * la interfaz.
	 */
	private String displayName;
	
	/**
	 * Número de cartas en el juego
	 */
	private int numCards;
	
	public Deck() {}
	
	public Deck(String deckType, int numCards, String imgPrefix, String displayName) {
		this.deckType = deckType;
		this.displayName = displayName;
		this.numCards = numCards;
	}

	/**
	 * Barajar las cartas
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public String getDeckType() {
		return deckType;
	}

	public void setDeckType(String deckType) {
		this.deckType = deckType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return displayName;
	}

	public int getNumCards() {
		return numCards;
	}

	public void setNumCards(int numCards) {
		this.numCards = numCards;
	}
}
