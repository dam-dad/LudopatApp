package games;

import java.io.Serializable;

public class Suit implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String imgPrefix;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPrefix() {
		return imgPrefix;
	}

	public void setImgPrefix(String imgPrefix) {
		this.imgPrefix = imgPrefix;
	}
}
