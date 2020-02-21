package gameslib;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import games.Card;
import games.Player;
import gameslib.endGame.EndGameController;
import help.HelpViewContoller;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import main.LudopatApp;
import net.Chat;
import ui.CardComponent;
/**
 * <b>GameControllerDosNET</b> <br>
 * <br>
 * 
 * Controlador del Dos Online
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
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
    
    //ID de los jugadores
    @FXML
    private Label player1ID;
    @FXML
    private Label player2ID;
    @FXML
    private Label player3ID;
    @FXML
    private Label player4ID;

	// ----------------------------------------------------------
	// Variables used by controller
	// ----------------------------------------------------------

	private ArrayList<HBox> playersBox;
	private ArrayList<Label> playersID;
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
	// DAVID: Así como dato también decir que soy un patata
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
		playersID = new ArrayList<>(Arrays.asList(player1ID, player2ID, player3ID, player4ID));
		playersName = new ArrayList<>(Arrays.asList(player1Name, player2Name, player3Name, player4Name));
		playersNumCards = new ArrayList<>(Arrays.asList(player1Cards, player2Cards, player3Cards, player4Cards));
		playersImage = new ArrayList<>(Arrays.asList(player1Image, player2Image, player3Image, player4Image));

		for (int p = 0; p < dosGame.getCurrentPlayers().size(); p++) {

			Player player = dosGame.getCurrentPlayers().get(p);
			Image crown = new Image(getClass().getResourceAsStream("/ui/images/crown.png"));

			if( player == dosGame.getLocalPlayer() ) {
				
				switch (p) {
				
				case 0:
					player1Crown.setImage(crown);
					break;
				case 1:
					player2Crown.setImage(crown);
					break;
				case 2:
					player3Crown.setImage(crown);
					break;
				case 3:
					player4Crown.setImage(crown);
					break;
				}
			}
			
			playersNumCards.get(p).setText(String.format("Número de cartas: %d", player.getHand().size()));
			playersID.get(p).setText("#" + String.valueOf(player.getId()));
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
		
		// Inicializamos el chat
		chat = new Chat(dosGame);
		
		// Visualizamos la primera mano del jugador
		initHand();
		
		if( dosGame.getLocalPlayer() != dosGame.getActivePlayer() ) {
			// Desactivamos los botones de pasar turno y robar
			drawButton.setDisable(true);
			nextButton.setDisable(true);
			disableHand();
		}
		
	}
	/**
	 * Notificación del chat cuando llega un mensaje
	 */
	public void chatNotification() {
		if(chatStack.getChildren().size() > 1) {
			chatButton.setId("notification");
		}
	}

	/**
	 * Desactivar las funcionalidades para este jugador
	 */
	private void disableHand() {
		
		drawButton.setDisable(true);
		nextButton.setDisable(true);
		
		handGrid.getChildren().stream().forEach(node -> {
			node.setDisable(true);
			node.setId("notPlayable");
		});
	}
	
	public void nextTurn() {
		
		enableHand();
		
	}
	
	/**
	 * Activamos las funcionalidades para este jugador.
	 * Normalmente llamado cuando es el turno del jugador
	 */
	private void enableHand() {
		
		drawButton.setDisable(false);
		
		handGrid.getChildren().stream().forEach(node -> {
			node.setDisable(false);
		});
		
		initHand();
	}
	
	/**
	 * Actualiza la mano del jugador y habilita el pasar turno
	 * @param newHand Nueva mano del jugador
	 */
	public void checkUpdatedHand(ArrayList<Card> newHand) {
		
		// Actualizamos la mano del jugador
		refreshHand();
		disableHand();
		
		// Pasar turno ahora está disponible
		nextButton.setDisable(false);
		
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
	/**
	 * Refresca la mano del jugador después de acciones
	 */
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
				final int pos = i;
				cardComp.setOnMouseClicked(e -> onSelectCard(card, cardComp, pos));
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
	/**
	 * Inicializa la mano y los botones del jugador
	 */
	private void initHand() {
		// Número de cartas a robar
		drawButton.setText("Robar (" + dosGame.getCardsToDraw() + ")");
		// Deshabilitamos el pasar turno del jugador
		nextButton.setDisable(true);
		refreshHand();
	}

	private void onSelectCard(Card card, CardComponent cmp, int indexInHand) {
		
		dosGame.client_throwCard(indexInHand);
		
		// Desabilitamos la mano del jugador
		disableHand();
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
	/**
	 * Finaliza la partida, se muestra la interfaz
	 */
	public void endGame() {
		
		// Desactiva el juego
		disableHand();

		JFXDialogLayout layout = new JFXDialogLayout();
	//	layout.setBody(new EndGameController(dosGame.getCurrentPlayers()));

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
		
	}
	/**
	 * Actualiza los contadores de cartas al final de la partida
	 */
	//Joel: Usenlo en el endgame o el ultimo que tire carta se va a quedar con 1 en el label potats
	public void updateCardCounters() {
		for (int i = 0; i < dosGame.getCurrentPlayers().size(); i++) {
			playersNumCards.get(i).setText(String.format("Número de cartas: %d", dosGame.getCurrentPlayers().get(i).getHand().size()));
		}
	}

	@FXML
	void drawCardAction(ActionEvent event) {
		
		dosGame.client_drawCard();
		
		// Desactivamos la mano del jugador
		disableHand();
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
		dosGame.client_nextTurn();
		disableHand();
	}

	@FXML
	void returnMenuAction(ActionEvent event) {
		ludopp.onlineGoMenu();
	}
	/**
	 * Notifica a los jugadores de la desconexion de otro de los jugadores y vuelve al menu
	 */
	public void notifyDisconnectDialog() {
		
		// Desabilitamos todos los controles
		disableHand();
		exitButton.setDisable(true);
		closeButton.setDisable(true);
		fullScreenButton.setDisable(true);
		chatButton.setDisable(true);
		
		Label disconnectLabel = new Label("Cliente desconectado");
		disconnectLabel.setMaxWidth(800);
		disconnectLabel.setId("tittle");
		
		HBox tittleBox = new HBox(disconnectLabel);
		tittleBox.setPrefWidth(800);
		tittleBox.setAlignment(Pos.CENTER);
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(new Label("Saliendo al menú principal...."));
		layout.setId("content");
		
		layout.maxHeight(200);
		
		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);
		dialog.show();
		Timer timer = new Timer();
		
		timer.schedule( new TimerTask() {
			
			@Override
			public void run() {
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						ludopp.goMenu();
						
					}
				});
				
			}
			
		} ,3000);
		
	}
	/**
	 * 
	 * Abre la ayuda 
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

		help = new HelpViewContoller("Dos");

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(help.getView());

		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);

		layout.setId("content");

		layout.maxHeight(200);

		dialog.show();
	}
	/**
	 * Abre el chat
	 * @param event
	 */
    @FXML
    void openChat(ActionEvent event) {
    	chatButton.setId("chatButton");
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

	public void closeChat() {
		chatButton.setId("chatButton");
		chatStack.getChildren().remove(chat.getView());
	}
	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

    @FXML
    void keys(KeyEvent event) {
    	switch (event.getCode()) {
		case C:
			//C presionada, acción de abrir y cerrar el chat.
			if (chatStack.getChildren().size() == 2) {
    			openChat(null);
    		}else {
    			closeChat();
    		}
			break;
		case F:
			//F presionada, acción de pantalla completa.
			fullscreenAction(null);
			break;
		default:
			//Acción no controlada.
			break;
		}
    }
	
}