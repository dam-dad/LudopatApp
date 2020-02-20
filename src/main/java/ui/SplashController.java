package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import main.LudopatApp;
/**
 * <b>SplashController</b> <br>
 * <br>
 * 
 * Controlador de la splash screen inicial
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
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
	/**
	 * Crea la transicion de fade
	 */
	private void setFade() {
		fadeTransition = new FadeTransition();
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setDuration(Duration.seconds(0.5));
		fadeTransition.setNode(view);
	}
	/**
	 * Espera 3 segundos con el logo en pantalla
	 */
	private void waitSplash() {
		timeline = new Timeline(new KeyFrame(Duration.millis(4000), ae -> fadeTransition.play()));
		timeline.play();
		fadeTransition.setOnFinished(ae -> ludopp.goMenu());
	}
	/**
	 * Accion para saltarse la espera del splash 
	 * @param event
	 */
	@FXML
	void skipSplashClick(MouseEvent event) {
		timeline.stop();
		ludopp.goMenu();
	}

	public BorderPane getView() {
		return view;
	}
}
