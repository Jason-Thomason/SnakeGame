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
		if(World.players == 1){
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("SCORE: " + World.SCORE, 15, 45);
			g.drawString("HIGHSCORE: " + World.HIGHSCORE, 15, 65);
		}
		if(World.players == 2){
			g.setFont(font);
			g.setColor(Color.blue);
			g.drawString("" + World.snakes.get(0).size, 15, 50);
			g.setColor(Color.red);
			g.drawString("" + World.snakes.get(1).size, Window.width - 20, 50);
		}
	}
	
}
