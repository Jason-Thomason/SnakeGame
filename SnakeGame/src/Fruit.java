import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Fruit {

	Random rand = new Random();
	
	
	public int x = (rand.nextInt((Window.width/10)-1)+1) * 10, y = (rand.nextInt((Window.height/10) - 5) + 4) * 10, width = 10, height = 10;
	public boolean spawned = false;
	public String fruitType;
	
	public Fruit(){
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(spawned = true){
		g.setColor(Color.red);
		g.fill3DRect(x, y, width, height, true);
		}
	}
	
	public void newPosition(){
		x = (rand.nextInt((Window.width/10)-1)+1) * 10;
		y = (rand.nextInt((Window.height/10) - 5) + 4) * 10;
	}
	
}
