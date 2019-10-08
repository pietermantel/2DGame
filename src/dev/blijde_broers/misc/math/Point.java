package dev.blijde_broers.misc.math;

public class Point {
	
	public float x, y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2 asVector2() {
		return new Vector2(x, y);
	}

}
