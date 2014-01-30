import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake {

	private int x, y, xDirection, yDirection;
	public int size = 0, lives = 2, deathTimer = 0;
	public int startingX, startingY;
	private static int width = 10, height = 10;
	private Color color;

	public boolean moved = false, dead = false;

	ArrayList<Snake> snakeParts = new ArrayList<Snake>();

	public Snake(int x, int y) {
		size = 1;
		this.x = x;
		this.y = y;
	}

	public Snake() {
		
	}

	public Snake(Color c, int x, int y) {
		snakeParts.add(this);
		color = c;
		this.startingX = x;
		this.startingY = y;
		this.x = x;
		this.y = y;
		this.size = 3;
		this.snakeParts.add(new Snake());
		this.snakeParts.add(new Snake());
	}

	public void tick() {
		// Makes sure that the the snake is the right length
		if (snakeParts.size() < this.size) {
			snakeParts.add(new Snake());
		} else if (snakeParts.size() > this.size) {
			snakeParts.remove(snakeParts.size() - 1);
		}

		// Controls the snake-like flow
		for (int i = snakeParts.size() - 1; i > 0; i--) {
			snakeParts.get(i).x = snakeParts.get(i - 1).x;
			snakeParts.get(i).y = snakeParts.get(i - 1).y;
		}

		// Updates the head's position
		try {
			snakeParts.get(0).x += xDirection;
			snakeParts.get(0).y += yDirection;
		} catch (Exception e) {
			dead = true;
		}

	}

	public void render(Graphics g) {
		for (Snake s : snakeParts) {
			g.setColor(color);
			g.fillOval(s.x, s.y, width, height);
		}
	}

	public void removePart() {
		// Removes the last part of the snake
		if (snakeParts.size() > 1) {
			snakeParts.remove(snakeParts.size() - 1);
			size--;
		}else if(snakeParts.size() == 1){
			dead = true;
			snakeParts.remove(snakeParts.size() - 1);
			size--;
		}
	}

	public void addPart() {
		// Adds a new part to the snake
		snakeParts.add(new Snake());
		size++;
	}

	public void respawn() {
		dead = false;
		if(snakeParts.size() == 0 && lives > 0){
			if(lives > 0){
			snakeParts.add(new Snake(this.color, this.startingX, this.startingY));
			size++;
			}
			deathTimer = 0;
			lives--;
			
		}
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

}
