package dev.pietermantel.object.component.instances;

import java.awt.Rectangle;

public class CollisionBox extends Rectangle {
	private int offsetX, offsetY;
	private static final long serialVersionUID = 1L;

	public CollisionBox(int x, int y, int width, int height, int offsetX, int offsetY) {
		super(x, y, width, height);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public CollisionBox(int x, int y, int width, int height) {
		super(x, y, width, height);
		offsetX = 0;
		offsetY = 0;
	}
	
	@Override
	public double getX() {
		return super.getX() + offsetX;
	}
	
	@Override
	public double getY() {
		return super.getY() + offsetY;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x + offsetX, y + offsetY, width, height);
	}
	
	public boolean intersects(CollisionBox c){
		Rectangle r = c.getRectangle();
		int x = this.x + offsetX;
		int y = this.y + offsetY;
		return r.width > 0 && r.height > 0 && width > 0 && height > 0
				&& r.x < x + width && r.x + r.width > x
				&& r.y < y + height && r.y + r.height > y;
	}

}
