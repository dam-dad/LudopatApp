package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import main.LudopatApp;

public class SplashController implements Initializable {
	@FXML
	private BorderPane view;
	private LudopatApp ludopp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public SplashController(LudopatApp app) throws IOException {

		this.ludopp = app;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/SplashScreenView.fxml"));
		loader.setController(this);
		loader.load();
	}
	public BorderPane getView() {
		return view;
	}
}
