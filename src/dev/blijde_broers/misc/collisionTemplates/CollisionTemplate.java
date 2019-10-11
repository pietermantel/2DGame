package dev.blijde_broers.misc.collisionTemplates;

import dev.blijde_broers.misc.math.*;
import dev.blijde_broers.object.components.instances.CollisionComponent;

public abstract class CollisionTemplate {

	protected CollisionComponent parent;
	protected Vector2[] corners;
	private Line[] ribs;
	private double radius;
	private boolean finishedInit = false;
	public boolean checking = false;

	public CollisionTemplate(Vector2[] corners) {
		this.corners = corners;
		ribs = new Line[corners.length];
		// updateRibs();
		calcRadius();
	}
	
	public CollisionTemplate() {
		
	}

	public boolean intersects(CollisionTemplate collisionTemplate) {
		if (finishedInit) {
			if (Math2D.dist(getTransform().mid.asPoint(),
					collisionTemplate.getTransform().mid.asPoint()) < (radius + collisionTemplate.radius)) {
				checking = true;
				for (Line thisL : ribs) {
					for (Line otherL : collisionTemplate.ribs) {
						if (otherL != null && thisL != null) {
							if (thisL.intersects(otherL)) {
								return true;
							} 
						}
					}
				}
				return false;
			}
		}
		finishedInit = true;
		return false;
	}

	// Updates all lines to their absolute coordinates. This is done by adding the
	// Transform absolute coordinates to all corners.
	public void updateRibs() {
		if (corners.length > 0) {
			for (int i = 0; i < corners.length - 1; i++) {
				ribs[i] = new Line(
						corners[i].add(getTransform().mid).rotate(getTransform().getRotation(),
								getTransform().mid.asPoint()),
						corners[i + 1].add(getTransform().mid).rotate(getTransform().getRotation(),
								getTransform().mid.asPoint()));
			}
			ribs[corners.length - 1] = new Line(
					corners[corners.length - 1].add(getTransform().mid).rotate(getTransform().getRotation(),
							getTransform().mid.asPoint()),
					corners[0].add(getTransform().mid).rotate(getTransform().getRotation(),
							getTransform().mid.asPoint()));
		}
	}
	
	public void calcRadius() {
		radius = 0;
		for (Vector2 c : corners) {
			double tempRadius = Math2D.dist(new Point(), c.asPoint());
			if (tempRadius > radius) {
				radius = tempRadius;
			}
		}
	}

	// Updates all points to their relative position originating from the Transform
	// position.
	public void setRotation(double rotation) {
		getTransform().setRotation(rotation);
		for (Vector2 c : corners) {
			c.setDirection(rotation);
		}
	}

	public void rotate(double theta) {
		getTransform().rotate(theta);
		for (Vector2 c : corners) {
			c.rotate(theta);
		}
	}

	public Transform getTransform() {
		return parent.getParent().getTransform();
	}

	public CollisionComponent getParent() {
		return parent;
	}

	public void setParent(CollisionComponent parent) {
		this.parent = parent;
	}

	public Vector2[] getCorners() {
		return corners;
	}

	public void setCorners(Vector2[] corners) {
		this.corners = corners;
		calcRadius();
	}

	public Line[] getRibs() {
		return ribs;
	}

	public void setRibs(Line[] ribs) {
		this.ribs = ribs;
	}
	
	public double getRadius() {
		return radius;
	}

}
