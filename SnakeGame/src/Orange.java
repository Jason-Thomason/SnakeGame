import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Orange extends Fruit{

	Image Orange = new ImageIcon("Orange.png").getImage();
	
	private static int value = 6;
	
	public Orange(){
		fruitType = "Orange";
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(spawned = true){
		g.setColor(Color.ORANGE);
		//g.fill3DRect(x, y, width, height, true);
		g.drawImage(Orange, this.x, this.y, width, height, null);
		}
	}
	
	public int getValue(){
		return value;
	}
	
}
