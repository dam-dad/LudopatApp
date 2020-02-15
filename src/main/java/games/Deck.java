package games;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;

/**
 * <b>Baraja</b> <br>
 * <br>
 * 
 * Baraja de cartas que contiene instancias de objetos carta y que sirve de
 * contenedor para dichos objetos
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
	 * Nombre identificativo de la baraja, usado para la implementación con la
	 * interfaz.
	 */
	private String deckType;

	/**
	 * Nombre de la baraja a visualizar en la interfaz.
	 */
	private String displayName;

	/**
	 * Número de cartas en el juego
	 */
	private int numCards;

	/**
	 * Prefijos de las cartas
	 */
	private ArrayList<Suit> suits;

	/**
	 * Número de cartas por palo
	 */
	private int cardsPerSuit;

	/**
	 * Si hay doble baraja
	 */
	private boolean doubleDeck;

	public Deck() {
	}

	/**
	 * Cargamos las imágenes en las cartas
	 */
	public void loadCards(String gameType) {

		cards = new ArrayList<Card>();

		// Si es doble baraja, añadimos otra baraja más
		initCards(gameType);
		if( isDoubleDeck() ) {
			initCards(gameType);
		}
	}
	
	public void initCards(String gameType) {
		
		for (Suit suit : suits) {

			for (int i = 1; i <= cardsPerSuit; i++) {

				String urlImage = String.format("/ui/images/%s/%s/%s_%d.png", gameType, suit.getName(),
						suit.getImgPrefix(), i);

				Card card = new Card();	
				card.setCardImage(new Image(getClass().getResource(urlImage).toString()));
				card.setCardValue(i);
				card.getCardMap().put(card.getCardValue(), urlImage);
				card.setSuit(suit);
				cards.add(card);
			}
		}
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

	public ArrayList<Suit> getSuits() {
		return suits;
	}

	public void setSuits(ArrayList<Suit> suits) {
		this.suits = suits;
	}

	public int getCardsPerSuit() {
		return cardsPerSuit;
	}

	public void setCardsPerSuit(int cardsPerSuit) {
		this.cardsPerSuit = cardsPerSuit;
	}

	public boolean isDoubleDeck() {
		return doubleDeck;
	}

	public void setDoubleDeck(boolean doubleDeck) {
		this.doubleDeck = doubleDeck;
	}
}
