package dev.pietermantel.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.pietermantel.background.Background;
import dev.pietermantel.background.BackgroundHandler;
import dev.pietermantel.input.KeyManager;
import dev.pietermantel.input.MouseManager;
import dev.pietermantel.input.MouseWheelManager;
import dev.pietermantel.object.GameState;
import dev.pietermantel.object.Handler;
import dev.pietermantel.object.instances.Player;
import dev.pietermantel.object.instances.TestObject;

public class Game implements Runnable {
	private Window window;
	private Thread thread;
	private Handler handler;
	private boolean running = false;

	// De huidige status van het spel.
	public static GameState STATE = GameState.game;
	public static Game GAME;

	public static final int FPS = 60;

	public Game() {
		start();
	}

	public static void main(String[] args) {
		GAME = new Game();
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
		KeyManager.init();
		window.getCanvas().addKeyListener(new KeyManager());
		window.getCanvas().addMouseListener(new MouseManager());

		window.getCanvas().addMouseWheelListener(new MouseWheelManager());
		
		BackgroundHandler.CURRENT_BACKGROUND = Background.playersBedroom;

		Handler.objects.add(new Player(0, 0, 0, GameState.game));
	}

	public void tick() {
		handler.tick();
		BackgroundHandler.tick();
	}

	public void render() {
		BufferStrategy bs = window.getCanvas().getBufferStrategy();
		if (bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, 1280, 720);
		BackgroundHandler.render(g);
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
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			if (running) {
				render();
			}
		}
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
