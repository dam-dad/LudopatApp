package games;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

/**
 * <b>Reglas del juego</b>
 * <br><br>
 * 
 * Reglas básicas del juego que se cargan a partir de un archivo XML propio
 * de cada juego y de las configuraciones de la partida.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class GameRules {

	public enum e_deckType {
		
		DECK_DOS,
		DECK_SPANISH,
		DECK_POKER
	}
	
	/**
	 * Diferentes tipos de cartas, española, de póker,.....
	 */
	private e_deckType deckType;
	
	/**
	 * Número de jugadores que va a haber en la partida
	 */
	private int numPlayers;

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

	public GameRules() {}

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
	
	public void setDeckType(e_deckType deckType) {
		this.deckType = deckType;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
}
