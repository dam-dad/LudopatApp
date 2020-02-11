package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import games.PlayerInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	VBox grid;
	@FXML
	StackPane stack;
	@FXML
	JFXTextField nameField;

	private ArrayList<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
	
	private StringProperty playerName = new SimpleStringProperty();
	
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
	
	Avatar selectedAvatar = new Avatar(avatarsReferences[8][0], avatarsReferences[8][1]);
	int selectedAvatarPos;

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
			
			grid.getChildren().add(0, selectedAvatar);
			
			addDefaultAvatars();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDefaultAvatars() {
		
		nPlayers = ludopatApp.getGameRules().getNumPlayers();
		
		selectedAvatarPos = getRandomNumberInRange(0, 7);
		selectedAvatar = new Avatar(avatarsReferences[selectedAvatarPos][0], avatarsReferences[selectedAvatarPos][1]);
		
		reloadImage();
		
		this.playerName.set(selectedAvatar.getPlayerName());
		nameField.setText(playerName.get());
	}
	
	private void setSelectedAvatar() {
		this.selectedAvatar = new Avatar(avatarsReferences[selectedAvatarPos][0], avatarsReferences[selectedAvatarPos][1]);
	}
	
	private int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private void reloadImage() {
		grid.getChildren().set(0, selectedAvatar);
		
		grid.getChildren().get(0).setOnMouseClicked(e -> changeAvatar(selectedAvatarPos));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Creamos tantos PlayerInfo como necesitamos
		for( int i = 0; i < 4; i++ ) {
			playersInfo.add(new PlayerInfo());
		}
	}

	private void changeAvatar(int posPlayer) {
		
		selector = new AvatarSelector(posPlayer, availableAvatars);

		stack.getChildren().add(1, selector);

		selector.getFinish().addListener(e -> closeDialog(posPlayer));
	}

	public void closeDialog(int pos) {
		if (pos > -1) {
			selectedAvatarPos = selector.getSelected();
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
		
		setSelectedAvatar();
		reloadImage();
	}

	public void refresh() {
		
		nPlayers = ludopatApp.getGameRules().getNumPlayers();
		
		// Ponemos información a los playerInfo
		playersInfo.get(0).setPlayerIcon(new Image(selectedAvatar.getImgName()));
		playerName.set(nameField.getText());
		playersInfo.get(0).setPlayerName(this.playerName.get());
		
		for (int i = 1; i < nPlayers; i++) {
			playersInfo.get(i).setPlayerIcon(new Image(avatarsReferences[7][0]));
			playersInfo.get(i).setPlayerName("Dav_IA_" + i);
		}
		
		for (int i = nPlayers; i < playersInfo.size(); i++) {
			playersInfo.get(i).setPlayerIcon(null);
			playersInfo.get(i).setPlayerName("");
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