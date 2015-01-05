import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * 		BUGS
 * -Snakes with no tail cannot pick up fruit
 */


public class World {

	public static enum GameMode {
		CLASSIC, BATTLE, TRON, TRON_INFINITE
	}

	public static GameMode gameMode = GameMode.BATTLE;

	public static int SCORE, HIGHSCORE, snakesLeft, level = 3, players = 1,
			totalSnakes, startingTotalSnakes = 4;
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
		for (int s = 0; s < snakes.size(); s++) {
			if (snakes.get(s).lives == 0 && snakes.get(s).dead) {
				snakes.remove(s);
				totalSnakes--;
			}
		}
		// Keeps track of how many snakes are left
		snakesLeft = 0;
		for (Snake s : snakes) {
			// Manges respawning
			if (s.lives > 0 && s.dead) {
				s.deathTimer++;
				if (s.deathTimer == 50) {
					respawnSnake(s);
					s.deathTimer = 0;

				}
			}
			if (s.lives > 0 || !s.dead) {
				snakesLeft++;
				s.tick();
				// Resets directions for player snakes
				if (s == snakes.get(0)) {
					HandlerClass.reset1();
				} else if (s == snakes.get(1)) {
					HandlerClass.reset2();
				}

			}
			if (s.dead) {
				while (s.snakeParts.size() > 0) {
					// s.removePart();
					s.snakeParts.remove(s.snakeParts.size() - 1);
					s.totalSize--;
				}
			}

			// Handles AI Snakes
			if (!s.human) {
				switch (s.mode) {
				case ROAM:
					AIRoam(s);
					break;
				}
			}
			s.moved = false;

		}
		background.tick();
		checkCollisions();

		if (gameMode != GameMode.TRON_INFINITE) {
			while (fruits.size() < Math.floor(Core.timer / 40) + 1) {
				newFruit();
			}
			while (fruits.size() > Math.floor(Core.timer / 40) + 1) {
				removeFruit();
			}
		}

		HUD.tick();
		setDirection();
		if (!Core.GAME_IS_RUNNING && gameMode == GameMode.CLASSIC) {
			fileHandler.writeFile();
			fileHandler.closeFile();
		}
		if (snakesLeft == 0) {
			System.out.println("Ending due to lack of snakes left");
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
			if (snakes.get(i).dead == false && snakes.get(i).totalSize > 0) {
				snakes.get(i).render(g);
			}
		}
		HUD.render(g);

		snake1Moved = false;
		snake2Moved = false;

	}

	public void setDirection() {
		if (snakes.size() > 0) {
			/*
			 * if (HandlerClass.upPressed) { snakes.get(0).direction =
			 * snakes.get(0).direction.UP; } if (HandlerClass.downPressed) {
			 * snakes.get(0).direction = snakes.get(0).direction.DOWN; } if
			 * (HandlerClass.leftPressed) { snakes.get(0).direction =
			 * snakes.get(0).direction.LEFT; } if (HandlerClass.rightPressed) {
			 * snakes.get(0).direction = snakes.get(0).direction.RIGHT; } if
			 * (!HandlerClass.upPressed && !HandlerClass.downPressed &&
			 * !HandlerClass.leftPressed && !HandlerClass.rightPressed) {
			 * snakes.get(0).direction = snakes.get(0).direction.NONE; }
			 */

		}
		if (snakes.size() > 1) {
			if (snakes.get(1).human) {
				if (HandlerClass.wPressed) {
					snakes.get(1).direction = Direction.UP;
				}
				if (HandlerClass.sPressed) {
					snakes.get(1).direction = Direction.DOWN;
				}
				if (HandlerClass.aPressed) {
					snakes.get(1).direction = Direction.LEFT;
				}
				if (HandlerClass.dPressed) {
					snakes.get(1).direction = Direction.RIGHT;
				}
				if (!HandlerClass.wPressed && !HandlerClass.sPressed
						&& !HandlerClass.aPressed && !HandlerClass.dPressed) {
					snakes.get(1).direction = Direction.NONE;
				}
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
			Snake snakeBeingChecked1 = snakes.get(s);
			if (snakeBeingChecked1.snakeParts.size() > 0) {
				// Check obstacles
				for (int o = 0; o < obstacles.size(); o++) {
					if (snakes.get(s).totalSize > 0) {
						obstacles.get(o).checkCollisions(snakes.get(s));
					}
				}
				// Check fruit
				for (int i = 0; i < fruits.size(); i++) {

					if (snakeBeingChecked1.snakeParts.get(0).x == fruits.get(i).x
							&& snakeBeingChecked1.snakeParts.get(0).y == fruits
									.get(i).y) {
						if (fruits.get(i).fruitType.equals("Apple")) {
							snakeBeingChecked1
									.addPart(snakeBeingChecked1.color);
							snakeBeingChecked1.addPart();
							snakeBeingChecked1.addPart();
							fruits.get(i).spawned = false;
							fruits.remove(i);
							if (players == 1) {
								SCORE += snakeBeingChecked1.totalSize;
							}
						} else if (fruits.get(i).fruitType.equals("Orange")) {
							snakeBeingChecked1
									.addPart(snakeBeingChecked1.color);
							snakeBeingChecked1.addPart();
							snakeBeingChecked1.addPart();
							snakeBeingChecked1.addPart();
							snakeBeingChecked1.addPart();
							snakeBeingChecked1.addPart();
							fruits.get(i).spawned = false;
							fruits.remove(i);
							if (players == 1) {
								SCORE += snakeBeingChecked1.totalSize;
							}
						}
					}
				}
				// Check collisions between snakes in triple loop (Snake s vs
				// Snake q at Snake q's part p)
				for (int q = 0; q < snakes.size(); q++) {
					if (q != s) {
						// Check head on collisions
						if (snakes.get(s).x == snakes.get(q).x
								&& snakes.get(s).y == snakes.get(q).y) {
							if (gameMode == GameMode.BATTLE) {
								if (snakes.get(s).totalSize < snakes.get(q).totalSize) {
									int z = snakes.get(s).totalSize;
									for (int n = 0; n < z; n++) {
										snakes.get(q).addPart(
												snakes.get(s).color);
										snakes.get(s).removePart();
									}
								} else if (snakes.get(s).totalSize > snakes.get(q).totalSize) {
									int z = snakes.get(q).totalSize;
									for (int n = 0; n < z; n++) {
										snakes.get(s).addPart(
												snakes.get(q).color);
										snakes.get(q).removePart();
									}
								} else if (snakes.get(s).totalSize == snakes.get(q).totalSize - 1) {
									int z = snakes.get(s).totalSize - 0;
									for (int n = 0; n < z; n++) {
										snakes.get(s).removePart();
										snakes.get(q).removePart();
									}
								}
							} else {
								for (SnakePart sp : snakes.get(s).snakeParts) {
									obstacles.add(new DeadSnakePart(sp));
								}
								snakes.get(s).dead = true;
								for (SnakePart sp : snakes.get(q).snakeParts) {
									obstacles.add(new DeadSnakePart(sp));
								}
								snakes.get(q).dead = true;
							}

						}// Checks "glitched" head on collision
						if (snakes.get(s).totalSize > 1 && snakes.get(q).totalSize > 1) {
							if (snakes.get(s).x == snakes.get(q).snakeParts
									.get(0).x
									&& snakes.get(s).y == snakes.get(q).snakeParts
											.get(0).y
									&& snakes.get(q).x == snakes.get(s).snakeParts
											.get(0).x
									&& snakes.get(q).y == snakes.get(s).snakeParts
											.get(0).y) {
								if (gameMode == GameMode.BATTLE) {
									if (snakes.get(s).totalSize < snakes.get(q).totalSize) {
										int z = snakes.get(s).totalSize;
										for (int n = 0; n < z; n++) {
											snakes.get(q).addPart(
													snakes.get(s).color);
											snakes.get(s).removePart();
										}
									} else if (snakes.get(s).totalSize > snakes
											.get(q).totalSize) {
										int z = snakes.get(q).totalSize;
										for (int n = 0; n < z; n++) {
											snakes.get(s).addPart(
													snakes.get(q).color);
											snakes.get(q).removePart();
										}
									} else if (snakes.get(s).totalSize == snakes
											.get(q).totalSize) {
										int z = snakes.get(s).totalSize - 1;
										for (int n = 0; n < z; n++) {
											snakes.get(s).removePart();
											snakes.get(q).removePart();
										}
									}
								} else {

									for (SnakePart sp : snakes.get(s).snakeParts) {
										obstacles.add(new DeadSnakePart(sp));
									}
									snakes.get(s).dead = true;
									for (SnakePart sp : snakes.get(q).snakeParts) {
										obstacles.add(new DeadSnakePart(sp));
									}
									snakes.get(q).dead = true;
								}

							}
						}
						for (int p = 0; p < snakes.get(q).totalSize - 1; p++) {
							if (snakes.get(s).totalSize > 0) {
								if (snakes.get(s).x == snakes.get(q).snakeParts
										.get(p).x
										&& snakes.get(s).y == snakes.get(q).snakeParts
												.get(p).y) {
									if (gameMode == GameMode.BATTLE) {
										if (snakes.get(s).totalSize > snakes.get(q).totalSize
												- p) {
											int z = snakes.get(q).snakeParts
													.size() - p;
											for (int n = 0; n < z; n++) {
												snakes.get(s)
														.addPart(
																snakes.get(q).snakeParts
																		.get(p).color);
												snakes.get(q).removePart();
											}
											// If snake s is smaller than snake
											// q's
											// tail at point of collision...
										} else if (snakes.get(s).totalSize < snakes
												.get(q).snakeParts.size() - p) {
											int z = snakes.get(q).totalSize;
											for (int u = 0; u < z; u++) {
												snakes.get(s).removePart();
											}
										}
									} else {
										for (SnakePart sp : snakes.get(s).snakeParts) {
											obstacles
													.add(new DeadSnakePart(sp));
										}
										snakes.get(s).dead = true;
									}
								}

							}
						}
					}
				}
				// Checks for self collision of snake s at part p

				for (int p = 3; p < snakes.get(s).snakeParts.size() - 1; p++) {
					if (snakes.get(s).x == snakes.get(s).snakeParts.get(p).x
							&& snakes.get(s).y == snakes.get(s).snakeParts
									.get(p).y) {
						// Declare the length of the tail before loop so it only
						// checks once
						int z = snakes.get(s).totalSize - p;
						for (int n = 0; n < z; n++) {
							if (gameMode != GameMode.BATTLE) {
								for (SnakePart sp : snakes.get(s).snakeParts) {
									obstacles.add(new DeadSnakePart(sp));
								}
								snakes.get(s).dead = true;
							} else {
								snakes.get(s).removePart();

							}
						}
					}
				}
			}
		}
	}

	public void nextLevel() {

		Core.timer = 0;
		try {
			mh.loadMap("Resources/Maps/Map" + level + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileHandler.openFile();
		fileHandler.readFile();

		// Resets map
		for (int o = 0; o < obstacles.size() - 1; o++) {
			if (obstacles.get(o).getClass() == DeadSnakePart.class) {
				obstacles.remove(o);
				o--;
			}
		}

		totalSnakes = startingTotalSnakes;

		// Resets ArrayList snakes and fills it
		snakes = new ArrayList<Snake>();
		for (int i = 0; i < totalSnakes; i++) {
			snakes.add(new Snake(colors[i], startingPositions[i][0],
					startingPositions[i][1]));

			// Sets as many snakes human as there are players
			if (i < players) {

				snakes.get(i).human = true;
			} else if (i >= players) {
				// Gives random direction for AI Snakes
				int d = rand.nextInt(4);
				switch (d) {
				case 0:
					snakes.get(i).direction = snakes.get(i).direction.LEFT;
					snakes.get(i).xDirection = -10;
					snakes.get(i).yDirection = 0;
					break;
				case 1:
					snakes.get(i).direction = snakes.get(i).direction.UP;
					snakes.get(i).xDirection = 0;
					snakes.get(i).yDirection = -10;
					break;
				case 2:
					snakes.get(i).direction = snakes.get(i).direction.DOWN;
					snakes.get(i).xDirection = 0;
					snakes.get(i).yDirection = 10;
					break;
				case 3:
					snakes.get(i).direction = snakes.get(i).direction.RIGHT;
					snakes.get(i).xDirection = 10;
					snakes.get(i).yDirection = 0;
					break;
				}
				newAIDirection(snakes.get(i));
			}
		}
		newFruit();
	}

	public void AIRoam(Snake s) {
		if (s.AITick == 0) {
			int r = rand.nextInt(20);
			if (r == 0) {
				newAIDirection(s);
				// s.moved = true;
				s.AITick++;
			}
		} else if (s.AITick == s.AITickMax) {
			s.AITick = 0;
		} else {
			s.AITick++;
		}
		AICheckAround(s);
	}

	public void AICheckAround(Snake s) {
		// Checks in front for obstacles
		for (Obstacle o : obstacles) {
			// Checks in front for obstacle
			if (s.totalSize > 0) {

				if ((s.x + s.xDirection == o.getX() && s.y + s.yDirection == o
						.getY())) {
					int r = rand.nextInt(2);
					switch (r) {
					case 0:
						// Checks to right before turning
						for (Obstacle b : obstacles) {
							if ((s.x - (s.yDirection) == b.getX())
									&& ((s.y + (s.xDirection) == b.getY()))) {
								turnLeft(s);
								s.AITick = 1;
								break;
							} else if (obstacles.indexOf(b) == obstacles.size() - 1) {
								turnRight(s);
								s.AITick = 1;
								break;
							}
						}
						break;
					case 1:
						// Checks to left before turning
						for (Obstacle b : obstacles) {
							if ((s.x + (s.yDirection) == b.getX())
									&& (s.y - (s.xDirection) == b.getY())) {
								turnRight(s);
								s.AITick = 1;
								break;
							} else if (obstacles.indexOf(b) == obstacles.size() - 1) {
								turnLeft(s);
								s.AITick = 1;
								break;
							}
						}
						break;
					}
				}
			}
		}
		for (Fruit f : fruits) {
			// Checks all spaces around it up to a distance of (6)
			s.xDirection = s.getXDirection();
			s.yDirection = s.getYDirection();
			for (int i = 1; i <= 6; i++) {
				if ((s.x + (s.xDirection * i) == f.x && s.y == f.y)
						|| (s.y + (s.yDirection * i) == f.y && s.x == f.x)) {
					s.AITick = s.AITickMax - i - 1;
				}
				if ((s.x + (s.yDirection * i) == f.x && s.y == f.y)
						|| (s.y - (s.xDirection * i) == f.y && s.x == f.x)) {
					turnLeft(s);
					s.AITick = s.AITickMax - i - 1;
				}
				if ((s.x - (s.yDirection * i) == f.x && s.y == f.y)
						|| (s.y + (s.xDirection * i) == f.y && s.x == f.x)) {
					turnRight(s);
					s.AITick = s.AITickMax - i - 1;
				} else {
					continue;
				}
			}
		}
		for (Snake q : snakes) {
			if (q != s) {
				for (int p = 0; p < q.totalSize - 1; p++) {
					if (gameMode == GameMode.BATTLE) {
						// Dodges other snake if it will die
						if (s.totalSize < q.totalSize - p) {
							if ((s.x + s.xDirection == q.snakeParts.get(p).x && s.y
									+ s.yDirection == q.snakeParts.get(p).y)) {
								int r = rand.nextInt(2);
								switch (r) {
								case 0:
									// Tries to turn right
									for (SnakePart sp : q.snakeParts) {
										if (s.x - s.yDirection == sp.x
												&& s.y + s.xDirection == sp.y
												&& s.totalSize < q.totalSize
														- q.snakeParts
																.indexOf(sp)) {
											turnLeft(s);
											break;
										} else if (sp == q.snakeParts
												.get(q.totalSize - 2)) {
											turnRight(s);
											break;
										}
									}
									s.AITick = 1;
									break;
								case 1:
									// Tries to turn left
									for (SnakePart sp : q.snakeParts) {
										if (s.x + s.yDirection == sp.x
												&& s.y - s.xDirection == sp.y
												&& s.totalSize < q.totalSize
														- q.snakeParts
																.indexOf(sp)) {
											turnRight(s);
											break;
										} else if (sp == q.snakeParts
												.get(q.totalSize - 2)) {
											turnLeft(s);
											break;
										}
									}
									s.AITick = 1;
									break;
								}
							}

						}
					} else {
						if ((s.x + s.xDirection == q.snakeParts.get(p).x && s.y
								+ s.yDirection == q.snakeParts.get(p).y)) {
							int r = rand.nextInt(2);
							switch (r) {
							case 0:
								// Tries to turn right
								for (SnakePart sp : q.snakeParts) {
									if (s.x - s.yDirection == sp.x
											&& s.y + s.xDirection == sp.y) {
										turnLeft(s);
										break;
									} else {
										turnRight(s);
										break;
									}
								}
								s.AITick = 1;
								break;
							case 1:
								// Tries to turn left
								for (SnakePart sp : q.snakeParts) {
									if (s.x + s.yDirection == sp.x
											&& s.y - s.xDirection == sp.y) {
										turnRight(s);
										break;
									} else {
										turnLeft(s);
										break;
									}
								}
								s.AITick = 1;
								break;
							}
						}

					}
				}

			} else if (q == s) {
				// Avoid self collission
				for (int p = 3; p < q.totalSize - 1; p++) {
					if ((s.x + s.xDirection == q.snakeParts.get(p).x && s.y == q.snakeParts
							.get(p).y)
							|| (s.y + s.yDirection == q.snakeParts.get(p).y && s.x == q.snakeParts
									.get(p).x)) {
						int r = rand.nextInt(2);
						switch (r) {
						case 0:
							turnRight(s);
							for (int g = 2; g < q.totalSize - 1; g++) {
								if ((s.x + s.xDirection == q.snakeParts.get(g).x && s.y == q.snakeParts
										.get(g).y)
										|| (s.y + s.yDirection == q.snakeParts
												.get(g).y && s.x == q.snakeParts
												.get(g).x)) {
									turnLeft(s);
									turnLeft(s);
									s.AITick = 1;
								}
							}
							break;
						case 1:
							turnLeft(s);
							for (int g = 2; g < q.totalSize - 1; g++) {
								if ((s.x + s.xDirection == q.snakeParts.get(g).x && s.y == q.snakeParts
										.get(g).y)
										|| (s.y + s.yDirection == q.snakeParts
												.get(g).y && s.x == q.snakeParts
												.get(g).x)) {
									turnRight(s);
									turnRight(s);
									s.AITick = 1;
								}
							}
							break;
						}
					}
				}
			}
		}

	}

	public void newAIDirection(Snake s) {
		if (s.totalSize > 0) {
			int g = rand.nextInt(2);
			switch (g) {
			case 0:
				// Checks to right before turning
				for (Obstacle b : obstacles) {
					if ((s.x - (s.yDirection) == b.getX())
							&& ((s.y + (s.xDirection) == b.getY()))) {
						turnLeft(s);
						s.AITick = 0;
						break;
					} else if (obstacles.indexOf(b) == obstacles.size() - 1) {
						turnRight(s);
						s.AITick = 0;
						break;
					}
				}
				break;
			case 1:
				// Checks to left before turning
				for (Obstacle b : obstacles) {
					if ((s.x + (s.yDirection) == b.getX())
							&& (s.y - (s.xDirection) == b.getY())) {
						turnRight(s);
						s.AITick = 1;
						break;
					} else if (obstacles.indexOf(b) == obstacles.size() - 1) {
						turnLeft(s);
						s.AITick = 1;
						break;
					}
				}
				break;
			}
		}
	}

	public static void turnRight(Snake s) {
		switch (s.direction) {
		case UP:
			s.direction = Direction.RIGHT;
			break;
		case NONE:
			s.direction = Direction.RIGHT;
			break;
		case DOWN:
			s.direction = Direction.LEFT;
			break;
		case LEFT:
			s.direction = Direction.UP;
			break;
		case RIGHT:
			s.direction = Direction.DOWN;
			break;
		}
	}

	public static void turnLeft(Snake s) {
		switch (s.direction) {
		case UP:
			s.direction = Direction.LEFT;
			break;
		case NONE:
			s.direction = Direction.LEFT;
			break;
		case DOWN:
			s.direction = Direction.RIGHT;
			break;
		case LEFT:
			s.direction = Direction.DOWN;
			break;
		case RIGHT:
			s.direction = Direction.UP;
			break;
		}
	}

	public void respawnSnake(Snake s) {
		s.dead = false;
		s.x = s.startingX;
		s.y = s.startingY;
		s.totalSize = 3;
		s.deathTimer = 0;
		s.lives--;
	}

}