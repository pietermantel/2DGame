package dev.blijde_broers.object.instances;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.misc.collisionComponentParts.instances.Rectangle;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.components.instances.CollisionComponent;
import dev.blijde_broers.object.components.instances.RigidBody;
import dev.blijde_broers.object.components.instances.TextureComponent;

public class Player extends GameObject {
	
	private RigidBody rigidBody;

	public Player(Transform transform) {
		super(transform, 0, GameObjectType.Player, GameState.Game);
		componentManager.addObjectComponent(new TextureComponent(this, "res\\textures\\player.png", new Transform(new Vector2(), transform.getDimensions())));
		componentManager.addObjectComponent(new CollisionComponent(this, new Rectangle(new Transform(new Vector2(), transform.getDimensions()))));
		componentManager.addObjectComponent(new RigidBody(this, new Vector2(), 0, 0.6f, 0.5f, 10f, true));
		rigidBody = componentManager.getRigidBody();
	}

	@Override
	public void tick() {
		if(KeyManager.pressed[KeyEvent.VK_S]) {
//			transform.increment(new Vector2(0, 3).rotate(transform.getRotation()));
			rigidBody.addPosForce((new Vector2(0, 7).rotate(transform.getRotation())));
		}
		if(KeyManager.pressed[KeyEvent.VK_W]) {
//			transform.increment(new Vector2(0, -3).rotate(transform.getRotation()));
			rigidBody.addPosForce((new Vector2(0, -7).rotate(transform.getRotation())));
		}
		if(KeyManager.pressed[KeyEvent.VK_D]) {
//			transform.rotate(0.05);
			rigidBody.addRotForce(0.04);
		}
		if(KeyManager.pressed[KeyEvent.VK_A]) {
//			transform.rotate(-0.05);
			rigidBody.addRotForce(-0.04);
		}
	}

	@Override
	public void render(Graphics g) {
//		transform.render(g);
	}

}
