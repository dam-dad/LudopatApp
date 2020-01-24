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
	
	public Deck() {}
	
	public Deck(String deckType, int numCards, String imgPrefix) {
		super();
		this.deckType = deckType;
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
	
	
}
