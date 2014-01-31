import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapHandler {

	public static int snake1StartingX, snake1StartingY, snake2StartingX,
			snake2StartingY;
	private int width, height;

	BufferedReader reader;

	public void loadMap(String filename) throws IOException {

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
					if (ch == '1') {
						World.startingPositions[0][0] = i * 10;
						World.startingPositions[0][1] = j * 10;
						System.out.println("Snake 1 at " + i + " and " + j);
					}
					if (ch == '2') {
						World.startingPositions[1][0] = i * 10;
						World.startingPositions[1][1] = j * 10;
					}
					if (ch == '3') {
						World.obstacles.add(new Wall(i, j));
					}
					if (ch == '4') {
						World.obstacles.add(new Spike(i, j));
					}
					if (ch == '5') {
						World.startingPositions[2][0] = i * 10;
						World.startingPositions[2][1] = j * 10;
					}
					if (ch == '6') {
						World.startingPositions[3][0] = i * 10;
						World.startingPositions[3][1] = j * 10;
					}
					if (ch == '7') {
						World.startingPositions[4][0] = i * 10;
						World.startingPositions[4][1] = j * 10;
					}
					if (ch == '8') {
						World.startingPositions[5][0] = i * 10;
						World.startingPositions[5][1] = j * 10;
					}
				}
			}
			Window.width = this.width * 10;
			Window.height = this.height * 10;
		}
	}

}
