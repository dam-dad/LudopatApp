package gameslib;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.Bindings;
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
    private Label appNameLabel;

    @FXML
    private HBox header;

    @FXML
    private Label gameNameLabel;

    @FXML
    private HBox actualColor;

    @FXML
    private ImageView color;

    @FXML
    private HBox actualNumber;

    @FXML
    private Label numberLabel;

    @FXML
    private JFXButton exitButton;

    @FXML
    private VBox players;

    @FXML
    private HBox player1;

    @FXML
    private ImageView player1Image;

    @FXML
    private Label player1Name;

    @FXML
    private Label player1Cards;

    @FXML
    private HBox player2;

    @FXML
    private ImageView player2Image;

    @FXML
    private Label player2Name;

    @FXML
    private Label player2Cards;

    @FXML
    private HBox player3;

    @FXML
    private ImageView player3Image;

    @FXML
    private Label player3Name;

    @FXML
    private Label player3Cards;

    @FXML
    private HBox player4;

    @FXML
    private ImageView player4Image;

    @FXML
    private Label player4Name;

    @FXML
    private Label player4Cards;

    @FXML
    private VBox table;

    @FXML
    private ImageView currentCard;

    @FXML
    private ImageView currentCard1;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton drawButton;

    @FXML
    private GridPane handGrid;
    
	LudopatApp ludopp;
	Dos dosGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String n = "Numero de cartas: ";
		player1Name.textProperty().bind(dosGame.getCurrentPlayers().get(0).playerNameProperty());
		player1Image.imageProperty().bind(dosGame.getCurrentPlayers().get(0).playerIconProperty());
		player1Cards.textProperty().bind(Bindings.concat(n).concat(dosGame.getCurrentPlayers().get(0).getHand().size()));
		player2Name.textProperty().bind(dosGame.getCurrentPlayers().get(1).playerNameProperty());
		player2Image.imageProperty().bind(dosGame.getCurrentPlayers().get(1).playerIconProperty());
		player2Cards.textProperty().bind(Bindings.concat(n).concat(dosGame.getCurrentPlayers().get(1).getHand().size()));
		if (dosGame.getCurrentPlayers().size() > 2) {
			player3Name.textProperty().bind(dosGame.getCurrentPlayers().get(2).playerNameProperty());
			player3Image.imageProperty().bind(dosGame.getCurrentPlayers().get(2).playerIconProperty());
			player3Cards.textProperty().bind(Bindings.concat(n).concat(dosGame.getCurrentPlayers().get(2).getHand().size()));
			if (dosGame.getCurrentPlayers().size() > 3) {
				player4Name.textProperty().bind(dosGame.getCurrentPlayers().get(3).playerNameProperty());
				player4Image.imageProperty().bind(dosGame.getCurrentPlayers().get(3).playerIconProperty());
				player4Cards.textProperty().bind(Bindings.concat(n).concat(dosGame.getCurrentPlayers().get(3).getHand().size()));
			}
		}
	
		appNameLabel.textProperty().bind(dosGame.getGameRules().gameTypeProperty());
		
		
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