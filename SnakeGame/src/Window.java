import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;


public class Window extends JFrame{

	public static int width = 400, height = 400;
	
	HandlerClass handler = new HandlerClass();
	
	public Window(){
		super("Snake Game");
		setup();
	}
	
	public void setup(){
	
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.addKeyListener(handler);
		this.setVisible(true);
		
	}
	
	public void setSize(){
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
	}
	
}
