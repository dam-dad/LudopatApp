package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.dom4j.DocumentException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import help.HelpViewContoller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.LudopatApp;
import ui.config.DeckConfigController;
import ui.config.GameConfigController;
import ui.config.PlayerSelectionController;
import ui.config.SummaryController;

/**
 * 
 * <b>Controlador vista multijugador</b>
 * <br><br>
 * 
 * Controlador para los ajustes de multijugador.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class MultiplayerController implements Initializable {
	
	// FXML : View
	//--------------------------------------------------
	
	@FXML
	private BorderPane view;
	
	@FXML
	private Button backButton, continueButton, menuButton;
	
	@FXML
	private Label pageLabel;
	
	@FXML
	private SplitPane configPane;
	
	@FXML
	private ImageView helpImage;
	
	@FXML
	private StackPane stack;
	
	//--------------------------------------------------
	// Variables
	//--------------------------------------------------
	
	private static final int ANCHOR_WIDTH = 800;
	
	enum e_menuStages {
		
		ST_CONFIG_GAME, 
		ST_CONFIG_INGAME, 
		ST_CONFIG_PLAYERS, 
		ST_CONFIG_SUMMARY
	}
	
	private e_menuStages currentStage;
	
	private GameConfigController gameConfig;
	private DeckConfigController deckConfig;
	private PlayerSelectionController playerConfig;
	private SummaryController summary;
	
	private LudopatApp ludopp;
	
	private final int TRANSITION_TIME = 500;
	
	private KeyValue key;
	private Timeline timeline;
	
	private final String CONTINUE = "Continuar";
	private final String PLAY = "Jugar";

	private HelpViewContoller help;
	
	//--------------------------------------------------
	
	// Model
	private IntegerProperty currentPage = new SimpleIntegerProperty();
	
	public MultiplayerController(LudopatApp app) throws IOException {
		
		this.ludopp = app;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MPConfigView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Bindings
		pageLabel.textProperty().bind( currentPage.asString() );
		
		gameConfig = new GameConfigController(ludopp);
		deckConfig = new DeckConfigController(ludopp);
		playerConfig = new PlayerSelectionController(ludopp);
		summary = new SummaryController(ludopp);
		
		// Para evitar que el usuario pueda arrastrar las ventanas
		gameConfig.setMaxWidth(ANCHOR_WIDTH);
		deckConfig.setMaxWidth(0);
		playerConfig.setMaxWidth(0);
		summary.setMaxWidth(0);
		
		// Añadimos los datos de configuración
		configPane.getItems().setAll(gameConfig, deckConfig, playerConfig, summary);
		configPane.getItems().set(0, gameConfig);
		configPane.getItems().set(1, deckConfig);
		configPane.getItems().set(2, playerConfig);
		configPane.getItems().set(3, summary);
		
		// Precargamos los paneles y los ponemos en posición
		configPane.setDividerPositions(1, 1, 1);
		
		backButton.disableProperty().bind( currentPage.isEqualTo(1));
		
		currentStage = e_menuStages.ST_CONFIG_GAME;
		currentPage.setValue(1);
	}
	
	private void nextStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_GAME:
				
				try {
					// Cargamos los ajustes para el juego seleccionado
					ludopp.getGameRules().initGameType();
					
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				
				deckConfig.selectDecks();
				
				key = new KeyValue(configPane.getDividers().get(0).positionProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(gameConfig.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(deckConfig.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
				
				break;
			case ST_CONFIG_INGAME:
				
				playerConfig.refresh();
				
				key = new KeyValue(configPane.getDividers().get(1).positionProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(deckConfig.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(playerConfig.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
			
				break;
			case ST_CONFIG_PLAYERS:
				
				playerConfig.closeDialog(-1);
				
				key = new KeyValue(configPane.getDividers().get(2).positionProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(playerConfig.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(summary.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
	          
	            // Ponemos los jugadores
	            ludopp.getGameRules().setPlayersInfo(playerConfig.getPlayersInfo());
	            
	            summary.initSummary();
	            continueButton.setText(PLAY);
	            
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	private void previousStage() {
		
		switch (currentStage) {
		
			case ST_CONFIG_INGAME:
				
				key = new KeyValue(configPane.getDividers().get(0).positionProperty(), 1);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(deckConfig.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(gameConfig.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
				
				break;
			case ST_CONFIG_PLAYERS:
				playerConfig.closeDialog(-1);
				
				key = new KeyValue(configPane.getDividers().get(1).positionProperty(), 1);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(playerConfig.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(deckConfig.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
	            
				break;
			case ST_CONFIG_SUMMARY:
				
				key = new KeyValue(configPane.getDividers().get(2).positionProperty(), 1);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(summary.maxWidthProperty(), 0);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();

				key = new KeyValue(playerConfig.maxWidthProperty(), ANCHOR_WIDTH);
	            timeline = new Timeline(new KeyFrame(Duration.millis(TRANSITION_TIME), key));
	            timeline.play();
	            
	            
	            continueButton.setText(CONTINUE);
	            
				break;
			default:
				break;
		}
		
		currentStage = e_menuStages.values()[currentPage.get()-1];
	}
	
	@FXML
	void onBackAction(ActionEvent event) {
		currentPage.setValue(currentPage.getValue()-1);
		previousStage();
	}
	
    @FXML
    void onContinueAction(ActionEvent event) {
		
		if( currentPage.getValue() == 4 ) {
			ludopp.initGame();
		} else {
			currentPage.setValue(currentPage.getValue()+1);
			nextStage();
		}
	}
	
	@FXML
	void onMenuAction(ActionEvent event) {
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
		
		help = new HelpViewContoller("Config");
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(tittleBox);
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
