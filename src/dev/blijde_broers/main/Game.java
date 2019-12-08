package dev.blijde_broers.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.input.MouseManager;
import dev.blijde_broers.input.MouseWheelManager;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.Handler;
import dev.blijde_broers.object.instances.Player;
import dev.blijde_broers.object.instances.TestObject;
import dev.blijde_broers.object.instances.Wall;

public class Game implements Runnable {
	private Window window;
	private Thread thread;
	private Handler handler;
	private LoadingScreen loadingScreen;
	private boolean running = false;

	@SuppressWarnings("unused")
	private int fps;
	private boolean esc;
	private boolean fthree;

	public static boolean debug = true;

	// De huidige status van het spel.
	public static GameState STATE = GameState.Menu;
	public static Game GAME;

	public static final int TPS = 60;

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
		window.dispose();
		running = false;
	}

	public void init() {
		window = new Window("New Game", (int) (1280 * 0.9), (int) (720 * 0.9));
		loadingScreen = new LoadingScreen();
		loadingScreen.start();
		loadingScreen.percentageDone = 0;
		window.getCanvas().addKeyListener(new KeyManager());
		window.getCanvas().addMouseListener(new MouseManager());
		window.getCanvas().addMouseWheelListener(new MouseWheelManager());

		loadingScreen.percentageDone = 33;

		handler = new Handler();

		loadingScreen.percentageDone = 67;

		setup();
		System.out.println(Handler.objects.size());

		loadingScreen.percentageDone = 100;
		try {
			loadingScreen.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setup() {
		handler.removeAll();
		Handler.objects.add(new Player(new Transform(new Vector2(300, 300), new Vector2(220, 337))));
		Handler.objects.add(new TestObject(new Transform(new Vector2(300, -500), new Vector2(100, 100))));
//		for (int y = 0; y < 2; y++) {
//			for (int x = 0; x < 2; x++) {
//				GameObject temp = new TestObject(new Transform(new Vector2(x * 40, y * 40), new Vector2(30, 30)));
//				// temp.getComponentManager().getRigidBody()
//				// .addPosForce(new Vector2(1, 0).rotate(Math.random() * 2 * Math.PI));
//				Handler.objects.add(temp);
//			}
//		}
		for (int r = 0; r < 360; r += 90) {
			Handler.objects.add(new Wall(new Transform(new Vector2(2000, 0).setDirection(Math.toRadians(r)),
					new Vector2(3900, 10).rotate(Math.toRadians(r + 90)))));
		}
	}

	public void tick() {
		handler.tick();
		toggleStates();
	}

	public void render() {
		BufferStrategy bs = window.getCanvas().getBufferStrategy();
		if (bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());
		g.setColor(Color.white);

		handler.render(g);

		g.setColor(Color.white);
		g.drawString(Double.toString(Handler.mainCamera.zoom), 10, 20);

		g.dispose();
		bs.show();
	}

	public void run() {
		init();
		int frames = 0;
		long lastTime = System.nanoTime();
		double ns = 1000000000 / TPS;
		double delta = 0;
		long timer = System.currentTimeMillis();

		LinkedList<Long> renderTime = new LinkedList<Long>();
		LinkedList<Long> tickTime = new LinkedList<Long>();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				long start = System.nanoTime();
				tick();
				tickTime.add((System.nanoTime() - start));
				delta--;
			}
			if (running) {
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				fps = frames;
				frames = 0;
				long average = 0;
				for (long l : tickTime) {
					average += l;
				}
				if (tickTime.size() > 0)
					System.out.println("Tick: " + (average / tickTime.size()) / 1000);
				average = 0;
				for (long l : renderTime) {
					average += l;
				}
				if (renderTime.size() > 0)
					System.out.println("Render: " + (average / renderTime.size()) / 1000);
				while (tickTime.size() > 0) {
					tickTime.removeFirst();
				}
				while (renderTime.size() > 0) {
					renderTime.removeFirst();
				}
			}
			if (running) {
				long start = System.nanoTime();
				render();
				renderTime.add((System.nanoTime() - start));
			}
		}
	}

	public void toggleStates() {
		boolean temp = esc;
		esc = false;
		if (KeyManager.pressed[KeyEvent.VK_ESCAPE]) {
			esc = true;
		}
		if (esc == true && temp == false) {
			if (STATE == GameState.Game) {
				STATE = GameState.Menu;
			} else {
				// STATE = GameState.Game;
			}
		}
		temp = fthree;
		fthree = false;
		if (KeyManager.pressed[KeyEvent.VK_F3]) {
			fthree = true;
		}
		if (fthree == true && temp == false) {
			if (debug) {
				debug = false;
			} else {
				debug = true;
			}
		}
		if (KeyManager.pressed[KeyEvent.VK_R]) {
			setup();
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
