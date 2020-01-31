package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.dom4j.DocumentException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.LudopatApp;
import ui.config.DeckConfigController;
import ui.config.GameConfigController;
import ui.config.PlayerSelectionController;
import ui.config.SummaryController;

/**
 * 
 * <b>Controlador vista multijugador</b>
 * <br><br>
 * 
 * Controlador para los ajustes de multijugador.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class MultiplayerController implements Initializable {
	
	// FXML : View
	//--------------------------------------------------
	
	@FXML
	private BorderPane view;
	
	@FXML
	private Button backButton, continueButton, menuButton;
	
	@FXML
	private Label pageLabel;
	
	@FXML
	private SplitPane configPane;
	
	//--------------------------------------------------
	
	// Variables
	//--------------------------------------------------
	
	private static final int ANCHOR_WIDTH = 800;
	
	enum e_menuStages {
		
		ST_CONFIG_GAME, 
		ST_CONFIG_INGAME, 
		ST_CONFIG_PLAYERS, 
		ST_CONFIG_SUMMARY
	}
	
	private e_menuStages currentStage;
	
	private GameConfigController gameConfig;
	private DeckConfigController deckConfig;
	private PlayerSelectionController playerConfig;
	private SummaryController summary;
	
	private LudopatApp ludopp;
	
	//--------------------------------------------------
	
	// Model
	private IntegerProperty currentPage = new SimpleIntegerProperty();
	
	public MultiplayerController(LudopatApp app) throws IOException {
		
		this.ludopp = app;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MPConfigView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Bindings
		pageLabel.textProperty().bind( currentPage.asString() );
		
		gameConfig = new GameConfigController(ludopp);
		deckConfig = new DeckConfigController(ludopp);
		playerConfig = new PlayerSelectionController();
		summary = new SummaryController(ludopp);
		
		// Para evitar que el usuario pueda arrastrar las ventanas
		deckConfig.setMaxWidth(0);
		playerConfig.setMaxWidth(0);
		summary.setMaxWidth(0);
		
		// Añadimos los datos de configuración
		configPane.getItems().setAll(gameConfig, deckConfig, playerConfig, summary);
		configPane.getItems().set(0, gameConfig);
		configPane.getItems().set(1, deckConfig);
		configPane.getItems().set(2, playerConfig);
		configPane.getItems().set(3, summary);
		
		// Precargamos los paneles y los ponemos en posición
		configPane.setDividerPositions(1, 1, 1);
		
		backButton.disableProperty().bind( currentPage.isEqualTo(1));
		
		currentStage = e_menuStages.ST_CONFIG_GAME;
		currentPage.setValue(1);
	}
	
	private void nextStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_GAME:
				
				try {
					// Cargamos los ajustes para el juego seleccionado
					ludopp.getGameRules().initGameType();
					
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				
				deckConfig.selectDecks();
				configPane.setDividerPositions(0, 1, 1);
				gameConfig.setMaxWidth(0);
				deckConfig.setMaxWidth(ANCHOR_WIDTH);
				break;
			case ST_CONFIG_INGAME:
				configPane.setDividerPositions(0, 0, 1);
				deckConfig.setMaxWidth(0);
				playerConfig.setMaxWidth(ANCHOR_WIDTH);
				break;
			case ST_CONFIG_PLAYERS:
				summary.initSummary();
				configPane.setDividerPositions(0, 0, 0);
				playerConfig.setMaxWidth(0);
				summary.setMaxWidth(ANCHOR_WIDTH);
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	private void previousStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_INGAME:
				configPane.setDividerPositions(1, 1, 1);
				gameConfig.setMaxWidth(ANCHOR_WIDTH);
				deckConfig.setMaxWidth(0);
				break;
			case ST_CONFIG_PLAYERS:
				configPane.setDividerPositions(0, 1, 1);
				deckConfig.setMaxWidth(ANCHOR_WIDTH);
				playerConfig.setMaxWidth(0);
				break;
			case ST_CONFIG_SUMMARY:
				configPane.setDividerPositions(0, 0, 1);
				playerConfig.setMaxWidth(ANCHOR_WIDTH);
				summary.setMaxWidth(0);
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	@FXML
	void onBackAction(ActionEvent event) {
		currentPage.setValue(currentPage.getValue()-1);
		previousStage();
	}
	
    @FXML
    void onContinueAction(ActionEvent event) {
		
		if( currentPage.getValue() == 4 ) {
			ludopp.initGame();
		} else {
			currentPage.setValue(currentPage.getValue()+1);
			nextStage();
		}
	}
	
	@FXML
	void onMenuAction(ActionEvent event) {
		ludopp.goMenu();
	}

	public BorderPane getView() {
		return view;
	}

}
