package dev.blijde_broers.misc.collisionComponentParts;

import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.object.components.instances.CollisionComponent;

public class IntersectionReturn {
	
	public Point[] points;
	public CollisionComponent cc;
	

	public IntersectionReturn(Point[] points, CollisionComponent cc) {
		this.points = points;
		this.cc = cc;
	}

}
