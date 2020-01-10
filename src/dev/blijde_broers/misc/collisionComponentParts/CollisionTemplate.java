package dev.blijde_broers.misc.collisionComponentParts;

import java.util.ArrayList;

import dev.blijde_broers.misc.math.Line;
import dev.blijde_broers.misc.math.Math2D;
import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.components.instances.CollisionComponent;

public abstract class CollisionTemplate {

	protected CollisionComponent parent;
	protected Vector2[] corners;
	private Line[] ribs;
	private double radiusSquared, radius;
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

	public boolean intersects(CollisionTemplate cT) {
		if (finishedInit) {
			if (Math2D.dist(getTransform().mid.asPoint(),
					cT.getTransform().mid.asPoint()) < (radiusSquared + cT.radiusSquared)) {
				checking = true;
				for (Line thisL : ribs) {
					for (Line otherL : cT.ribs) {
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

//		if (finishedInit) {
//			if (Math2D.dist(getTransform().mid.asPoint(),
//					cT.getTransform().mid.asPoint()) < (radius + cT.radius)) {
//				checking = true;
//				Line temp = new Line(getTransform().mid.asPoint(), cT.getTransform().mid.asPoint());
//				double thisDist = 0;
//				for (Line thisL : ribs) {
//					Point intersection = temp.intersection(thisL);
//					double tempDist;
//					if(intersection != null)
//						if ((tempDist = Math2D.dist(getTransform().mid.asPoint(), intersection)) > thisDist)
//							thisDist = tempDist;
//				}
//				double otherDist = 0;
//				for (Line otherL : cT.ribs) {
//					Point intersection = null;
//					if(otherL != null)
//						intersection = temp.intersection(otherL);
//					double tempDist;
//					if(intersection != null)
//						if ((tempDist = Math2D.dist(cT.getTransform().mid.asPoint(), intersection)) > otherDist)
//							otherDist = tempDist;
//				}
//				if (thisDist + otherDist >= Math2D.dist(getTransform().mid.asPoint(),
//						cT.getTransform().mid.asPoint()))
//					return true;
//				else return false;
//			}
//		}
//		finishedInit = true;
//		return false;
	}

	public Point[] intersection(CollisionTemplate collisionTemplate) {
		if (finishedInit) {
			if (Math2D.dist(getTransform().mid.asPoint(),
					collisionTemplate.getTransform().mid.asPoint()) < (radiusSquared + collisionTemplate.radiusSquared)) {
				checking = true;
				ArrayList<Point> points = new ArrayList<Point>();
				for (Line thisL : ribs) {
					for (Line otherL : collisionTemplate.ribs) {
						if (otherL != null && thisL != null) {
							Point intersection;
							if ((intersection = thisL.intersection(otherL)) != null) {
								points.add(intersection);
							}
						}
					}
				}
				if (points.size() > 0) {
					Point[] returns = new Point[points.size()];
					for (int i = 0; i < points.size(); i++) {
						returns[i] = points.get(i);
					}
					return returns;
				} else
					return null;
			}
		}
		finishedInit = true;
		return null;
	}

	// Updates all lines to their absolute coordinates. This is done by adding the
	// Transform absolute coordinates to all corners.
	public void updateRibs() {
		if (corners.length > 0) {
			for (int i = 0; i < corners.length - 1; i++) {
				ribs[i] = new Line(
						corners[i].plus(getTransform().mid).rotate(getTransform().getRotation(),
								getTransform().mid.asPoint()),
						corners[i + 1].plus(getTransform().mid).rotate(getTransform().getRotation(),
								getTransform().mid.asPoint()));
			}
			ribs[corners.length - 1] = new Line(
					corners[corners.length - 1].plus(getTransform().mid).rotate(getTransform().getRotation(),
							getTransform().mid.asPoint()),
					corners[0].plus(getTransform().mid).rotate(getTransform().getRotation(),
							getTransform().mid.asPoint()));
		}
	}

	public void calcRadius() {
		radiusSquared = 0;
		for (Vector2 c : corners) {
			double tempRadius = Math2D.distSqr(new Point(), c.asPoint());
			if (tempRadius > radiusSquared) {
				radiusSquared = tempRadius;
			}
		}
		radius = Math.sqrt(radiusSquared);
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
		if (parent.getParent() != null)
			return parent.getParent().getTransform();
		return new Transform();
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

	public double getRadiusSquared() {
		return radiusSquared;
	}
	
	public double getRadius() {
		return radius;
	}

}
