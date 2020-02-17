package gameslib;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import games.Card;
import games.Player;
import help.HelpViewContoller;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import main.LudopatApp;
import net.Chat;
import ui.CardComponent;

public class GameControllerDosNET implements Initializable {

	// FXML : View
	// ----------------------------------------------------------

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
    private JFXButton closeButton;

    @FXML
    private JFXButton fullScreenButton;

    @FXML
    private StackPane chatStack;

    @FXML
    private JFXButton chatButton;

    @FXML
    private VBox players;

    @FXML
    private HBox player1;

    @FXML
    private ImageView player1Image;

    @FXML
    private ImageView player1Crown;

    @FXML
    private Label player1Name;

    @FXML
    private Label player1Cards;

    @FXML
    private HBox player2;

    @FXML
    private ImageView player2Image;

    @FXML
    private ImageView player2Crown;

    @FXML
    private Label player2Name;

    @FXML
    private Label player2Cards;

    @FXML
    private HBox player3;

    @FXML
    private ImageView player3Image;

    @FXML
    private ImageView player3Crown;

    @FXML
    private Label player3Name;

    @FXML
    private Label player3Cards;

    @FXML
    private HBox player4;

    @FXML
    private ImageView player4Image;

    @FXML
    private ImageView player4Crown;

    @FXML
    private Label player4Name;

    @FXML
    private Label player4Cards;

    @FXML
    private VBox table;

    @FXML
    private StackPane stack;

    @FXML
    private ImageView currentCard;

    @FXML
    private ImageView deckCard;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton drawButton;

    @FXML
    private GridPane handGrid;

	// ----------------------------------------------------------
	// Variables used by controller
	// ----------------------------------------------------------

	private ArrayList<HBox> playersBox;
	private ArrayList<Label> playersName;
	private ArrayList<Label> playersNumCards;
	private ArrayList<ImageView> playersImage;

	private LudopatApp ludopp;
	private Dos dosGame;
	private StringProperty gameType = new SimpleStringProperty();
	
	private HelpViewContoller help;
	private Chat chat;

	// ----------------------------------------------------------

	// DAVID: Aún necesita más adaptaciones para la versión online,
	// como el lanzamiento de carta, el pasar turno y el robo
	public GameControllerDosNET(LudopatApp app, String gameType) {
		this.ludopp = app;
		this.dosGame = (Dos) ludopp.getCurrentGame();
		setGameType(gameType);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/OnlineDosGameView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// En un principio deshabilitamos los dos últimos jugadores, puesto
		// que no siempre van a jugar
		player3.setVisible(false);
		player4.setVisible(false);

		// Añadimos los datos de los jugadores
		playersBox = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
		playersName = new ArrayList<>(Arrays.asList(player1Name, player2Name, player3Name, player4Name));
		playersNumCards = new ArrayList<>(Arrays.asList(player1Cards, player2Cards, player3Cards, player4Cards));
		playersImage = new ArrayList<>(Arrays.asList(player1Image, player2Image, player3Image, player4Image));

		for (int p = 0; p < dosGame.getCurrentPlayers().size(); p++) {

			Player player = dosGame.getCurrentPlayers().get(p);
			playersNumCards.get(p).setText(String.format("Número de cartas: %d", player.getHand().size()));
			playersName.get(p).textProperty().bind(player.getPlayerInfo().playerNameProperty());
			playersImage.get(p).imageProperty().bind(player.getPlayerInfo().playerIconProperty());
			playersBox.get(p).setVisible(true);
		}

		// Bindings
		numberLabel.setText(String.valueOf(dosGame.getCurrentValue()));
		currentCard.setImage(dosGame.getLastCard().getCardImage());
		gameNameLabel.textProperty().bind(gameType);
		numberLabel.textProperty().bindBidirectional(dosGame.currentValueProperty(), new NumberStringConverter());

		// Cambios que suceden en el juego y se reflejan en la interfaz
		dosGame.currentColorProperty().addListener((o, ov, nv) -> onChangedImageColor(nv));
		dosGame.lastCardProperty().addListener((o, ov, nv) -> onChangeCard(nv));
		dosGame.activePlayerProperty().addListener((o, ov, nv) -> onChangedPlayer(ov, nv));
		
		onChangedImageColor(dosGame.getCurrentColor());
		onChangedPlayer(null, dosGame.getActivePlayer());
		
		if( dosGame.getLocalPlayer() != dosGame.getActivePlayer() ) {
			// Desactivamos los botones de pasar turno y robar
			drawButton.setDisable(true);
			nextButton.setDisable(true);
		}
		chat = new Chat(dosGame);
		// Visualizamos la primera mano del jugador
		initHand();
	}

	private void onChangedPlayer(Player ov, Player nv) {

		// Quitamos el estilo a uno y se lo ponemos a otro
		if (ov != null) {
			
			Optional<Player> ovPlayer = dosGame.getCurrentPlayers().stream().filter(
					p -> p.getPlayerInfo().getUserID() == ov.getPlayerInfo().getUserID()).findFirst();
			
			if( ovPlayer.isPresent() ) {
				int lpIndex = dosGame.getPlayerPosition(ovPlayer.get());
				playersBox.get(lpIndex).setStyle(null);
			}
		}

		if (nv != null) {
			
			Optional<Player> nvPlayer = dosGame.getCurrentPlayers().stream().filter(
					p -> p.getPlayerInfo().getUserID() == nv.getPlayerInfo().getUserID()).findFirst();
			
			if( nvPlayer.isPresent() ) {

				int npIndex = dosGame.getPlayerPosition(nvPlayer.get());
				playersBox.get(npIndex).setStyle("-fx-effect: dropshadow(gaussian, white, 20, 0.5, 0, 0);");
			}
		}
	}
	

	private void onChangeCard(Card nv) {

		if (nv != null) {
			currentCard.setImage(nv.getCardImage());
		}
	}

	public void refreshHand() {

		// Limpiamos la mano actual del jugador y la actualizamos
		handGrid.getChildren().clear();

		int i = 0;
		dosGame.startTurn();
		for (Card card : dosGame.getLocalPlayer().getHand()) {
			CardComponent cardComp = new CardComponent(card.getCardImage());
			cardComp.turn();
			handGrid.add(cardComp, i, 0);

			if (card.isPlayable()) {
				cardComp.setOnMouseClicked(e -> onSelectCard(card, cardComp));
				cardComp.setId("playable");
				cardComp.setFitWidth(85);
				cardComp.setFitHeight(135);
			} else {
				cardComp.setId("notPlayable");
				cardComp.setFitWidth(75);
				cardComp.setFitHeight(125);
			}
			i++;
		}

	}

	private void initHand() {
		// Número de cartas a robar
		drawButton.setText("Robar (" + dosGame.getCardsToDraw() + ")");
		// Deshabilitamos el pasar turno del jugador
		nextButton.setDisable(true);
		refreshHand();
	}

	private void onSelectCard(Card card, CardComponent cmp) {
		/*
		dosGame.throwCard(card);

		playersNumCards.get(dosGame.getPlayerPosition(dosGame.getActivePlayer()))
				.setText(String.format("Número de cartas: %d", dosGame.getActivePlayer().getHand().size()));

		int ourCol = GridPane.getColumnIndex(cmp);
		boolean needsReOrder = false;

		// Si no ha sido la última carta del jugador y
		// no era la última de la mano, reordenamos el
		// grid
		if (dosGame.getActivePlayer().getHand().size() > 0 && ourCol != handGrid.getChildren().size() - 1) {

			needsReOrder = true;
		}

		// Eliminamos la carta
		handGrid.getChildren().remove(cmp);

		// Si necesitamos reordenar
		if (needsReOrder)
			GridPane.setColumnIndex(handGrid.getChildren().get(handGrid.getChildren().size() - 1), ourCol);

		// Deshabilitamos las cartas del jugador
		handGrid.getChildren().stream().forEach(node -> {
			CardComponent cc = (CardComponent) node;
			cc.setDisable(true);
			cc.setId("notPlayable");
			cc.setFitWidth(75);
			cc.setFitHeight(125);
		});

		drawButton.setDisable(true);
		nextButton.setDisable(false);

		if (dosGame.getActivePlayer().getHand().size() < 1) {
			nextButton.setDisable(true);
			endGame();
		}
		*/
	}

	private void onChangedImageColor(String nv) {
		switch (nv) {
		case "white":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/white/actualwhite.png").toString()));
			break;
		case "blue":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/blue/actualblue.png").toString()));
			break;
		case "green":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/green/actualgreen.png").toString()));
			break;
		case "yellow":
			color.setImage(new Image(getClass().getResource("/ui/images/dos/yellow/actualyellow.png").toString()));
			break;
		}
	}

	public void endGame() {
		/*
		updateCardCounters();
		dosGame.endGame();

		// Desactiva el juego
		nextButton.setDisable(true);
		drawButton.setDisable(true);

		handGrid.getChildren().stream().forEach(node -> {
			node.setDisable(true);
		});

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setBody(new EndGameController(dosGame.getCurrentPlayers()));

		layout.setId("bg");
		layout.getStylesheets().add(getClass().getResource("/ui/css/EndGame.css").toExternalForm());

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
		*/
	}

	private void updateCardCounters() {
		for (int i = 0; i < playersNumCards.size(); i++) {
			playersNumCards.get(i).setText(String.format("Número de cartas: %d", dosGame.getCurrentPlayers().get(i).getHand().size()));
		}
	}

	@FXML
	void drawCardAction(ActionEvent event) {
		/*
		if (!dosGame.getDeck().getCards().isEmpty()) {
			dosGame.drawCard();

			// Volvemos a habilitar el pasar turno
			nextButton.setDisable(false);
			// Actualizamos el número de cartas del jugador
			playersNumCards.get(dosGame.getPlayerPosition(dosGame.getActivePlayer()))
					.setText(String.format("Número de cartas: %d", dosGame.getActivePlayer().getHand().size()));

			// Ahora tenemos que añadir las cartas robadas
			refreshHand();
			showHand();

			if (dosGame.getDeck().getCards().isEmpty()) {
				endGame();
			}
		}

		// El jugador no puede hacer nada más
		handGrid.getChildren().stream().forEach(node -> {
			CardComponent cc = (CardComponent) node;
			cc.setDisable(true);
			cc.setId("notPlayable");
			cc.setFitWidth(75);
			cc.setFitHeight(125);
		});
		drawButton.setDisable(true);
		*/
	}

	@FXML
	void exitAction(ActionEvent event) {
		// dialogo confirmacion
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
		/*
		drawButton.setDisable(false);

		dosGame.nextTurn();// esto cambia el jugador activo
*/
		// Si hay un bloqueo activo, saltamos al siguiente
//		if (dosGame.isBlocked()) {
//			dosGame.setBlocked(false);
//			dosGame.nextTurn();
//		}
	}

	@FXML
	void returnMenuAction(ActionEvent event) {
		ludopp.goMenu();
	}

	@FXML
	void openHelp(MouseEvent event) {

		Label helpLabel = new Label("Ayuda");
		helpLabel.setMaxWidth(800);
		helpLabel.setId("tittle");

		HBox tittleBox = new HBox(helpLabel);
		tittleBox.setPrefWidth(800);
		tittleBox.setAlignment(Pos.CENTER);

		help = new HelpViewContoller("Dos");

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(help.getView());

		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);

		layout.setId("content");

		layout.maxHeight(200);

		dialog.show();
	}
	
    @FXML
    void openChat(ActionEvent event) {
    	chatStack.getChildren().add(chat.getView());
    }

	public GridPane getView() {
		return view;
	}

	public final StringProperty gameTypeProperty() {
		return this.gameType;
	}
	

	public final String getGameType() {
		return this.gameTypeProperty().get();
	}
	

	public final void setGameType(final String gameType) {
		this.gameTypeProperty().set(gameType);
	}
	
}