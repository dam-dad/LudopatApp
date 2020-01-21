package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class MultiplayerController implements Initializable {
	
	// FXML : View
	//--------------------------------------------------
	
	@FXML
	private BorderPane view;
	
	@FXML
	private Button backBt, continueBt, menuBt;
	
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
	
	//--------------------------------------------------
	
	public MultiplayerController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}
	
	private void changeStage() {
		
		switch (currentStage) {

			case ST_CONFIG_GAME:
				break;
			case ST_CONFIG_INGAME:
				break;
			case ST_CONFIG_PLAYERS:
				break;
			case ST_CONFIG_SUMMARY:
				break;
			default:
				break;
		}
	}
	
	@FXML
	private void onBackAction(ActionEvent event) {
		
	}
	
	@FXML
	private void onContinueAction(ActionEvent event) {
		
	}
	
	@FXML
	private void onMenuAction(ActionEvent event) {
		
	}

	public BorderPane getView() {
		return view;
	}

}
