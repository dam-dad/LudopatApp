package gameslib.endGame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import games.Player;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
/**
 * <b>EndGameController</b> <br>
 * <br>
 * 
 * Controlador de la pantalla de final de juego
 * que contiene los datos finales.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class EndGameController extends VBox implements Initializable {
	
	private ArrayList<Player> players;
	
	public EndGameController(ArrayList<Player> players) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/endGame/EndGameComponent.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.players = players;
			
			addPlayers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	/**
	 * Añade a los jugadores a la tabla de final de partida
	 */
	private void addPlayers() {
		for (int i = 0; i < players.size(); i++) {
			getChildren().add(new PlayerEndGameController(i, players.get(i).getPlayerInfo().getPlayerIcon(), players.get(i).getPlayerInfo().getPlayerName(), players.get(i).getHand().size()));
		}
	}
	
	public VBox getView() {
		return this;
	}

}
