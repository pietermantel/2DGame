package dev.pietermantel.background;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.pietermantel.input.KeyManager;
import dev.pietermantel.main.Game;

public class BackgroundHandler {
	public static Background CURRENT_BACKGROUND;
	public static int SCROLL_X = 0, SCROLL_Y = 0, xV, yV, ACTUAL_X, ACTUAL_Y;
	public static final int MAX_VELOCITY = 20;

	public static void render(Graphics g) {
		int x = SCROLL_X;
		int y = SCROLL_Y;
		if (x < -CURRENT_BACKGROUND.getImage().getWidth(null) + Game.GAME.getWindow().getCanvas().getWidth())
			x = -CURRENT_BACKGROUND.getImage().getWidth(null) + Game.GAME.getWindow().getCanvas().getWidth();
		if (y < -CURRENT_BACKGROUND.getImage().getHeight(null) + Game.GAME.getWindow().getCanvas().getHeight())
			y = -CURRENT_BACKGROUND.getImage().getHeight(null) + Game.GAME.getWindow().getCanvas().getHeight();
		if(x > 0) x = 0;
		if(y > 0) y = 0;
		ACTUAL_X = x;
		ACTUAL_Y = y;
		//System.out.println(x + ", " + y + ", " + SCROLL_X + ", " + SCROLL_Y);
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
		if (xV > MAX_VELOCITY)
			xV = MAX_VELOCITY;
		if (xV < -MAX_VELOCITY)
			xV = -MAX_VELOCITY;
		if (yV > MAX_VELOCITY)
			yV = MAX_VELOCITY;
		if (yV < -MAX_VELOCITY)
			yV = -MAX_VELOCITY;
		SCROLL_X += xV;
		SCROLL_Y += yV;
	}

//	private static Player getPlayer() {
//		for (GameObject object : Handler.objects) {
//			if (object.getType() == GameObjectType.Player) {
//				return (Player) object;
//			}
//		}
//		return null;
//	}
	
}
