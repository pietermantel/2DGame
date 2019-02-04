package dev.pietermantel.object.instances;

import java.awt.Graphics;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.Type;
import dev.pietermantel.object.component.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(int x, int y, int id, GameState state) {
		super(x, y, id, Type.TestObject, state);
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/TestObject.jpg", 0, 0));
	}
		
	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}
	
}
