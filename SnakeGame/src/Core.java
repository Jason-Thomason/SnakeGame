import java.awt.Graphics;
import java.awt.image.VolatileImage;

public class Core implements Runnable {

	World world;
	Window window = new Window();

	VolatileImage image;
	Graphics g;

	public static int timer = 0, highScore = 0;
	public static boolean GAME_IS_RUNNING;

	private int loops;
	private static int FPS = 13, MAX_FRAME_SKIP = 2, SKIP_TICKS = 1000 / FPS;
	private long CURRENT_TIME = System.currentTimeMillis();

	static Core core;

	public static void main(String[] args) {
		core = new Core();
	}

	public Core() {
		newGame();

	}

	public void tick() {
		timer++;
		window.setSize();
		world.tick();

	}

	public void render(Graphics g) {
		world.render(g);

		g = window.getGraphics();
		g.drawImage(image, 0, 0, null);
	}

	public void run() {
		System.out.println("Run Start");
		image = window.createVolatileImage(Window.width, Window.height);
		g = image.getGraphics();
		GAME_IS_RUNNING = true;

		try {
			while (GAME_IS_RUNNING) {

				loops = 0;
				// Calculate data as long as we are behind schedule and we
				// havn't skipped display too much
				while (System.currentTimeMillis() > CURRENT_TIME
						&& loops < MAX_FRAME_SKIP) {
					tick();

					CURRENT_TIME += SKIP_TICKS;
					loops++;
				}
				render(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void newGame() {
		world = new World();
		Thread t1 = new Thread(this);
		t1.start();
	}
}
