package dev.blijde_broers.object;

import java.awt.Graphics;

import dev.blijde_broers.main.Game;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.object.components.ObjectComponentManager;

public abstract class GameObject {
	
	protected Transform transform;
	protected int id;
	protected GameObjectType type;
	protected GameState[] states;
	protected ObjectComponentManager componentManager = new ObjectComponentManager();

	public GameObject(Transform transform, int id, GameObjectType type, GameState[] states) {
		this.transform = transform;
		this.id = id;
		this.type = type;
		this.states = states;
	}
	
	public GameObject(Transform transform, int id, GameObjectType type, GameState state) {
		this.transform = transform;
		this.id = id;
		this.type = type;
		this.states = new GameState[1];
		this.states[0] = state;
	}
	
	public void manageTick() {
		componentManager.tick();
		tick();
	}
	
	public void manageRender(Graphics g) {
		componentManager.render(g);
		render(g);
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	
	
	public int getActualX() {
		return (int) (((transform.x - Game.GAME.getMainCamera().transform.x) * Game.GAME.getMainCamera().zoom) + Game.GAME.getWindow().getWidth() / 2 - Game.GAME.getMainCamera().zoom / 2);
	}
	
	public int getActualY() {
		return (int) (((transform.y - Game.GAME.getMainCamera().transform.y) * Game.GAME.getMainCamera().zoom) + Game.GAME.getWindow().getHeight() / 2 - Game.GAME.getMainCamera().zoom / 2);
	}
	
	public GameState[] getStates() {
		return states;
	}

	public void setStates(GameState[] states) {
		this.states = states;
	}

	public Transform getTransform() {
		return transform;
	}
	
	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameObjectType getType() {
		return type;
	}

	public void setType(GameObjectType type) {
		this.type = type;
	}

	public ObjectComponentManager getComponentManager() {
		return componentManager;
	}

	public void setComponentManager(ObjectComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	

}
