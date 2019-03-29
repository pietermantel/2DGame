package dev.pietermantel.object.component.instances;

import java.awt.Graphics;

import dev.pietermantel.object.GameObject;

public class GravityComponent extends CollisionBoxComponent {
	private int yVel = 0;
	private CollisionBox[] collisionBoxes;

	public GravityComponent(GameObject parent, CollisionBox[] collisionBoxes, boolean collisionMovement) {
		super(parent, collisionBoxes, collisionMovement);
		this.collisionBoxes = collisionBoxes;
	}

	public GravityComponent(GameObject parent, CollisionBox collisionBox, boolean collisionMovement) {
		super(parent, collisionBox, collisionMovement);
		collisionBoxes = new CollisionBox[1];
		collisionBoxes[0] = collisionBox;
	}

	@Override
	public void tick() {
		yVel += 1;
		if (yVel >= 0) {
			for (int i = 0; i < yVel; i++) {
				super.updateCollisionBoxes();
				if(!super.collides()) {
					parent.setY(parent.getY() + 1);
				}
			}
		} else {
			for (int i = 0; i < -yVel; i++) {
				super.updateCollisionBoxes();
				if(!super.collides()) {
					parent.setY(parent.getY() - 1);
				}
			}
		}
		super.updateCollisionBoxes();
		if(super.collides()) {
			yVel = 0;
			parent.setY(parent.getY() - 1);
		}
		collisionBoxes = super.collisionBoxes;
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}
}
