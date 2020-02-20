package gameslib;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import games.Card;

import gameslib.endGame.SolitaireEndGameController;
import help.HelpViewContoller;
import help.InitialSolitaireHelp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.LudopatApp;
import ui.CardComponent;
import util.Stopwatch;
/**
 * <b>GameControllerSolitaire</b> <br>
 * <br>
 * 
 * Controlador del Solitario
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class GameControllerSolitaire implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private Label appNameLabel;

	@FXML
	private HBox header;

	@FXML
	private Label gameNameLabel;

	@FXML
	private ImageView helpImage;

	@FXML
	private Label currentTimeLabel;

	@FXML
	private Label currentRoundLabel;

	@FXML
	private JFXButton exitButton;

	@FXML
	private JFXButton closeButton;

	@FXML
	private JFXButton fullScreenButton;

	@FXML
	private VBox table;

	@FXML
	private StackPane stack;

	@FXML
	private GridPane tableGrid;

	@FXML
	private ImageView diamondsImage;

	@FXML
	private ImageView clubsImage;

	@FXML
	private ImageView heartsImage;

	@FXML
	private ImageView spadesImage;

	@FXML
	private JFXButton nextButton;

	@FXML
	private GridPane handGrid;

	@FXML
	private ImageView card1Image;

	@FXML
	private ImageView card2Image;

	@FXML
	private ImageView card3Image;

	@FXML
	private ImageView card4Image;

	@FXML
	private ImageView discardCard;

	@FXML
	private ImageView savedCard;

	@FXML
	private ImageView deckCard;

	private ArrayList<ImageView> tableCards;

	// variables
	private LudopatApp ludopp;
	private Solitaire solitaireGame;
	private HelpViewContoller help;
	private IntegerProperty round = new SimpleIntegerProperty(1);

	private LocalTime start;
	private Timeline timeline;

	public GameControllerSolitaire(LudopatApp app) {
		this.ludopp = app;
		this.solitaireGame = (Solitaire) ludopp.getCurrentGame();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/SolitaireGameView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		start= LocalTime.now();
		currentTimeLabel.setText("00:00");
		gameNameLabel.textProperty().bind(solitaireGame.getGameRules().gameTypeProperty());
		tableCards = new ArrayList<ImageView>(Arrays.asList(diamondsImage, clubsImage, heartsImage, spadesImage));
		currentRoundLabel.textProperty().bind(Bindings.concat("Ronda ", round.asString()));
		int i = 0;
		for (ImageView image : tableCards) {
			image.setImage(solitaireGame.getCardsInGame().get(i).get().getCardImage());
			final int j = i;
			solitaireGame.getCardsInGame().get(i).addListener((o, ov, nv) -> onPlayedCard(nv, j));
			i++;
		}
		refreshHand();
		showInitialHelp();
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
            	String time = Stopwatch.handle(0, start);
            	currentTimeLabel.setText(time);
            	
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		timeline.play();
	}

	private void onPlayedCard(Card nv, int i) {

		if (nv != null) {

			tableCards.get(i).setImage(solitaireGame.getCardsInGame().get(i).get().getCardImage());

		}
	}
	/**
	 * Refresca la mano del jugador
	 */
	public void refreshHand() {
		handGrid.getChildren().clear();
		int i = 0;
		for (Card card : solitaireGame.getPlayer().getHand()) {
			CardComponent cardComp = new CardComponent(card.getCardImage());

			handGrid.add(cardComp, i, 0);
			if (card.isPlayable()) {
				cardComp.setOnMouseClicked(e -> onSelectCard(card, cardComp));
				cardComp.setId("playable");
				cardComp.setFitWidth(85);
				cardComp.setFitHeight(135);
				cardComp.turn();
			} else {
				cardComp.setId("notPlayable");
				cardComp.setFitWidth(75);
				cardComp.setFitHeight(125);
				cardComp.turn();
				if (!solitaireGame.isSaved()) {
					cardComp.setOnMouseClicked(e -> onSaveCard(card, cardComp));
					cardComp.setId("saveable");
					cardComp.setFitWidth(85);
					cardComp.setFitHeight(135);
				}

			}
			i++;
		}
		// checkeamos que la guardada sea playable tambien
		if (solitaireGame.isSaved()) {
			if (solitaireGame.getSavedCard().isPlayable()) {
				savedCard.setOnMouseClicked(e -> onSelectSavedCard());
				savedCard.setId("playable");
				savedCard.setFitWidth(85);
				savedCard.setFitHeight(135);
			} else {
				savedCard.setOnMouseClicked(e -> nothing());
				savedCard.setId("notPlayable");
				savedCard.setFitWidth(75);
				savedCard.setFitHeight(125);
			}
		}
		if (solitaireGame.getDeck().getCards().size() < 1 && solitaireGame.getDiscardedCards().size() < 1
				&& solitaireGame.getPlayer().getHand().size() < 1 && !solitaireGame.isSaved()) {
			endGame();
		}
	}
	/**
	 * Este metodo no hace nada (usado para eliminar cómodamente acciones
	 * de botones)
	 */
	private void nothing() {
		// este metodo no hace nada
	}
	/**
	 * Descarta las cartas de la mano y
	 * las mueve al monton de descarte
	 */
	private void discardCards() {
		int j = solitaireGame.getPlayer().getHand().size() - 1;
		for (int i = j; i >= 0; i--) {
			Card card = solitaireGame.getPlayer().getHand().remove(i);
			solitaireGame.getDiscardedCards().add(card);
		}
	}
	/**
	 * Guarda una carta en la sección de guardadas
	 * @param card
	 * @param cardComp
	 */
	private void onSaveCard(Card card, CardComponent cardComp) {
		solitaireGame.saveCard(card);
		solitaireGame.getPlayer().getHand().remove(card);
		savedCard.setImage(cardComp.getImage());
		refreshHand();
	}
	/**
	 * Lanza la carta guardada
	 */
	private void onSelectSavedCard() {
		solitaireGame.throwCard(solitaireGame.getSavedCard());
		solitaireGame.setSaved(false);
		savedCard.setImage(null);
		refreshHand();
	}
	/**
	 * Lanza ua carta de mano
	 * @param card
	 * @param cardComp
	 */
	private void onSelectCard(Card card, CardComponent cardComp) {
		solitaireGame.throwCard(card);
		int col = GridPane.getColumnIndex(cardComp);

		handGrid.getChildren().remove(cardComp);

		// reordena
		if (handGrid.getChildren().size() > 0) {
			GridPane.setColumnIndex(handGrid.getChildren().get(handGrid.getChildren().size() - 1), col);
		}
		
		refreshHand();
	}
	/**
	 * Muestra la ayuda inicial del solitario
	 */
	private void showInitialHelp() {
		InitialSolitaireHelp help = new InitialSolitaireHelp();

		JFXDialogLayout initialHelp = new JFXDialogLayout();
		initialHelp.setBody(help.getView());
		initialHelp.setStyle("/ui/css/DosBoardStyle.css");
		initialHelp.setId("all");

		JFXDialog initialHelpDialog = new JFXDialog(stack, initialHelp, DialogTransition.CENTER);
		initialHelpDialog.show();

		initialHelp.setOnMouseClicked(e -> initialHelpDialog.close());
	}
	/**
	 * Finaliza la partida
	 */
	private void endGame() {
		
		//Recoge los segundos
		String minutesStr = "";
		for (int i = 0; i < 2; i++) {
			minutesStr += currentTimeLabel.getText().charAt(i);
		}
		int minutes = Integer.parseInt(minutesStr);
		
		//Recoge los segundos
		String secondsStr = "";
		for (int i = 3; i < 5; i++) {
			secondsStr += currentTimeLabel.getText().charAt(i);
		}
		int seconds = Integer.parseInt(secondsStr);
		
		//Recoge las rondas
		int rounds = round.get();
		
		//End game
		SolitaireEndGameController endGameController = new SolitaireEndGameController(currentTimeLabel.getText(), minutes, seconds, rounds);

		timeline.pause();
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setBody(endGameController.getView());

		layout.setId("content");
		layout.getStylesheets().add(getClass().getResource("/ui/css/DosBoardStyle.css").toExternalForm());

		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);
		dialog.setOverlayClose(false);

		JFXButton menu = new JFXButton("Menú");
		menu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				dialog.close();
				ludopp.goMenu();
			}
		});

		menu.setId("button");
		menu.getStylesheets().add(getClass().getResource("/ui/css/EndGame.css").toExternalForm());

		JFXButton exit = new JFXButton("Salir");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				dialog.close();
				exitAction(null);
			}
		});

		exit.setId("button");
		exit.getStylesheets().add(getClass().getResource("/ui/css/EndGame.css").toExternalForm());

		layout.setActions(menu, exit);
		dialog.show();
	}
	/**
	 * Sale del juego
	 * @param event
	 */
	@FXML
	void exitAction(ActionEvent event) {
		Platform.exit();
	}
	/**
	 * Pone el juego en pantalla completa
	 * @param event
	 */
	@FXML
	void fullscreenAction(ActionEvent event) {
		if (!ludopp.getMainStage().isFullScreen()) {
			ludopp.getMainStage().setFullScreen(true);
		} else {
			ludopp.getMainStage().setFullScreen(false);
		}
	}
	/**
	 * Pasa turno
	 * @param event
	 */
	@FXML
	void nextTurnAction(ActionEvent event) {
		discardCards();
		discardCard.setImage(new Image(getClass().getResource("/ui/images/poker/card_back.png").toString()));
		solitaireGame.dealCards();
		refreshHand();
		if (solitaireGame.getDiscardedCards().size() < 1) {
			discardCard.setImage(new Image(getClass().getResource("/ui/images/userNull.png").toString()));
		}
		round.set(round.get()+1);
	}
	/**
	 * Abre la ayuda del juego actual
	 * @param event
	 */
	@FXML
	void openHelp(MouseEvent event) {
		Label helpLabel = new Label("Ayuda");
		helpLabel.setMaxWidth(800);
		helpLabel.setId("tittle");

		HBox tittleBox = new HBox(helpLabel);
		tittleBox.setPrefWidth(800);
		tittleBox.setAlignment(Pos.CENTER);

		help = new HelpViewContoller("Solitaire");

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(help.getView());

		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);

		layout.setId("content");

		layout.maxHeight(200);

		dialog.show();
	}
	/**
	 * Vuelve al menub principal
	 * @param event
	 */
	@FXML
	void returnMenuAction(ActionEvent event) {
		ludopp.goMenu();
	}

	public GridPane getView() {
		return view;
	}
}
