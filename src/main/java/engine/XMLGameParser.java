package engine;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import games.Deck;


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
	
	public XMLGameParser(String gameName) throws DocumentException {
		
		SAXReader reader = new SAXReader();
		Document xml = reader.read(getClass().getResource("/games/"+gameName+".xml"));
		
		root = xml.getRootElement();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Deck> getAvailableDecks() {
			
		List<Element> decksNodes = root.selectNodes("//deck");
		
		ArrayList<Deck> decks = new ArrayList<Deck>();
		
		for( Node deck : decksNodes ) {
			decks.add(new Deck(deck.valueOf("@name"), 
					Integer.parseInt(deck.selectSingleNode("numCards").getText()),
					deck.selectSingleNode("imgPrefix").getText()));
		}
		
		return decks;
	}
}
