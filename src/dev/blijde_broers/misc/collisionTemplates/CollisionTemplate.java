package dev.blijde_broers.misc.collisionTemplates;


import dev.blijde_broers.misc.math.Line;
import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.misc.math.Transform;

public abstract class CollisionTemplate {

	protected Transform transform;
	protected Point[] corners;
	private Line[] ribs;

	public CollisionTemplate(Transform transform, Point[] corners) {
		this.transform = transform;
		ribs = new Line[corners.length];
		for(int i = 0; i < corners.length - 1; i++) {
			ribs[i] = new Line(corners[i], corners[i + 1]);
		}
		ribs[corners.length - 1] = new Line(corners[corners.length - 1], corners[0]);
	}

	public CollisionTemplate intersects(CollisionTemplate collisionTemplate) {
		for (Line thisL : ribs) {
			for (Line otherL : collisionTemplate.ribs) {
				if(thisL.add(transform).intersects(otherL.add(collisionTemplate.transform)) != null) {
					return collisionTemplate;
				}
			}
		}
		return null;
	}
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Point[] getCorners() {
		return corners;
	}

	public void setCorners(Point[] corners) {
		this.corners = corners;
	}

	public Line[] getRibs() {
		return ribs;
	}

	public void setRibs(Line[] ribs) {
		this.ribs = ribs;
	}

	

}
