package dev.blijde_broers.object.components.instances;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import dev.blijde_broers.main.Game;
import dev.blijde_broers.misc.Texture;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.components.ObjectComponent;

public class TextureComponent extends ObjectComponent {

	private Texture texture;
	private boolean autoRotate;
	private Transform offset;
	private double theta;

	public TextureComponent(GameObject parent, String img, boolean autoRotate, Transform offset) {
		super(parent);
		texture = Texture.getTexture(img);
		this.autoRotate = autoRotate;
		this.offset = offset;
	}

	public TextureComponent(GameObject parent, Texture texture, boolean autoRotate, Transform offset) {
		super(parent);
		this.texture = texture;
		this.autoRotate = autoRotate;
		this.offset = offset;
	}

	@Override
	public void tick() {
//		if (autoRotate) {
			theta = parent.getTransform().add(offset).rotation;
//		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform old = g2D.getTransform();
		g2D.translate(parent.getActualX(), parent.getActualY());
//		g2D.translate((int) (Game.GAME.getMainCamera().zoom * offset.width / 2), (int) (Game.GAME.getMainCamera().zoom * offset.height / 2));
		g2D.rotate(theta);
		g2D.translate((int) -(Game.GAME.getMainCamera().zoom * offset.width / 2), (int) -(Game.GAME.getMainCamera().zoom * offset.height / 2));
		g2D.drawImage(texture.img, 0, 0, (int) (Game.GAME.getMainCamera().zoom * offset.width),
				(int) (Game.GAME.getMainCamera().zoom * offset.height), null);
		g2D.setTransform(old);
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public boolean isAutoRotate() {
		return autoRotate;
	}

	public void setAutoRotate(boolean autoRotate) {
		this.autoRotate = autoRotate;
	}

	public Transform getOffset() {
		return offset;
	}

	public void setOffset(Transform offset) {
		this.offset = offset;
	}

}
