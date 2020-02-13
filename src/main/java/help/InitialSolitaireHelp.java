package help;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.CardComponent;

public class InitialSolitaireHelp {
	
	@FXML
	VBox view;
	@FXML
	GridPane grid1;
	@FXML
	GridPane grid2;
	
	public InitialSolitaireHelp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/help/SolitaireInitialHelpView.fxml"));
			loader.setController(this);
			loader.load();
			
			CardComponent playableCard = new CardComponent(new Image(getClass().getResource("/ui/images/poker/card_back.png").toString()));
			playableCard.setId("playable");
			playableCard.turn();
			CardComponent saveableCard = new CardComponent(new Image(getClass().getResource("/ui/images/poker/card_back.png").toString()));
			saveableCard.setId("saveable");
			saveableCard.turn();
			
			grid1.add(playableCard, 0, 0);
			grid2.add(saveableCard, 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public VBox getView() {
		return this.view;
	}
	
}
