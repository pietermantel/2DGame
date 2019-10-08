package dev.blijde_broers.camera;

import java.awt.event.KeyEvent;

import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.input.MouseWheelManager;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;

public class MainCamera {
	
	public Transform transform;
	public double zoom = 1;

	public MainCamera(Transform transform) {
		this.transform = transform;
	}
	
	public void tick() {
		zoom = -0.1 * MouseWheelManager.mouseWheelRotation + 1;
		if(KeyManager.pressed[KeyEvent.VK_DOWN]) {
			transform.increment(new Vector2(0, -3));
		}
		if(KeyManager.pressed[KeyEvent.VK_UP]) {
			transform.increment(new Vector2(0, 3));
		}
		if(KeyManager.pressed[KeyEvent.VK_RIGHT]) {
			transform.increment(new Vector2(-3, 0));
		}
		if(KeyManager.pressed[KeyEvent.VK_LEFT]) {
			transform.increment(new Vector2(3, 0));
		}
	}
	
	
	
}
