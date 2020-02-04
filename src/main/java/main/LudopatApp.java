package main;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import games.Card;
import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;
import gameslib.Dos;
import gameslib.GameControllerDos;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	//-------- -------------------------------------------



	@Override
	public void start(Stage primaryStage) throws Exception {

		mainStage = primaryStage;
		mainStage.setResizable(false);
		
		goMultiplayerMenu();
		
		// Inicamos la aplicación, el SplashScreen
	//	initApp();

		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
		
		// Esperamos a mostrar el menú
		
	}

	// Métodos llamados desde vistas
	// -------------------------------------------------------------------

	public void goMultiplayerMenu() {

		try {

			gameRules = new GameRules();
			multiplayerController = new MultiplayerController(this);

			Scene scene = new Scene(multiplayerController.getView(), 800, 600);
			mainStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initApp() {
		
		try {
			SplashController splashController = new SplashController(this);

			Scene scene = new Scene(splashController.getView(), 800, 600);
			mainStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	public void goMenu() {
		try {
			mainMenuController = new MainMenuController(this);

			Scene scene = new Scene(mainMenuController.getView(), 800, 600);
			mainStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initGame() {
		
		alignScreen();

		String gameType = gameRules.getGameType();
		
		switch (gameType) {

			case "dos":
				initDosGame();
				break;
			default:
				break;
		}
	}
	
	private void alignScreen() {
		double screenWidthCenter = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		double screenHeightCenter = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		
		mainStage.setX(screenWidthCenter - DOS_SCREEN_WIDTH_REQUIRED/2);
		mainStage.setY(screenHeightCenter - DOS_SCREEN_HEIGHT_REQUIRED/2);
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
		
		// Cargamos las cartas especiales
		ArrayList<Card> deckCards = deck.getCards();
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_BLUE);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_changeblue.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_GREEN);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_changegreen.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_WHITE);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_changewhite.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_CHANGE_YELLOW);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_changeyellow.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_BLOCK);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_block.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_INVERSE);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_inverse.png").toString()));
			
			deckCards.add(card);
		}
		
		for (int i = 0; i < 2; i++) {
			
			Card card = new Card();
			card.setCardValue(Dos.SPECIAL_PLUSONE);
			card.setCardImage(
					new Image(getClass().getResource("/ui/images/dos/special/dos_special_plusone.png").toString()));
			
			deckCards.stream().forEach(  node -> {
			
			});
			deckCards.add(card);
		}
		
		// TEST
		ArrayList<Player> players = new ArrayList<Player>();
		for( int i = 0; i < 4; i++ ) {
			Player player = new Player();
			player.setId(i);
			player.setPlayerName("Player_"+i);
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
