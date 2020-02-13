package engine;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import games.Deck;
import games.Suit;


/**
 * 
 * <b>Lector XML</b>
 * <br><br>
 * 
 * Clase encargada de leer los XML de los distintos juegos.
 * La ruta de los mismos tiene que estar dentro de la 
 * subcarpeta games y contener el nombre del juego.
 * 
 * El xml contiene información acerca de las barajas soportadas
 * por este juego.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */
public class XMLGameParser {

	/**
	 * Raiz del nodo, que en este caso va a ser las barajas
	 */
	
	private Element root;
	private String[] decks;;
	
	
	public void readAvailableDecks(String[] decks) throws DocumentException {
		
		this.decks = decks;
		
		SAXReader reader = new SAXReader();
		Document xml = reader.read(getClass().getResource("/games/decks.xml"));
		
		root = xml.getRootElement();

	}
	
	@SuppressWarnings("unchecked")
	public String[] getGameDecks(String gameName) throws DocumentException {
		
		// Necesitamos devolver la lista de barajas disponibles
		
		SAXReader reader = new SAXReader();
		Document xml = reader.read(getClass().getResource("/games/games.xml"));
		
		Element rootNode = xml.getRootElement();
		
		List<Element> decksRefs = rootNode.selectNodes("//game[@name='"+gameName+"']/deckRef");
		
		String[] str = new String[decksRefs.size()];
		
		for( int i = 0; i < decksRefs.size(); i++ ) {
			str[i] = "'" + decksRefs.get(i).getStringValue() + "'";
		}
		
		return str;
	}
	
	/**
	 * Las barajas disponibles para el juego seleccionado
	 * @return Barajas disponibles
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayList<Deck> getAvailableDecks() {
			
		StringBuilder condition = new StringBuilder("(");
		int i = 0;
		for( i = 0; i < decks.length-1; i++ ) {
			condition.append(decks[i] + ",");
		}
		condition.append(decks[i] + ")");

		List<Element> decksNodes = root.selectNodes("//deck[@name="+condition + "]");

		ArrayList<Deck> decks = new ArrayList<Deck>();
		
		for( Node deck : decksNodes ) {

			Deck ourDeck = new Deck();
			
			ourDeck.setNumCards(Integer.parseInt(deck.selectSingleNode("numCards").getText()));
			ourDeck.setDisplayName(deck.valueOf("@displayName"));
			ourDeck.setCardsPerSuit(Integer.parseInt(deck.selectSingleNode("cardsPerSuit").getText()));
			ourDeck.setDeckType(deck.valueOf("@name"));
			
			List<Element> suitNodes = deck.selectSingleNode("suits").selectNodes("suit");
			
			ArrayList<Suit> suits = new ArrayList<Suit>();
			
			for( Node suitNode : suitNodes ) {
				Suit s = new Suit();
				s.setImgPrefix(suitNode.selectSingleNode("imgPrefix").getText());
				s.setName(suitNode.selectSingleNode("name").getText());
				suits.add(s);
			}
			
			ourDeck.setSuits(suits);
			
			
			decks.add(ourDeck);
		}
		
		return decks;
	}
}
