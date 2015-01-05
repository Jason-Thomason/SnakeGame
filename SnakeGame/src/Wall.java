import java.awt.Color;
import java.awt.Graphics;

public class Wall extends Obstacle {

	private int x, y, width = 10, height = 10;

	public Wall(int x, int y) {
		this.x = x * 10;
		this.y = y * 10;
	}

	public void render(Graphics g) {
		g.setColor(Color.pink);
		g.fill3DRect(x, y, width, height, true);
	}

	public void checkCollisions(Snake s) {
		// Checks for collision
		if (this.x == s.x && this.y == s.y) {
			// Moves head back one space
			if (s.snakeParts.size() > 0) {
				s.x = s.snakeParts.get(0).x;
				s.y = s.snakeParts.get(0).y;
				// Moves each body part back one space
				for (int h = 0; h < s.snakeParts.size() - 1; h++) {
					if (s.snakeParts.size() > 1) {
						s.snakeParts.get(h).setX(s.snakeParts.get(h + 1).x);
						s.snakeParts.get(h).setY(s.snakeParts.get(h + 1).y);
					}

				}
			}
			s.removePart();
		}

	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
