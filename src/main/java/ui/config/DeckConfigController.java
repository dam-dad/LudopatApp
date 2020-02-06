package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import games.Deck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

	// FXML : View
	//---------------------------------------------------------
	
    @FXML
    private ToggleGroup deckGroup;
    
    @FXML
    private ToggleGroup nDeckGroup;

    @FXML
    private RadioButton pokerDeck, dosDeck, espDesk;
    
    @FXML
    private RadioButton oneRadio, twoRadio;

   //---------------------------------------------------------
    
    
    private LudopatApp ludopp;
    
	public DeckConfigController(LudopatApp app) {
		
		this.ludopp = app;
	
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
		
		// Seleccionamos el primer elemento por defecto
		oneRadio.setSelected(true);
		
		deckGroup.selectedToggleProperty().addListener( (o, ov, nv ) -> onDeckChanged(nv));
		nDeckGroup.selectedToggleProperty().addListener( (o, ov, nv) -> onChangedNumDeck(nv));
	}
	
	private void onChangedNumDeck(Toggle nv) {
		
		RadioButton btn = (RadioButton)nv;
		
		// ¿Es doble baraja?
		if( btn.equals(twoRadio)) {
			ludopp.getGameRules().getDeckType().setDoubleDeck(true);
		} else {
			ludopp.getGameRules().getDeckType().setDoubleDeck(false);
		}
	}

	private void onDeckChanged(Toggle nv) {
		
		RadioButton btn = (RadioButton)nv;
		Optional<Deck> deck = ludopp.getGameRules().getAvailableDecks().stream()
				.filter( dk -> btn.getText().equals(dk.getDisplayName())).findFirst();
		
		if( deck.isPresent() )
			ludopp.getGameRules().setDeckType(deck.get());
	}

	/**
	 * Cada vez que se cambia de juego y se pasa a su configuración,
	 * ponemos las barajas disponibles.
	 */
	public void selectDecks() {
		
		// Hacemos visibles los botones que se corresponden
		// con este juego de cartas
		for (Toggle btn : deckGroup.getToggles()) {

			RadioButton radio = (RadioButton) btn;
			
			if (ludopp.getGameRules().getAvailableDecks() != null) {
				
				if (ludopp.getGameRules().getAvailableDecks().stream()
						.anyMatch(deck -> deck.getDeckType().equals(radio.getId()))) {

					radio.setDisable(false);
					radio.setSelected(true); // Se seleccionará el último
				}
			}
		}
	}
}
