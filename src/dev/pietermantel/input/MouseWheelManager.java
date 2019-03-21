package dev.pietermantel.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MouseWheelManager extends MouseAdapter {
	public static int mouseWheelRotation = 0;
	
	public void mouseWheelRotation(MouseWheelEvent e) {
		mouseWheelRotation = e.getWheelRotation();
	}

}
