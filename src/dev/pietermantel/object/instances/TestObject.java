package dev.pietermantel.object.instances;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.pietermantel.input.KeyManager;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.Type;
import dev.pietermantel.object.component.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(int x, int y, int id, GameState state) {
		super(x, y, id, Type.TestObject, state);
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/TestObject.jpg", 0, 0));
	}
	
	private static int xV = 1;
	private static int yV = 1;
		
	@Override
	public void tick() {
		if(KeyManager.pressed[KeyEvent.VK_W]) {
			yV += -1;
		}
		if(KeyManager.pressed[KeyEvent.VK_S]) {
			yV += 1;
		}
		if(KeyManager.pressed[KeyEvent.VK_D]) {
			xV += 1;
		}
		if(KeyManager.pressed[KeyEvent.VK_A]) {
			xV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_W] && yV < 0) {
			yV += 1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_S] && yV > 0) {
			yV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_D] && xV > 0) {
			xV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_A] && xV < 0) {
			xV += 1;
		}
		x += xV;
		y += yV;
	}

	@Override
	public void render(Graphics g) {
		
	}
	
}
