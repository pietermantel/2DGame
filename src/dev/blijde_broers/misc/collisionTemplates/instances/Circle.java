package dev.blijde_broers.misc.collisionTemplates.instances;

import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.misc.math.Transform;

public class Circle extends CollisionTemplate {
	
	@SuppressWarnings("unused")
	private Transform transform;

	public Circle(Transform transform) {
		super();
	}
	
	@Override
	public boolean intersects(CollisionTemplate collisionTemplate) {
		if(collisionTemplate instanceof Circle) {
			return true;
		}
		return false;
	}

}
