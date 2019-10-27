package dev.blijde_broers.misc.collisionComponentParts;

import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.object.components.instances.CollisionComponent;

public class IntersectionReturn {
	
	public Point point;
	public CollisionComponent cc;
	

	public IntersectionReturn(Point point, CollisionComponent cc) {
		this.point = point;
		this.cc = cc;
	}

}
