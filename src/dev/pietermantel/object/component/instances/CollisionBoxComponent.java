package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class CollisionBoxComponent extends Component{
	private Rectangle[] collisionBoxes;
	public static LinkedList<Rectangle> ALL_COLLISION_BOXES = new LinkedList<Rectangle>();

	public CollisionBoxComponent(GameObject parent, Rectangle[] collisionBoxes) {
		super(parent);
		this.collisionBoxes = collisionBoxes;
		for(Rectangle rectangle : collisionBoxes) {
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
}
