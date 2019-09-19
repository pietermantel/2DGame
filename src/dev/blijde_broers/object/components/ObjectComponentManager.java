package dev.blijde_broers.object.components;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.blijde_broers.misc.collisionTemplates.instances.EmptyCollisionTemplate;
import dev.blijde_broers.object.components.instances.CollisionComponent;

public class ObjectComponentManager {
	// Deze class zorgt ervoor dat de components - de eigenschappen van een object -
	// allemaal geupdate worden.
	
	private int collisionComponentIndex;
	
	private ArrayList<ObjectComponent> objectComponents = new ArrayList<ObjectComponent>();

	public void tick() {
		for (ObjectComponent objectComponent : objectComponents) {
			objectComponent.tick();
		}
	}

	public void render(Graphics g) {
		for (ObjectComponent objectComponent : objectComponents) {
			objectComponent.render(g);
		}
	}

	public ArrayList<ObjectComponent> getObjectComponents() {
		return objectComponents;
	}

	public void setObjectComponents(ArrayList<ObjectComponent> objectComponents) {
		this.objectComponents = objectComponents;
	}
	
	public void addCollisionComponent(CollisionComponent cc) {
		collisionComponentIndex = objectComponents.size();
		objectComponents.add(cc);
	}
	
	public CollisionComponent getCollisionComponent() {
		if(objectComponents.get(collisionComponentIndex) != null) {
			return (CollisionComponent) objectComponents.get(collisionComponentIndex);
		} else {
			return new CollisionComponent(null, new EmptyCollisionTemplate());
		}
		
	}
	
	
}
