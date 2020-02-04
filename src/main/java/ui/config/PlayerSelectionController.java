package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.LudopatApp;
import ui.config.avatar.Avatar;
import ui.config.avatar.AvatarSelector;

/**
 * 
 * <b>Controlador configuración jugadores</b> <br>
 * <br>
 * 
 * Controlador para los ajustes de multijugador con opciones para elegir los
 * distintos personajes y el nombre de cada jugador
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
	StackPane stack;

	private LudopatApp ludopp;
	
	String[][] avatarsReferences = { { getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 1" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 2" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 3" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 4" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 5" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 6" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 7" },
			{ getClass().getResource("/ui/images/Frame.png").toString(), "Avatar 8" },
			{ getClass().getResource("/ui/images/Noframe.png").toString(), "" } };

	// TODO Añadir imagenes y nombres de avatar finales.
	Avatar[] availableAvatars = { new Avatar(avatarsReferences[0][0], avatarsReferences[0][1]),
			new Avatar(avatarsReferences[1][0], avatarsReferences[1][1]),
			new Avatar(avatarsReferences[2][0], avatarsReferences[2][1]),
			new Avatar(avatarsReferences[3][0], avatarsReferences[3][1]),
			new Avatar(avatarsReferences[4][0], avatarsReferences[4][1]),
			new Avatar(avatarsReferences[5][0], avatarsReferences[5][1]),
			new Avatar(avatarsReferences[6][0], avatarsReferences[6][1]),
			new Avatar(avatarsReferences[7][0], avatarsReferences[7][1]),
			new Avatar(avatarsReferences[8][0], avatarsReferences[8][1]), 
			new Avatar(avatarsReferences[8][0], avatarsReferences[8][1])};
	
	int[] selectedAvatars = { 0, 1, 2, 3 };

	AvatarSelector selector;

	public PlayerSelectionController(LudopatApp app) {

		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PlayerSelectionView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.ludopp = app;

			reloadImages();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reloadImages() {
		
		grid.getChildren().clear();
		grid.add(availableAvatars[selectedAvatars[0]], 0, 0);
		grid.add(availableAvatars[selectedAvatars[1]], 1, 0);
			
		int nPlayers = ludopp.getGameRules().getNumPlayers();
		
		Avatar p3Avatar = availableAvatars[selectedAvatars[2]];
		Avatar p4Avatar = availableAvatars[selectedAvatars[3]];
		
		if( nPlayers == 2 ) {
			p3Avatar = availableAvatars[availableAvatars.length-2];
			grid.getChildren().get(2).setDisable(true);
			grid.getChildren().get(3).setDisable(true);
			p4Avatar = availableAvatars[availableAvatars.length-1];
		} else if( nPlayers == 3) {
			p4Avatar = availableAvatars[availableAvatars.length-1];
			grid.getChildren().get(3).setDisable(true);
		} 

		grid.add(p3Avatar, 0, 1);
		grid.add(p4Avatar, 1, 1);
	
		// Ajustamos atendiendo a los jugadores que tenemos
	
		grid.getChildren().get(2).setDisable(false);
		grid.getChildren().get(3).setDisable(false);
		
		if( nPlayers == 2 ) {
			grid.getChildren().get(2).setDisable(true);
			grid.getChildren().get(3).setDisable(true);
		} else if( nPlayers == 3) {
			grid.getChildren().get(3).setDisable(true);
		} 

		grid.getChildren().get(0).setOnMouseClicked(e -> changeAvatar(0));
		grid.getChildren().get(1).setOnMouseClicked(e -> changeAvatar(1));
		grid.getChildren().get(2).setOnMouseClicked(e -> changeAvatar(2));
		grid.getChildren().get(3).setOnMouseClicked(e -> changeAvatar(3));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void refresh() {
		
		reloadImages();
	}
	
	private void changeAvatar(int posPlayer) {
	
		selector = new AvatarSelector(posPlayer, availableAvatars, selectedAvatars);

		stack.getChildren().add(1, selector);

		selector.getFinish().addListener(e -> closeDialog(posPlayer));
	}

	private void closeDialog(int pos) {
		selectedAvatars[pos] = selector.getSelected();

		stack.getChildren().remove(1);
		Avatar[] aux = { new Avatar(avatarsReferences[0][0], avatarsReferences[0][1]),
				new Avatar(avatarsReferences[1][0], avatarsReferences[1][1]),
				new Avatar(avatarsReferences[2][0], avatarsReferences[2][1]),
				new Avatar(avatarsReferences[3][0], avatarsReferences[3][1]),
				new Avatar(avatarsReferences[4][0], avatarsReferences[4][1]),
				new Avatar(avatarsReferences[5][0], avatarsReferences[5][1]),
				new Avatar(avatarsReferences[6][0], avatarsReferences[6][1]),
				new Avatar(avatarsReferences[7][0], avatarsReferences[7][1])
				};
		availableAvatars = aux;

		reloadImages();
	}

}
