package dev.blijde_broers.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import dev.blijde_broers.camera.MainCamera;
import dev.blijde_broers.input.KeyManager;
import dev.blijde_broers.input.MouseManager;
import dev.blijde_broers.input.MouseWheelManager;
import dev.blijde_broers.misc.math.Transform;
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
	
	private boolean esc;
	
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
		thread.join();
		running = false;
	}
	
	public void init() {
		window = new Window("New Game", 1280, 720);
		window.getCanvas().addKeyListener(new KeyManager());
		window.getCanvas().addMouseListener(new MouseManager());
		window.getCanvas().addMouseWheelListener(new MouseWheelManager());
		
		handler = new Handler();
		mainCamera = new MainCamera(new Transform());
		
		setup();
	}
	
	public void setup() {
		handler.removeAll();
		Handler.objects.add(new Player(new Transform()));
		Handler.objects.add(new TestObject(new Transform(1, 0)));
	}
	
	public void tick() {
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
