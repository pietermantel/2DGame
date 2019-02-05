package dev.pietermantel.object.instances;

import java.awt.Graphics;

import dev.pietermantel.background.BackgroundHandler;
import dev.pietermantel.main.Game;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.component.Component;
import dev.pietermantel.object.component.instances.TextureComponent;

public class Player extends GameObject {

	public Player(int x, int y, int id, GameState state) {
		super(x, y, id, GameObjectType.Player, state);
		componentManager.getComponents().add(new TextureComponent(this, "res/textures/player.png", 0, 0));
	}
	
	private int Width, Height;
	{
		for (Component component : Player.getComponentManager().getComponents()) {
			if (component instanceof TextureComponent) {
				Width = ((TextureComponent) component).getTexture().getWidth(null);
			}
		}
		System.out.println(Width);
	}
	{
		for (Component component : Player.getComponentManager().getComponents()) {
			if (component instanceof TextureComponent) {
				Height = ((TextureComponent) component).getTexture().getHeight(null);
			}
		}
		System.out.println(Height);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		x = (Game.GAME.getWindow().getCanvas().getWidth() / 2 - Width) - (BackgroundHandler.SCROLL_X - BackgroundHandler.ACTUAL_X);
		y = (Game.GAME.getWindow().getCanvas().getHeight() / 2 - Height) - (BackgroundHandler.SCROLL_Y - BackgroundHandler.ACTUAL_Y);
		if (x < 0) {
			x = 0;
			BackgroundHandler.SCROLL_X = Game.GAME.getWindow().getCanvas().getWidth() / 2 - Width;
		}
		if (y < 0) {
			y = 0;
			BackgroundHandler.SCROLL_Y = Game.GAME.getWindow().getCanvas().getHeight() / 2 - Height;
		}
		if (x > Game.GAME.getWindow().getCanvas().getWidth() - Width) {
			x = Game.GAME.getWindow().getCanvas().getWidth() - Width;
//			BackgroundHandler.SCROLL_X = BackgroundHandler.SCROLL_X = Game.GAME.getWindow().getCanvas().getWidth() / 2 - Width;
		}
		if (y > Game.GAME.getWindow().getCanvas().getHeight() - Height) {
			y = Game.GAME.getWindow().getCanvas().getHeight() - Height;
//			BackgroundHandler.SCROLL_Y = BackgroundHandler.SCROLL_Y = Game.GAME.getWindow().getCanvas().getHeight() / 2 - Height;
	}
	}
}
