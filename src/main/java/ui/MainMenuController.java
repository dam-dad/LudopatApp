package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
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
	
	FadeTransition fadeTransition;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playFadeTransition();
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
    void playFadeTransition() {
    	fadeTransition = new FadeTransition();
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setDuration(Duration.seconds(0.75));
		fadeTransition.setNode(view);
		fadeTransition.play();	
    }
    public BorderPane getView() {
		return view;
	}
	

}
