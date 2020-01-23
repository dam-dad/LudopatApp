package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * 
 * <b>Controlador configuración juego</b>
 * <br><br>
 * 
 * Controlador para los ajustes de multijugador con
 * opciones para elegir número de jugadores y tipo
 * de juego.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */

public class GameConfigController extends AnchorPane implements Initializable {


    @FXML
    private RadioButton p2Radio;

    @FXML
    private ToggleGroup playerGroup;

    @FXML
    private RadioButton p3Radio;

    @FXML
    private RadioButton p4Radio;

    @FXML
    private RadioButton dosRadio;

    @FXML
    private ToggleGroup gameGroup;
    
	public GameConfigController() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GameConfigView.fxml"));
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

}
