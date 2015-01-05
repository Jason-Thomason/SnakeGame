import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SnakePart {

	public int x, y;
	private static int width = 10, height = 10;
	public Color color;
	public Image image0, image1, image2, image3, image4, image5, currentImage;

	private int direction;

	// 0 = Vertical, 1 = Horizontal, 2 = UpLeft, 3 = UpRight, 4 = DownLeft, 5 =
	// DownRight

	public SnakePart(Color c) {
		this.color = c;
		setImages();
	}

	public void tick() {
		setCurrentImage();
		//System.out.println("Direction = " + direction);
	}

	public void render(Graphics g) {
		g.setColor(color);
		if (World.gameMode == World.GameMode.TRON
				|| World.gameMode == World.GameMode.TRON_INFINITE) {
			g.fillRect(x, y, width, height);
		} else if (currentImage != null) {
			g.drawImage(currentImage, x, y, width, height, null);
		} else {
			g.fillOval(x, y, width, height);
		}

	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setDirection(int d){
		direction = d;
	}

	private void setImages() {
		if (this.color == Color.blue) {
			try {
				image0 = ImageIO.read(new File("SnakePart0-BLUE.png"));
				image1 = ImageIO.read(new File("SnakePart1-BLUE.png"));
				image2 = ImageIO.read(new File("SnakePart2-BLUE.png"));
				image3 = ImageIO.read(new File("SnakePart3-BLUE.png"));
				image4 = ImageIO.read(new File("SnakePart4-BLUE.png"));
				image5 = ImageIO.read(new File("SnakePart5-BLUE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setCurrentImage(){
		switch (direction){
		case 0: currentImage = image0;
			break;
		case 1: currentImage = image1;
			break;
		case 2: currentImage = image2;
			break;
		case 3: currentImage = image3;
			break;
		case 4: currentImage = image4;
			break;
		case 5: currentImage = image5;
			break;
		}
	}
}
