package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ReceivedEmote extends VBox implements Initializable{


    @FXML
    private Label IssuerNameLabel;

    @FXML
    private ImageView emoteImage;
    
    String emoteCode;
	String issuer;
	
	public ReceivedEmote(String emoteCode, String issuer) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/ReceivedMessage.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.emoteCode = emoteCode;
			this.issuer = issuer;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.IssuerNameLabel.setText(this.issuer);
		Image emote = new Image(getClass().getResource("ui/images/chat/emotes/" + emoteCode + ".png").toString());
		this.emoteImage.setImage(emote);
	}
	
}
