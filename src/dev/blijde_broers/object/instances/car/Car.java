package dev.blijde_broers.object.instances.car;

import java.awt.Graphics;

import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameObjectType;
import dev.blijde_broers.object.GameState;

public class Car extends GameObject {

	public Car(Transform transform) {
		super(transform, 0, GameObjectType.Car, GameState.Game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

}
