package net;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Chat implements Initializable {

	@FXML
    private VBox view;

    @FXML
    private VBox content;

    @FXML
    private StackPane actionsStack;

    @FXML
    private HBox actionsBox;

    @FXML
    private JFXTextArea messageArea;

    @FXML
    private JFXButton emojiButton;

    @FXML
    private JFXButton sendButton;

    @FXML
    private JFXButton chatButton;

    @FXML
    void closeChat(ActionEvent event) {

    }

    
	public Chat () {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/MPChatView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}

	@FXML
    void emoteAction(ActionEvent event) {

    }

    @FXML
    void sendButton(ActionEvent event) {

    }
    public VBox getView() {
		return view;
	}
}
