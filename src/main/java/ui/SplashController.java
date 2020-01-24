package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import main.LudopatApp;

public class SplashController implements Initializable {
	@FXML
	private BorderPane view;
	@FXML
    private Button skipButton;
	
	private LudopatApp ludopp;
	private Timeline timeline;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		waitSplash();

	}

	public SplashController(LudopatApp app) throws IOException {
		this.ludopp = app;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/SplashScreenView.fxml"));
		loader.setController(this);
		loader.load();
	}
	private void waitSplash() {
		timeline = new Timeline(new KeyFrame(Duration.millis(5000),ae -> ludopp.goMenu()));
		timeline.play();
	}
	@FXML
    void skipSplash(ActionEvent event) {
		timeline.stop();
		ludopp.goMenu();
    }
	public BorderPane getView() {
		return view;
	}
}
