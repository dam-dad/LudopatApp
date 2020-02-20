package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * <b>SentEmote</b> <br>
 * <br>
 * 
 * Componente de emoticono enviado
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class SentEmote extends VBox implements Initializable{
	@FXML
    private ImageView emoteImage;
    
    @FXML
    private HBox messageBox;
	/**
	 * Codigo de emoticono
	 */
	String emoteCode;
	
	public SentEmote (String emoteCode){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/SentEmote.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.emoteCode = emoteCode;
			Image emote = new Image(getClass().getResource("/ui/images/chat/emotes/" + emoteCode + ".png").toString());
			this.emoteImage.setImage(emote);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public HBox getMessageBox() {
		return this.messageBox;
	}
}
