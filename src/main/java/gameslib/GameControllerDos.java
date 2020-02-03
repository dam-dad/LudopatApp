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
	Dos dosGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		player1Label.textProperty().bind(dosGame.getCurrentPlayers().get(0).playerNameProperty());
		player2Label.textProperty().bind(dosGame.getCurrentPlayers().get(1).playerNameProperty());
		if (dosGame.getCurrentPlayers().size() > 2) {
			player3Label.textProperty().bind(dosGame.getCurrentPlayers().get(2).playerNameProperty());
			if (dosGame.getCurrentPlayers().size() > 3) {
				player4Label.textProperty().bind(dosGame.getCurrentPlayers().get(3).playerNameProperty());
			}
		}
	}

	public GameControllerDos(LudopatApp app) {
		this.ludopp = app;
		this.dosGame =(Dos)ludopp.getCurrentGame();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GameView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public GridPane getView() {
		return view;
	}
}