package gameslib;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import games.Card;
import games.Player;
import gameslib.endGame.EndGameController;
import help.HelpViewContoller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import statistics.PlayerStatistic;
import ui.CardComponent;

/**
 * <b>GameControllerDos</b> <br>
 * <br>
 * 
 * Controlador base de el juego del DOS
 * 
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class GameControllerDos implements Initializable {

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
	private VBox players;

	@FXML
	private HBox player1;

	@FXML
	private ImageView player1Image;

	@FXML
	private Label player1Name, player1Cards;

	@FXML
	private HBox player2;

	@FXML
	private ImageView player2Image;

	@FXML
	private Label player2Name, player2Cards;

	@FXML
	private HBox player3;

	@FXML
	private ImageView player3Image;

	@FXML
	private Label player3Name, player3Cards;

	@FXML
	private HBox player4;

	@FXML
	private ImageView player4Image;

	@FXML
	private Label player4Name, player4Cards;

	@FXML
	private VBox table;

	@FXML
	private ImageView currentCard;

	@FXML
	private ImageView currentCard1;

	@FXML
	private JFXButton nextButton;

	@FXML
	private JFXButton drawButton;

	@FXML
	private GridPane handGrid;

	@FXML
	private StackPane stack;

	@FXML
	private ImageView player1Crown;

	@FXML
	private ImageView player2Crown;

	@FXML
	private ImageView player3Crown;

	@FXML
	private ImageView player4Crown;

	@FXML
	private ImageView helpImage;

	// ----------------------------------------------------------
	// Variables used by controller
	// ----------------------------------------------------------

	private ArrayList<HBox> playersBox;
	private ArrayList<Label> playersName;
	private ArrayList<Label> playersNumCards;
	private ArrayList<ImageView> playersImage;

	private LudopatApp ludopp;
	private Dos dosGame;

	private HelpViewContoller help;

	private static double xOffset = 0;
	private static double yOffset = 0;
	private EventHandler<MouseEvent> click;
	private EventHandler<MouseEvent> drag;
	
	
	//Informe
	public static final String JRXML_FILE = "/ui/reports/result.jrxml";
	private static final String USER_FOLDER = System.getProperty("user.home") + "\\desktop\\LudopatApp_Informes\\";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final String date = LocalDate.now().format(formatter);
	public static final String PDF_FILE = USER_FOLDER + "Resultado_" + date;
	private static String PDF_ROUTE = "";
	private static final String PDF = ".pdf";
	private EndGameController endgame;

	// ----------------------------------------------------------

	public GameControllerDos(LudopatApp app) {
		this.ludopp = app;
		this.dosGame = (Dos) ludopp.getCurrentGame();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/DosGameView.fxml"));
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

			Image crown = new Image(getClass().getResourceAsStream("/ui/images/crown.png"));

			if (!player.isAI()) {
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
			playersName.get(p).textProperty().bind(player.getPlayerInfo().playerNameProperty());
			playersImage.get(p).imageProperty().bind(player.getPlayerInfo().playerIconProperty());
			playersBox.get(p).setVisible(true);
		}

		// Bindings
		numberLabel.setText(String.valueOf(dosGame.getCurrentValue()));
		currentCard.setImage(dosGame.getLastCard().getCardImage());
		gameNameLabel.textProperty().bind(dosGame.getGameRules().gameTypeProperty());
		numberLabel.textProperty().bindBidirectional(dosGame.currentValueProperty(), new NumberStringConverter());

		// Cambios que suceden en el juego y se reflejan en la interfaz
		dosGame.currentColorProperty().addListener((o, ov, nv) -> onChangedImageColor(nv));
		dosGame.lastCardProperty().addListener((o, ov, nv) -> onChangeCard(nv));
		dosGame.activePlayerProperty().addListener((o, ov, nv) -> onChangedPlayer(ov, nv));

		onChangedImageColor(dosGame.getCurrentColor());
		onChangedPlayer(null, dosGame.getActivePlayer());

		// Visualizamos la primera mano del jugador
		initHand();
		showHand();
		setMovingHandler();
	}

	private void setColors() {
		view.getStylesheets().remove(0);

		if (ludopp.isWhiteMode()) {
			// Modo claro
			helpImage.setImage(new Image(getClass().getResource("/ui/images/whiteModeHelp.png").toString()));
			view.getStylesheets().add(getClass().getResource("/ui/css/whiteMode/DosBoardStyle.css").toString());
		} else {
			// Modo oscuro
			helpImage.setImage(new Image(getClass().getResource("/ui/images/help.png").toString()));
			view.getStylesheets().add(getClass().getResource("/ui/css/DosBoardStyle.css").toString());
		}
	}

	private void setMovingHandler() {
		click = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = ludopp.getMainStage().getX() - event.getScreenX();
				yOffset = ludopp.getMainStage().getY() - event.getScreenY();
			}
		};
		drag = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ludopp.getMainStage().setX(event.getScreenX() + xOffset);
				ludopp.getMainStage().setY(event.getScreenY() + yOffset);
			}
		};
		header.setOnMousePressed(click);
		header.setOnMouseDragged(drag);

		appNameLabel.setOnMousePressed(click);
		appNameLabel.setOnMouseDragged(drag);
	}

	private void removeMovingHandler() {
		header.setOnMousePressed(e -> nothing());
		header.setOnMouseDragged(e -> nothing());

		appNameLabel.setOnMousePressed(e -> nothing());
		appNameLabel.setOnMouseDragged(e -> nothing());
	}

	/**
	 * Este metodo no hace nada, sirve para limpiar eventhandlers
	 */
	private void nothing() {

	}

	private void onChangedPlayer(Player ov, Player nv) {

		if (ov != null && ov.isAI()) {
			// Actualizamos el número de cartas que tiene la IA
			switch (dosGame.getPlayerPosition(ov)) {
			case 0:
				player1Cards.setText(String.format("Número de cartas: %d", ov.getHand().size()));
				break;
			case 1:
				player2Cards.setText(String.format("Número de cartas: %d", ov.getHand().size()));
				break;
			case 2:
				player3Cards.setText(String.format("Número de cartas: %d", ov.getHand().size()));
				break;
			case 3:
				player4Cards.setText(String.format("Número de cartas: %d", ov.getHand().size()));
				break;
			}
		}

		if (nv.isAI()) {
			// Deshabilitamos los botones de acción
			nextButton.setDisable(true);
			drawButton.setDisable(true);
		} else {
			nextButton.setDisable(false);
			drawButton.setDisable(false);
		}

		// Quitamos el estilo a uno y se lo ponemos a otro
		if (ov != null) {
			int lpIndex = dosGame.getPlayerPosition(ov);
			playersBox.get(lpIndex).setStyle(null);
		}

		if (nv != null) {
			int npIndex = dosGame.getPlayerPosition(nv);

			if (ludopp.isWhiteMode()) {
				playersBox.get(npIndex).setStyle("-fx-effect: dropshadow(gaussian, black, 20, 0.5, 0, 0);");
			} else {
				playersBox.get(npIndex).setStyle("-fx-effect: dropshadow(gaussian, white, 20, 0.5, 0, 0);");
			}

			initHand();
			showHand();
		}

		if (dosGame.isInverse()) {
			if (dosGame.getPlayerPosition(nv) == 0) {
				playersBox.get(dosGame.getCurrentPlayers().size() - 1)
						.setStyle("-fx-effect: dropshadow(gaussian, grey, 20, 0.5, 0, 0);");
			} else {
				playersBox.get(dosGame.getPlayerPosition(nv) - 1)
						.setStyle("-fx-effect: dropshadow(gaussian, grey, 20, 0.5, 0, 0);");
			}
		} else {
			if (dosGame.getPlayerPosition(nv) == dosGame.getCurrentPlayers().size() - 1) {
				playersBox.get(0).setStyle("-fx-effect: dropshadow(gaussian, grey, 20, 0.5, 0, 0);");
			} else {
				playersBox.get(dosGame.getPlayerPosition(nv) + 1)
						.setStyle("-fx-effect: dropshadow(gaussian, grey, 20, 0.5, 0, 0);");
			}
		}

		if (ov != null && ov.getHand().size() == 0) {
			endGame();
		}
	}

	private void onChangeCard(Card nv) {

		if (nv != null) {
			currentCard.setImage(nv.getCardImage());
		}
	}

	/**
	 * Refresca la interfaz de la mano después de una acciónr
	 */
	public void refreshHand() {

		// Limpiamos la mano actual del jugador y la actualizamos
		handGrid.getChildren().clear();

		int i = 0;
		dosGame.startTurn();
		for (Card card : dosGame.getActivePlayer().getHand()) {
			CardComponent cardComp = new CardComponent(card.getCardImage());
			if (dosGame.getActivePlayer().isAI()) {
				cardComp.turn();
			}

			handGrid.add(cardComp, i, 0);

			if (!dosGame.getActivePlayer().isAI()) {
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
			}
			i++;
		}

	}

	/**
	 * Refresca la mano de una IA despues de una acción
	 */
	public void refreshIAHand() {
		// Limpiamos la mano actual del jugador y la actualizamos
		handGrid.getChildren().clear();

		int i = 0;

		for (Card card : dosGame.getActivePlayer().getHand()) {
			CardComponent cardComp = new CardComponent(card.getCardImage());

			handGrid.add(cardComp, i, 0);
			i++;
		}
	}

	/**
	 * Inicializa la mano, activando y desactivando los botones necesarios
	 */
	private void initHand() {
		// Número de cartas a robar
		drawButton.setText("Robar (" + dosGame.getCardsToDraw() + ")");
		// Deshabilitamos el pasar turno del jugador
		nextButton.setDisable(true);
		refreshHand();
	}

	private void onSelectCard(Card card, CardComponent cmp) {
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
	}

	private void showHand() {
		handGrid.getChildren().stream().forEach(node -> {
			CardComponent cardComp = (CardComponent) node;
			cardComp.turn();
		});

	}

	public void hideHand() {
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
		updateCardCounters();
		dosGame.endGame();

		// Desactiva el juego
		nextButton.setDisable(true);
		drawButton.setDisable(true);

		handGrid.getChildren().stream().forEach(node -> {
			node.setDisable(true);
		});

		JFXDialogLayout layout = new JFXDialogLayout();
		endgame = new EndGameController(dosGame.getCurrentPlayers());
		layout.setBody(endgame);

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

		JFXButton report = new JFXButton("Generar informe");
		report.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				report();
			}

		});

		report.setId("button");
		report.getStylesheets().add(getClass().getResource("/ui/css/EndGame.css").toExternalForm());

		layout.setActions(report, menu, exit);
		dialog.show();
	}

	private void report() {
		
		//Comprueba si tiene creada la carpeta de reportes y si no la crea.
		File userFolder = new File(USER_FOLDER);
		if (!userFolder.exists()) {
			System.out.println("Creando carpeta...");
			userFolder.mkdirs();
		}
		
		//Comprueba si ya existe otro informe con el mismo nombre
		PDF_ROUTE = "";
		File userReportFile = new File(PDF_FILE + PDF);
		if (userReportFile.exists()) {
			int i = 0;
			while(userReportFile.exists()) {
				i++;
				userReportFile = new File(PDF_FILE + "(" + i + ")" + PDF);
			}
			
			PDF_ROUTE += PDF_FILE + "(" + i + ")" + PDF;
		}else {
			PDF_ROUTE += PDF_FILE + PDF;
		}
		
		try {

			ArrayList<PlayerStatistic> playerSt = new ArrayList<PlayerStatistic>();
			for (Player p : EndGameController.getPlayers()) {
				playerSt.add(p.getStatistics());
				p.getStatistics().sysoStatistics();
			}

			JasperReport jsr = JasperCompileManager
					.compileReport(GameControllerDos.class.getResourceAsStream(JRXML_FILE));
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("anyo", 2014);// no lo uso, pero se lo paso
			JasperPrint jprint = JasperFillManager.fillReport(jsr, parameters,
					new JRBeanCollectionDataSource(playerSt));
			JasperExportManager.exportReportToPdfFile(jprint, PDF_ROUTE);
			Desktop.getDesktop().open(new File(PDF_ROUTE));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateCardCounters() {
		for (int i = 0; i < dosGame.getCurrentPlayers().size(); i++) {
			playersNumCards.get(i).setText(
					String.format("Número de cartas: %d", dosGame.getCurrentPlayers().get(i).getHand().size()));
		}
	}

	@FXML
	void drawCardAction(ActionEvent event) {
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
			removeMovingHandler();
		} else {
			ludopp.getMainStage().setFullScreen(false);
			setMovingHandler();
		}
	}

	@FXML
	void nextTurnAction(ActionEvent event) {
		drawButton.setDisable(false);

		hideHand();

		dosGame.nextTurn();// esto cambia el jugador activo

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
		if (stack.getChildren().size() == 1) {
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
	}

	public GridPane getView() {
		return view;
	}
}