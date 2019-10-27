package dev.blijde_broers.misc.math;

public class Vector2 {
	
	public float x = 0, y = 0;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2(Point p1, Point p2) {
		x = p2.x - p1.x;
		y = p2.y - p1.y;
	}
	
	public Vector2() {
		x = 0;
		y = 0;
	}
	
	public Point asPoint() {
		return new Point(x, y);
	}
	
	public Vector2 add(Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}
	
	public String toString() {
		return "Vector2: x=" + x + " y=" + y;
	}
	
	public Vector2 rotate(double theta) {
		double r = Math2D.dist(new Point(), this.asPoint());
		double a = Math.atan2(y, x);
		double a1 = a + theta;
		x = (float) (r * Math.cos(a1));
		y = (float) (r * Math.sin(a1));
		return new Vector2(x, y);
	}
	
	public Vector2 rotate(double theta, Point p) {
		double r = Math2D.dist(p, this.asPoint());
		double a = Math.atan2((y - p.y), (x - p.x));
		double b = a + theta;
		x = (float) (r * Math.cos(b) + p.x);
		y = (float) (r * Math.sin(b) + p.y);
		return new Vector2(x, y);
	}
	
	public Vector2 setDirection(double direction) {
		double r = Math2D.dist(new Point(), this.asPoint());
		x = (float) (r * Math.cos(direction));
		y = (float) (r * Math.sin(direction));
		return new Vector2(x, y);
	}
	
	public Vector2 setDirection(double direction, Point p) {
		double r = Math2D.dist(p, this.asPoint());
		x = (float) (r * Math.cos(direction) + p.x);
		y = (float) (r * Math.sin(direction) + p.y);
		return new Vector2(x, y);
	}
	
	public double getDirection() {
		return Math.atan2(y, x);
	}
	
	public void increment(Vector2 v) {
		x += v.x;
		y += v.y;
	}
	
	public void increment(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public Vector2 min() {
		return new Vector2(-x, -y);
	}
	
	public void multiplyThis(float a) {
		x *= a;
		y *= a;
	}
	
	public Vector2 multiply(float a) {
		return new Vector2(x * a, y * a);
	}
	
	public void divideThis(float a) {
		x /= a;
		y /= a;
	}

	public Vector2 divide(float a) {
		return new Vector2(x /= a, y /= a);
	}

}
