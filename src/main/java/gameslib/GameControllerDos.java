package gameslib;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LudopatApp;

public class GameControllerDos implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private Label ludopatApp;

	@FXML
	private HBox cabecera;

	@FXML
	private Label tres;

	@FXML
	private Label actualColorLabel;

	@FXML
	private ImageView colorImage;

	@FXML
	private Label actualValueLabel;

	@FXML
	private Label valueLabel;

	@FXML
	private JFXButton leaveButton;

	@FXML
	private VBox players;

	@FXML
	private ImageView player1Image;

	@FXML
	private Label player1Label;

	@FXML
	private Label handCardsLabel1;

	@FXML
	private Label cardNumberLabel1;

	@FXML
	private ImageView player2Image;

	@FXML
	private Label player2Label;

	@FXML
	private Label handCardsLabel2;

	@FXML
	private Label cardNumberLabel2;

	@FXML
	private ImageView player3Image;

	@FXML
	private Label player3Label;

	@FXML
	private Label handCardsLabel3;

	@FXML
	private Label cardNumberLabel3;

	@FXML
	private ImageView player4Image;

	@FXML
	private Label player4Label;

	@FXML
	private Label handCardsLabel4;

	@FXML
	private Label cardNumberLabel4;

	@FXML
	private HBox hand;

	LudopatApp ludopp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public GameControllerDos(LudopatApp app) throws IOException {

		this.ludopp = app;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GameView.fxml"));
		loader.setController(this);
		loader.load();
	}
}