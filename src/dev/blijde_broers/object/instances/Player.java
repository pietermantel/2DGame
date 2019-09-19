package dev.blijde_broers.object.instances;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.misc.collisionTemplates.instances.Rectangle;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.components.instances.CollisionComponent;
import dev.blijde_broers.object.components.instances.TextureComponent;

public class Player extends GameObject {

	public Player(Transform transform) {
		super(transform, 0, GameObjectType.Player, GameState.Game);
		componentManager.getObjectComponents().add(new TextureComponent(this, "res\\textures\\player.png", true, new Transform(0, 0, 2.2f, 3.37f)));
		componentManager.addCollisionComponent(new CollisionComponent(this, new Rectangle(transform, new Transform(0, 0, 2.2f, 3.37f))));
	}

	@Override
	public void tick() {
		if(KeyManager.pressed[KeyEvent.VK_S]) {
			transform.moveOnRelativeYAxis(-0.1f);
		}
		if(KeyManager.pressed[KeyEvent.VK_W]) {
			transform.moveOnRelativeYAxis(0.1f);
		}
		if(KeyManager.pressed[KeyEvent.VK_D]) {
			transform.rotation += 0.1;
		}
		if(KeyManager.pressed[KeyEvent.VK_A]) {
			transform.rotation -= 0.1;
		}
	}

	@Override
	public void render(Graphics g) {
		
	}

}
