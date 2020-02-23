package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.LudopatApp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ConfigMenuController extends AnchorPane implements Initializable {
	
	@FXML
	private AnchorPane root;
	
	@FXML
    private JFXToggleButton modeToggle;

    @FXML
    private JFXCheckBox silencedCheck;

    @FXML
    private JFXSlider volumeSlider;

    @FXML
    private JFXButton backButton;
	
	private LudopatApp ludopp;

	private BooleanProperty silenced = new SimpleBooleanProperty();
	private DoubleProperty volume = new SimpleDoubleProperty();
	private BooleanProperty whiteMode = new SimpleBooleanProperty();
	
	public ConfigMenuController(LudopatApp ludopp) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/config/GeneralConfigView.fxml"));
			loader.setController(this);
			loader.load();
			
			this.ludopp = ludopp;
			
			silenced.set(ludopp.isSilenced());
			volume.set(ludopp.getVolume());
			whiteMode.set(ludopp.isWhiteMode());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		volume.bindBidirectional(volumeSlider.valueProperty());
		silenced.bindBidirectional(silencedCheck.selectedProperty());
		whiteMode.bindBidirectional(modeToggle.selectedProperty());
		
		whiteMode.addListener(e -> changeColors());
		silenced.addListener(e -> silence());
		volume.addListener(e -> changeVolume());
	}
	
	private void changeColors() {
		root.getStylesheets().remove(0);
		if (whiteMode.get()) {
			// Modo claro
			root.getStylesheets().add(getClass().getResource("/ui/css/whiteMode/ConfigMenuStyle.css").toString());
		}else {
			// Modo oscurod
			root.getStylesheets().add(getClass().getResource("/ui/css/ConfigMenuStyle.css").toString());
		}
	}
	
	private void silence() {
		if (silenced.get()) {
			ludopp.stopMusic();
		}else {
			ludopp.playMusic();
			changeVolume();
		}
	}
	
	private void changeVolume() {
		ludopp.setMusicVolume(volume.get()/100);
	}
	
	private void saveChanges() {
		File file = new File(getClass().getResource("/config/config.dat").getFile());
		try {
			RandomAccessFile configFile = new RandomAccessFile(file, "rw");
			//Borra el archivo
			configFile.setLength(0);
			//Escribe si está silenciado
			configFile.writeBoolean(silenced.get());
			//Escribe el volumen
			configFile.writeDouble(volume.get()/100);
			//Escribe si es modo claro
			configFile.writeBoolean(whiteMode.get());
			
			//Cerramos el archivo
			configFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void goBack(ActionEvent event) {
    	saveChanges();
    	ludopp.goMenu();
    }
    
    public AnchorPane getView() {
		return this.root;
	}

}
