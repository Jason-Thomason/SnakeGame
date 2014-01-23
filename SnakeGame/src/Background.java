import java.awt.Color;
import java.awt.Graphics;


public class Background {
private int rColor = 0, gColor = 0, bColor = 0;
	
	public Background(){
		
	}
	
	
	
	public void tick(){
		//updateColor();
	}
	
	public void render(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, Window.width, Window.height);
	}
	
	public void updateColor(){
		
	}
}
