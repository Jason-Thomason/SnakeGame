import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Spike extends Obstacle {

	private int x, y, width = 10, height = 10;
	public String obstacleType = "Spike";

	Image spikeBall = new ImageIcon("SpikeBall.png").getImage();

	public Spike(int x, int y) {
		this.x = x * 10;
		this.y = y * 10;
	}

	public void render(Graphics g) {
		g.drawImage(spikeBall, this.x, this.y, null);
	}

	public void checkCollisions(Snake s) {
		// Checks for collision
		if (this.x == s.x && this.y == s.y) {
			int z = (int) Math.floor(s.totalSize / 2);
			for (int n = 0; n < z; n++){
				s.removePart();
			}
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
