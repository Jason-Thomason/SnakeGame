import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Snake {

	private int x, y, xDirection, yDirection; 
	public int size = 0;
	private static int width = 10, height = 10;
	private Color color = Color.green;
	
	
	public boolean snake1Moved = false, snake2Moved = false, dead = false;
	
	ArrayList<Snake> snakeParts = new ArrayList<Snake>();
	
	public Snake(int x, int y){
		size = 1;
		this.x = x;
		this.y = y;
	}
	
	public Snake(){
		
	}
	
	
	public Snake(Color c, int x, int y){
		size = 1;
		color = c;
		this.x = x;
		this.y = y;
		snakeParts.add(this);
		addPart();
		addPart();
	}
	
	public void tick(){
		//Makes sure that the the snake is the right length
		if(snakeParts.size() < this.size){
			addPart();
		}else if(snakeParts.size() > this.size){
			removePart();
		}
		
		//Controls the snake-like flow
		for(int i = snakeParts.size()-1; i >0 ; i--){
			snakeParts.get(i).x = snakeParts.get(i-1).x;
			snakeParts.get(i).y = snakeParts.get(i-1).y;
		}
		
		//Updates the head's position
		try{
			snakeParts.get(0).x += xDirection;
			snakeParts.get(0).y += yDirection;
		}catch(Exception e){
			dead = true;
		}
		
	}

	
	public void render(Graphics g){
		for(Snake s: snakeParts){
		g.setColor(color);
		g.fillOval(s.x, s.y, width, height);
		}
	}
	
	public void removePart(){
		//Removes the last part of the snake
		if(snakeParts.size() > 1){
			snakeParts.remove(snakeParts.size() - 1);
			size--;
		}
	}
	
	public void addPart(){
		//Adds a new part to the snake
		snakeParts.add(new Snake());
		size++;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setXDirection(int x){
		this.xDirection = x;
	}
	
	public void setYDirection(int y){
		this.yDirection = y;
	}
	
}
