package ui.config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;

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


	// FXML : View
	//---------------------------------------------------------
	
    @FXML
    private RadioButton p2Radio, p3Radio, p4Radio, dos;

    @FXML
    private ToggleGroup playerGroup, gameGroup;

    //---------------------------------------------------------
    
    private LudopatApp ludopp;
    
	public GameConfigController(LudopatApp app) {
	
		this.ludopp = app;
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GameConfigView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			setColors();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Cada vez que cambiamos los jugadores
		playerGroup.selectedToggleProperty().addListener( (o, ov, nv) -> onPlayersChanged(nv));
		
		// Cada vez que cambiamos el tipo de juego
		gameGroup.selectedToggleProperty().addListener(( (o, ov, nv) -> onTypeChanged(nv)));
		

		// Seleccionamos el primer elemento
		p2Radio.setSelected(true);
		dos.setSelected(true);
	}
	
	private void setColors() {
		this.getStylesheets().remove(0);

		if (ludopp.isWhiteMode()) {
			// Modo claro
			this.getStylesheets().add(getClass().getResource("/ui/css/whiteMode/ConfigMenuStyle.css").toString());
		} else {
			// Modo oscuro
			this.getStylesheets().add(getClass().getResource("/ui/css/ConfigMenuStyle.css").toString());
		}
	}

	private void onTypeChanged(Toggle nv) {
		
		RadioButton bt = (RadioButton)nv;
		
		if( bt != null ) {
			ludopp.getGameRules().setGameType(bt.getId());
		}
	}

	private void onPlayersChanged(Toggle nv) {
		
		if( nv.equals(p2Radio)) {
			ludopp.getGameRules().setNumPlayers(2);
		} else if( nv.equals(p3Radio))
			ludopp.getGameRules().setNumPlayers(3);
		else {
			ludopp.getGameRules().setNumPlayers(4);
		}
	}

}
