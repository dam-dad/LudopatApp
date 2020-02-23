package uinet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import games.PlayerInfo;

import com.jfoenix.controls.JFXDialog.DialogTransition;

import help.HelpViewContoller;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.LudopatApp;
import ui.config.PlayerSelectionController;
/**
 * <b>ClientConfigController</b> <br>
 * <br>
 * 
 * Controlador de la vista de configuracion de cliente multijugador
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class ClientConfigController implements Initializable {

	@FXML
	private StackPane stack;

	@FXML
	private BorderPane view;

	@FXML
	private ImageView appImage;

	@FXML
	private ImageView helpImage;

	@FXML
	private Label pageLabel;

	@FXML
    private HBox hox;
	
	@FXML
	private SplitPane configPane;

	@FXML
	private Button menuButton;

	@FXML
	private Button backButton;

	@FXML
	private Button continueButton;
	
	private static double xOffset = 0;
    private static double yOffset = 0;

	// parametros
	private static final int ANCHOR_WIDTH = 800;
	private final int TRANSITION_TIME = 500;
	
	enum e_menuStages {

		ST_CONFIG_PLAYERS, ST_CONFIG_IP, ST_CONFIG_WAITING

	}

	private e_menuStages currentStage;

	// controladores
	private LudopatApp ludopp;
	private PlayerSelectionController playerSelection;
	private IpConfigController ipConfig;
	private WaitingRoomController waitingRoom;
	private HelpViewContoller help;

	// utilidades
	private KeyValue key;
	private Timeline timeline;
	private boolean inServer;
	
	// variables
	private IntegerProperty currentPage = new SimpleIntegerProperty();

	public ClientConfigController(LudopatApp ludopp) {
		this.ludopp = ludopp;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/ClientConfigView.fxml"));
		loader.setController(this);
		try {
			loader.load();
			
			setColors();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings
		pageLabel.textProperty().bind(currentPage.asString());

		// inicializar controladores
		playerSelection = new PlayerSelectionController(ludopp);
		playerSelection.setNetworkClient(true); // Somos usuarios online
		ipConfig = new IpConfigController(ludopp);
		waitingRoom = new WaitingRoomController(ludopp);

		// evitar movimientos
		playerSelection.setMaxWidth(ANCHOR_WIDTH);
		ipConfig.setMaxWidth(0);
		waitingRoom.setMaxWidth(0);

		// añadir los datos
		configPane.getItems().setAll(playerSelection, ipConfig, waitingRoom);
		configPane.getItems().set(0, playerSelection);
		configPane.getItems().set(1, ipConfig);
		configPane.getItems().set(2, waitingRoom);

		// movemos los separadores
		configPane.setDividerPositions(1, 1);

		// desactivar boton atras en pagina 1
		//backButton.disableProperty().bind(currentPage.isEqualTo(1));
		backButton.setDisable(true);

		currentStage = e_menuStages.ST_CONFIG_PLAYERS;
		currentPage.setValue(1);
		setMovingHandler();
	}
	
	private void setColors() {
		stack.getStylesheets().remove(0);

		if (ludopp.isWhiteMode()) {
			// Modo claro
			helpImage.setImage(new Image(getClass().getResource("/ui/images/whiteModeHelp.png").toString()));
			stack.getStylesheets().add(getClass().getResource("/ui/css/whiteMode/ConfigMenuStyle.css").toString());
		} else {
			// Modo oscuro
			helpImage.setImage(new Image(getClass().getResource("/ui/images/help.png").toString()));
			stack.getStylesheets().add(getClass().getResource("/ui/css/ConfigMenuStyle.css").toString());
		}
	}
	
	/**
	 * Crea un evento para poder mover la ventana al clickar y arrastrar
	 */
	private void setMovingHandler() {
		hox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = ludopp.getMainStage().getX() - event.getScreenX();
                yOffset = ludopp.getMainStage().getY() - event.getScreenY();
            }
        });
		hox.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	ludopp.getMainStage().setX(event.getScreenX() + xOffset);
            	ludopp.getMainStage().setY(event.getScreenY() + yOffset);
            }
        });	
	}
	/**
	 * Muestra la sala de espera a los usuarios
	 * @param players Lista de jugadores
	 * @param serverIP Ip del servidor
	 */
	public void showWaitingRoom(ArrayList<PlayerInfo> players, String serverIP) {

		if( !inServer ) {
			
			key = new KeyValue(configPane.getDividers().get(1).positionProperty(), 0);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();
	
			key = new KeyValue(ipConfig.maxWidthProperty(), 0);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();
	
			key = new KeyValue(waitingRoom.maxWidthProperty(), ANCHOR_WIDTH);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();
			
			waitingRoom.setWaitingRoomIP(serverIP); // Ponemos la IP del servidor
			inServer = true;
		}
		
		waitingRoom.refresh(players);
	}
	
	@FXML
	void onBackAction(ActionEvent event) {
		currentPage.setValue(currentPage.getValue() - 1);
		previousStage();
	}
	/**
	 * Lleva al usuario a la anterior sección de la configuracion
	 */
	private void previousStage() {

		key = new KeyValue(configPane.getDividers().get(0).positionProperty(), 1);
		timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
		timeline.play();

		key = new KeyValue(ipConfig.maxWidthProperty(), 0);
		timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
		timeline.play();

		key = new KeyValue(playerSelection.maxWidthProperty(), ANCHOR_WIDTH);
		timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
		timeline.play();
		
		backButton.setDisable(true);
		currentStage = e_menuStages.values()[currentPage.get() - 1];

	}
	
	/**
	 * Ha habido algún error en la conexión, 
	 * volvemos a restaurar la ventana
	 */
	public void resetConnectionStatus() {
		
		Label notFoundLabel = new Label("Error de conexión");
		notFoundLabel.setMaxWidth(800);
		notFoundLabel.setId("tittle");
		
		HBox tittleBox = new HBox(notFoundLabel);
		tittleBox.setPrefWidth(800);
		tittleBox.setAlignment(Pos.CENTER);
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
		layout.setBody(new Label("No ha sido posible conectar con el servidor"));
		layout.getStylesheets().add(getClass().getResource("/ui/css/DosBoardStyle.css").toString());
		layout.setPrefHeight(200);
		layout.setId("content");
		
		layout.maxHeight(200);
		
		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);
		dialog.show();
		
		ipConfig.setConnectionStatus(false);
		menuButton.setDisable(false);
		continueButton.setDisable(false);
		backButton.setDisable(false);
		continueButton.setId("");
		backButton.setId("");
		menuButton.setId("");
		currentPage.setValue(2);
	}
	
	/**
	 * Conecta al usuario con el cliente
	 * @param event
	 */
	@FXML
	void onContinueAction(ActionEvent event) {
		
		if( currentPage.getValue() == 2 ) {
			
			// Entonces conectamos con el cliente
			playerSelection.closeDialog(-1);
			playerSelection.refresh();
			ipConfig.setConnectionStatus(true);
			ludopp.getUserClient().setPlayerInfo(playerSelection.getPlayersInfo().get(0));
			ludopp.initClient(ipConfig.getIp());		
			continueButton.setDisable(true);
			backButton.setDisable(true);
			continueButton.setId("disable");
			backButton.setId("disable");
			menuButton.setDisable(true);
			menuButton.setId("disable");
			currentPage.setValue(3);
		}
		
		else {
			
			currentPage.setValue(currentPage.getValue() + 1);
			nextStage();
		}
		
	}
	/**
	 * Lleva al usuario a la siguiente seccion de la pantalla de configuracion
	 */
	private void nextStage() {
		switch (currentStage) {

		case ST_CONFIG_PLAYERS:
			playerSelection.closeDialog(-1);
			playerSelection.refresh();

			key = new KeyValue(configPane.getDividers().get(0).positionProperty(), 0);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();

			key = new KeyValue(playerSelection.maxWidthProperty(), 0);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();

			key = new KeyValue(ipConfig.maxWidthProperty(), ANCHOR_WIDTH);
			timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
			timeline.play();
			backButton.setDisable(false);
			
			ludopp.getUserClient().setPlayerInfo(playerSelection.getPlayersInfo().get(0));

			break;
		default:
			break;
		}

		currentStage = e_menuStages.values()[currentPage.get() - 1];

	}

	@FXML
	void onMenuAction(ActionEvent event) {
		ludopp.onlineGoMenu();
	}

	@FXML
	void openHelp(MouseEvent event) {
		Label helpLabel = new Label("Ayuda");
		helpLabel.setMaxWidth(800);
		helpLabel.setId("tittle");

		HBox titleBox = new HBox(helpLabel);
		titleBox.setPrefWidth(800);
		titleBox.setAlignment(Pos.CENTER);

		help = new HelpViewContoller("Config");

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(titleBox);
		layout.setBody(help.getView());

		JFXDialog dialog = new JFXDialog(stack, layout, DialogTransition.CENTER);

		layout.setId("content2");

		layout.maxHeight(200);

		dialog.show();
	}

	public StackPane getView() {
		return stack;
	}
}
