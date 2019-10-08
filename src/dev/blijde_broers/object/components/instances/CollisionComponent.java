package dev.blijde_broers.object.components.instances;

import java.awt.Color;
import java.awt.Graphics;

import dev.blijde_broers.misc.collisionTemplates.CollisionTemplate;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.Handler;
import dev.blijde_broers.object.components.ObjectComponent;

public class CollisionComponent extends ObjectComponent {

	private CollisionTemplate collisionTemplate;
	
	private Color currentColor = Color.red;

	public CollisionComponent(GameObject parent, CollisionTemplate collisionTemplate) {
		super(parent);
		collisionTemplate.setParent(this);
		this.collisionTemplate = collisionTemplate;
	}

	@Override
	public void tick() {
		CollisionTemplate intersection = null;
		collisionTemplate.checking = false;
		for (GameObject o : Handler.objects) {
			CollisionTemplate temp = null;
			if (!o.equals(parent)) {
				temp = collisionTemplate.intersects(o.getComponentManager().getCollisionComponent().collisionTemplate);
			}
			if (temp != null) {
				intersection = temp;
				break;
			}
		}
		if(intersection != null) {
			currentColor = Color.red;
		} else {
			if(collisionTemplate.checking) {
				currentColor = Color.green;
			} else {
				currentColor = Color.blue;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		TextureComponent.renderCollisionTemplate(g, this, currentColor);
	}

	public CollisionTemplate getCollisionTemplate() {
		return collisionTemplate;
	}

	public void setCollisionTemplate(CollisionTemplate collisionTemplate) {
		this.collisionTemplate = collisionTemplate;
	}
	
	

}
