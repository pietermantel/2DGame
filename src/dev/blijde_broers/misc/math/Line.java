package dev.blijde_broers.misc.math;

import java.awt.geom.Line2D;

public class Line {

	public float x1, y1, x2, y2;

	public Line(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Line(Point start, Point end) {
		x1 = start.x;
		y1 = start.y;
		x2 = start.x;
		y2 = start.y;
	}

	public Point intersects(Line l) {
		double d = (x1 - x2) * (l.y1 - l.y2) - (y1 - y2) * (l.x1 - l.x2);
		if (d == 0 || !Line2D.linesIntersect(x1, y1, x2, y2, l.x1, l.y1, l.x2, l.y2))
			return null;
		double xi = ((l.x1 - l.x2) * (x1 * y2 - y1 * x2) - (x1 - x2) * (l.x1 * l.y2 - l.y1 * l.x2)) / d;
		double yi = ((l.y1 - l.y2) * (x1 * y2 - y1 * x2) - (y1 - y2) * (l.x1 * l.y2 - l.y1 * l.x2)) / d;
		return new Point((int) xi, (int) yi);
	}
	
	public Line add(Transform transform) {
		return new Line(x1 + transform.x, y1 + transform.y, x1 + transform.x, y1 + transform.y);
	}

}
