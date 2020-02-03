package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.config.avatar.Avatar;
import ui.config.avatar.AvatarSelector;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * 
 * <b>Controlador configuración jugadores</b>
 * <br><br>
 * 
 * Controlador para los ajustes de multijugador con
 * opciones para elegir los distintos personajes 
 * y el nombre de cada jugador
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */

public class PlayerSelectionController extends AnchorPane implements Initializable {

	@FXML
	GridPane grid;
	@FXML
	ImageView player1Image;
	
	//TODO Añadir imagenes y nombres de avatar finales.
	Avatar[] availableAvatars = {new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 1"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 2"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 3"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 4")
			, new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 5"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 6"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 7"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 8"), new Avatar(getClass().getResource("/ui/images/Frame.png").toString(),"")};
	
	int[] selectedAvatars = {0, 1, 2, 3};
	
	public PlayerSelectionController() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PlayerSelectionView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Acciones para las imagenes.
		player1Image.setOnMouseClicked(e -> changeAvatar(0));
	}
	
	private void changeAvatar(int posPlayer) {
		//TODO Lanzar el JFDialog para la elección del avatar que devolvuelve la posicion del avatar seleccionado
		
		//LAS SIGUIENTES LINEAS DE CODIGO SON SOLO DE PRUEBA
		///*
		Stage stage = new Stage();
		AvatarSelector selector = new AvatarSelector(posPlayer, availableAvatars);
		Scene scene = new Scene(selector);
		stage.setScene(scene);
		stage.show();
		//*/
	}

}
