import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	Font font = new Font("Arial", Font.BOLD, 15);

	public void tick() {
		if (World.SCORE > World.HIGHSCORE)
			World.HIGHSCORE = World.SCORE;
	}

	public void render(Graphics g) {
		if (World.gameMode == World.GameMode.CLASSIC) {
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("SCORE: " + World.SCORE, 15, 45);
			g.drawString("HIGHSCORE: " + World.HIGHSCORE, 15, 65);
		} else if (World.gameMode != World.GameMode.CLASSIC) {
			for (int i = 0; i < World.totalSnakes; i++) {
				g.setFont(font);
				g.setColor(World.colors[i]);
				if (World.snakes.get(i).lives > 0) {
					g.drawString(
							"" + World.snakes.get(i).lives,
							(Window.width / 2)
									+ (40 * ((int) Math.ceil((double) i / 2)) * ((int) Math
											.pow(-1, i))), 50);
				}
				if (World.snakes.get(i).lives == 0 && World.snakes.get(i).dead) {
					g.drawString(
							"Dead",
							(Window.width / 2)
									+ (40 * ((int) Math.ceil((double) i / 2)) * ((int) Math
											.pow(-1, i))) - 20, 50);
				}
				if (World.snakes.get(i).dead && World.snakes.get(i).lives > 0) {
					g.drawString(
							"" + (5 - (World.snakes.get(i).deathTimer / 10)),
							(Window.width / 2)
									+ (40 * ((int) Math.ceil((double) i / 2)) * ((int) Math
											.pow(-1, i))), 70);
				}
			}
		}

	}

}
