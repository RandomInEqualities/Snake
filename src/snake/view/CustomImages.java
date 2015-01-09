package snake.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomImages {
	public BufferedImage apple, logo, headUp, headDown, headLeft, headRight, snakeCornerRU, snakeCornerLU, snakeCornerRD, snakeCornerLD, snakeHorizontal, snakeVertical, background, gameOverTitle, pausedTitle;
	
	public CustomImages() {
		try {
			apple = ImageIO.read(new File("apple.png"));
			logo = ImageIO.read(new File("snake-logo.png"));
			headUp = ImageIO.read(new File("head_up.png"));
			headDown = ImageIO.read(new File("head_down.png"));
			headLeft = ImageIO.read(new File("head_left.png"));
			headRight = ImageIO.read(new File("head_right.png"));
			snakeCornerRU = ImageIO.read(new File("SnakeCornerRU.png"));
			snakeCornerLU = ImageIO.read(new File("SnakeCornerLU.png"));
			snakeCornerRD = ImageIO.read(new File("SnakeCornerRD.png"));
			snakeCornerLD = ImageIO.read(new File("SnakeCornerLD.png"));
			snakeHorizontal = ImageIO.read(new File("SnakeHorizontal.png"));
			snakeVertical = ImageIO.read(new File("SnakeVertical.png"));
			background = ImageIO.read(new File("TileBackground.png"));
			gameOverTitle = ImageIO.read(new File("TitleGameOver.png"));
			pausedTitle = ImageIO.read(new File("TitlePaused.png"));
		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
	}
	
}
