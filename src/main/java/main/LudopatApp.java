package main;

import java.io.IOException;
import java.util.ArrayList;

import games.Card;
import games.Deck;
import games.GameRules;
import games.Player;
import gameslib.Dos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
	
	//---------------------------------------------------
	
	// Variables de la App
	//---------------------------------------------------
	
	private GameRules gameRules;
	private Stage mainStage;
	
	//-------- -------------------------------------------

	@Override
	public void start(Stage primaryStage) throws Exception {

		mainStage = primaryStage;
		mainStage.setResizable(false);
		
		goMultiplayerMenu();
		
		// Inicamos la aplicación, el SplashScreen
	//	initApp();
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

		String gameType = gameRules.getGameType();
		
		switch (gameType) {

			case "dos":
				initDosGame();
				break;
			default:
				break;
		}
	}
	
	// -------------------------------------------------------------------

	public GameRules getGameRules() {
		return gameRules;
	}
	
	// Lógica distintos juegos
	//-----------------------------------------------------------
	
	public void initDosGame() {
		System.out.println("He llegado");
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
		
		Dos dosGame = new Dos(deck, gameRules, players);
		
		//......se inicializa el controller primero
		
		dosGame.initGame();
	}
	
	//-----------------------------------------------------------
	
	public static void main(String[] args) {
		launch(args);
	}

}
