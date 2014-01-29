import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class World {

	public static int SCORE, HIGHSCORE = 0, level = 1, players = 2, winner = 0;
	public static boolean snake1Moved, snake2Moved;
	
	static ArrayList<Snake> snakes;
	static ArrayList<Fruit> fruits = new ArrayList<Fruit>();
	static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	Background background = new Background();
	HUD HUD = new HUD();
	FileHandler fileHandler = new FileHandler();
	MapHandler mh = new MapHandler();
	Random rand = new Random();
	static Snake snake1;
	static Snake snake2;
	
	
	
	
	public World(){
		nextLevel();
	}
	
	
	public void tick(){
		
		background.tick();
		checkCollisions();
		for(Snake s : snakes){
			if(s.dead == false){
				s.tick();
			}
		}
		if(fruits.size() < snakes.size() + 6){
			newFruit();
		}
		if(fruits.size() > snakes.size() + 6){
			removeFruit();
		}
		mh.tick();
		HUD.tick();
		setDirection();
		if(!Core.GAME_IS_RUNNING && players == 1){
			fileHandler.writeFile();
			fileHandler.closeFile();
		}
		
		
		
	}
	
	public void render(Graphics g){
		background.render(g);
		for(int i = 0; i < obstacles.size(); i++){
			obstacles.get(i).render(g);
		}
		for(int i = 0; i < snakes.size(); i++){
			if(snakes.get(i).dead == false){
				snakes.get(i).render(g);
			}
		}
		for(int i = 0; i < fruits.size(); i++){
			if(fruits.get(i).spawned){
				fruits.get(i).render(g);
			}
		}
		mh.render(g);	
		HUD.render(g);
		
		snake1Moved = false;
		snake2Moved = false;
		
	}
	
	public void setDirection(){
			if(HandlerClass.upPressed){
				snakes.get(0).setYDirection(-10);
				snakes.get(0).setXDirection(0);
			}
			if(HandlerClass.downPressed){
				snakes.get(0).setYDirection(10);
				snakes.get(0).setXDirection(0);
			}
			if(HandlerClass.leftPressed){
				snakes.get(0).setYDirection(0);
				snakes.get(0).setXDirection(-10);
			}
			if(HandlerClass.rightPressed){
				snakes.get(0).setYDirection(0);
				snakes.get(0).setXDirection(10);
			}
			if(!HandlerClass.upPressed && !HandlerClass.downPressed && !HandlerClass.leftPressed && !HandlerClass.rightPressed){
				snakes.get(0).setYDirection(0);
				snakes.get(0).setXDirection(0);
			}
			if(players == 2){
				if(HandlerClass.wPressed){
					snakes.get(1).setYDirection(-10);
					snakes.get(1).setXDirection(0);
				}
				if(HandlerClass.sPressed){
					snakes.get(1).setYDirection(10);
					snakes.get(1).setXDirection(0);
				}
				if(HandlerClass.aPressed){
					snakes.get(1).setYDirection(0);
					snakes.get(1).setXDirection(-10);
				}
				if(HandlerClass.dPressed){
					snakes.get(1).setYDirection(0);
					snakes.get(1).setXDirection(10);
				}
				if(!HandlerClass.wPressed && !HandlerClass.sPressed && !HandlerClass.aPressed && !HandlerClass.dPressed){
					snakes.get(1).setYDirection(0);
					snakes.get(1).setXDirection(0);
				}
			}
		}

	public void newFruit(){
		int r = rand.nextInt(10);
		Fruit fruit;
		
		if(r == 0){
			fruit = new Orange();
		}else{
			fruit = new Apple();
		}
		
		if(fruit.spawned == false){
			for(Obstacle o : obstacles){
				if(fruit.x == o.getX() && fruit.y == o.getY()){
				fruit.newPosition();
				newFruit();
				}
			}
			for(Snake s: snakes){
				 if(fruit.x == s.getX() && fruit.y == s.getY()){
						fruit.newPosition();
						newFruit();
					}else{
						fruit.spawned = true;
						fruits.add(fruit);		
				}
			}
		}
	}
	
	public void removeFruit(){
		fruits.remove(fruits.get(fruits.size() - 1));
	}
	
	public void checkCollisions(){
		for(int s = 0; s < snakes.size(); s++){
			//Check obstacles
			for(Obstacle o : obstacles){
				o.checkCollisions(snakes.get(s).snakeParts.get(0).getX(), snakes.get(s).snakeParts.get(0).getY());
			}
			//Check fruit
			for(int i = 0; i < fruits.size(); i++){
				if(snakes.get(s).getX() == fruits.get(i).x && snakes.get(s).getY() == fruits.get(i).y){
					if(fruits.get(i).fruitType.equals("Apple")){
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						fruits.get(i).spawned = false;
						fruits.remove(i);
						if(players == 1){
							SCORE += snakes.get(s).size;
						}
					}else if(fruits.get(i).fruitType.equals("Orange")){
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						snakes.get(s).addPart();
						fruits.get(i).spawned = false;
						fruits.remove(i);
						if(players == 1){
							SCORE += snakes.get(s).size;
						}
					}
				}
			}
			if(players > 1){
				for(int q = 0; q < snakes.size(); q++){
					if(q != s){
						for(int p = 0; p < snakes.get(q).size; p++){
							if(snakes.get(s).getX() == snakes.get(q).snakeParts.get(p).getX() && snakes.get(s).getY() == snakes.get(q).snakeParts.get(p).getY()){
								if(snakes.get(s).size >= snakes.get(q).size - p){
									for(int n = 0; n < snakes.get(q).size - p; n++){
										snakes.get(q).removePart();
										snakes.get(s).addPart();
									}
								}else if(snakes.get(s).size < snakes.get(q).size - p){
									if(snakes.get(s).size == 1){
										snakes.get(s).removePart();
									}else{
										for(int u = 0; u < Math.floor(snakes.get(s).size / 2); u++){
											snakes.get(s).removePart();
									}
								}
							}
						}
					}
					}
				}
			}
			for(int p = 3; p < snakes.get(s).size; p ++){
				if( snakes.get(s).snakeParts.get(0).getX() == snakes.get(s).snakeParts.get(p).getX() &&  snakes.get(s).snakeParts.get(0).getY() == snakes.get(s).snakeParts.get(p).getY()){
					for(int n = 0; n < snakes.get(s).size - p; n++){
						snakes.get(s).removePart();
					}
				}
			}
		}
	}

	public void nextLevel(){
			
			//level++;
			try {
				mh.loadMap("Map" + level + ".txt");
				System.out.println("Map " + level + " loaded");
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			fileHandler.openFile();
			fileHandler.readFile();
			
			snakes = new ArrayList<Snake>();		
			snakes.add(new Snake(Color.BLUE, mh.snake1StartingX, mh.snake1StartingY));
			if(players == 2){ 
				snakes.add(new Snake(Color.RED, mh.snake2StartingX, mh.snake2StartingY));
			}
		newFruit();
	}
	
	
}
