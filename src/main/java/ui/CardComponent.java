package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardComponent extends ImageView {
	private Image cardImage;
	
	private Image cardBack;
	
	public CardComponent(Image cardImage, Image cardBack) {
		this.cardImage = cardImage;
		this.cardBack = cardBack;
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
