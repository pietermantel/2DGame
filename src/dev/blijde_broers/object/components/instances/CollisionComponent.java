package dev.blijde_broers.object.components.instances;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.blijde_broers.main.Game;
import dev.blijde_broers.misc.collisionComponentParts.CollisionTemplate;
import dev.blijde_broers.misc.collisionComponentParts.IntersectionReturn;
import dev.blijde_broers.misc.math.Math2D;
import dev.blijde_broers.misc.math.Point;
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
		boolean intersects = false;
		collisionTemplate.checking = false;
		collisionTemplate.updateRibs();
		for (GameObject o : Handler.objects) {
			if (!o.equals(parent)) {
				if (Math2D.dist(collisionTemplate.getTransform().mid.asPoint(),
						o.getComponentManager().getCollisionComponent().collisionTemplate.getTransform().mid
								.asPoint()) < (collisionTemplate.getRadius()
										+ o.getComponentManager().getCollisionComponent().collisionTemplate
												.getRadius())) {
					collisionTemplate.checking = true;
					intersects = collisionTemplate.intersects(o.getComponentManager().getCollisionComponent().collisionTemplate);
				}
			}
		}
		if (intersects) {
			currentColor = Color.red;
		} else {
			if (collisionTemplate.checking) {
				currentColor = Color.green;
			} else {
				currentColor = Color.blue;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		if(Game.debug) {
			TextureComponent.renderCollisionTemplate(g, this, currentColor);
		}
	}
	
	public boolean intersects() {
		collisionTemplate.updateRibs();
		for (GameObject o : Handler.objects) {
			if (!o.equals(parent)) {
				if (Math2D.dist(collisionTemplate.getTransform().mid.asPoint(),
						o.getComponentManager().getCollisionComponent().collisionTemplate.getTransform().mid
								.asPoint()) < (collisionTemplate.getRadius()
										+ o.getComponentManager().getCollisionComponent().collisionTemplate
												.getRadius())) {
					collisionTemplate.checking = true;
					if(o.getComponentManager().getCollisionComponent().collisionTemplate
							.intersects(collisionTemplate)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean intersects(CollisionComponent cc) {
		collisionTemplate.updateRibs();
		return collisionTemplate.intersects(cc.getCollisionTemplate());
	}
	
	public IntersectionReturn[] intersection() {
		collisionTemplate.checking = false;
		collisionTemplate.updateRibs();
		ArrayList<IntersectionReturn> intersections = new ArrayList<IntersectionReturn> ();
		for (GameObject o : Handler.objects) {
			Point[] temp = null;
			if (!o.equals(parent)) {
				if (Math2D.dist(collisionTemplate.getTransform().mid.asPoint(),
						o.getComponentManager().getCollisionComponent().collisionTemplate.getTransform().mid
								.asPoint()) < (collisionTemplate.getRadiusSquared()
										+ o.getComponentManager().getCollisionComponent().collisionTemplate
												.getRadiusSquared())) {
					collisionTemplate.checking = true;
					temp = o.getComponentManager().getCollisionComponent().collisionTemplate
							.intersection(collisionTemplate);
				}
			}
			if (temp != null) {
				intersections.add(new IntersectionReturn(temp, o.getComponentManager().getCollisionComponent()));
			}
		}
		if(intersections.size() > 0) {
			IntersectionReturn[] returns = new IntersectionReturn[intersections.size()];
			for(int i = 0; i < intersections.size(); i++) {
				returns[i] = intersections.get(i);
			}
			return returns;
		}
		return null;
	}

	public CollisionTemplate getCollisionTemplate() {
		return collisionTemplate;
	}

	public void setCollisionTemplate(CollisionTemplate collisionTemplate) {
		this.collisionTemplate = collisionTemplate;
	}

}
