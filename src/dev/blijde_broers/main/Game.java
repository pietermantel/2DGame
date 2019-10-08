package dev.blijde_broers.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import dev.blijde_broers.camera.MainCamera;
import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.input.MouseManager;
import dev.blijde_broers.input.MouseWheelManager;
import dev.blijde_broers.misc.math.ExtendedMath;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameState;
import dev.blijde_broers.object.Handler;
import dev.blijde_broers.object.instances.Player;
import dev.blijde_broers.object.instances.TestObject;

public class Game implements Runnable {
	private Window window;
	private Thread thread;
	private Handler handler;
	private MainCamera mainCamera;
	private boolean running = false;
	
	@SuppressWarnings("unused")
	private int fps;
	private boolean esc;
	
	public boolean debug = false;
	
	// De huidige status van het spel.
	public static GameState STATE = GameState.Menu;
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
		window.dispose();
		running = false;
	}
	
	public void init() {
		window = new Window("New Game", (int) (1280 * 0.8), (int) (720 * 0.8));
		window.getCanvas().addKeyListener(new KeyManager());
		window.getCanvas().addMouseListener(new MouseManager());
		window.getCanvas().addMouseWheelListener(new MouseWheelManager());
		
		handler = new Handler();
		mainCamera = new MainCamera(new Transform(new Vector2(0, 0)));
		
		setup();
		System.out.println(Handler.objects.size());
	}
	
	public void setup() {
		handler.removeAll();
		Handler.objects.add(new Player(new Transform(new Vector2(), new Vector2(220, 337))));
		Handler.objects.add(new TestObject(new Transform(new Vector2(), new Vector2(100, 100))));
//		for(int i = 0; i < 100; i++) {
//			Handler.objects.add(new TestObject(new Transform(new Vector2(ExtendedMath.random(-1000, 1000), ExtendedMath.random(-1000, 1000)), new Vector2(100, 100))));
//		}
	}
	
	public void tick() {
		mainCamera.tick();
		handler.tick();
		toggleStates();
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
		g.setColor(Color.white);
		
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
		
		LinkedList<Long> renderTime = new LinkedList<Long>();
		LinkedList<Long> tickTime = new LinkedList<Long>();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				long start = System.nanoTime();
				tick();
				tickTime.add((System.nanoTime() - start));
				delta--;
			}
			if(running) {
				frames++;
			}
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				fps = frames;
				frames = 0;
				long average = 0;
				for (long l : tickTime) {
					average += l;
				}
				if(tickTime.size() > 0)
				System.out.println("Tick: " + (average / tickTime.size()) / 1000);
				average = 0;
				for (long l : renderTime) {
					average += l;
				}
				if(renderTime.size() > 0)
				System.out.println("Render: " + (average / renderTime.size()) / 1000);
				while (tickTime.size() > 0) {
					tickTime.removeFirst();
				}
				while (renderTime.size() > 0) {
					renderTime.removeFirst();
				}
			}
			if(running) {
				long start = System.nanoTime();
				render();
				renderTime.add((System.nanoTime() - start));
			}
		}
	}
	
	public void toggleStates() {
		boolean temp = esc;
		esc = false;
		if(KeyManager.pressed[KeyEvent.VK_ESCAPE]) {
			esc = true;
		}
		if(esc == true && temp == false) {
			if(STATE == GameState.Game) {
				STATE = GameState.Menu;
			} else {
//				STATE = GameState.Game;
			}
		}
		temp = debug;
		debug = false;
		if(KeyManager.pressed[KeyEvent.VK_F3]) {
			debug = true;
		}
		if(debug == true && temp == false) {
			if(STATE == GameState.Game) {
				STATE = GameState.Menu;
			} else {
//				STATE = GameState.Game;
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

	public MainCamera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(MainCamera mainCamera) {
		this.mainCamera = mainCamera;
	}
}
