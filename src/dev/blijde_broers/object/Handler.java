package dev.blijde_broers.object;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import dev.blijde_broers.camera.MainCamera;
import dev.blijde_broers.main.Game;
import dev.blijde_broers.menu.Menu;
import dev.blijde_broers.misc.math.Transform;

public class Handler {

	public static ArrayList<GameObject> objects = new ArrayList<GameObject>();

	public static Menu menu = new Menu();

	public static MainCamera mainCamera = new MainCamera(new Transform());

	public void removeAll() {
		for (int i = 0; i < objects.size(); i++) {
			objects.remove(i);
		}
	}

	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			if (Arrays.asList(objects.get(i).getStates()).contains(Game.STATE)) {
				objects.get(i).manageTick();
			}
		}
		if (Game.STATE == GameState.Menu) {
			menu.tick();
		}
		if (Arrays.asList(MainCamera.STATES).contains(Game.STATE)) {
			mainCamera.manageTick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			if (Arrays.asList(objects.get(i).getStates()).contains(Game.STATE)) {
				objects.get(i).manageRender(g);
			}
		}
		if (Game.STATE == GameState.Menu) {
			menu.render(g);
		}
	}

}
