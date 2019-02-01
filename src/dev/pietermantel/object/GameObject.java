package dev.pietermantel.object;

import java.awt.Graphics;

import dev.pietermantel.object.component.ComponentManager;

public abstract class GameObject {
	protected int x, y, id;
	protected Type type;
	// Dit zijn de states waarbij dit object zichtbaar moet zijn.
	// Het is een array omdat je misschien meerdere states wil hebben waarbij het zichtbaar is.
	protected GameState[] states;
	// Dit regelt de components, zeg maar eigenschappen, van het object: dingen als textures, zwaartekracht, knopinteractie etc.
	protected ComponentManager componentManager;
	
	public GameObject(int x, int y, int id, Type type, GameState[] states) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type = type;
		this.states = states;
		
		componentManager = new ComponentManager();
	}
	
	// Een andere constructor met een enkele state, voor als er maar een state is. Dan is dit makkelijker.
	// Je kan meerdere constructors hebben met verschillende arguments.
	public GameObject(int x, int y, int id, Type type, GameState state) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type = type;
		
		states = new GameState[1];
		states[0] = state;
		componentManager = new ComponentManager();
	}
	
	public void manageTick() {
		componentManager.tick();
		tick();
	}
	
	public void manageRender(Graphics g) {
		componentManager.render(g);
		render(g);
	}
	
	// Abstracte methods zijn methods die een object dat dit object "extend" moet hebben.
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public GameState[] getStates() {
		return states;
	}

	public void setStates(GameState[] states) {
		this.states = states;
	}
	
	
}
