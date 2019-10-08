package dev.blijde_broers.misc.collisionTemplates.instances;

import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;

public class Rectangle extends CollisionTemplate {

	public Rectangle(Transform transform) {
		super(new Vector2[] { transform.getLRCorner(), transform.getLLCorner(), transform.getULCorner(),
				transform.getURCorner() });
	}

}
