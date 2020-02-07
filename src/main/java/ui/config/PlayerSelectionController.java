package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import games.PlayerInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
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

	private ArrayList<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
	
	String[][] avatarsReferences = {
			{ getClass().getResource("/ui/images/avatar_carpenter.png").toString(), "Carpintero" },
			{ getClass().getResource("/ui/images/avatar_doctor.png").toString(), "Doctor" },
			{ getClass().getResource("/ui/images/avatar_lawyer.png").toString(), "Abogado" },
			{ getClass().getResource("/ui/images/avatar_programmer.png").toString(), "Hacker" },
			{ getClass().getResource("/ui/images/avatar_dab.png").toString(), "Dab niño" },
			{ getClass().getResource("/ui/images/avatar_potat.png").toString(), "Potat" },
			{ getClass().getResource("/ui/images/avatar_travolta.png").toString(), "Travolta" },
			{ getClass().getResource("/ui/images/avatar_davIA.png").toString(), "Dav_IA.sad" },
			{ getClass().getResource("/ui/images/userNull.png").toString(), "" }
	};

	// TODO Añadir imagenes y nombres de avatar finales.
	Avatar[] availableAvatars = {
			new Avatar(avatarsReferences[0][0], avatarsReferences[0][1]),
			new Avatar(avatarsReferences[1][0], avatarsReferences[1][1]),
			new Avatar(avatarsReferences[2][0], avatarsReferences[2][1]),
			new Avatar(avatarsReferences[3][0], avatarsReferences[3][1]),
			new Avatar(avatarsReferences[4][0], avatarsReferences[4][1]),
			new Avatar(avatarsReferences[5][0], avatarsReferences[5][1]),
			new Avatar(avatarsReferences[6][0], avatarsReferences[6][1]),
			new Avatar(avatarsReferences[7][0], avatarsReferences[7][1])
	};
	
	Avatar[] allAvatars = {
			new Avatar(avatarsReferences[0][0], avatarsReferences[0][1]),
			new Avatar(avatarsReferences[1][0], avatarsReferences[1][1]),
			new Avatar(avatarsReferences[2][0], avatarsReferences[2][1]),
			new Avatar(avatarsReferences[3][0], avatarsReferences[3][1]),
			new Avatar(avatarsReferences[4][0], avatarsReferences[4][1]),
			new Avatar(avatarsReferences[5][0], avatarsReferences[5][1]),
			new Avatar(avatarsReferences[6][0], avatarsReferences[6][1]),
			new Avatar(avatarsReferences[7][0], avatarsReferences[7][1]),
			new Avatar(avatarsReferences[8][0], avatarsReferences[8][1]),
			new Avatar(avatarsReferences[8][0], avatarsReferences[8][1])
	};
	
	int[] selectedAvatars = new int[4];

	AvatarSelector selector;
	
	LudopatApp ludopatApp;
	int nPlayers;

	public PlayerSelectionController(LudopatApp ludopatApp) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PlayerSelectionView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.ludopatApp = ludopatApp;
			
			addDefaultAvatars();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDefaultAvatars() {
		
		nPlayers = ludopatApp.getGameRules().getNumPlayers();
		
		selectedAvatars[0] = 0;
		selectedAvatars[1] = 1;
		
		switch (nPlayers) {
		case 2:
			selectedAvatars[2] = 8;
			selectedAvatars[3] = 9;
			break;
		case 3:
			selectedAvatars[2] = 2;
			selectedAvatars[3] = 9;
			break;
		default:
			selectedAvatars[2] = 2;
			selectedAvatars[3] = 3;
			break;
		}

		reloadImages();
	}

	private void reloadImages() {
		grid.getChildren().clear();
		
		grid.add(allAvatars[selectedAvatars[0]], 0, 0);
		grid.add(allAvatars[selectedAvatars[1]], 1, 0);
		grid.add(allAvatars[selectedAvatars[2]], 0, 1);
		grid.add(allAvatars[selectedAvatars[3]], 1, 1);

		grid.getChildren().get(0).setOnMouseClicked(e -> changeAvatar(0));
		grid.getChildren().get(1).setOnMouseClicked(e -> changeAvatar(1));
		
		if (selectedAvatars[2] == 8) {
			grid.getChildren().get(2).setDisable(true);
			grid.getChildren().get(3).setDisable(true);
		}else {
			if (selectedAvatars[3] == 9) {
				grid.getChildren().get(3).setDisable(true);
			}else {
				grid.getChildren().get(2).setDisable(false);
				grid.getChildren().get(3).setDisable(false);
			}
		}
		
		grid.getChildren().get(2).setOnMouseClicked(e -> changeAvatar(2));
		grid.getChildren().get(3).setOnMouseClicked(e -> changeAvatar(3));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Creamos tantos PlayerInfo como necesitamos
		for( int i = 0; i < 4; i++ ) {
			playersInfo.add(new PlayerInfo());
		}
	}

	private void changeAvatar(int posPlayer) {
		
		selector = new AvatarSelector(posPlayer, availableAvatars, selectedAvatars);

		stack.getChildren().add(1, selector);

		selector.getFinish().addListener(e -> closeDialog(posPlayer));
	}

	public void closeDialog(int pos) {
		if (pos > -1) {
			selectedAvatars[pos] = selector.getSelected();

			// Le añadimos esta información al playerInfo
			playersInfo.get(pos).setPlayerIcon(new Image(allAvatars[selectedAvatars[pos]].getImgName()));
			playersInfo.get(pos).setPlayerName(allAvatars[selectedAvatars[pos]].getPlayerName());
		}
		
		try {
			stack.getChildren().remove(1);
		} catch (IndexOutOfBoundsException e) {
		}

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

	public void refresh() {
		addDefaultAvatars();
		
		// Ponemos información a los playerInfo
		for( int i = 0; i < 4; i++ ) {
			playersInfo.get(i).setPlayerIcon(new Image(allAvatars[selectedAvatars[i]].getImgName()));
			playersInfo.get(i).setPlayerName(allAvatars[selectedAvatars[i]].getPlayerName());
		}
	}

	public ArrayList<PlayerInfo> getPlayersInfo() {
		
		ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
		// Devolvemos sólo lo correspondiente
		for( int i = 0; i < ludopatApp.getGameRules().getNumPlayers(); i++ ) {
			players.add(playersInfo.get(i));
		}
		return playersInfo;
	}

}