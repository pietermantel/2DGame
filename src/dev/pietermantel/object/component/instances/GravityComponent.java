package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.pietermantel.object.GameObject;

public class GravityComponent extends CollisionBoxComponent {
	private int yVel = 0;
	private Rectangle[] collisionBoxes;

	public GravityComponent(GameObject parent, Rectangle[] collisionBoxes) {
		super(parent, collisionBoxes, false);
		this.collisionBoxes = collisionBoxes;
	}

	public GravityComponent(GameObject parent, Rectangle collisionBox) {
		super(parent, collisionBox, false);
		collisionBoxes = new Rectangle[1];
		collisionBoxes[0] = collisionBox;
	}

	@Override
	public void tick() {
		yVel += 1;
		if (yVel >= 0) {
			for (int i = 0; i < yVel; i++) {
				if(!super.collides()) {
					parent.setY(parent.getY() + 1);
				}
			}
		} else {
			for (int i = 0; i < -yVel; i++) {
				if(!super.collides()) {
					parent.setY(parent.getY() - 1);
				}
			}
		}
		if(super.collides()) yVel = 0;
		super.updateCollisionBoxes();
		collisionBoxes = super.collisionBoxes;
	}

	@Override
	public void render(Graphics g) {
		g.drawRect(collisionBoxes[0].x, collisionBoxes[0].y, collisionBoxes[0].width, collisionBoxes[0].height);
	}
}
