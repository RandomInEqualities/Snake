package snake.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {
	
	public static final BufferedImage LOGO;
	public static final BufferedImage BACKGROUND;
	public static final BufferedImage APPLE;
	public static final BufferedImage SNAKE_HEAD_UP;
	public static final BufferedImage SNAKE_HEAD_DOWN;
	public static final BufferedImage SNAKE_HEAD_LEFT;
	public static final BufferedImage SNAKE_HEAD_RIGHT;
	public static final BufferedImage SNAKE_CORNER_RU;
	public static final BufferedImage SNAKE_CORNER_LU;
	public static final BufferedImage SNAKE_CORNER_RD;
	public static final BufferedImage SNAKE_CORNER_LD;
	public static final BufferedImage SNAKE_HORIZONTAL;
	public static final BufferedImage SNAKE_VERTICAL;
	public static final BufferedImage TITLE_GAME_OVER;
	public static final BufferedImage TITLE_PAUSE;
	public static final BufferedImage TITLE_MENU;
	public static final BufferedImage BUTTON_SINGLEPLAYER;
	public static final BufferedImage BUTTON_MULTIPLAYER;
	public static final BufferedImage BUTTON_CONTROLS;
	public static final BufferedImage BUTTON_HIGHSCORE;
	public static final BufferedImage BUTTON_QUIT;
	public static final BufferedImage BUTTON_PLAY;
	public static final BufferedImage BUTTON_PLAY_AGAIN; 
	public static final BufferedImage BUTTON_MENU;
	
	private static final String IMAGE_PATH = "resources/images/";

	static {
		LOGO = loadImage("snake-logo.png");
		BACKGROUND = loadImage("TileBackground.png");

		APPLE = loadImage("apple.png");
		SNAKE_HEAD_UP = loadImage("head_up.png");
		SNAKE_HEAD_DOWN = loadImage("head_down.png");
		SNAKE_HEAD_LEFT = loadImage("head_left.png");
		SNAKE_HEAD_RIGHT = loadImage("head_right.png");
		SNAKE_CORNER_RU = loadImage("SnakeCornerRU.png");
		SNAKE_CORNER_LU = loadImage("SnakeCornerLU.png");
		SNAKE_CORNER_RD = loadImage("SnakeCornerRD.png");
		SNAKE_CORNER_LD = loadImage("SnakeCornerLD.png");
		SNAKE_HORIZONTAL = loadImage("SnakeHorizontal.png");
		SNAKE_VERTICAL = loadImage("SnakeVertical.png");

		TITLE_GAME_OVER = loadImage("TitleGameOver.png");
		TITLE_PAUSE = loadImage("TitlePaused.png");
		TITLE_MENU = loadImage("TitleMenu.png");

		BUTTON_SINGLEPLAYER = loadImage("ButtonSingleplayer.png");
		BUTTON_MULTIPLAYER = loadImage("ButtonMultiplayer.png");
		BUTTON_CONTROLS = loadImage("ButtonControls.png");
		BUTTON_HIGHSCORE = loadImage("ButtonHighScores.png");
		BUTTON_QUIT = loadImage("ButtonQuit.png");
		BUTTON_PLAY = loadImage("ButtonPlay.png");
		BUTTON_PLAY_AGAIN = loadImage("ButtonPlayAgain.png");
		BUTTON_MENU = loadImage("ButtonMenu.png");
	}

	private static BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(IMAGE_PATH + filename));
		}
		catch (IOException error) {
			throw new RuntimeException("Unable to load image " + filename + ": " + error.getMessage());
		}
	}

}
