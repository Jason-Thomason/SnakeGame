import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class World {

	public static int xDirection, yDirection;
	
	ArrayList<Snake> snakeParts = new ArrayList<Snake>();
	ArrayList<Fruit> fruits = new ArrayList<Fruit>();
	
	Background background = new Background();
	Fruit fruit = new Fruit();
	
	
	public World(){
		System.out.println("World Created");
		snakeParts.add(new Snake(Color.blue));
		snakeParts.add(new Snake(200, 310));
		snakeParts.add(new Snake(200, 320));
		newFruit();
	}
	
	
	public void tick(){
		background.tick();
		manageSnake();
		checkCollisions();
		if(fruit.spawned == false){
			newFruit();
		}
		System.out.println(snakeParts.get(0).getY());
		
		
	}
	
	public void render(Graphics g){
		background.render(g);
		for(int i = 0; i < snakeParts.size(); i++){
			snakeParts.get(i).render(g);
		}
		fruit.render(g);	
		
		
	}
	
	public void manageSnake(){
		
		for(int i = snakeParts.size()-1; i >0 ; i--){
			snakeParts.get(i).setX(snakeParts.get(i-1).getX());
			snakeParts.get(i).setY(snakeParts.get(i-1).getY());
		}
		
		snakeParts.get(0).setX(snakeParts.get(0).getX() + xDirection);
		snakeParts.get(0).setY(snakeParts.get(0).getY() +yDirection);
		
		setDirection();
	}
	
	public void setDirection(){
			if(HandlerClass.upPressed){
				yDirection = -10;
				xDirection = 0;
			}
			if(HandlerClass.downPressed){
				yDirection = 10;
				xDirection = 0;
			}
			if(HandlerClass.leftPressed){
				yDirection = 0;
				xDirection = -10;
			}
			if(HandlerClass.rightPressed){
				yDirection = 0;
				xDirection = 10;
			}
			
		}

	public void newFruit(){
		
		if(fruit.spawned == false){
			if(fruit.x == snakeParts.get(0).getX() || fruit.y == snakeParts.get(0).getY()){
				fruit.newPosition();
			}for(Snake s: snakeParts){
				 if(fruit.x == s.getX() && fruit.y == s.getY()){
						fruit.newPosition();
						newFruit();
					}else{
						fruit.spawned = true;
				}
			}
		}
	}
	
	public void checkCollisions(){
		if(snakeParts.get(0).getX() == fruit.x && snakeParts.get(0).getY() == fruit.y){
			snakeParts.add(new Snake());
			snakeParts.add(new Snake());
			snakeParts.add(new Snake());
			fruit.spawned = false;
		}
		if(snakeParts.get(0).getX() < 0 || snakeParts.get(0).getX() > 390 || snakeParts.get(0).getY() < 10 || snakeParts.get(0).getY() > 390){
			Core.GAME_IS_RUNNING = false;
			System.out.println("Collision with edge");
		}
		for(int i = 2; i < snakeParts.size(); i++){
			if(snakeParts.get(0).getX() == snakeParts.get(i).getX() && snakeParts.get(0).getY() == snakeParts.get(i).getY() && snakeParts.size()>3){
				Core.GAME_IS_RUNNING = false;
				System.out.println("Collision with self");
			}
		}
	}
}
