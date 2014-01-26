import java.awt.*;

public class Obstacle {

	private int x, y, width = 10, height = 10;
	
	public Obstacle(int x, int y){
		this.x = x*10;
		this.y = y*10;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(Color.PINK);
		g.fill3DRect(x, y, width, height, true);
	}
	
	public void checkCollisions(){
		if(this.x == World.headX && this.y == World.headY){
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
