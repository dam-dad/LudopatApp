package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * <b>CardComponent</b> <br>
 * <br>
 * 
 * Componente carta, que guarda la imagen de la carta y su reverso
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class CardComponent extends ImageView {
	private Image cardImage;
	
	private Image cardBack = new Image(getClass().getResource("/ui/images/dos/card_back.png").toString());
	
	public CardComponent(Image cardImage) {
		this.cardImage = cardImage;
		this.setImage(cardBack);
	}
	/**
	 * Intercambia la carta con su reverso y viceversa
	 */
	public void turn() {
		if (this.imageProperty().get().equals(cardBack)) {
			this.setImage(cardImage);
		}else {
			this.setImage(cardBack);
		}
	}
}
