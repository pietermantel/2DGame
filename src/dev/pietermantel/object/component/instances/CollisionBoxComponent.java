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
			int rot = 0;
			int distance = 0;
			while(collides((int) Math.sin((Math.asin(1) / 90) * rot) * distance, (int) Math.cos((Math.acos(0) / 90) * rot) * distance)) {
				distance++;
				rot = 0;
				for(int i = 0; i < 360; i++) {
					rot++;
					if(!collides((int) Math.sin((Math.asin(1) / 90) * rot) * distance, (int) Math.cos((Math.acos(0) / 90) * rot) * distance))
						break;
				}
			}
			parent.setX(parent.getX() + (int) Math.sin((Math.asin(1) / 90) * rot) * distance);
			parent.setY(parent.getY() + (int) Math.cos((Math.acos(0) / 90) * rot) * distance);
//			int[] distances = new int[4];
//			if (collides(0, 0)) {
//				distances[0] = 0;
//				while (collides(distances[0], 0)) {
//					distances[0] += 1;
//				}
//				distances[1] = 0;
//				while (collides(-distances[1], 0)) {
//					distances[1] += 1;
//				}
//				distances[2] = 0;
//				while (collides(0, distances[2])) {
//					distances[2] += 1;
//				}
//				distances[3] = 0;
//				while (collides(0, -distances[3])) {
//					distances[3] += 1;
//				}
//			}
//			int temp = 0;
//			int lowest = Integer.MAX_VALUE;
//			for (int i = 0; i < 4; i++) {
//				if (distances[i] < lowest) {
//					temp = i;
//					lowest = distances[i];
//				}
//			}
////			if(distances[1] > 0) {
////				System.out.println(distances[0] + " " + distances[1] + " " + distances[2] + " " + distances[3]);
////				System.out.println(temp);
////			}
//			if (temp == 0)
//				parent.setX(parent.getX() + distances[0]);
//			if (temp == 1)
//				parent.setX(parent.getX() - distances[1]);
//			if (temp == 2)
//				parent.setY(parent.getY() + distances[2]);
//			if (temp == 3)
//				parent.setY(parent.getY() - distances[3]);
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

	public boolean collides(int plusX, int plusY) {
		LinkedList<Rectangle> otherCBs = new LinkedList<Rectangle>(otherCBs());
		for (Rectangle r1 : otherCBs) {
			for (Rectangle r2 : collisionBoxes) {
				if (r1.intersects(new Rectangle(r2.x + plusX, r2.y + plusY, r2.width, r2.height)))
					return true;
			}
		}
		return false;
	}
	
	public void updateCollisionBoxes() {
		for(int i = 0; i < collisionBoxes.length; i++) {
			Rectangle temp1 = collisionBoxes[i];
			collisionBoxes[i] = new Rectangle(parent.getX(), parent.getY(), temp1.width, temp1.height);
		}
		for(int i = 0; i < collisionBoxes.length; i++) {
			ALL_COLLISION_BOXES.set(i + startPersonalCBs, collisionBoxes[i]);
		}
	}
	
	public LinkedList<Rectangle> otherCBs() {
		LinkedList<Rectangle> out = new LinkedList<Rectangle>();
		for(int i = 0; i < ALL_COLLISION_BOXES.size(); i++) {
			if(!(i >= startPersonalCBs && i < startPersonalCBs + collisionBoxes.length)) {
				out.add(ALL_COLLISION_BOXES.get(i));
			}
		}
		return out;
	}
}
