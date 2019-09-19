package dev.blijde_broers.misc.math;

public class Transform {
	
	public float x, y, width, height;
	
	public double rotation;

	public Transform(float x, float y, float width, float height, double rotation) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
	}
	
	public Transform(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rotation = 0;
	}
	
	public Transform(float x, float y) {
		this.x = x;
		this.y = y;
		width = 0;
		height = 0;
		rotation = 0;
	}
	
	public Transform() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		rotation = 0;
	}
	
	public Transform add(Transform arg) {
		return new Transform(x + arg.x, y + arg.y);
	}
	
	public void increment(Transform arg) {
		x += arg.x;
		y += arg.y;
		rotation += arg.rotation;
	}
	
	public void moveOnRelativeXAxis(float distance) {
		x += Math.cos(rotation) * distance;
		y += Math.sin(rotation) * distance;
	}
	
	public void moveOnRelativeYAxis(float distance) {
		x += Math.cos(rotation + 0.5 * Math.PI) * distance;
		y += Math.sin(rotation + 0.5 * Math.PI) * distance;
	}

}
