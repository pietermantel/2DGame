package dev.blijde_broers.object.instances;

import java.awt.Graphics;

import dev.blijde_broers.misc.collisionTemplates.instances.MultiCorner;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.components.instances.CollisionComponent;
import dev.blijde_broers.object.components.instances.TextureComponent;

public class TestObject extends GameObject {

	public TestObject(Transform transform) {
		super(transform, 0, GameObjectType.TestObject, GameState.Game);
		componentManager.addObjectComponent(new CollisionComponent(this, new MultiCorner(new Transform(new Vector2(), transform.getDimensions().multiply(2f), Math.PI * 0.25), 4)));
		componentManager.addObjectComponent(new TextureComponent(this, "res\\textures\\TestObject.jpg", new Transform(new Vector2(), transform.getDimensions())));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
//		transform.render(g);
	}

}
