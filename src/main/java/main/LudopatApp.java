package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainMenuController;
import ui.MultiplayerController;

/**
 * 
 * <b>Aplicación LudtopatApp</b>
 * <br><br>
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

	private MultiplayerController multiplayerController;
	private MainMenuController mainMenuController; 
	
	private Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
		
		mainStage = primaryStage;
		
		mainStage.setResizable(false);
		goMultiplayerMenu();
		primaryStage.show();
	}

	// Métodos llamados desde vistas
	//-------------------------------------------------------------------
	
	public void goMultiplayerMenu() {
		
		try {
			
			multiplayerController = new MultiplayerController(this);
			
			Scene scene = new Scene(multiplayerController.getView(), 800, 600);
			mainStage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initApp() {
		
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
	
	//-------------------------------------------------------------------
	
	public static void main(String[] args) {
		launch(args);
	}

}
