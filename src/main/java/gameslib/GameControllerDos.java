package gameslib;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import games.Card;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LudopatApp;
import ui.CardComponent;

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
	//	player1Image.imageProperty().bind(dosGame.getCurrentPlayers().get(0).playerIconProperty());
		player1Cards.textProperty()
				.bind(Bindings.concat(dosGame.getCurrentPlayers().get(0).getHand().size()));
		player2Name.textProperty().bind(dosGame.getCurrentPlayers().get(1).playerNameProperty());
	//	player2Image.imageProperty().bind(dosGame.getCurrentPlayers().get(1).playerIconProperty());
		player2Cards.textProperty()
				.bind(Bindings.concat(dosGame.getCurrentPlayers().get(1).getHand().size()));
		if (dosGame.getCurrentPlayers().size() > 2) {
			player3Name.textProperty().bind(dosGame.getCurrentPlayers().get(2).playerNameProperty());
		//	player3Image.imageProperty().bind(dosGame.getCurrentPlayers().get(2).playerIconProperty());
			player3Cards.textProperty()
					.bind(Bindings.concat(dosGame.getCurrentPlayers().get(2).getHand().size()));
			if (dosGame.getCurrentPlayers().size() > 3) {
				player4Name.textProperty().bind(dosGame.getCurrentPlayers().get(3).playerNameProperty());
		//		player4Image.imageProperty().bind(dosGame.getCurrentPlayers().get(3).playerIconProperty());
				player4Cards.textProperty()
						.bind(Bindings.concat(dosGame.getCurrentPlayers().get(3).getHand().size()));
			}
		}
		appNameLabel.textProperty().bind(dosGame.getGameRules().gameTypeProperty());
		numberLabel.textProperty().bind(Bindings.concat(dosGame.getCurrentValue()));
		dosGame.currentColorProperty().addListener(e -> changeImageColor());
		currentCard.imageProperty().bind(dosGame.getLastCard().cardImageProperty());
		//currentCard1.setImage(new Image(getClass().getResource("/ui/images/dos/card_back.png").toString()));
		initHand();
		showHand();
		
		
	
	}

	private void initHand() {
		int i = 0;
		dosGame.startTurn();
		for (Card card : dosGame.getActivePlayer().getHand()) {
			CardComponent cardComp = new CardComponent(card.getCardImage());
			handGrid.add(cardComp, i, 0);
			if (card.isPlayable()) {
				cardComp.setOnMouseClicked(e -> dosGame.throwCard(card));
				cardComp.getStyleClass().add("playable");
			}
			i++;
		}
	}

	private void showHand() {
		handGrid.getChildren().stream().forEach(node -> {
			CardComponent cardComp = (CardComponent) node;
			cardComp.turn();
		});

	}

	public void hideHand( ActionEvent event ) {
		
	}
	private void changeImageColor() {
		switch (dosGame.currentColorProperty().get()) {
		case "white":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/white/dos_white_1.png").toString()));
			break;
		case "blue":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/blue/dos_blue_1.png").toString()));
			break;
		case "green":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/green/dos_green_1.png").toString()));
			break;
		case "yellow":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/yellow/dos_yellow_1.png").toString()));
			break;
		}
	}

	@FXML
	void drawCardAction(ActionEvent event) {
		if (!dosGame.getDeck().getCards().isEmpty()) {
			dosGame.drawCard();
			if (!dosGame.getDeck().getCards().isEmpty()) {
				currentCard1.setDisable(true);
			}
		}
		drawButton.setDisable(true);
	}

	@FXML
	void exitAction(ActionEvent event) {
		// dialogo confirmacion
		Platform.exit();
	}

	@FXML
	void fullscreenAction(ActionEvent event) {
		ludopp.getMainStage().setFullScreen(true);
	}

	@FXML
	void nextTurnAction(ActionEvent event) {
		drawButton.setDisable(false);
		// aqui habria que esconder la mano (hidehand)
		dosGame.nextTurn();// esto cambia el jugador activo
		initHand();
		showHand();
	}

	@FXML
	void returnMenuAction(ActionEvent event) {
		// dialogo confirmacion
		ludopp.goMenu();
	}

	public GameControllerDos(LudopatApp app) {
		this.ludopp = app;
		this.dosGame = (Dos) ludopp.getCurrentGame();
		
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