import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HandlerClass implements KeyListener{

public static boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
public static boolean aPressed = false, dPressed = false, wPressed = false, sPressed = false;
public static boolean spacePressed = false;

	

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_LEFT && !rightPressed && !World.snakes.get(0).moved){
			leftPressed = true;
			upPressed = false;
			downPressed = false;
			World.snakes.get(0).moved = true;
		}	
		if(event.getKeyCode() == KeyEvent.VK_RIGHT && !leftPressed && !World.snakes.get(0).moved){
			rightPressed = true;
			upPressed = false;
			downPressed = false;
			World.snakes.get(0).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_UP && !downPressed && !World.snakes.get(0).moved){
			upPressed = true;
			leftPressed = false;
			rightPressed = false;
			World.snakes.get(0).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN && !upPressed && !World.snakes.get(0).moved){
			downPressed = true;
			leftPressed = false;
			rightPressed = false;
			World.snakes.get(0).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_SPACE){
			if(Core.GAME_IS_RUNNING == false || World.snakesLeft <= 1){
				Core.GAME_IS_RUNNING = false;
				leftPressed = false;
				rightPressed = false;
				upPressed = false;
				downPressed = false;
				aPressed = false;
				dPressed = false;
				wPressed = false;
				sPressed = false;
				spacePressed = true;
				World.SCORE = 0;
				//World.level = 0;
				Core.main(null);
			}
		}
		if(event.getKeyCode() == KeyEvent.VK_A && !dPressed && !World.snakes.get(1).moved){
			aPressed = true;
			wPressed = false;
			sPressed = false;
			World.snakes.get(1).moved = true;
		}	
		if(event.getKeyCode() == KeyEvent.VK_D && !aPressed && !World.snakes.get(1).moved){
			dPressed = true;
			wPressed = false;
			sPressed = false;
			World.snakes.get(1).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_W && !sPressed && !World.snakes.get(1).moved){
			wPressed = true;
			aPressed = false;
			dPressed = false;
			World.snakes.get(1).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_S && !wPressed && !World.snakes.get(1).moved){
			sPressed = true;
			aPressed = false;
			dPressed = false;
			World.snakes.get(1).moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		
	}
	
	public static void reset1(){
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
	}
	
	public static void reset2(){
		aPressed = false;
		dPressed = false;
		wPressed = false;
		sPressed = false;
	}

		public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_LEFT){
			//leftPressed = false;
		}	
		if(event.getKeyCode() == KeyEvent.VK_RIGHT){
			//rightPressed = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_UP){
			//upPressed = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN){
			//downPressed = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getleftPressed(){
		return leftPressed;
	}
	
	public boolean getrightPressed(){
		return rightPressed;
	}
	
	public boolean getupPressed(){
		return upPressed;
	}
	
	public boolean getdownPressed(){
		return downPressed;
	}

}
