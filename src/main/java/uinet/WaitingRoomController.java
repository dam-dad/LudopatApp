package uinet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import games.PlayerInfo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;
import util.ipSearch;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
/**
 * <b>WaitingRoomController</b> <br>
 * <br>
 * 
 * Controlador de la vista de la sala de espera
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class WaitingRoomController extends AnchorPane implements Initializable {


    @FXML
    private AnchorPane view;

    @FXML
    private GridPane player1;

    @FXML
    private Label player1Cod;

    @FXML
    private Label player1Name;

    @FXML
    private ImageView player1Image;

    @FXML
    private GridPane player2;

    @FXML
    private Label player2Cod;

    @FXML
    private Label player2Name;

    @FXML
    private ImageView player2Image;

    @FXML
    private GridPane player3;

    @FXML
    private Label player3Cod;

    @FXML
    private Label player3Name;

    @FXML
    private ImageView player3Image;

    @FXML
    private GridPane player4;

    @FXML
    private Label player4Cod;

    @FXML
    private Label player4Name;

    @FXML
    private ImageView player4Image;

    @FXML
    private Label ipLabel;
    
    private ArrayList<GridPane> usersBox = new ArrayList<>();
    private ArrayList<ImageView> usersImage = new ArrayList<>();
    private ArrayList<Label> usersName = new ArrayList<>();
    
	public WaitingRoomController(LudopatApp ludotapp) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/WaitingRoomView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String systemipaddress= ipSearch.ip();
		
        ipLabel.setText(systemipaddress);

		usersImage.addAll( Arrays.asList(player1Image, player2Image, player3Image, player4Image));
		usersName.addAll( Arrays.asList(player1Name, player2Name, player3Name, player4Name));
		usersBox.addAll( Arrays.asList( player1, player2, player3, player4));
	}
	
	/**
	 * IP del servidor de la sala de espera
	 * Normalmente este método es llamado 
	 * una vez por el cliente 
	 */
	public void setWaitingRoomIP(String ip) {
		ipLabel.setText(ip);
	}
	
	/**
	 * Refrescamos la interfaz con los jugadores presentes
	 * @param players
	 */
	public void refresh(ArrayList<PlayerInfo> players) {
		
		// Refrescamos la vista con los usuarios conectados
		usersBox.stream().forEach( c -> c.setId("disable")); // Cambiar a set style
		
		for( int i = 0; i < players.size(); i++ ) {
			
			usersBox.get(i).setId("grid"); // Cambiar a set style
			usersImage.get(i).setImage(players.get(i).getPlayerIcon());
			usersName.get(i).setText(players.get(i).getPlayerName());
		}
	}

}
