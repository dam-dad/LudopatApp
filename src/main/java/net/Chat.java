package net;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gameslib.Dos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Chat implements Initializable {

	public enum Style {
		BOLD("bold--"), ITALIC("italic"), BLACK("black-"), GOLD("gold--");

		String code;

		Style(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}

	}

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

		if (message.length() >= 7) {
			for (int i = 1; i <= 6; i++) {
				nextSixChars += message.charAt(i);
			}

			// Comprobamos el primer caracter el mensaje
			switch (message.charAt(0)) {
			case '@':
				// Si es '@' puede tratarse de un mensaje privado a x
				// 49 es el int de '1' y 48 el de '0'
				if (message.charAt(1) >= 49 && message.charAt(1) <= dosGame.getCurrentPlayers().size() + 48) {
					// Mensaje privado
					if (dosGame.getActivePlayer().getId() == fromId) {
						// Mensaje privado escrito por el jugador
						showPrivateMessage(message, fromId);
					} else {
						if (dosGame.getActivePlayer().getId() == message.charAt(1) - 48) {
							// Destinatario del mensaje
							showPrivateMessage(message, fromId);
						}
					}
				}
				break;
			case '/':
				// Si es '/' puede tratarse de un emote
				// Si es igual a algún emote code se trata de un emote
				switch (nextSixChars) {
				case "emote1":
					showEmote(nextSixChars, fromId);
					return;
				case "emote2":
					showEmote(nextSixChars, fromId);
					return;
				case "emote3":
					showEmote(nextSixChars, fromId);
					return;
				case "emote4":
					showEmote(nextSixChars, fromId);
					return;
				case "emote5":
					showEmote(nextSixChars, fromId);
					return;
				case "emote6":
					showEmote(nextSixChars, fromId);
					return;
				case "emote7":
					showEmote(nextSixChars, fromId);
					return;
				case "emote8":
					showEmote(nextSixChars, fromId);
					return;
				default:
					// No es un emote
					break;
				}

				break;
			case '*':
				// Si es '*' puede tratarse de un estilo
				// Si es igual a algún Style code se trata de un estilo
				switch (nextSixChars) {
				case "bold--":
					showMessage(message, "bold", fromId);
					return;
				case "italic":
					showMessage(message, "italic", fromId);
					return;
				case "black-":
					showMessage(message, "black", fromId);
					return;
				case "gold--":
					showMessage(message, "gold", fromId);
					return;
				default:
					// Si no es ninguno no es un estilo y por consiguiente no tiene modificador
					break;
				}

				break;
			default:
				// No tiene modificador
				showNormalMessage(message, fromId);
				break;
			}
		} else {
			if (message.length() > 3) {
				if (message.charAt(0) == '@') {
					// Si es '@' puede tratarse de un mensaje privado a x
					// 49 es el int de '1' y 48 el de '0'
					if (message.charAt(1) >= 49 && message.charAt(1) <= dosGame.getCurrentPlayers().size() + 48) {
						// Mensaje privado
						if (dosGame.getActivePlayer().getId() == fromId) {
							// Mensaje privado escrito por el jugador
							showPrivateMessage(message, fromId);
						} else {
							if (dosGame.getActivePlayer().getId() == message.charAt(1) - 48) {
								// Destinatario del mensaje
								showPrivateMessage(message, fromId);
							}
						}
					}
				}
			}else {
				//Se trata de un mensaje normal
				showNormalMessage(message, fromId);
			}
		}

	}

	private void showPrivateMessage(String message, int fromCode) {
		String messageOK = "";
		for (int i = 3; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}
		// TODO controlador mensaje
		if (fromCode != dosGame.getActivePlayer().getId()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
//			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
//			received.setId("private");
//			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
//			SentMessage sent = new SentMessage(messageOK);
//			sent.setId("private");
//			content.getChildren().add(sent);
		}
	}

	private void showEmote(String emoteCode, int fromCode) {
		// TODO controlador emote
		if (fromCode != dosGame.getActivePlayer().getId()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
//			content.getChildren().add(new ReceivedEmote(emoteCode, issuer));
		} else {
			// Se trata de un mensaje de salida
//			content.getChildren().add(new SentEmote(emoteCode));	
		}
	}

	private void showMessage(String message, String styleCode, int fromCode) {
		String messageOK = "";
		for (int i = 8; i < message.length(); i++) {
			messageOK += message.charAt(i);
		}
		// TODO controlador mensaje
		if (fromCode != dosGame.getActivePlayer().getId()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
//			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
//			received.setId(styleCode);
//			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
//			SentMessage sent = new SentMessage(messageOK);
//			sent.setId(styleCode);
//			content.getChildren().add(sent);
		}
	}

	private void showNormalMessage(String message, int fromCode) {
		// TODO controlador mensaje
		if (fromCode != dosGame.getActivePlayer().getId()) {
			// Se trata de un mensaje de entrada
			String issuer = dosGame.getCurrentPlayers().get(fromCode).getPlayerInfo().getPlayerName();
//			ReceivedMessage received = new ReceivedMessage(messageOK, issuer);
//			content.getChildren().add(received);
		} else {
			// Se trata de un mensaje de salida
//			SentMessage sent = new SentMessage(messageOK);
//			content.getChildren().add(sent);
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
