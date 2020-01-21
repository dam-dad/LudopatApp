package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class SummaryController extends AnchorPane implements Initializable {

	public SummaryController() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SummaryView.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
