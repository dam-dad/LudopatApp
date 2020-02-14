package ui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import main.LudopatApp;

public class MPSelectionModeController {

    @FXML
    private VBox view;

    @FXML
    private JFXButton vsAIButton;

    @FXML
    private JFXButton joinButton;
    
    @FXML
    private JFXButton onlineButton;
    
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
