package uinet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.LudopatApp;
/**
 * <b>IPConfigController</b> <br>
 * <br>
 * 
 * Controlador de la vista de selección de ip para juego online
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class IpConfigController extends AnchorPane implements Initializable {

	@FXML
	private AnchorPane component;

	@FXML
	private JFXTextField ipText;
	
    @FXML
    private HBox connectionBox;

    @FXML
    private Label connectingLabel;

    @FXML
    private JFXSpinner connectingSpinner;

    private BooleanProperty connectionValid = new SimpleBooleanProperty(false);
    
	LudopatApp ludopp;
	private StringProperty ip = new SimpleStringProperty();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ip.bind(ipText.textProperty());
		connectingLabel.visibleProperty().bind(connectionValid);
		connectingSpinner.visibleProperty().bind(connectionValid);
	}
	
	/**
	 * Visualiza o no el estado de conexión
	 * @param bConnecting
	 */
	public void setConnectionStatus(boolean bConnecting) {
		connectionValid.set(bConnecting);
	}

	public IpConfigController(LudopatApp ludopp) {
		this.ludopp = ludopp;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/online/IPSelectConfigView.fxml"));
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
	
	public JFXTextField getIpText() {
		return ipText;
	}
	

}
