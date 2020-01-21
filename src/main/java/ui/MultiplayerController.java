package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	private Button backBt, continueBt, menuBt;
	
	@FXML
	private Label pageLabel;
	
	@FXML
	private SplitPane configPane;
	
	//--------------------------------------------------
	
	// Variables
	//--------------------------------------------------
	
	enum e_menuStages {
		
		ST_CONFIG_GAME, 
		ST_CONFIG_INGAME, 
		ST_CONFIG_PLAYERS, 
		ST_CONFIG_SUMMARY
	}
	
	private e_menuStages currentStage;
	
	private SummaryController summary;
	
	private LudopatApp ludopp;
	
	//--------------------------------------------------
	
	private IntegerProperty currentPage = new SimpleIntegerProperty(1);
	
	public MultiplayerController(LudopatApp app) throws IOException {
		
		this.ludopp = app;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Al principio no está visible
		backBt.setVisible(false);
		
		// Bindings
		pageLabel.textProperty().bind( currentPage.asString() );
		
		// Precargamos los paneles y los ponemos en posición
		configPane.setDividerPositions(0, 1, 1, 1);
		
		summary = new SummaryController();
		configPane.getItems().add(summary);
	}
	
	private void nextStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_GAME:
				backBt.setVisible(true); 
				configPane.setDividerPositions(0, 0, 1, 1);
				break;
			case ST_CONFIG_INGAME:
				configPane.setDividerPositions(0, 0, 0, 1);
				break;
			case ST_CONFIG_PLAYERS:
				configPane.setDividerPositions(0, 0, 0, 0);
				break;
			case ST_CONFIG_SUMMARY:
				// Empezar la partida
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	private void previousStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_INGAME:
				backBt.setVisible(false);
				configPane.setDividerPositions(0, 1, 1, 1); // A configuración partida
				break;
			case ST_CONFIG_PLAYERS:
				configPane.setDividerPositions(0, 0, 1, 1);
				break;
			case ST_CONFIG_SUMMARY:
				configPane.setDividerPositions(0, 0, 0, 1);
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	@FXML
	private void onBackAction(ActionEvent event) {
		currentPage.subtract(1);
		previousStage();
	}
	
	@FXML
	private void onContinueAction(ActionEvent event) {
		currentPage.add(1);
		nextStage();
	}
	
	@FXML
	private void onMenuAction(ActionEvent event) {
		ludopp.goMenu();
	}

	public BorderPane getView() {
		return view;
	}

}
