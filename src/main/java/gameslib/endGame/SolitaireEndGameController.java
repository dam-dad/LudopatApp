package gameslib.endGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SolitaireEndGameController implements Initializable {
	
    @FXML
    private VBox view;

    @FXML
    private Label timeText;

    @FXML
    private Label roundsText;

    @FXML
    private Label scoreText;
    
    private final int SECONDS_PER_ROUND = 5;
    
    String time;
    String minutes;
    String seconds;
    String rounds;
    int scoreMin;
    int scoreSec;
	
	public SolitaireEndGameController(String time, int min, int sec, int rounds) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/endGame/SolitaireEndGameComponent.fxml"));
			loader.setController(this);
			loader.load();
			
			this.minutes = String.valueOf(min);
			this.seconds = String.valueOf(sec);
			this.rounds = String.valueOf(rounds);
			
			int handicapSeconds = rounds * SECONDS_PER_ROUND;
			int handicapMinutes = 0;
			
			while (handicapSeconds/60 > 0) {
				handicapMinutes++;
				handicapSeconds -= 60;
			}
			
			this.time = time;
			this.scoreMin = min + handicapMinutes;
			this.scoreSec = sec + handicapSeconds;
			while (scoreSec >= 60) {
				this.scoreMin++;
				this.scoreSec -= 60;
			}
			
			setTexts();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTexts() {
		timeText.setText(time);
		roundsText.setText(rounds);
		if (scoreMin < 10 && scoreSec < 10) {
			scoreText.setText("0" + scoreMin + ":0" + scoreSec);
		}else {
			if (scoreMin < 10) {
				scoreText.setText("0" + scoreMin + ":" + scoreSec);
			}else {
				if (scoreSec < 10) {
					scoreText.setText(scoreMin + ":0" + scoreSec);
				}
			}
		}
		setStyles();
	}
	
	private void setStyles() {
		//Tiempo
		if (Integer.parseInt(minutes) < 2) {
			timeText.setId("two");
		}if (Integer.parseInt(minutes) < 1) {
			timeText.setId("one");
		}
		//Rondas
		if (Integer.parseInt(rounds) < 40) {
			roundsText.setId("two");
		}if (Integer.parseInt(rounds) < 30) {
			roundsText.setId("one");
		}
		//Puntuación
		if (scoreMin < 5) {
			scoreText.setId("two");
		}if (scoreMin < 4) {
			scoreText.setId("one");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public VBox getView() {
		return this.view;
	}

}
