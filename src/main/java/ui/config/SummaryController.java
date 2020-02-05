package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import games.GameRules;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;

/**
 * 
 * <b>Controlador vista resumen ajustes</b> <br>
 * <br>
 * 
 * Controlador para el resumen de los ajustes multijugador
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */
public class SummaryController extends AnchorPane implements Initializable {

	@FXML
	private Label playerNumberLabel, gameLabel, deckLabel, cardNumberLabel;

	@FXML
	private ImageView imagePlayer1, imagePlayer2, imagePlayer3, imagePlayer4;

	@FXML
	private Label player1Label, player2Label, player3Label, player4Label;

	private LudopatApp ludopp;

	public SummaryController(LudopatApp ludopp) {

		this.ludopp = ludopp;

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/SummaryView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initSummary() {

		// Ahora obtenemos los datos de las reglas de juego
		GameRules rules = ludopp.getGameRules();

		playerNumberLabel.setText(String.valueOf(rules.getNumPlayers()));
		gameLabel.setText(rules.getGameType());

		if (rules.getDeckType() != null) {
			deckLabel.setText(rules.getDeckType().toString());

			int nCards = rules.getDeckType().getNumCards();
			if (rules.getDeckType().isDoubleDeck()) {
				nCards *= 2;
			}
			cardNumberLabel.setText(String.valueOf(nCards));

			imagePlayer1.setImage(rules.getPlayersInfo().get(0).getPlayerIcon());
			player1Label.setText(rules.getPlayersInfo().get(0).getPlayerName());
			
			imagePlayer2.setImage(rules.getPlayersInfo().get(1).getPlayerIcon());
			player2Label.setText(rules.getPlayersInfo().get(1).getPlayerName());
			
			if (rules.getPlayersInfo().size() > 3) {
				imagePlayer3.setImage(rules.getPlayersInfo().get(2).getPlayerIcon());
				player3Label.setText(rules.getPlayersInfo().get(2).getPlayerName());
				if (rules.getPlayersInfo().size() == 4) {
					imagePlayer4.setImage(rules.getPlayersInfo().get(3).getPlayerIcon());
					player4Label.setText(rules.getPlayersInfo().get(3).getPlayerName());
				}
			}
		}

	}

}
