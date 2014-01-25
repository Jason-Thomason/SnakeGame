import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
	
	Font font = new Font("Arial", Font.BOLD, 15);
	
	public void tick(){
		if(World.SCORE > World.HIGHSCORE)
			World.HIGHSCORE = World.SCORE;
	}
	
	public void render(Graphics g){
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("SCORE: " + World.SCORE, 15, 40);
		g.drawString("HIGHSCORE: " + World.HIGHSCORE, 15, 60);
	}
	
}
