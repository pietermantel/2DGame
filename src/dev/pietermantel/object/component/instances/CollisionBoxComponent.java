package dev.pietermantel.object.component.instances;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class CollisionBoxComponent extends Component {
	private Rectangle[] collisionBoxes;
	private boolean collisionMovement;
	private int startPersonalCBs;
	private int oldX = 0, oldY = 0;
	public static LinkedList<Rectangle> ALL_COLLISION_BOXES = new LinkedList<Rectangle>();

	public CollisionBoxComponent(GameObject parent, Rectangle[] collisionBoxes, boolean collisionMovement) {
		super(parent);
		this.collisionBoxes = collisionBoxes;
		startPersonalCBs = ALL_COLLISION_BOXES.size();
		for (Rectangle rectangle : collisionBoxes) {
			ALL_COLLISION_BOXES.add(rectangle);
		}
		this.collisionMovement = collisionMovement;
	}

	public CollisionBoxComponent(GameObject parent, Rectangle collisionBox, boolean collisionMovement) {
		super(parent);
		collisionBoxes = new Rectangle[1];
		collisionBoxes[0] = collisionBox;
		startPersonalCBs = ALL_COLLISION_BOXES.size();
		ALL_COLLISION_BOXES.add(collisionBox);
		this.collisionMovement = collisionMovement;
	}

	@Override
	public void tick() {
		if (collisionMovement) {
			if (collides()) {
				parent.setX(oldX);
				parent.setY(oldY);
			}
			oldX = parent.getX();
			oldY = parent.getY();
//			if(collides())
//			System.out.println(oldX + " " + oldY);
		}
		updateCollisionBoxes();
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

	public boolean collides() {
		LinkedList<Rectangle> otherCBs = new LinkedList<Rectangle>(otherCBs());
		for (Rectangle r1 : otherCBs) {
			for (Rectangle r2 : collisionBoxes) {
				if (r1.intersects(r2))
					return true;
			}
		}
		return false;
	}

	public void updateCollisionBoxes() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			Rectangle temp1 = collisionBoxes[i];
			collisionBoxes[i] = new Rectangle(parent.getX(), parent.getY(), temp1.width, temp1.height);
		}
		for (int i = 0; i < collisionBoxes.length; i++) {
			ALL_COLLISION_BOXES.set(i + startPersonalCBs, collisionBoxes[i]);
		}
	}

	public LinkedList<Rectangle> otherCBs() {
		LinkedList<Rectangle> out = new LinkedList<Rectangle>();
		for (int i = 0; i < ALL_COLLISION_BOXES.size(); i++) {
			if (!(i >= startPersonalCBs && i < startPersonalCBs + collisionBoxes.length)) {
				out.add(ALL_COLLISION_BOXES.get(i));
			}
		}
		return out;
	}
}
