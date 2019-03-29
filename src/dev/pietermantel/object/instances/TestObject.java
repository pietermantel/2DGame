package dev.pietermantel.object.instances;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.pietermantel.input.KeyManager;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.component.instances.CollisionBox;
import dev.pietermantel.object.component.instances.GravityComponent;
import dev.pietermantel.object.component.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(int x, int y, int id, GameState state, boolean collisionMovement) {
		super(x, y, id, GameObjectType.TestObject, state);
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/TestObject.jpg", 0, 0));
		componentManager.getComponents().add(new GravityComponent(this, new CollisionBox[] {new CollisionBox(x, y, 100, 100, 0, 0), new CollisionBox(x, y, 100, 100, 100, 100)}, collisionMovement));
	}

	@Override
	public void tick() {
		if (KeyManager.pressed[KeyEvent.VK_DOWN])
			y += 5;
		if (KeyManager.pressed[KeyEvent.VK_UP])
			y -= 5;
		if (KeyManager.pressed[KeyEvent.VK_RIGHT])
			x += 5;
		if (KeyManager.pressed[KeyEvent.VK_LEFT])
			x -= 5;
	}

	@Override
	public void render(Graphics g) {

	}

}
