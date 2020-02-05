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

public class PlayerEndGameController extends HBox implements Initializable {
	
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/endGame/EndGameComponent.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			switch (position) {
			case 1:
				this.setId("first");
				break;
			case 2:
				this.setId("second");
				break;
			case 3:
				this.setId("third");
				break;
			case 4:
				this.setId("loser");
				break;
			}
			
			this.posLabel.setText(String.valueOf(position));	
			this.playerImage.setImage(playerImage);
			this.playerNameLabel.setText(playerName);
			this.cardLabel.setText(String.valueOf(cards));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
