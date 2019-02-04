package dev.pietermantel.object.instances;

import java.awt.Graphics;

import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.component.instances.TextureComponent;

public class Player extends GameObject {

	public Player(int x, int y, int id, GameState state) {
		super(x, y, id, GameObjectType.Player, state);
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/player.png", 0, 0));
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

}
