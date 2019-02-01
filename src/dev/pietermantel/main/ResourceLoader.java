package dev.pietermantel.main;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ResourceLoader {
	public static Image loadTexture(String name) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(name);
		Image logo = ImageIO.read(input);
		return logo;
	}
}
