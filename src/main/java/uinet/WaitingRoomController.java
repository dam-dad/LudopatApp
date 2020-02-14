package uinet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;

public class WaitingRoomController extends AnchorPane implements Initializable {

	public WaitingRoomController(LudopatApp ludotapp) {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/WaitingRoomView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
