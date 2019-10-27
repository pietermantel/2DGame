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

public class RotationTest extends GameObject {

	private RigidBody rigidBody;
	
	public RotationTest(Transform transform) {
		super(transform, 0, GameObjectType.RotationTest, GameState.Game);
		componentManager.addObjectComponent(new CollisionComponent(this,
				new Rectangle(new Transform(new Vector2(), transform.getDimensions().multiply(1f), 0))));
		componentManager.addObjectComponent(new TextureComponent(this, "res\\textures\\TestObject.jpg",
				new Transform(new Vector2(), transform.getDimensions())));
		componentManager.addObjectComponent(new RigidBody(this, new Vector2(), 0, 0.1f, 0.1f, 10f, true));
		rigidBody = componentManager.getRigidBody();
	}

	@Override
	public void tick() {
		if (KeyManager.pressed[KeyEvent.VK_SPACE]) {
			rigidBody.resoluteToImpact(new Vector2(transform.getDimensions().x * 0.5f, transform.getDimensions().y * 0),
					Math.toRadians(-90), 1d);
		}
//		System.out.println(transform.mid.toString());

	}

	@Override
	public void render(Graphics g) {

	}

}
