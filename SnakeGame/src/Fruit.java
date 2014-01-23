import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Fruit {

	Random rand = new Random();
	
	public int x = (rand.nextInt(38)+1)*10, y = (rand.nextInt(36)+3)*10, width = 10, height = 10;
	public boolean spawned = false;
	
	public Fruit(){
	
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(spawned = true){
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		}
	}
	
	public void newPosition(){
		x = (rand.nextInt(38)+1)*10;
		y = (rand.nextInt(36)+3)*10;
	}
	
}
