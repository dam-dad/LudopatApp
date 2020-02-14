package uinet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import help.HelpViewContoller;
import javafx.fxml.Initializable;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.LudopatApp;

import ui.config.PlayerSelectionController;

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
	private SplitPane configPane;

	@FXML
	private Button menuButton;

	@FXML
	private Button backButton;

	@FXML
	private Button continueButton;
	
	//parametros
	private final String CONTINUE = "Continuar";
	private final String PLAY = "Jugar";
	private static final int ANCHOR_WIDTH = 800;
	private final int TRANSITION_TIME = 500;
	enum e_menuStages {

		ST_CONFIG_PLAYERS, ST_CONFIG_IP, ST_CONFIG_WAITING

	}
	private e_menuStages currentStage;

	//controladores
	private LudopatApp ludopp;
	private PlayerSelectionController playerSelection;
	private IpConfigController ipConfig;
	private WaitingRoomController waitingRoom;
	private HelpViewContoller help;

	//utilidades
	private KeyValue key;
	private Timeline timeline;
	
	//variables
	private IntegerProperty currentPage = new SimpleIntegerProperty();
	
	public ClientConfigController(LudopatApp ludopp) {
		this.ludopp = ludopp;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/ClientConfigView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//bindings
		pageLabel.textProperty().bind( currentPage.asString() );
		
		//inicializar controladores
		playerSelection = new PlayerSelectionController(ludopp);
		ipConfig = new IpConfigController(ludopp);
		waitingRoom = new WaitingRoomController(ludopp);
		
		//evitar movimientos
		playerSelection.setMaxWidth(ANCHOR_WIDTH);
		ipConfig.setMaxWidth(0);
		waitingRoom.setMaxWidth(0);
		
		//añadir los datos 
		configPane.getItems().addAll(playerSelection, ipConfig, waitingRoom);
		configPane.getItems().set(0, playerSelection);
		configPane.getItems().set(1, ipConfig);
		configPane.getItems().set(2, waitingRoom);
		
		//movemos los separadores
		configPane.setDividerPositions(1,1);
		
		//desactivar boton atras en pagina 1
		backButton.disableProperty().bind( currentPage.isEqualTo(1));
		
		currentStage = e_menuStages.ST_CONFIG_PLAYERS;
		currentPage.setValue(1);
	}

	@FXML
	void onBackAction(ActionEvent event) {

	}

	@FXML
	void onContinueAction(ActionEvent event) {

	}

	@FXML
	void onMenuAction(ActionEvent event) {

	}

	@FXML
	void openHelp(MouseEvent event) {
	}
}
