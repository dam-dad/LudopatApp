package main;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;
import gameslib.Dos;
import gameslib.GameControllerDos;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.MainMenuController;
import ui.MultiplayerController;
import ui.SplashController;

/**
 * 
 * <b>Aplicación LudtopatApp</b> <br>
 * <br>
 * 
 * Aplicación principal del gestor de juegos de cartas
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class LudopatApp extends Application {

	// Controladores
	//---------------------------------------------------
	
	private MultiplayerController multiplayerController;
	private MainMenuController mainMenuController;
	private GameControllerDos gameControllerDos;
	
	//---------------------------------------------------
	
	// Variables de la App
	//---------------------------------------------------
	
	private GameRules gameRules;
	private Stage mainStage;
	
	public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	
	private Game currentGame;
	
	private final int DOS_SCREEN_WIDTH_REQUIRED = 1250;
	private final int DOS_SCREEN_HEIGHT_REQUIRED = 700;
	
	private final int MENU_SCREEN_WIDTH_REQUIRED = 800;
	private final int MENU_SCREEN_HEIGHT_REQUIRED = 600;
	//-------- -------------------------------------------



	@Override
	public void start(Stage primaryStage) throws Exception {

		mainStage = primaryStage;
		mainStage.setResizable(false);
		
		// Inicamos la aplicación, el SplashScreen
		initApp();

		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
		
	}

	// Métodos llamados desde vistas
	// -------------------------------------------------------------------

	public void goMultiplayerMenu() {

		try {

			gameRules = new GameRules();
			multiplayerController = new MultiplayerController(this);
			
			Scene scene = new Scene(multiplayerController.getView(), 800, 600);

			FadeTransition fadeTransition = new FadeTransition();
			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.setDuration(Duration.millis(375));
			fadeTransition.setNode(mainMenuController.getView());
			fadeTransition.setOnFinished(ae -> mainStage.setScene(scene));

			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), ae -> fadeTransition.play()));
			timeline.play();

			FadeTransition fadeTransitionOut = new FadeTransition();
			fadeTransitionOut.setFromValue(1);
			fadeTransitionOut.setToValue(0);
			fadeTransitionOut.setDuration(Duration.millis(375));
			fadeTransitionOut.setNode(mainMenuController.getView());

			Timeline timelineOut = new Timeline(new KeyFrame(Duration.millis(1), ae -> fadeTransitionOut.play()));
			timelineOut.play();
			
			alignScreenMenu();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initApp() {
		
		try {
			SplashController splashController = new SplashController(this);

			Scene scene = new Scene(splashController.getView(), 800, 600);
			mainStage.setScene(scene);
			
			alignScreenMenu();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	public void goMenu() {
		
		try {
			mainMenuController = new MainMenuController(this);

			Scene scene = new Scene(mainMenuController.getView(), 800, 600);
			mainStage.setScene(scene);
			
			alignScreenMenu();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initGame() {

		String gameType = gameRules.getGameType();
		
		switch (gameType) {

			case "dos":
				initDosGame();
				break;
			default:
				break;
		}
		
		alignScreen();
	}
	
	private void alignScreen() {
		double screenWidthCenter = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		double screenHeightCenter = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		
		mainStage.setX(screenWidthCenter - DOS_SCREEN_WIDTH_REQUIRED/2);
		mainStage.setY(screenHeightCenter - DOS_SCREEN_HEIGHT_REQUIRED/2);
	}
	
	private void alignScreenMenu() {
		double screenWidthCenter = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		double screenHeightCenter = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		
		mainStage.setX(screenWidthCenter - MENU_SCREEN_WIDTH_REQUIRED/2);
		mainStage.setY(screenHeightCenter - MENU_SCREEN_HEIGHT_REQUIRED/2);
	}

	// -------------------------------------------------------------------

	public GameRules getGameRules() {
		return gameRules;
	}
	
	// Lógica distintos juegos
	//-----------------------------------------------------------
	
	public void initDosGame() {

		Deck deck = gameRules.getDeckType();
		deck.loadCards("dos");
		
		Dos.loadSpecialCards(deck, this.getClass());
		if( deck.isDoubleDeck() ) {
			// Si hay doble baraja, cargamos más
			Dos.loadSpecialCards(deck, this.getClass());
		}
		// Creamos los juugadores
		ArrayList<Player> players = new ArrayList<Player>();
		for( int i = 0; i < getGameRules().getNumPlayers(); i++ ) {
			Player player = new Player();
			player.setId(i);
			player.setPlayerInfo(getGameRules().getPlayersInfo().get(i));
			players.add(player);
		}
		
		gameRules.setNumPlayers(4);
		Dos dosGame = new Dos(deck, gameRules, players);
		
		//......se inicializa el controller primero
		currentGame = dosGame;
		dosGame.initGame();
		gameControllerDos = new GameControllerDos(this);
		Scene scene = new Scene(gameControllerDos.getView());
		mainStage.setScene(scene);
	}
	
	//-----------------------------------------------------------
	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}
	public static void main(String[] args) {
		launch(args);
	}

}
