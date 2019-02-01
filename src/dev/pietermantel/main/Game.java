package dev.pietermantel.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.pietermantel.object.GameState;
import dev.pietermantel.object.Handler;
import dev.pietermantel.object.instances.TestObject;

public class Game implements Runnable {
	private Window window;
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	
	// De huidige status van het spel.
	public static GameState STATE = GameState.game;
	
	public static final int FPS = 60;

	public Game() {
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
		window = new Window("Hallo", 1280, 720);
		handler = new Handler();
		
		Handler.objects.add(new TestObject(100, 100, 0, GameState.game));
	}
	
	public void tick() {
		handler.tick();
	}
	
	public void render() {
		BufferStrategy bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 1280, 720);
		handler.render(g);
		
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
