package dev.pietermantel.main;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	public Window(String title, int width, int height) {
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle(title);
		setVisible(true);
		setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setFocusable(true);
		add(canvas);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
