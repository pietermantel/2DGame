package dev.pietermantel.background;

import java.awt.Image;
import java.io.IOException;

import dev.pietermantel.main.ResourceLoader;

public enum Background {
	playersBedroom("res/backgrounds/player_bedroom.jpg");

	private String imagePath;
	private Image image;

	private Background(String imagePath) {
		this.imagePath = imagePath;
		try {
			image = ResourceLoader.loadTexture(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
