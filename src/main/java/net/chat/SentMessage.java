package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SentMessage extends VBox implements Initializable{
	@FXML
    private Label messageLabel;
    
    @FXML
    private HBox messageBox;
	
	public SentMessage(String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/SentMessage.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			messageLabel.setText(message);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public Label getMessageLabel() {
		return this.messageLabel;
	}
	
	public HBox getMessageBox() {
		return this.messageBox;
	}
}
