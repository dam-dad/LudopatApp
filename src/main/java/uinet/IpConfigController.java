package uinet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;

public class IpConfigController extends AnchorPane implements Initializable {

	@FXML
	private AnchorPane component;

	@FXML
	private JFXTextField ipText;

	LudopatApp ludopp;
	private StringProperty ip = new SimpleStringProperty();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ip.bind(ipText.textProperty());

	}

	public IpConfigController(LudopatApp ludopp) {
		this.ludopp = ludopp;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/IpSelectConfigView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final StringProperty ipProperty() {
		return this.ip;
	}
	

	public final String getIp() {
		return this.ipProperty().get();
	}
	

	public final void setIp(final String ip) {
		this.ipProperty().set(ip);
	}
	

}
