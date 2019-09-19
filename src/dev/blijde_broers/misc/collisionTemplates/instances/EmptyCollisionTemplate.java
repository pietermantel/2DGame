package dev.blijde_broers.misc.collisionTemplates.instances;


import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.misc.math.Transform;

public class EmptyCollisionTemplate extends CollisionTemplate {

	public EmptyCollisionTemplate() {
		super(new Transform(), new Point[0]);
	}
	
	

}
