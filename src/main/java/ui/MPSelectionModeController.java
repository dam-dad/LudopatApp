package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LudopatApp;
/**
 * <b>MPSelectionModeController</b> <br>
 * <br>
 * 
 * Controlador de la vista de seleccion de modo multijugador
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class MPSelectionModeController implements Initializable{

    @FXML
    private VBox view;
    
    @FXML
    private HBox hox;

    @FXML
    private JFXButton vsAIButton;

    @FXML
    private JFXButton joinButton;
    
    @FXML
    private JFXButton onlineButton;
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    LudopatApp ludopp;
    
    public MPSelectionModeController(LudopatApp ludopp) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MPSelectionModeView.fxml"));
			loader.setController(this);
			loader.load();
			
			this.ludopp = ludopp;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		setMovingHandler();
		
	}
    /**
	 * Crea un evento para poder mover la ventana al clickar y arrastrar
	 */
	private void setMovingHandler() {
		hox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = ludopp.getMainStage().getX() - event.getScreenX();
                yOffset = ludopp.getMainStage().getY() - event.getScreenY();
            }
        });
		hox.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	ludopp.getMainStage().setX(event.getScreenX() + xOffset);
            	ludopp.getMainStage().setY(event.getScreenY() + yOffset);
            }
        });	
	}
    

    @FXML
    void toAIconfig(ActionEvent event) {
    	ludopp.goAIMenu();
    }

    @FXML
    void toJoinAction(ActionEvent event) {
    	ludopp.goClientConfig();
    }
    
    @FXML
    void toOnlineConfig(ActionEvent event) {
    	ludopp.goServerConfig();
    }

    @FXML
    void toMenu(ActionEvent event) {
    	ludopp.goMenu();
    }
    
    public VBox getView() {
		return this.view;
	}

	

}
