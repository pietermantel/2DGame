package dev.blijde_broers.object.instances;

import java.awt.Color;
import java.awt.Graphics;

import dev.blijde_broers.misc.collisionTemplates.instances.Rectangle;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.components.instances.CollisionComponent;
import dev.blijde_broers.object.components.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(Transform transform) {
		super(transform, 0, GameObjectType.TestObject, GameState.Game);
		componentManager.addCollisionComponent(new CollisionComponent(this, new Rectangle(transform, new Transform(0, 0, 1, 1))));
		componentManager.getObjectComponents().add(new TextureComponent(this, "res\\textures\\player.png", true, new Transform(0, 0, 1, 1)));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) transform.x, (int) transform.y, 1, 1);

	}

}
