import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Fruit {

	Random rand = new Random();
	Image apple = new ImageIcon("Cartoon Apple.png").getImage();
	
	public int x = (rand.nextInt(38)+1)*10, y = (rand.nextInt(36)+3)*10, width = 10, height = 10;
	public boolean spawned = false;
	
	public Fruit(){
	
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(spawned = true){
		//g.setColor(Color.red);
		//g.fill3DRect(x, y, width, height, true);
			g.drawImage(apple, this.x, this.y, 10, 10, null);
		}
	}
	
	public void newPosition(){
		x = (rand.nextInt(38)+1)*10;
		y = (rand.nextInt(36)+3)*10;
	}
	
}
