import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class World {

	public static int SCORE, HIGHSCORE, snakesLeft, level = 1, players = 2;
	public static boolean snake1Moved, snake2Moved;

	static ArrayList<Snake> snakes;
	static ArrayList<Fruit> fruits = new ArrayList<Fruit>();
	static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	static int[][] startingPositions = new int[6][2];
	static Color[] colors = { Color.blue, Color.red, Color.green, Color.yellow,
			Color.magenta, Color.black };

	Background background = new Background();
	HUD HUD = new HUD();
	FileHandler fileHandler = new FileHandler();
	MapHandler mh = new MapHandler();
	Random rand = new Random();


	public World() {
		nextLevel();
	}

	public void tick() {
		// Keeps track of how many snakes are left
		snakesLeft = 0;
		for (Snake s : snakes) {
			if (s.lives > 0 || !s.dead) {
				snakesLeft++;
				s.tick();
				s.moved = false;
			}
			// Manges respawning
			if (s.lives > 0 && s.dead) {
				s.deathTimer++;
				if (s.deathTimer == 50) {
					s.respawn();
					// Resets directions for player snakes
					if (s == snakes.get(0)) {
						HandlerClass.reset1();
					} else if (s == snakes.get(1)) {
						HandlerClass.reset2();
					}

				}
			}

		}
		background.tick();
		checkCollisions();

		while (fruits.size() < Math.floor(Core.timer / 40) + 1) {
			newFruit();
		}
		while (fruits.size() > Math.floor(Core.timer / 40) + 1) {
			removeFruit();
		}
		HUD.tick();
		setDirection();
		if (!Core.GAME_IS_RUNNING && players == 1) {
			fileHandler.writeFile();
			fileHandler.closeFile();
		}
		if (snakesLeft == 0) {
			System.out.println("Out of snakes");
			Core.GAME_IS_RUNNING = false;
		}

	}

	public void render(Graphics g) {
		background.render(g);
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).render(g);
		}
		for (int i = 0; i < fruits.size(); i++) {
			if (fruits.get(i).spawned) {
				fruits.get(i).render(g);
			}
		}
		for (int i = 0; i < snakes.size(); i++) {
			if (snakes.get(i).dead == false) {
				snakes.get(i).render(g);
			}
		}
		HUD.render(g);

		snake1Moved = false;
		snake2Moved = false;

	}

	public void setDirection() {
		if (HandlerClass.upPressed) {
			snakes.get(0).setYDirection(-10);
			snakes.get(0).setXDirection(0);
		}
		if (HandlerClass.downPressed) {
			snakes.get(0).setYDirection(10);
			snakes.get(0).setXDirection(0);
		}
		if (HandlerClass.leftPressed) {
			snakes.get(0).setYDirection(0);
			snakes.get(0).setXDirection(-10);
		}
		if (HandlerClass.rightPressed) {
			snakes.get(0).setYDirection(0);
			snakes.get(0).setXDirection(10);
		}
		if (!HandlerClass.upPressed && !HandlerClass.downPressed
				&& !HandlerClass.leftPressed && !HandlerClass.rightPressed) {
			snakes.get(0).setYDirection(0);
			snakes.get(0).setXDirection(0);
		}
		if (players > 1 && snakes.get(1).human) {
			if (HandlerClass.wPressed) {
				snakes.get(1).setYDirection(-10);
				snakes.get(1).setXDirection(0);
			}
			if (HandlerClass.sPressed) {
				snakes.get(1).setYDirection(10);
				snakes.get(1).setXDirection(0);
			}
			if (HandlerClass.aPressed) {
				snakes.get(1).setYDirection(0);
				snakes.get(1).setXDirection(-10);
			}
			if (HandlerClass.dPressed) {
				snakes.get(1).setYDirection(0);
				snakes.get(1).setXDirection(10);
			}
			if (!HandlerClass.wPressed && !HandlerClass.sPressed
					&& !HandlerClass.aPressed && !HandlerClass.dPressed) {
				snakes.get(1).setYDirection(0);
				snakes.get(1).setXDirection(0);
			}
		}
	}

	public void newFruit() {
		int r = rand.nextInt(5);
		Fruit fruit;

		if (r == 0) {
			fruit = new Orange();
		} else {
			fruit = new Apple();
		}
		// Makes sure fruit doesn't spawn on obstacles or snakes
		if (fruit.spawned == false) {
			for (Obstacle o : obstacles) {
				if (fruit.x == o.getX() && fruit.y == o.getY()) {
					fruit.newPosition();
					newFruit();
				}
			}
			for (Snake s : snakes) {
				if (fruit.x == s.getX() && fruit.y == s.getY()) {
					fruit.newPosition();
					newFruit();
				} else {
					fruit.spawned = true;
					fruits.add(fruit);
				}
			}
		}
	}

	public void removeFruit() {
		fruits.remove(fruits.get(fruits.size() - 1));
	}

	public void checkCollisions() {
		for (int s = 0; s < snakes.size(); s++) {
			// Check obstacles
			for (Obstacle o : obstacles) {
				if (snakes.get(s).size > 0) {
					o.checkCollisions(snakes.get(s).snakeParts.get(0).getX(),
							snakes.get(s).snakeParts.get(0).getY());
				}
			}
			// Check fruit
			for (int i = 0; i < fruits.size(); i++) {
				if (snakes.get(s).size > 0) {
					if (snakes.get(s).snakeParts.get(0).getX() == fruits.get(i).x
							&& snakes.get(s).snakeParts.get(0).getY() == fruits
									.get(i).y) {
						if (fruits.get(i).fruitType.equals("Apple")) {
							snakes.get(s).addPart(snakes.get(s).color);
							snakes.get(s).addPart();
							snakes.get(s).addPart();
							fruits.get(i).spawned = false;
							fruits.remove(i);
							if (players == 1) {
								SCORE += snakes.get(s).size;
							}
						} else if (fruits.get(i).fruitType.equals("Orange")) {
							snakes.get(s).addPart(snakes.get(s).color);
							snakes.get(s).addPart();
							snakes.get(s).addPart();
							snakes.get(s).addPart();
							snakes.get(s).addPart();
							snakes.get(s).addPart();
							fruits.get(i).spawned = false;
							fruits.remove(i);
							if (players == 1) {
								SCORE += snakes.get(s).size;
							}
						}
					}
				}
			}
			// Check collisions between snakes in triple loop (Snake s vs
			// Snake q at Snake q's part p)
			if (players > 1) {
				for (int q = 0; q < snakes.size(); q++) {
					if (q != s) {
						for (int p = 0; p < snakes.get(q).size; p++) {
							if (snakes.get(s).size > 0) {
								if (snakes.get(s).snakeParts.get(0).getX() == snakes
										.get(q).snakeParts.get(p).getX()
										&& snakes.get(s).snakeParts.get(0)
												.getY() == snakes.get(q).snakeParts
												.get(p).getY()) {
									if (snakes.get(s).size > snakes.get(q).size
											- p) {
										for (int n = 0; n < snakes.get(q).size
												- p; n++) {
											snakes.get(s).addPart(snakes.get(q).snakeParts.get(p).color);
											snakes.get(q).removePart(); 
										}
										// If snake s is bigger than snake
										// q's
										// tail at point of collision...
									} else if (snakes.get(s).size < snakes
											.get(q).size - p) {
										if (snakes.get(s).size == 1) {
											snakes.get(s).removePart();
										} else {
											for (int u = 0; u < Math
													.floor(snakes.get(s).size / 2); u++) {
												snakes.get(s).removePart();
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// Checks for self collision of snake s at part p
			for (int p = 1; p < snakes.get(s).size; p++) {
				if (snakes.get(s).snakeParts.get(0).getX() == snakes.get(s).snakeParts
						.get(p).getX()
						&& snakes.get(s).snakeParts.get(0).getY() == snakes
								.get(s).snakeParts.get(p).getY()) {
					for (int n = 0; n < snakes.get(s).size - p; n++) {
						if (players == 1 && p > 2) {
							snakes.get(s).dead = true;
						} else {
							snakes.get(s).removePart();
						}
					}
				}
			}
		}
	}

	public void nextLevel() {

		Core.timer = 0;
		try {
			mh.loadMap("Map" + level + ".txt");
			System.out.println("Map " + level + " loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileHandler.openFile();
		fileHandler.readFile();

		// Resets ArrayList snakes and fills it
		snakes = new ArrayList<Snake>();
		for (int i = 0; i < players; i++) {
			snakes.add(new Snake(colors[i], startingPositions[i][0],
					startingPositions[i][1]));
			//Sets first and second snake to human.
			if(i <= 1){
				snakes.get(i).human = true;
			}
		}
		newFruit();
	}

}