package dev.pietermantel.object.instances;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.pietermantel.background.BackgroundHandler;
import dev.pietermantel.main.Game;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.component.instances.CollisionBoxComponent;
import dev.pietermantel.object.component.instances.TextureComponent;

public class Player extends GameObject {
	private int width, height;

	public Player(int x, int y, int id, GameState state) {
		super(x, y, id, GameObjectType.Player, state);
		TextureComponent tc;
		componentManager.getComponents().add(tc = new TextureComponent(this, "res/textures/player.png", 0, 0));
		width = tc.getTexture().getWidth(null);
		height = tc.getTexture().getHeight(null);
		componentManager.getComponents().add(new CollisionBoxComponent(this, new Rectangle(x, y, width, height), false));
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		x = (Game.GAME.getWindow().getCanvas().getWidth() / 2 - width / 2) - (BackgroundHandler.SCROLL_X - BackgroundHandler.ACTUAL_X);
		y = (Game.GAME.getWindow().getCanvas().getHeight() / 2 - height / 2) - (BackgroundHandler.SCROLL_Y - BackgroundHandler.ACTUAL_Y);
		if (x < 0) {
			x = 0;
			BackgroundHandler.SCROLL_X = Game.GAME.getWindow().getCanvas().getWidth() / 2 - width / 2;
			BackgroundHandler.xV = 0;
		}
		if (y < 0) {
			y = 0;
			BackgroundHandler.SCROLL_Y = Game.GAME.getWindow().getCanvas().getHeight() / 2 - height / 2;
			BackgroundHandler.yV = 0;
		}
		if (x > Game.GAME.getWindow().getCanvas().getWidth() - width) {
			x = Game.GAME.getWindow().getCanvas().getWidth() - width;
			BackgroundHandler.SCROLL_X = BackgroundHandler.ACTUAL_X - (Game.GAME.getWindow().getCanvas().getWidth() / 2 - width / 2);
			BackgroundHandler.xV = 0;
		}
		if (y > Game.GAME.getWindow().getCanvas().getHeight() - height) {
			y = Game.GAME.getWindow().getCanvas().getHeight() - height;
			BackgroundHandler.SCROLL_Y = BackgroundHandler.ACTUAL_Y - (Game.GAME.getWindow().getCanvas().getHeight() / 2 - height / 2);
			BackgroundHandler.yV = 0;
		}
	}
}
