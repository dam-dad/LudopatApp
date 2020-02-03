package ui.config.avatar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class AvatarSelector extends GridPane implements Initializable {
	
	@FXML
	GridPane grid;
	
	public AvatarSelector(int selectedPos, Avatar[] availableAvatars) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/avatar/AvatarSelector.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			availableAvatars[selectedPos].setSelected(true);
			
			grid.add(availableAvatars[0], 1, 1);
			grid.add(availableAvatars[1], 3, 1);
			grid.add(availableAvatars[2], 5, 1);
			grid.add(availableAvatars[3], 7, 1);
			grid.add(availableAvatars[4], 1, 3);
			grid.add(availableAvatars[5], 3, 3);
			grid.add(availableAvatars[6], 5, 3);
			grid.add(availableAvatars[7], 7, 3);
			
			grid.setAlignment(Pos.CENTER);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
