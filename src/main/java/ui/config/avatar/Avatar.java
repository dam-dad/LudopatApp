package ui.config.avatar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * 
 * <b>Controlador componente avatar</b>
 * <br><br>
 * 
 * Controlador para el componente de avatar
 * incrustado dentro del avatar selector dialog
 * que incluye la imagen y nombre para dicho avatar.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 */

public class Avatar extends VBox implements Initializable {
	
	@FXML
	ImageView avatarImage;
	@FXML
	Label avatarName;
	
	StringProperty imgName = new SimpleStringProperty();
	StringProperty playerName = new SimpleStringProperty();
	BooleanProperty selected = new SimpleBooleanProperty(false);
	
	public Avatar(String imgName, String playerName) {

		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/avatar/Avatar.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
			this.imgName.set(imgName);
			this.playerName.set(playerName);
			
			//Asigna la imagen
			this.avatarImage.setImage(new Image(this.imgName.get()));
			//Asigna el nombre
			this.avatarName.setText(this.playerName.get());
			
			selected();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.selected.addListener(e -> selected());
	}
	
	private void selected() {
		if (selected.get()) {
			this.setStyle("-fx-effect: dropshadow(three-pass-box, green, 30, 0.2, 0, 0);");
			this.setDisable(true);
		}else {
			this.setStyle("-fx-effect: dropshadow(three-pass-box, black, 10, 0.2, 0, 0);");
			this.setDisable(false);
		}
	}
	
	public Avatar getAvatar() {
		return this;
	}
	
	
	public final String getImgName() {
		return this.imgName.get();
	}
	
	public final String getPlayerName() {
		return this.playerName.get();
	}
	
	public final BooleanProperty selectedProperty() {
		return this.selected;
	}
	
	public final boolean isSelected() {
		return this.selectedProperty().get();
	}
	
	public final void setSelected(final boolean selected) {
		this.selectedProperty().set(selected);
	}
	
}
