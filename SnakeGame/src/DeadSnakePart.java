import java.awt.Color;
import java.awt.Graphics;

public class DeadSnakePart extends Obstacle {

	private int x, y, width = 10, height = 10;
	private Color color;

	public DeadSnakePart(SnakePart sp) {
		this.x = sp.x;
		this.y = sp.y;
		this.color = sp.color;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void checkCollisions(Snake s) {
		// Checks for collision
		if (this.x == s.x && this.y == s.y) {
			for (SnakePart sp : s.snakeParts) {
				World.obstacles.add(new DeadSnakePart(sp));
			}
			s.dead = true;
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
