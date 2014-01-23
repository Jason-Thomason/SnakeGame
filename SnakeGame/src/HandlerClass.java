import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HandlerClass implements KeyListener{

public static boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
	
	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_LEFT && !rightPressed){
			leftPressed = true;
			upPressed = false;
			downPressed = false;
		}	
		if(event.getKeyCode() == KeyEvent.VK_RIGHT && !leftPressed){
			rightPressed = true;
			upPressed = false;
			downPressed = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_UP && !downPressed){
			upPressed = true;
			leftPressed = false;
			rightPressed = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN && !upPressed){
			downPressed = true;
			leftPressed = false;
			rightPressed = false;
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
