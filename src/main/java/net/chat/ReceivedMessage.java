package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReceivedMessage extends VBox implements Initializable{

    @FXML
    private Label IssuerNameLabel;

    @FXML
    private Label messageLabel;
    
	
	
	public ReceivedMessage(String message, String issuer) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/ReceivedMessage.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			

			IssuerNameLabel.setText(issuer);
			messageLabel.setText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
}
