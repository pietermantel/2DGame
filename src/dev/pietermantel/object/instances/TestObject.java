package dev.pietermantel.object.instances;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.component.instances.CollisionBoxComponent;
import dev.pietermantel.object.component.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(int x, int y, int id, GameState state) {
		super(x, y, id, GameObjectType.TestObject, state);
		CollisionBoxComponent cbc;
		Rectangle cb;
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/TestObject.jpg", 0, 0));
		componentManager.getComponents().add(cbc = new CollisionBoxComponent(this, cb = new Rectangle(x, y, 100, 100)));
	}
		
	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}
	
}
