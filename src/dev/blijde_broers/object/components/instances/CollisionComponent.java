package dev.blijde_broers.object.components.instances;

import java.awt.Graphics;

import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.Handler;
import dev.blijde_broers.object.components.ObjectComponent;

public class CollisionComponent extends ObjectComponent {

	private CollisionTemplate collisionTemplate;

	public CollisionComponent(GameObject parent, CollisionTemplate collisionTemplate) {
		super(parent);
		this.collisionTemplate = collisionTemplate;
	}

	@Override
	public void tick() {
		collisionTemplate.setTransform(parent.getTransform());
		for (GameObject o : Handler.objects) {
			CollisionTemplate temp = o.getComponentManager().getCollisionComponent().collisionTemplate
					.intersects(collisionTemplate);
			if (temp != null) {
				System.out.println("intersect");
			}
		}

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

}
