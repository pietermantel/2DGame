package dev.pietermantel.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
	private Window window;
	private Thread thread;
	private boolean running = false;
	
	public static final int FPS = 60;

	public Game() {
		window = new Window("Hallo", 800, 600);
		start();
	}

	public static void main(String[] args) {
		new Game();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() throws InterruptedException {
		thread.join();
		running = false;
	}
	
	public void init() {
		
	}
	
	public void tick() {
		
	}
	
	public void render() {
		BufferStrategy bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		
		g.dispose();
		bs.show();
	}
	
	public void run() {
		init();
		int frames = 0;
		long lastTime = System.nanoTime();
		double ns = 1000000000 / FPS;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				frames++;
			}
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			if(running) {
				render();
			}
		}
	}

}
