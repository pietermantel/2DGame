package dev.pietermantel.background;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.pietermantel.input.KeyManager;
import dev.pietermantel.main.Game;
import dev.pietermantel.object.GameObject;
import dev.pietermantel.object.GameObjectType;
import dev.pietermantel.object.Handler;
import dev.pietermantel.object.instances.Player;

public class BackgroundHandler {
	public static Background CURRENT_BACKGROUND;
	public static int SCROLL_X = 0, SCROLL_Y = 0, xV, yV;
	public static final int MAX_VELOCITY = 5;

	public static void render(Graphics g) {
		int x = SCROLL_X;
		int y = SCROLL_Y;
		if (x < -CURRENT_BACKGROUND.getImage().getWidth(null) + Game.GAME.getWindow().getCanvas().getWidth())
			x = -CURRENT_BACKGROUND.getImage().getWidth(null) + Game.GAME.getWindow().getCanvas().getWidth();
		if (y < -CURRENT_BACKGROUND.getImage().getHeight(null) + Game.GAME.getWindow().getCanvas().getHeight())
			y = -CURRENT_BACKGROUND.getImage().getHeight(null) + Game.GAME.getWindow().getCanvas().getHeight();
		System.out.println(x + ", " + y);
		g.drawImage(CURRENT_BACKGROUND.getImage(), x, y, null);
	}

	public static void tick() {
		if (KeyManager.pressed[KeyEvent.VK_W]) {
			yV += 1;
		}
		if (KeyManager.pressed[KeyEvent.VK_S]) {
			yV -= 1;
		}
		if (KeyManager.pressed[KeyEvent.VK_D]) {
			xV -= 1;
		}
		if (KeyManager.pressed[KeyEvent.VK_A]) {
			xV += 1;
		}
		if (!KeyManager.pressed[KeyEvent.VK_W] && !KeyManager.pressed[KeyEvent.VK_S] && yV > 0) {
			yV -= 1;
		}
		if (!KeyManager.pressed[KeyEvent.VK_W] && !KeyManager.pressed[KeyEvent.VK_S] && yV < 0) {
			yV += 1;
		}
		if (!KeyManager.pressed[KeyEvent.VK_A] && !KeyManager.pressed[KeyEvent.VK_D] && xV > 0) {
			xV -= 1;
		}
		if (!KeyManager.pressed[KeyEvent.VK_A] && !KeyManager.pressed[KeyEvent.VK_D] && xV < 0) {
			xV += 1;
		}
		if (xV > 20)
			xV = 20;
		if (xV < -20)
			xV = -20;
		if (yV > 20)
			yV = 20;
		if (yV < -20)
			yV = -20;
		SCROLL_X += xV;
		SCROLL_Y += yV;
	}

	private static Player getPlayer() {
		for (GameObject object : Handler.objects) {
			if (object.getType() == GameObjectType.Player) {
				return (Player) object;
			}
		}
		return null;
	}
}
