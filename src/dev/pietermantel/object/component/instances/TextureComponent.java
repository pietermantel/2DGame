package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import dev.pietermantel.main.ResourceLoader;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class TextureComponent extends Component {
	private Image texture;
	public int imgOffsetX, imgOffsetY;
	
	public TextureComponent(GameObject parent, String texturePath, int imgOffsetX, int imgOffsetY) {
		super(parent);
		this.imgOffsetX = imgOffsetX;
		this.imgOffsetY = imgOffsetY;
		try {
			texture = ResourceLoader.loadTexture(texturePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, parent.getX() + imgOffsetX, parent.getY() + imgOffsetY, null);
	}
}