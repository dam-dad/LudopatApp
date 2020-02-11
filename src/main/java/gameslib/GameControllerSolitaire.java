package gameslib;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import games.Card;
import help.HelpViewContoller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.LudopatApp;
import ui.CardComponent;

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

	// variables
	private LudopatApp ludopp;
	private Solitaire solitaireGame;
	private HelpViewContoller help;

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
		gameNameLabel.textProperty().bind(solitaireGame.getGameRules().gameTypeProperty());

		refreshHand();
	}

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
			} else {
				if (solitaireGame.getSavedCard() == null) {
					cardComp.setOnMouseClicked(e -> onSaveCard(card, cardComp));
				}
				cardComp.setId("notPlayable");
				cardComp.setFitWidth(75);
				cardComp.setFitHeight(125);
			}
			i++;
		}
		// checkeamos que la guardada sea playable tambien
		if (solitaireGame.getSavedCard() != null) {
			if (solitaireGame.getSavedCard().isPlayable()) {
				savedCard.setOnMouseClicked(e -> onSelectSavedCard());
				savedCard.setId("playable");
				savedCard.setFitWidth(85);
				savedCard.setFitHeight(135);
			} else {

			}
		}
	}

	private void onSaveCard(Card card, CardComponent cardComp) {
		solitaireGame.saveCard(card);
		savedCard.setImage(cardComp.getImage());

	}

	private void onSelectSavedCard() {
		solitaireGame.throwCard(solitaireGame.getSavedCard());
		solitaireGame.setSavedCard(null);
		savedCard.setImage(null);
	}

	private void onSelectCard(Card card, CardComponent cardComp) {
		solitaireGame.throwCard(card);
		int col = GridPane.getColumnIndex(cardComp);
		handGrid.getChildren().remove(cardComp);

		// reordena
		GridPane.setColumnIndex(handGrid.getChildren().get(handGrid.getChildren().size() - 1), col);

		if (solitaireGame.getDeck().getCards().size() < 1 && solitaireGame.getDiscardedCards().size() < 1
				&& solitaireGame.getPlayer().getHand().size() < 1) {
			endGame();
		}

	}

	private void endGame() {
		// TODO Auto-generated method stub

	}

	@FXML
	void exitAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void fullscreenAction(ActionEvent event) {
		if (!ludopp.getMainStage().isFullScreen()) {
			ludopp.getMainStage().setFullScreen(true);
		} else {
			ludopp.getMainStage().setFullScreen(false);
		}
	}

	@FXML
	void nextTurnAction(ActionEvent event) {

	}

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

	@FXML
	void returnMenuAction(ActionEvent event) {
		ludopp.goMenu();
	}
}
