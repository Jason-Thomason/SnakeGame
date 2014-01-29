import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MapHandler {

	public static int snake1StartingX, snake1StartingY, snake2StartingX, snake2StartingY;
	private int width, height;
	
	BufferedReader reader;
	
	
	
	
	public void loadMap(String filename) throws IOException{
		
		ArrayList lines = new ArrayList();
		width = 0;
	    height = 0;
		
		
		reader = new BufferedReader(new FileReader(filename));
		 while (true) {
	            String line = reader.readLine();
	            // no more lines to read
	            if (line == null) {
	                reader.close();
	                break;
	            }
	            if (!line.startsWith("!")) {
	                lines.add(line);
	                width = Math.max(width, line.length());

	            }
	        }
	        height = lines.size();
	        for (int j = 0; j < height; j++) {
	            String line = (String) lines.get(j);
	            for (int i = 0; i < width; i++) {
	                if (i < line.length()) {
	                    char ch = line.charAt(i);
	                    if(ch == '1'){
	                    	snake1StartingX = i*10;
	                    	snake1StartingY = j*10;
	                    	System.out.println("Snake 1 at " + i + " and " + j);
	                    }
	                    if(ch == '2'){
	                    	snake2StartingX = i*10;
	                    	snake2StartingY = j*10;
	                    }
	                    if(ch == '3'){
	                    	World.obstacles.add(new Wall(i, j));
	                    }
	                    if(ch == '4'){
	                    	World.obstacles.add(new Spike(i, j));
	                    }
	                }
	            }
		        Window.width = this.width * 10;
		        Window.height = this.height * 10;
	        }
	}
	
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
	}
	
}
