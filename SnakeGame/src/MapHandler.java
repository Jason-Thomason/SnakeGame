import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MapHandler {

	public static int startingX, startingY;
	
	BufferedReader reader;
	ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	
	
	public void loadMap(String filename) throws IOException{
		
		ArrayList lines = new ArrayList();
		int width = 0;
	    int height = 0;
		
		
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
	        for (int j = 0; j < 39; j++) {
	            String line = (String) lines.get(j);
	            for (int i = 0; i < width; i++) {
	                if (i < line.length()) {
	                    char ch = line.charAt(i);
	                    if(ch == '1'){
	                    	startingX = i*10;
	                    	startingY = j*10;
	                    }
	                    if(ch == '2'){
	                    	obstacles.add(new Obstacle(i, j ));
	                    	//System.out.println("Obstacle Created at " + i + " " + j);
	                    }
	                }

	            }
	        }
	}
	
	
	public void tick(){
		for(int i = 0; i < obstacles.size(); i++){
			obstacles.get(i).checkCollisions();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < obstacles.size(); i++){
			obstacles.get(i).render(g);
		}
	}
	
	public boolean checkFruit(int x, int y){
		boolean overlap = false;
		for(Obstacle ob: obstacles){
			if(ob.getX() == x && ob.getY() == y){
				overlap = true;
				break;
			}else{
				overlap = false;
			}
		}
		return overlap;
	}
	
}
