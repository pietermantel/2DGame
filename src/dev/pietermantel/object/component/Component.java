package dev.pietermantel.object.component;

import java.awt.Graphics;

import dev.pietermantel.object.GameObject;

public abstract class Component {
	// Dit is het object waar dit component bijhoort
	protected GameObject parent;
	
	public Component(GameObject parent) {
		this.parent = parent;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}
}
