package games;

import java.util.ArrayList;

import org.dom4j.DocumentException;

import engine.XMLGameParser;

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

	
	/**
	 * Diferentes tipos de cartas, española, de póker,.....
	 * Las barajas disponibles dependen del juego
	 * seleccionado.
	 */
	private Deck deckType;
	
	/**
	 * Número de jugadores que va a haber en la partida
	 */
	private int numPlayers;
	
	/**
	 * Barajas soportadas para este juego
	 */
	private ArrayList<Deck> availableDecks;

	/**
	 * Modo de juego, solitario, dos, póker,....
	 */
	private String gameType;
	
	/**
	 * Referencia al XML del juego
	 */
	private XMLGameParser parser;
	
	public GameRules() {}
	
	public void setDeckType(Deck deckType) {
		this.deckType = deckType;
	}
	
	public Deck getDeckType() {
		return deckType;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void setGameType(String gameType)  {
		this.gameType = gameType;
	}
	
	public String getGameType() {
		return gameType;
	}

	/**
	 * Iniciamos los ajustes para el juego seleccionado
	 * y los cargamos en las regas del juego.
	 * 
	 * @throws DocumentException
	 */
	public void initGameType() throws DocumentException {

		// Necesitamos cargar los elementos del XML para obtener la baraja
		parser = new XMLGameParser(gameType);
		
		availableDecks = parser.getAvailableDecks();	
	}

	public ArrayList<Deck> getAvailableDecks() {
		return availableDecks;
	}
}
