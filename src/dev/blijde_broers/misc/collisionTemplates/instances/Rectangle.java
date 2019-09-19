package dev.blijde_broers.misc.collisionTemplates.instances;

import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.misc.math.Transform;

public class Rectangle extends CollisionTemplate {

	public Rectangle(Transform parent, Transform transform) {
		super(parent,
				new Point[] { new Point(-(transform.width / 2), -(transform.height / 2)),
						new Point((transform.width / 2), -(transform.height / 2)),
						new Point((transform.width / 2), (transform.height / 2)),
						new Point(-(transform.width / 2), (transform.height / 2)) });
	}

}
