package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import help.HelpViewContoller;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.util.Duration;
import main.LudopatApp;
/**
 * <b>MainMenuController</b> <br>
 * <br>
 * 
 * Controlador de la vista del menu principal
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
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

	@FXML
	private StackPane stack;
	
	private LudopatApp ludopp;
	private HelpViewContoller help;
	
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
	
	/**
	 * Muestra la documentacion y ayuda basica del juego
	 * @param event
	 */
	@FXML
    void documentationAction(ActionEvent event) {
		Label helpLabel = new Label("Ayuda");
		helpLabel.setMaxWidth(800);
		helpLabel.setId("tittle");
		
		HBox tittleBox = new HBox(helpLabel);
		tittleBox.setPrefWidth(800);
		tittleBox.setAlignment(Pos.CENTER);
		
		help = new HelpViewContoller("Index");
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(help.getView());
		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);
		
		layout.setId("content");
		
		dialog.show();
    }
	
    @FXML
    void exitAction(ActionEvent event) {
    	Platform.exit();
    }
    
    @FXML
    void multiPlayerAction(ActionEvent event) {
    	ludopp.goMPSelectionMode();
//    	ludopp.goMultiplayerMenu();
    }

    @FXML
    void singlePlayerAction(ActionEvent event) {
    	ludopp.initSinglePlayer();
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
