package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class GravityComponent extends Component {
	private int yVel;
	private Rectangle[] collisionBoxes;

	public GravityComponent(GameObject parent, Rectangle[] collisionBoxes) {
		super(parent);
		this.collisionBoxes = collisionBoxes;
	}

	public GravityComponent(GameObject parent, Rectangle collisionBox) {
		super(parent);
		collisionBoxes = new Rectangle[1];
		collisionBoxes[0] = collisionBox;
	}

	@Override
	public void tick() {
		yVel += 1;
		if (yVel >= 0) {
			for (int i = 0; i < yVel; i++) {
				parent.setY(parent.getY() + 1);
			}
		} else {
			for (int i = 0; i < -yVel; i++) {
				parent.setY(parent.getY() - 1);
			}
		}
	}

	@Override
	public void render(Graphics g) {

	}

	public boolean collides() {
		for (Rectangle r1 : CollisionBoxComponent.ALL_COLLISION_BOXES) {
			for (Rectangle r2 : collisionBoxes) {
				if (r1.intersects(r2))
					return true;
			}
		}
		return false;
	}
}
