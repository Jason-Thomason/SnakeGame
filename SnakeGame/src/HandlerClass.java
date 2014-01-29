import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HandlerClass implements KeyListener{

public static boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
public static boolean aPressed = false, dPressed = false, wPressed = false, sPressed = false;
public static boolean spacePressed = false;

	

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_LEFT && !rightPressed && !World.snake1Moved){
			leftPressed = true;
			upPressed = false;
			downPressed = false;
			World.snake1Moved = true;
		}	
		if(event.getKeyCode() == KeyEvent.VK_RIGHT && !leftPressed && !World.snake1Moved){
			rightPressed = true;
			upPressed = false;
			downPressed = false;
			World.snake1Moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_UP && !downPressed && !World.snake1Moved){
			upPressed = true;
			leftPressed = false;
			rightPressed = false;
			World.snake1Moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN && !upPressed && !World.snake1Moved){
			downPressed = true;
			leftPressed = false;
			rightPressed = false;
			World.snake1Moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_SPACE){
			if(Core.GAME_IS_RUNNING == false){
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
		if(event.getKeyCode() == KeyEvent.VK_A && !dPressed && !World.snake2Moved){
			aPressed = true;
			wPressed = false;
			sPressed = false;
			World.snake2Moved = true;
		}	
		if(event.getKeyCode() == KeyEvent.VK_D && !aPressed && !World.snake2Moved){
			dPressed = true;
			wPressed = false;
			sPressed = false;
			World.snake2Moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_W && !sPressed && !World.snake2Moved){
			wPressed = true;
			aPressed = false;
			dPressed = false;
			World.snake2Moved = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_S && !wPressed && !World.snake2Moved){
			sPressed = true;
			aPressed = false;
			dPressed = false;
			World.snake2Moved = true;
		}
		
	}

	@Override
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
