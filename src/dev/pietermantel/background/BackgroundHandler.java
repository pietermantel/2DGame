package dev.pietermantel.background;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.pietermantel.input.KeyManager;

public class BackgroundHandler {
	
	public static Background CURRENT_BACKGROUND;
	
	public static int SCROLL_X = 0, SCROLL_Y = 0, xV, yV;
	
	
	public static void render(Graphics g) {
		g.drawImage(CURRENT_BACKGROUND.getImage(), SCROLL_X, SCROLL_Y, null);
	}
	
	public static void tick() {
		if(KeyManager.pressed[KeyEvent.VK_W]) {
			yV += -1;
		}
		if(KeyManager.pressed[KeyEvent.VK_S]) {
			yV += 1;
		}
		if(KeyManager.pressed[KeyEvent.VK_D]) {
			xV += 1;
		}
		if(KeyManager.pressed[KeyEvent.VK_A]) {
			xV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_W] && yV < 0) {
			yV += 1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_S] && yV > 0) {
			yV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_D] && xV > 0) {
			xV += -1;
		}
		if(!KeyManager.pressed[KeyEvent.VK_A] && xV < 0) {
			xV += 1;
		}
		SCROLL_X -= xV;
		SCROLL_Y -= yV;
	}

}
