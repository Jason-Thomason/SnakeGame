import java.awt.Color;
import java.awt.Graphics;


public class Wall extends Obstacle{
	
	private int x, y, width = 10, height = 10;
	
	public Wall(int x, int y) {
		this.x = x * 10;
		this.y = y * 10;
	}
	
	public void render(Graphics g){
		g.setColor(Color.pink);
		g.fill3DRect(x, y, width, height, true);
	}

	public void checkCollisions(int x, int y){
		if(this.x == x && this.y == y){
			Core.GAME_IS_RUNNING = false;
			System.out.println("Collision with Obstacle");
		}
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	
}
