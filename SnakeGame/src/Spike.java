import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Spike extends Obstacle{

	private int x, y, width = 10, height = 10;
	public String obstacleType = "Spike";
	
	Image spikeBall = new ImageIcon("SpikeBall.png").getImage();
	
	public Spike(int x, int y){
		this.x = x * 10;
		this.y = y * 10;
	}
	
	public void render(Graphics g){
		g.drawImage(spikeBall, this.x, this.y, null);
	}
	
	public void checkCollisions(int x, int y){
		if(this.x == x && this.y == y){
			for(int i = 0; i < World.snakes.size(); i++){
				if(this.x == World.snakes.get(i).snakeParts.get(0).getX() && this.y == World.snakes.get(i).snakeParts.get(0).getY()){
					for(int n = 0; n < 4; n++){
						World.snakes.get(i).removePart();
					}
				}
			}
		}
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

}
