import java.awt.Color;
import java.awt.Graphics;


public class Snake {

	private int x, y;
	private static int width = 10, height = 10;
	private Color color = Color.green;
	
	public Snake(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Snake(){
		
	}
	
	public Snake(Color c, int x, int y){
		color = c;
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
}
