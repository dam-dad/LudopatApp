package ui.config.avatar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
/**
 * <b>AvatarSelector</b> <br>
 * <br>
 * 
 * Componente selector de avatares que permite al usuario elegir uno de los 8 
 * posibles avatares de jugador
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class AvatarSelector extends GridPane implements Initializable {
	
	@FXML
	GridPane grid;
	
	int selected;
	
	private BooleanProperty finish = new SimpleBooleanProperty(false);
	
	public AvatarSelector(int selectedPos, Avatar[] availableAvatars) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/avatar/AvatarSelector.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.selected = selectedPos;
			
			grid.add(availableAvatars[0], 1, 1);
			grid.add(availableAvatars[1], 3, 1);
			grid.add(availableAvatars[2], 5, 1);
			grid.add(availableAvatars[3], 7, 1);
			grid.add(availableAvatars[4], 1, 3);
			grid.add(availableAvatars[5], 3, 3);
			grid.add(availableAvatars[6], 5, 3);
			grid.add(availableAvatars[7], 7, 3);
			
			grid.setAlignment(Pos.CENTER);
			
			grid.getChildren().get(0).setOnMouseClicked(e -> selected(0));
			grid.getChildren().get(1).setOnMouseClicked(e -> selected(1));
			grid.getChildren().get(2).setOnMouseClicked(e -> selected(2));
			grid.getChildren().get(3).setOnMouseClicked(e -> selected(3));
			grid.getChildren().get(4).setOnMouseClicked(e -> selected(4));
			grid.getChildren().get(5).setOnMouseClicked(e -> selected(5));
			grid.getChildren().get(6).setOnMouseClicked(e -> selected(6));
			grid.getChildren().get(7).setOnMouseClicked(e -> selected(7));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void selected(int selected) {
		this.selected = selected;
		this.finish.set(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public int getSelected() {
		return selected;
	}
	
	public BooleanProperty getFinish() {
		return this.finish;
	}

}
