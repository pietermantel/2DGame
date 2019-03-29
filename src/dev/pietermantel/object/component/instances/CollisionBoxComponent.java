package dev.pietermantel.object.component.instances;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.component.Component;

public class CollisionBoxComponent extends Component {
	protected CollisionBox[] collisionBoxes;
	protected boolean collisionMovement;
	protected int startPersonalCBs;
	protected int oldX, oldY;
	public static LinkedList<CollisionBox> ALL_COLLISION_BOXES = new LinkedList<CollisionBox>();

	public CollisionBoxComponent(GameObject parent, CollisionBox[] collisionBoxes, boolean collisionMovement) {
		super(parent);
		this.collisionBoxes = collisionBoxes;
		startPersonalCBs = ALL_COLLISION_BOXES.size();
		for (CollisionBox collisionBox : collisionBoxes) {
			ALL_COLLISION_BOXES.add(collisionBox);
		}
		this.collisionMovement = collisionMovement;
	}

	public CollisionBoxComponent(GameObject parent, CollisionBox collisionBox, boolean collisionMovement) {
		super(parent);
		collisionBoxes = new CollisionBox[1];
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
		g.setColor(Color.red);
		for(CollisionBox c : collisionBoxes) {
			g.drawRect((int) c.getX(), (int) c.getY(), c.width, c.height);
		}
	}

	public CollisionBox[] getCollisionBoxes() {
		return collisionBoxes;
	}

	public void setCollisionBoxes(CollisionBox[] collisionBoxes) {
		this.collisionBoxes = collisionBoxes;
	}

	protected boolean collides() {
		LinkedList<CollisionBox> otherCBs = new LinkedList<CollisionBox>(otherCBs());
		for (CollisionBox c1 : otherCBs) {
			for (CollisionBox c2 : collisionBoxes) {
				if (c1.intersects(c2))
					return true;
			}
		}
		return false;
	}

	protected void updateCollisionBoxes() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			collisionBoxes[i].x = parent.getX();
			collisionBoxes[i].y = parent.getY();
		}
		for (int i = 0; i < collisionBoxes.length; i++) {
			ALL_COLLISION_BOXES.set(i + startPersonalCBs, collisionBoxes[i]);
		}
	}

	protected LinkedList<CollisionBox> otherCBs() {
		LinkedList<CollisionBox> out = new LinkedList<CollisionBox>();
		for (int i = 0; i < ALL_COLLISION_BOXES.size(); i++) {
			if (!(i >= startPersonalCBs && i < startPersonalCBs + collisionBoxes.length)) {
				out.add(ALL_COLLISION_BOXES.get(i));
			}
		}
		return out;
	}
}
