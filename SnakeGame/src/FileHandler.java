import java.io.*;
import java.util.*;
import java.lang.*;



public class FileHandler {

	Formatter formatter;
	Scanner scanner;
	File file;
	
	public void openFile(){
		try{
			file = new File("SnakeGameData.txt");
			file.setReadOnly();
			file.setWritable(true);
			System.out.println("File opened");
			
			
			if(!file.exists()) formatter = new Formatter(file);
			
			
			scanner = new Scanner(file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void readFile(){
		try{
		while(scanner.hasNext()){
			//System.out.println(scanner.next("HIGHSCORE"));
			String line = scanner.nextLine();
			System.out.println("Line: " + line);
			if(line.contains("HIGHSCORE")) {
				System.out.println("Highscore found.");
				int number = Integer.valueOf(line.substring(line.indexOf(' ') + 1));
				System.out.println("Highscore = " + number);
				World.HIGHSCORE = number;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeFile(){
		//System.out.println("Writing HighScore");
		try {
			formatter = new Formatter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		formatter.format("HIGHSCORE %s", World.HIGHSCORE);
		//System.out.println("HighScore of " + World.HIGHSCORE + " written");
		file.setWritable(false);
		formatter.close();
	}
	
	public void closeFile(){
		file.setWritable(false);
		formatter.close();
		scanner.close();
	}
	
}
