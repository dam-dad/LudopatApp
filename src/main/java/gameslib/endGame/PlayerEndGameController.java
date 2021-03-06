package gameslib.endGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
/**
 * <b>PlayerEndGameController</b> <br>
 * <br>
 * 
 * Controlador de la tabla de jugadores para el final de la partida
 * 
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class PlayerEndGameController extends HBox implements Initializable {
	
	@FXML
	private HBox view;
	
    @FXML
    private Label posLabel;

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label cardLabel;
	
	public PlayerEndGameController(int position, Image playerImage, String playerName, int cards) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/endGame/PlayerEndGameComponent.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			switch (position) {
			case 0:
				this.setId("first");
				break;
			case 1:
				this.setId("second");
				break;
			case 2:
				this.setId("third");
				break;
			case 3:
				this.setId("loser");
				break;
			}
			
			posLabel.setText(String.valueOf(position + 1));	
			this.playerImage.setImage(playerImage);
			playerNameLabel.setText(playerName);
			cardLabel.setText(String.valueOf(cards));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
