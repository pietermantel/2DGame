package dev.pietermantel.object;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

import dev.pietermantel.main.Game;

public class Handler {
	// Deze class zorgt ervoor dat de tick en render methods van alle objecten goed
	// worden uitgevoerd.
	public static LinkedList<GameObject> objects = new LinkedList<GameObject>();

	public void tick() {
		for (GameObject object : objects) {
			// Kijkt of de lijst de huidige state bevat. Als dat niet zo is, moet er ook
			// niets gebeuren.
			if (Arrays.asList(object.getStates()).contains(Game.STATE)) {
				object.manageTick();
			}
		}
	}

	public void render(Graphics g) {
		for (GameObject object : objects) {
			// Kijkt of de lijst de huidige state bevat. Als dat niet zo is, moet er ook
			// niets gebeuren.
			if (Arrays.asList(object.getStates()).contains(Game.STATE)) {
				object.manageRender(g);
			}
		}
	}
}
