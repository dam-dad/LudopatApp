package net.chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.Chat;

public class EmoteSelector extends VBox implements Initializable {

	@FXML
	private JFXButton backButton;
	
	@FXML
	private HBox box1;
	
	@FXML
	private HBox box2;
	
	private Chat chat;

	public EmoteSelector(Chat chat) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ui/fxml/online/chat/EmoteSelectorComponent.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			this.chat = chat;

		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 1;
		for (Node image : box1.getChildren()) {
			final int aux = i;
			((ImageView) image).setOnMouseClicked(e -> writeEmote(aux));
			i++;
		}
		for (Node image : box2.getChildren()) {
			final int aux = i;
			((ImageView) image).setOnMouseClicked(e -> writeEmote(aux));
			i++;
		}
	}

	private void writeEmote(int i) {
		chat.appendEmote("/emote" + i);
		close(null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void close(ActionEvent event) {
		chat.getActionsStack().getChildren().remove(this);
	}
}
