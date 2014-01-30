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

	public void checkCollisions(int x, int y) {
		if (this.x == x && this.y == y) {
			for (int i = 0; i < World.snakes.size(); i++) {
				if (World.snakes.get(i).snakeParts.size() > 0) {
					if (this.x == World.snakes.get(i).snakeParts.get(0).getX()
							&& this.y == World.snakes.get(i).snakeParts.get(0)
									.getY()) {
						for (int n = 0; n < 1 ; n++) {
								for(int h = 0; h < World.snakes.get(i).size - 1; h++){
									//Moves each part back one space
									World.snakes.get(i).snakeParts.get(h).setX(World.snakes.get(i).snakeParts.get(h+1).getX());
									World.snakes.get(i).snakeParts.get(h).setY(World.snakes.get(i).snakeParts.get(h+1).getY());
								}
								World.snakes.get(i).removePart();
						}
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
