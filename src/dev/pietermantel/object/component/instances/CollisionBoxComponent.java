package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class CollisionBoxComponent extends Component {
	private Rectangle[] collisionBoxes;
	public static LinkedList<Rectangle> ALL_COLLISION_BOXES = new LinkedList<Rectangle>();

	public CollisionBoxComponent(GameObject parent, Rectangle[] collisionBoxes) {
		super(parent);
		this.collisionBoxes = collisionBoxes;
		for (Rectangle rectangle : collisionBoxes) {
			ALL_COLLISION_BOXES.add(rectangle);
		}
	}

	public CollisionBoxComponent(GameObject parent, Rectangle collisionBox) {
		super(parent);
		collisionBoxes = new Rectangle[1];
		collisionBoxes[0] = collisionBox;
		ALL_COLLISION_BOXES.add(collisionBox);
	}

	@Override
	public void tick() {
		int[] distances = new int[4];
		if (collides(0, 0)) {
			int temp = 0;
			while (collides(temp, 0)) {
				temp += 1;
			}
			distances[0] = temp;
			temp = 0;
			while (collides(-temp, 0)) {
				temp += 1;
			}
			distances[1] = temp;
			temp = 0;
			while (collides(0, temp)) {
				temp += 1;
			}
			distances[2] = temp;
			temp = 0;
			while (collides(0, -temp)) {
				temp += 1;
			}
			distances[3] = temp;
		}
		int temp = 0;
		int lowest = Integer.MAX_VALUE;
		for(int i = 0; i < 4; i++) {
			if(distances[i] < lowest) {
				temp = i;
				lowest = distances[i];
			}
		}
		if(temp == 0) parent.setX(parent.getX() + distances[0]);
		if(temp == 1) parent.setX(parent.getX() - distances[1]);
		if(temp == 2) parent.setY(parent.getY() + distances[2]);
		if(temp == 3) parent.setY(parent.getY() - distances[3]);
	}

	@Override
	public void render(Graphics g) {

	}

	public Rectangle[] getCollisionBoxes() {
		return collisionBoxes;
	}

	public void setCollisionBoxes(Rectangle[] collisionBoxes) {
		this.collisionBoxes = collisionBoxes;
	}

	public boolean collides(int plusX, int plusY) {
		for (Rectangle r1 : CollisionBoxComponent.ALL_COLLISION_BOXES) {
			for (Rectangle r2 : collisionBoxes) {
				if (r1.intersects(r2.getX() + plusX, r2.getY() + plusY, r2.getWidth(), r2.getHeight()))
					return true;
			}
		}
		return false;
	}
}
