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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

	private Dos dosGame;

	@FXML
	void closeChat(ActionEvent event) {
		dosGame.getNETHud().closeChat();
	}

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

	private void identifyMessage(String message, int fromId) {
		String nextSixChars = "";

		if (message.length() <= 4) {
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
					case "*b ":
						showMessage(message, style, fromId);
						break;
					case "*i ":
						showMessage(message, style, fromId);
						break;
					case "*d ":
						showMessage(message, style, fromId);
						break;
					case "*g ":
						showMessage(message, style, fromId);
						break;
					}
					break;
				case '@':
					// Si es '@' puede tratarse de un mensaje privado a x
					// 49 es el int de '1' y 48 el de '0'
					if (message.charAt(1) > 48 && message.charAt(1) < dosGame.getCurrentPlayers().size() + 48) {
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
						break;
					}
				}
			}
		}

		/*
		 * OLD if (message.length() >= 7) { for (int i = 1; i <= 6; i++) { nextSixChars
		 * += message.charAt(i); }
		 * 
		 * // Comprobamos el primer caracter el mensaje switch (message.charAt(0)) {
		 * case '@': // Si es '@' puede tratarse de un mensaje privado a x // 49 es el
		 * int de '1' y 48 el de '0' if (message.charAt(1) >= 49 && message.charAt(1) <=
		 * dosGame.getCurrentPlayers().size() + 48) { // Mensaje privado if
		 * (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == fromId) { // Mensaje
		 * privado escrito por el jugador showPrivateMessage(message, fromId); } else {
		 * if (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == message.charAt(1)
		 * - 48) { // Destinatario del mensaje showPrivateMessage(message, fromId); } }
		 * } break; case '/': // Si es '/' puede tratarse de un emote // Si es igual a
		 * algún emote code se trata de un emote switch (nextSixChars) { case "emote1":
		 * showEmote(nextSixChars, fromId); return; case "emote2":
		 * showEmote(nextSixChars, fromId); return; case "emote3":
		 * showEmote(nextSixChars, fromId); return; case "emote4":
		 * showEmote(nextSixChars, fromId); return; case "emote5":
		 * showEmote(nextSixChars, fromId); return; case "emote6":
		 * showEmote(nextSixChars, fromId); return; case "emote7":
		 * showEmote(nextSixChars, fromId); return; case "emote8":
		 * showEmote(nextSixChars, fromId); return; default: // No es un emote break; }
		 * 
		 * break; case '*': // Si es '*' puede tratarse de un estilo // Si es igual a
		 * algún Style code se trata de un estilo switch (message.charAt(1)) { case 'b':
		 * showMessage(message, "bold", fromId); return; case 'i': showMessage(message,
		 * "italic", fromId); return; case 'd': showMessage(message, "black", fromId);
		 * return; case 'g': showMessage(message, "gold", fromId); return; default: //
		 * Si no es ninguno no es un estilo y por consiguiente no tiene modificador
		 * break; }
		 * 
		 * break; default: // No tiene modificador showNormalMessage(message, fromId);
		 * break; } } else { if (message.length() > 3) { if (message.charAt(0) == '@') {
		 * // Si es '@' puede tratarse de un mensaje privado a x // 49 es el int de '1'
		 * y 48 el de '0' if (message.charAt(1) >= 49 && message.charAt(1) <=
		 * dosGame.getCurrentPlayers().size() + 48) { // Mensaje privado if
		 * (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == fromId) { // Mensaje
		 * privado escrito por el jugador showPrivateMessage(message, fromId); } else {
		 * if (dosGame.getLocalPlayer().getPlayerInfo().getUserID() == message.charAt(1)
		 * - 48) { // Destinatario del mensaje showPrivateMessage(message, fromId); } }
		 * } }else { if (message.charAt(0) == '*') { // Si es '*' puede tratarse de un
		 * estilo // Si es igual a algún Style code se trata de un estilo switch
		 * (message.charAt(1)) { case 'b': showMessage(message, "bold", fromId); return;
		 * case 'i': showMessage(message, "italic", fromId); return; case 'd':
		 * showMessage(message, "black", fromId); return; case 'g': showMessage(message,
		 * "gold", fromId); return; default: // Si no es ninguno no es un estilo y por
		 * consiguiente no tiene modificador break; } }else { //Se trata de un mensaje
		 * normal showNormalMessage(message, fromId); } } }else { //Se trata de un
		 * mensaje normal showNormalMessage(message, fromId); } }
		 */

	}

	private void showPrivateMessage(String message, int fromCode) {
		String messageOK = "";
		for (int i = 3; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
			received.setId("private");
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(messageOK);
			sent.setId("private");
			content.getChildren().add(sent);
		}
	}

	private void showEmote(String emoteCode, int fromCode) {
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
			content.getChildren().add(new ReceivedEmote(emoteCode, issuer));
		} else {
			// Se trata de un mensaje de salida
			content.getChildren().add(new SentEmote(emoteCode));
		}
	}

	private void showMessage(String message, String styleCode, int fromCode) {
		String messageOK = "";
		for (int i = 8; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}

		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
			received.setId(styleCode);
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(messageOK);
			sent.setId(styleCode);
			content.getChildren().add(sent);
		}
	}

	private void showNormalMessage(String message, int fromCode) {
		if (fromCode != dosGame.getLocalPlayer().getPlayerInfo().getUserID()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
			ReceivedMessage received = new ReceivedMessage(message, issuer);
			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
			SentMessage sent = new SentMessage(message);
			content.getChildren().add(sent);
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				dosGame.getClientThread().sendMessage(messageArea.getText());
				messageArea.setText("");
			}
		});

	}

	public VBox getView() {
		return view;
	}

	public void getMessage(String message, int senderID) {
		identifyMessage(message, senderID);
	}
}
