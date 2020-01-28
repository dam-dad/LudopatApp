package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import main.LudopatApp;

public class SplashController implements Initializable {
	@FXML
	private BorderPane view;

	private LudopatApp ludopp;
	private Timeline timeline;
	FadeTransition fadeTransition;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFade();
		waitSplash();
	}

	public SplashController(LudopatApp app) throws IOException {
		this.ludopp = app;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/SplashScreenView.fxml"));
		loader.setController(this);
		loader.load();
	}
	private void setFade() {
		fadeTransition = new FadeTransition();
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setDuration(Duration.seconds(0.5));
		fadeTransition.setNode(view);
	}
	private void waitSplash() {
		timeline = new Timeline(new KeyFrame(Duration.millis(4000), ae -> fadeTransition.play()));
		timeline.play();
		fadeTransition.setOnFinished(ae -> ludopp.goMenu());
	}

	@FXML
	void skipSplashClick(MouseEvent event) {
		timeline.stop();
		ludopp.goMenu();
	}

	public BorderPane getView() {
		return view;
	}
}
