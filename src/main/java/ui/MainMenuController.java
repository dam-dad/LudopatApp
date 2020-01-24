package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.LudopatApp;

public class MainMenuController implements Initializable{

	@FXML
	private BorderPane view;

	@FXML
	private Button singlePlayerButton;

	@FXML
	private Button multiPlayerButton;

	@FXML
	private Button documentationButton;

	@FXML
	private Button exitButton;
	
	private LudopatApp ludopp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public MainMenuController (LudopatApp app) throws IOException {
		
		this.ludopp = app;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MainMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}
	

	@FXML
    void documentationAction(ActionEvent event) {

    }

    @FXML
    void exitAction(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void multiPlayerAction(ActionEvent event) {
    	ludopp.goMultiplayerMenu();
    }

    @FXML
    void singlePlayerAction(ActionEvent event) {

    }

    public BorderPane getView() {
		return view;
	}
	

}
