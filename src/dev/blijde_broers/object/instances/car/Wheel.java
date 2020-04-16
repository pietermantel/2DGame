package dev.blijde_broers.object.instances.car;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.misc.collisionComponentParts.instances.MultiCorner;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.components.instances.CollisionComponent;
import dev.blijde_broers.object.components.instances.RigidBody;
import dev.blijde_broers.object.components.instances.TextureComponent;

public class Wheel extends GameObject {
	
	private RigidBody rigidBody;

	public Wheel(Transform transform, int id) {
		super(transform, id, GameObjectType.Wheel, GameState.Game);
		componentManager.addObjectComponent(new CollisionComponent(this,
				new MultiCorner(new Transform(new Vector2(), transform.getDimensions().multiply(1)), 100)));
		componentManager.addObjectComponent(new TextureComponent(this, "res\\textures\\TestObject.jpg",
				new Transform(new Vector2(), transform.getDimensions())));
		componentManager.addObjectComponent(new RigidBody(this, new Vector2(), 0, 0.001, 0.001, 1, true, true));
		rigidBody = componentManager.getRigidBody();
	}

	@Override
	public void tick() {
		if (KeyManager.pressed[KeyEvent.VK_L]) {
			// transform.rotate(0.05);
			rigidBody.addRotForce(.04);
			// rigidBody.addPosForce(new Vector2(10, 0));
		}
		if (KeyManager.pressed[KeyEvent.VK_J]) {
			// transform.rotate(-0.05);
			rigidBody.addRotForce(-.04);
			// rigidBody.addPosForce(new Vector2(-10, 0));
		}

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

}
