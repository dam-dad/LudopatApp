package main;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.dom4j.DocumentException;

import games.Card;
import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;
import games.PlayerInfo;
import gamesAI.Dav_IA_DOS;
import gameslib.Dos;
import gameslib.GameControllerDos;
import gameslib.GameControllerDosNET;
import gameslib.GameControllerSolitaire;
import gameslib.Solitaire;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.Client;
import net.GameServer;
import net.Server;
import net.ServerClient;
import net.User;
import ui.ConfigMenuController;
import ui.MPSelectionModeController;
import ui.MainMenuController;
import ui.MultiplayerController;
import ui.SplashController;
import uinet.ClientConfigController;
import uinet.ServerConfigController;
import util.ipSearch;

/**
 * 
 * <b>Aplicación LudopatApp</b> <br>
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
	// ---------------------------------------------------

	private MultiplayerController multiplayerController;
	private ServerConfigController serverConfigController;
	private MainMenuController mainMenuController;
	private GameControllerDos gameControllerDos;
	private GameControllerDosNET gameControllerDosNET;
	private GameControllerSolitaire solitaireController;
	private ClientConfigController clientConfigController;
	private ConfigMenuController configMenuController;

	private MPSelectionModeController mpSelectionModeController;

	// ---------------------------------------------------

	// Variables online
	// ---------------------------------------------------

	private User userClient;
	private Client connectionClient;
	private Server connectionServer;
	private GameServer gameServer;
	private Game serverCurrentGame;
	private MediaPlayer mediaPlayer;

	// ---------------------------------------------------

	// Variables de la App
	// ---------------------------------------------------

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
	
	//Configuración general
	private BooleanProperty silenced = new SimpleBooleanProperty(false);
	private DoubleProperty volume = new SimpleDoubleProperty(0.8);
	private BooleanProperty whiteMode = new SimpleBooleanProperty(false);
	// -------- -------------------------------------------

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainStage = primaryStage;
		mainStage.setResizable(false);
		
		loadConfig();
		
		// Inicamos la aplicación, el SplashScreen
		initApp();
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResource("/ui/images/Noframe.png").toString()));
		
		primaryStage.show();
		
	}
	
	//Carga la configuración general
	private void loadConfig() {
		File file = new File(getClass().getResource("/config/config.dat").getFile());
		try {
			RandomAccessFile configFile = new RandomAccessFile(file, "r");
			//Lee si está silenciado
			silenced.set(configFile.readBoolean());
			//Lee el volumen
			volume.set(configFile.readDouble());
			//Lee si es modo claro
			whiteMode.set(configFile.readBoolean());
			
			//Cerramos el archivo
			configFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void playMusic() {
		Media sound = new Media(getClass().getResource("/ui/sound/sound.mp3").toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
				mediaPlayer.play();
			}
		});

		mediaPlayer.play();
	}

	public void stopMusic() {
		mediaPlayer.pause();
	}

	public void setMusicVolume(double newVolume) {
		mediaPlayer.setVolume(newVolume);

	}

	// Métodos llamados desde vistas
	// -------------------------------------------------------------------
	/**
	 * Lleva al usuario a la pantalla de selección de Multijugador
	 */
	public void goMPSelectionMode() {
		mpSelectionModeController = new MPSelectionModeController(this);

		Scene scene = new Scene(mpSelectionModeController.getView(), 800, 600);

		fadeTransition(mainMenuController.getView(), scene);
	}
	/**
	 * Lleva al usuario a la pantalla de configuración general
	 */
	public void goConfigMenu() {
		configMenuController = new ConfigMenuController(this);

		Scene scene = new Scene(configMenuController.getView(), 800, 600);

		fadeTransition(mainMenuController.getView(), scene);
	}

	/**
	 * Lleva al usuario a la configuración de servidor de partida
	 */
	public void goServerConfig() {

		try {

			// Creamos el usuario
			userClient = new User();

			gameRules = new GameRules();
			serverConfigController = new ServerConfigController(this);

			Scene scene = new Scene(serverConfigController.getView(), 800, 600);
			fadeTransition(mpSelectionModeController.getView(), scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lleva al usuario a la configuracion de cliente de partida
	 */
	public void goClientConfig() {

		// Creamos el usuario
		userClient = new User();

		gameRules = new GameRules();
		clientConfigController = new ClientConfigController(this);

		Scene scene = new Scene(clientConfigController.getView(), 800, 600);
		fadeTransition(mpSelectionModeController.getView(), scene);

	}

	/**
	 * Lleva al usuario al menú de partida contra IA
	 */
	public void goAIMenu() {

		try {

			gameRules = new GameRules();
			multiplayerController = new MultiplayerController(this);

			Scene scene = new Scene(multiplayerController.getView(), 800, 600);
			fadeTransition(mpSelectionModeController.getView(), scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transición de fade entre menús
	 * 
	 * @param view
	 * @param scene
	 */
	private void fadeTransition(Node view, Scene scene) {

		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setDuration(Duration.millis(375));
		fadeTransition.setNode(view);
		fadeTransition.setOnFinished(ae -> mainStage.setScene(scene));

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), ae -> fadeTransition.play()));
		timeline.play();

		FadeTransition fadeTransitionOut = new FadeTransition();
		fadeTransitionOut.setFromValue(1);
		fadeTransitionOut.setToValue(0);
		fadeTransitionOut.setDuration(Duration.millis(375));
		fadeTransitionOut.setNode(view);

		Timeline timelineOut = new Timeline(new KeyFrame(Duration.millis(1), ae -> fadeTransitionOut.play()));
		timelineOut.play();

		alignScreenMenu();
	}

	/**
	 * Inicio de la app que muestra la splash screen
	 */
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

	/**
	 * Inicia una partida de 1 solo jugador (Solitario)
	 */
	public void initSinglePlayer() {

		gameRules = new GameRules();
		gameRules.setGameType("solitario");

		try {
			gameRules.initGameType(gameRules.getGameType());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Deck deck = gameRules.getAvailableDecks().get(0);
		deck.loadCards("poker");

		// Creamos los jugadores
		ArrayList<Player> players = new ArrayList<Player>();
		// Primero creamos al jugador activo
		Player player = new Player();
		player.setId(0);
		players.add(player);

		Solitaire solitaireGame = new Solitaire(deck, gameRules, players);

		// ......se inicializa el controller primero
		currentGame = solitaireGame;

		solitaireController = new GameControllerSolitaire(this);

		Scene scene = new Scene(solitaireController.getView());
		mainStage.setScene(scene);
		alignScreen();
	}

	/**
	 * Lleva al usuario al menu principal
	 */
	public void goMenu() {
		
		loadConfig();

		try {

			if (currentGame != null)
				currentGame = null;

			if (serverCurrentGame != null)
				serverCurrentGame = null;

			mainMenuController = new MainMenuController(this);

			Scene scene = new Scene(mainMenuController.getView(), 800, 600);
			mainStage.setScene(scene);
			alignScreenMenu();
			if (mediaPlayer == null) {
				playMusic();
				setMusicVolume(volume.get());
				if (silenced.get()) {
					stopMusic();
					
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Llamado desde el modo online, para asegurarse de cerrar las conexiones con
	 * los clientes y el servidor
	 */
	public void onlineGoMenu() {

		// Hacemos una acción u otra dependiendo de si es el Host o no
		if (connectionServer != null) {
			connectionServer.closeRoom(true); // Forzamos el cierre del servidor
		}

		else if (gameServer != null) {
			gameServer.closeServer();
		}

		else if (connectionClient != null) {
			// Avisamos a los clientes de que este cliente se va a desconectar
			connectionClient.disconnectClient();
		}

		goMenu();

	}

	/**
	 * Inicia la partida con las reglas elegidas
	 */
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

	/**
	 * Alinea la ventana al centro de la pantalla
	 */
	private void alignScreen() {
		double screenWidthCenter = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		double screenHeightCenter = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;

		mainStage.setX(screenWidthCenter - DOS_SCREEN_WIDTH_REQUIRED / 2);
		mainStage.setY(screenHeightCenter - DOS_SCREEN_HEIGHT_REQUIRED / 2);
	}

	private void alignScreenMenu() {
		double screenWidthCenter = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		double screenHeightCenter = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;

		mainStage.setX(screenWidthCenter - MENU_SCREEN_WIDTH_REQUIRED / 2);
		mainStage.setY(screenHeightCenter - MENU_SCREEN_HEIGHT_REQUIRED / 2);
	}

	// -------------------------------------------------------------------

	public GameRules getGameRules() {
		return gameRules;
	}

	// Lógica distintos juegos
	// -----------------------------------------------------------
	/**
	 * Inicia la baraja del DOS, los jugadores, reparte y lanza la partida
	 */
	public void initDosGame() {

		Deck deck = gameRules.getDeckType();
		deck.loadCards("dos");

		Dos.loadSpecialCards(deck, this.getClass());
		if (deck.isDoubleDeck()) {
			// Si hay doble baraja, cargamos más
			Dos.loadSpecialCards(deck, this.getClass());
		}

		// Creamos los jugadores
		ArrayList<Player> players = new ArrayList<Player>();
		// Primero creamos al jugador activo
		Player player = new Player();
		player.setId(0);
		player.setPlayerInfo(getGameRules().getPlayersInfo().get(0));
		players.add(player);
		// Ahora añadimos a la IA
		for (int i = 1; i < getGameRules().getNumPlayers(); i++) {
			Player AIPlayer = new Player();
			AIPlayer.setId(i);
			AIPlayer.setAI(true);
			AIPlayer.setAIController(new Dav_IA_DOS(AIPlayer));
			AIPlayer.setPlayerInfo(getGameRules().getPlayersInfo().get(i));
			players.add(AIPlayer);
		}

		Dos dosGame = new Dos(deck, gameRules, players);

		for (int i = 1; i < getGameRules().getNumPlayers(); i++) {
			players.get(i).getAIController().setBaseGame(dosGame);
		}
		// ......se inicializa el controller primero
		currentGame = dosGame;
		dosGame.initGame();
		gameControllerDos = new GameControllerDos(this);
		dosGame.setController(gameControllerDos);
		Scene scene = new Scene(gameControllerDos.getView());
		mainStage.setScene(scene);
	}

	/**
	 * Elimina todos los hilos y cierra la aplicación
	 */
	@Override
	public void stop() throws Exception {

		// Hay que asegurarse de eliminar todos los hilos
		if (currentGame != null) {
			for (Player p : currentGame.getCurrentPlayers()) {
				if (p.isAI()) {
					p.getAIController().setStopAI(true);
				}
			}
		}

		// Hacemos una acción u otra dependiendo de si es el Host o no
		if (connectionServer != null) {
			connectionServer.closeRoom(true); // Forzamos el cierre del servidor
		}

		else if (gameServer != null) {
			gameServer.closeServer();
		}

		else if (connectionClient != null) {
			// Avisamos a los clientes de que este cliente se va a desconectar
			connectionClient.disconnectClient();
		}

		System.exit(0);
	}

	// -----------------------------------------------------------

	// NET Related
	// -----------------------------------------------------------

	/**
	 * Actualización de la interfaz de la sala de espera
	 */
	public void refreshRoomView(ArrayList<PlayerInfo> players, String ip) {

		if (connectionServer != null) {
			// Entonces somos el host
			serverConfigController.showWaitingRoom(players);
		} else {
			// Entonces somo el cliente remoto
			clientConfigController.showWaitingRoom(players, ip);
		}

	}

	/**
	 * Inicio el servidor
	 */
	public void initServer() {

		connectionServer = new Server(this, ipSearch.ip());
		new Thread(connectionServer).start();
	}

	/**
	 * Se inicia el cliente y se conecta con la ip
	 */
	public void initClient(String ip) {

		if (connectionServer != null)
			connectionClient = new Client(this, userClient.getPlayerInfo()); // Host
		else
			connectionClient = new Client(this, ip, userClient.getPlayerInfo()); // Cliente remoto

		new Thread(connectionClient).start();
	}

	/**
	 * Inicio del juego en el servidor
	 * 
	 * @param players Información de los jugadores en la partida
	 */
	public void server_initGame(ArrayList<PlayerInfo> players, ArrayList<ServerClient> clients) {

		getGameRules().setPlayersInfo(players);

		Deck deck = gameRules.getDeckType();
		deck.loadCards("dos");

		Dos.loadSpecialCards(deck, this.getClass());
		if (deck.isDoubleDeck()) {
			Dos.loadSpecialCards(deck, this.getClass());
		}

		ArrayList<Player> gamePlayers = new ArrayList<Player>();
		int k = 0;
		for (PlayerInfo p : gameRules.getPlayersInfo()) {
			Player player = new Player();
			player.setId(p.getUserID());
			player.setPlayerInfo(p);
			gamePlayers.add(player);

			if (++k >= gameRules.getNumPlayers())
				break;
		}

		// Ahora necesitamos empezar la conexión con los clientes en el juego
		gameServer = new GameServer(clients, this);
		connectionServer = null; // Limpiamos la referencia a connectionServer, ya ha terminado su funcionalidad

		Dos dosGame = new Dos(deck, gameRules, gamePlayers);
		serverCurrentGame = dosGame;
		dosGame.initGame();

		dosGame.initGameServer(this);
	}

	/**
	 * Visualiza el juego para el cliente
	 * 
	 * @param players         Jugadores en la sesión
	 * @param tableCard       Carta sobre la mesa
	 * @param currentPlayerID Actual turno del jugador
	 * @param clientID        ID del cliente original
	 * @param gameType        Modo de juego del servidor
	 */
	public void client_startGame(ArrayList<Player> players, Card tableCard, int currentPlayerID, int clientID,
			String gameType) {

		currentGame = new Dos(players, tableCard, currentPlayerID, clientID, connectionClient);

		gameControllerDosNET = new GameControllerDosNET(this, gameType);
		Scene scene = new Scene(gameControllerDosNET.getView());
		mainStage.setScene(scene);
		alignScreen();

		((Dos) getCurrentGame()).setNETHud(gameControllerDosNET);
	}

	/**
	 * Si ha fallado la conexión con el servidor se avisa a la interfaz
	 */
	public void client_failConnection() {

		connectionClient = null; // No podemos hacer nada más
		clientConfigController.resetConnectionStatus();

	}

	// -----------------------------------------------------------

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public User getUserClient() {
		return userClient;
	}

	public void setUserClient(User userClient) {
		this.userClient = userClient;
	}

	public GameServer getGameServer() {
		return gameServer;
	}

	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	public Game getServerCurrentGame() {
		return serverCurrentGame;
	}
	
	public boolean isSilenced() {
		return this.silenced.get();
	}
	
	public Double getVolume() {
		return this.volume.get() * 100;
	}
	
	public boolean isWhiteMode() {
		return this.whiteMode.get();
	}

}
