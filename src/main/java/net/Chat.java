package net;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gameslib.Dos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.chat.EmoteSelector;
import net.chat.ReceivedEmote;
import net.chat.ReceivedMessage;
import net.chat.SentEmote;
import net.chat.SentMessage;

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
	private ScrollPane scroll;

	private Dos dosGame;

	private EmoteSelector emoteSelector;

	public Chat(Dos dosGame) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/chat/MPChatView.fxml"));
		loader.setController(this);

		try {
			loader.load();

			this.dosGame = dosGame;
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emoteSelector = new EmoteSelector(this);
	}

	@FXML
	void closeChat(ActionEvent event) {
		dosGame.getNETHud().closeChat();
	}

	private void identifyMessage(String message, int fromId) {
		if (message.length() < 4) {
			showNormalMessage(message, fromId);
		} else {
			if (message.length() == 7 && message.charAt(0) == '/') {
				switch (message) {
				case "/emote1":
					showEmote(message, fromId);
					break;
				case "/emote2":
					showEmote(message, fromId);
					break;
				case "/emote3":
					showEmote(message, fromId);
					break;
				case "/emote4":
					showEmote(message, fromId);
					break;
				case "/emote5":
					showEmote(message, fromId);
					break;
				case "/emote6":
					showEmote(message, fromId);
					break;
				case "/emote7":
					showEmote(message, fromId);
					break;
				case "/emote8":
					showEmote(message, fromId);
					break;
				}
			} else {
				switch (message.charAt(0)) {
				case '*':
					String style = message.substring(0, 2);
					switch (style) {
					case "*b":
						showMessage(message, "bold", fromId);
						break;
					case "*i":
						showMessage(message, "italic", fromId);
						break;
					case "*d":
						showMessage(message, "black", fromId);
						break;
					case "*g":
						showMessage(message, "gold", fromId);
						break;
					}
					break;
				case '@':
					// Si es '@' puede tratarse de un mensaje privado a x
					// 49 es el int de '1' y 48 el de '0'
					if (message.charAt(1) >= 48 && message.charAt(1) < dosGame.getCurrentPlayers().size() + 48) {
						// Mensaje privado
						if (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == fromId) {
							// Mensaje privado escrito por el jugador
							showPrivateMessage(message, fromId);
						} else {
							if (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == message.charAt(1) - 48) {
								// Destinatario del mensaje
								showPrivateMessage(message, fromId);
							}
						}
					}
					break;
				default:
					// Se trata de un mensaje normal
					showNormalMessage(message, fromId);
					break;
				}
			}
		}
		scroll.layout();
		scroll.setVvalue(1.0d);
	}

	private void showPrivateMessage(String message, int fromCode) {
		String messageOK = "";
		for (int i = 3; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().stream().filter(p -> p.getPlayerInfo().getUserID() == fromCode)
					.findFirst().get().getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
			received.getMessageLabel().setId("black");
			received.getMessageBox().setId("private");
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(messageOK);
			sent.getMessageLabel().setId("black");
			sent.getMessageBox().setId("private");
			content.getChildren().add(sent);
		}
	}

	private void showEmote(String emoteCode, int fromCode) {
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().stream().filter(p -> p.getPlayerInfo().getUserID() == fromCode)
					.findFirst().get().getPlayerInfo().getPlayerName();
			content.getChildren().add(new ReceivedEmote(emoteCode, issuer));
		} else {
			// Se trata de un mensaje de salida
			content.getChildren().add(new SentEmote(emoteCode));
		}
	}

	private void showMessage(String message, String styleCode, int fromCode) {
		String messageOK = "";
		for (int i = 3; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}

		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().stream().filter(p -> p.getPlayerInfo().getUserID() == fromCode)
					.findFirst().get().getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
			received.getMessageLabel().setId(styleCode);
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(messageOK);
			sent.getMessageLabel().setId(styleCode);
			content.getChildren().add(sent);
		}
	}

	private void showNormalMessage(String message, int fromCode) {
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().stream().filter(p -> p.getPlayerInfo().getUserID() == fromCode)
					.findFirst().get().getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(message, issuer);
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(message);
			content.getChildren().add(sent);
		}
	}

	@FXML
	void emoteAction(ActionEvent event) {
		actionsStack.getChildren().add(emoteSelector);
	}

	@FXML
	void sendButton(ActionEvent event) {
		if (messageArea.getText().trim().length() > 0) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					dosGame.getClientThread().sendMessage(messageArea.getText());
					messageArea.setText("");
				}
			});
		}
	}

	public void appendEmote(String emoteCode) {
		messageArea.setText(emoteCode);
		sendButton(null);
	}

	public VBox getView() {
		return view;
	}

	public void getMessage(String message, int senderID) {
		identifyMessage(message, senderID);
	}

	public StackPane getActionsStack() {
		return actionsStack;
	}

	public void setActionsStack(StackPane actionsStack) {
		this.actionsStack = actionsStack;
	}
}
