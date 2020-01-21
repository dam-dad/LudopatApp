package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MultiplayerController;

public class LudopatApp extends Application {

	private MultiplayerController multiplayerController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		multiplayerController = new MultiplayerController();
		
		// Saltando directamente a la configuración del Menu
		primaryStage.setTitle("Configuración del juego");
		
		Scene scene = new Scene(multiplayerController.getView(), 640, 480);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
