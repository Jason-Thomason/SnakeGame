import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

	Random rand = new Random();

	public int x, y, xDirection, yDirection;
	public int size = 0, lives = 2, deathTimer = 0, AIMove = 0, AITick = 0,
			AITickMax;
	public int startingX, startingY;
	private static int width = 10, height = 10;
	public Color color;

	public boolean moved = false, dead = false, human = false;

	ArrayList<SnakePart> snakeParts = new ArrayList<SnakePart>();


	public static enum SnakeMode {
		ROAM
	}

	public Direction direction = Direction.NONE;
	SnakeMode mode = SnakeMode.ROAM;

	public Snake() {

	}

	public Snake(Color c) {
		this.color = c;
	}

	public Snake(Color c, int x, int y) {
		color = c;
		this.startingX = x;
		this.startingY = y;
		this.x = x;
		this.y = y;
		this.size = 3;
		// Sets random TickMax
		if (!this.human) {
			this.AITickMax = rand.nextInt(5) + 3;
		}
		if (World.gameMode == World.GameMode.CLASSIC
				|| World.gameMode == World.GameMode.TRON_INFINITE) {
			lives = 0;
		} else if (World.gameMode == World.GameMode.BATTLE) {
			lives = 2;
		} else {
			lives = 3;
		}
		tick();
	}

	public void tick() {
		// Kill snake if size reaches 0 or adjusts size if dead
		if (size == 0 || dead == true) {
			dead = true;
			size = 0;
		}
		// Makes sure that the the snake is the right length
		while (snakeParts.size() < this.size - 1) {
			snakeParts.add(new SnakePart(color));
		}
		while (snakeParts.size() > this.size - 1 && snakeParts.size() > 0) {
			snakeParts.remove(snakeParts.size() - 1);
		}

		// Controls the snake-like flow
		if (!direction.equals(direction.NONE)) {
			for (int i = snakeParts.size() - 1; i > 0; i--) {
				snakeParts.get(i).x = snakeParts.get(i - 1).x;
				snakeParts.get(i).y = snakeParts.get(i - 1).y;
			}
			if (snakeParts.size() > 0) {
				snakeParts.get(0).x = this.x;
				snakeParts.get(0).y = this.y;
			}
		}


		// Updates the head's position and x/y variables
		try {
			x += this.direction.getXDirection(direction);
			y += this.direction.getYDirection(direction);
			if (World.gameMode == World.GameMode.TRON_INFINITE) {
				this.snakeParts.add(new SnakePart(color));
				this.size++;
			}

		} catch (Exception e) {
			dead = true;
		}
		setDirections();
		for (SnakePart s: snakeParts){
			s.tick();
		}
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
		for (SnakePart s : snakeParts) {
			s.render(g);
		}
	}

	public void removePart() {
		// Removes the last part of the snake
		if (size > 1) {
			snakeParts.remove(snakeParts.size() - 1);
			size--;
		} else if (size == 1 && !dead) {
			dead = true;
			size = 0;
		}
	}

	public void addPart(Color c) {
		// Adds a new part to the snake
		snakeParts.add(new SnakePart(c));
		size++;
	}
	
	public void setDirections(){
		// Determines direction of each snakePart
				for (int i = 0; i < snakeParts.size(); i++) {
					SnakePart s = snakeParts.get(i);
					SnakePart sBehind = null;
					SnakePart sFront = null;
					if (snakeParts.size() > i + 1) {
						sBehind = snakeParts.get(i + 1);
					}
					if (i > 0) {
						sFront = snakeParts.get(i - 1);
					}
					// Check for very first snakePart
					if (i == 0 && sBehind != null) {
						if (y < s.y) {
							if (sBehind.y > s.y) {
								// if head is above current part
								s.setDirection(0);
							} else if (sBehind.y == s.y) {
								if (sBehind.x < s.x) {
									s.setDirection(5);
								} else if (sBehind.x > s.x) {
									s.setDirection(4);
								}
							}
						} else if (y > s.y) {
							// if head is below current part
							if (sBehind.y < s.y) {
								s.setDirection(0);
							} else if (sBehind.y == s.y) {
								if (sBehind.x < s.x) {
									s.setDirection(3);
								} else if (sBehind.x > s.x) {
									s.setDirection(2);
								}
							}
						} else if (y == s.y) {
							// if head is level to current part
							if (sBehind.y == s.y) {
								s.setDirection(1);
							} else if (sBehind.y > s.y) {
								if (s.x < x){
									s.setDirection(2);
								}else if (s.x > x){
									s.setDirection(3);
								}
							} else if (sBehind.y < s.y){
								if (s.x < x){
									s.setDirection(4);
								}else if (s.x > x){
									s.setDirection(5);
								}
							}
						}
					}
					// Checks for the rest of the tail
					if (i > 0){
						if (sBehind != null){
							if (sFront.y < s.y) {
								if (sBehind.y > s.y) {
									// if head is above current part
									s.setDirection(0);
								} else if (sBehind.y == s.y) {
									if (sBehind.x < s.x) {
										s.setDirection(5);
									} else if (sBehind.x > s.x) {
										s.setDirection(4);
									}
								}
							} else if (sFront.y > s.y) {
								// if head is below current part
								if (sBehind.y < s.y) {
									s.setDirection(0);
								} else if (sBehind.y == s.y) {
									if (sBehind.x < s.x) {
										s.setDirection(3);
									} else if (sBehind.x > s.x) {
										s.setDirection(2);
									}
								}
							} else if (sFront.y == s.y) {
								// if head is level to current part
								if (sBehind.y == s.y) {
									s.setDirection(1);
								} else if (sBehind.y > s.y) {
									if (s.x < sFront.x){
										s.setDirection(2);
									}else if (s.x > sFront.x){
										s.setDirection(3);
									}
								} else if (sBehind.y < s.y){
									if (s.x < sFront.x){
										s.setDirection(4);
									}else if (s.x > sFront.x){
										s.setDirection(5);
									}
								}
							}
							//Checks for last part
						}else if(i == snakeParts.size() - 1){
							if(sFront.x == s.x){
								s.setDirection(0);
							}else{
								s.setDirection(1);
							}
							
						}
						
					}
				}
	}

	public void addPart() {
		// Adds a new part to the snake
		snakeParts.add(new SnakePart(this.color));
		size++;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXDirection(int x) {
		this.xDirection = x;
	}

	public void setYDirection(int y) {
		this.yDirection = y;
	}

	public int getXDirection() {
		return direction.getXDirection(direction);
		}

	public int getYDirection() {
		return direction.getYDirection(direction);
	}
}
