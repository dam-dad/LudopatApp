package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import games.Deck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;


/**
 * 
 * <b>Controlador configuración baraja</b>
 * <br><br>
 * 
 * Controlador para los ajustes de multijugador con
 * opciones para elegir tipo de baraja y número de
 * cartas
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */

public class DeckConfigController extends AnchorPane implements Initializable {

    @FXML
    private ToggleGroup deckGroup;
    
    @FXML
    private RadioButton pokerDeck;

    @FXML
    private RadioButton dosDeck;

    @FXML
    private RadioButton espDeck;
    
    private LudopatApp ludopp;
    
	public DeckConfigController() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/DeckConfigView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void selectDecks() {
		
		// Hacemos visibles los botones que se corresponden
		// con este juego de cartas
		for (Toggle btn : deckGroup.getToggles()) {

			RadioButton radio = (RadioButton) btn;
			
			if (ludopp.getGameRules().getAvailableDecks().stream()
					.anyMatch(deck -> deck.getDeckType().equals(radio.getId()))) {
				
				radio.setVisible(true);
			}
		}
	}
}
