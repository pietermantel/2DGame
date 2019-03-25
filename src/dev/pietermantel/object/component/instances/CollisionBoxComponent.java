package dev.pietermantel.object.component.instances;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class CollisionBoxComponent extends Component {
	protected Rectangle[] collisionBoxes;
	protected boolean collisionMovement;
	protected int startPersonalCBs;
	protected int oldX, oldY;
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
		updateCollisionBoxes();
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
		Rectangle[] cb = getCollisionBoxes();
		g.setColor(Color.red);
		g.drawRect(cb[0].x, cb[0].y, cb[0].width, cb[0].height);
	}

	public Rectangle[] getCollisionBoxes() {
		return collisionBoxes;
	}

	public void setCollisionBoxes(Rectangle[] collisionBoxes) {
		this.collisionBoxes = collisionBoxes;
	}

	protected boolean collides() {
		LinkedList<Rectangle> otherCBs = new LinkedList<Rectangle>(otherCBs());
		for (Rectangle r1 : otherCBs) {
			for (Rectangle r2 : collisionBoxes) {
				if (r1.intersects(r2))
					return true;
			}
		}
		return false;
	}

	protected void updateCollisionBoxes() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			Rectangle temp1 = collisionBoxes[i];
			collisionBoxes[i] = new Rectangle(parent.getX(), parent.getY(), temp1.width, temp1.height);
		}
		for (int i = 0; i < collisionBoxes.length; i++) {
			ALL_COLLISION_BOXES.set(i + startPersonalCBs, collisionBoxes[i]);
		}
	}

	protected LinkedList<Rectangle> otherCBs() {
		LinkedList<Rectangle> out = new LinkedList<Rectangle>();
		for (int i = 0; i < ALL_COLLISION_BOXES.size(); i++) {
			if (!(i >= startPersonalCBs && i < startPersonalCBs + collisionBoxes.length)) {
				out.add(ALL_COLLISION_BOXES.get(i));
			}
		}
		return out;
	}
}
