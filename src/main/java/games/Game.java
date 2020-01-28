package games;

import java.util.ArrayList;

/**
 * <b>Juego</b>
 * <br><br>
 * Clase base de los juegos que contiene las funcionalidades
 * básicas para cada juego. 
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public abstract class Game {

	/**
	 * Número máximo de jugadores, actualmente con valor 4
	 */
	private static final int MAX_PLAYERS = 4;
	
	/**
	 * Mazo del juego
	 */
	protected Deck deck;
	
	/**
	 * Reglas en las que se basa el juego
	 */
	protected GameRules gameRules;
	
	/**
	 * Jugadores en la partida actual
	 */
	protected ArrayList<Player> currentPlayers = new ArrayList<Player>(MAX_PLAYERS);

	/**
	 * Cartas sobre la mesa
	 */
	protected ArrayList<Card> cartasMesa = new ArrayList<Card>();
	
	public Game(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		this.deck = deck;
		this.gameRules = gameRules;
		this.currentPlayers = currentPlayers;
	}

	/**
	 * Se inicia el juego, se cargan los 
	 * parámetros básicos de cada juego.
	 */
	public abstract void initGame();
	
	/**
	 * Finaliza el juego, normalmente
	 * se saca un resumen de las distintas
	 * puntuaciones.
	 */
	public abstract void endGame();
	
	/**
	 * Un jugador lanza una carta
	 * @param card: Carta que ha jugado
	 */
	public abstract void throwCard(Card card);
	
	/**
	 * Se reparten las cartas a los jugadores
	 */
	public abstract void dealCards();
	
	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public GameRules getGameRules() {
		return gameRules;
	}

	public void setGameRules(GameRules gameRules) {
		this.gameRules = gameRules;
	}

	public ArrayList<Player> getCurrentPlayers() {
		return currentPlayers;
	}

	public void setCurrentPlayers(ArrayList<Player> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}

	public ArrayList<Card> getCartasMesa() {
		return cartasMesa;
	}

	public void setCartasMesa(ArrayList<Card> cartasMesa) {
		this.cartasMesa = cartasMesa;
	}
}
