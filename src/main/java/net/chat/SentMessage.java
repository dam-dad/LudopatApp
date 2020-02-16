package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SentMessage implements Initializable{
	@FXML
    private Label messageLabel;

	String message;
	
	public SentMessage(String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/SentMessage.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.message = message;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.messageLabel.setText(this.message);
	}
}
