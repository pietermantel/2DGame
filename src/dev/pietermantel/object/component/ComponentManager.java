package dev.pietermantel.object.component;

import java.awt.Graphics;
import java.util.LinkedList;

public class ComponentManager {
	// Deze class zorgt ervoor dat de components - de eigenschappen van een object -
	// allemaal geupdate worden.
	private LinkedList<Component> components = new LinkedList<Component>();

	public void tick() {
		for (Component component : components) {
			component.tick();
		}
	}

	public void render(Graphics g) {
		for (Component component : components) {
			component.render(g);
		}
	}

	public LinkedList<Component> getComponents() {
		return components;
	}

	public void setComponents(LinkedList<Component> components) {
		this.components = components;
	}
}
