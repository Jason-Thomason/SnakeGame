import java.awt.*;

public class Obstacle {

	private int x, y, width = 10, height = 10;
	
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(Color.PINK);
		g.fill3DRect(x, y, width, height, true);
	}
	
	public void checkCollisions(int x, int y){

	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
		
}
