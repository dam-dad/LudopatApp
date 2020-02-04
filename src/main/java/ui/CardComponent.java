package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardComponent extends ImageView {
	private Image cardImage;
	
	private Image cardBack = new Image(getClass().getResource("ui/images/dos/card_back.png").toString());
	
	public CardComponent(Image cardImage) {
		this.cardImage = cardImage;
		this.setImage(cardBack);
	}
	
	public void turn() {
		if (this.imageProperty().get().equals(cardBack)) {
			this.setImage(cardImage);
		}else {
			this.setImage(cardBack);
		}
	}
}
