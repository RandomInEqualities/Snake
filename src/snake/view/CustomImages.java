package snake.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomImages {
	public BufferedImage apple, logo, headUp, headDown, headLeft, headRight,
			snakeCornerRU, snakeCornerLU, snakeCornerRD, snakeCornerLD,
			snakeHorizontal, snakeVertical, background, gameOverTitle,
			pausedTitle, menuTitle, singleplayer_btn, multiplayer_btn,
			controls_btn, highScores_btn, quit_btn, play_btn, playAgain_btn,
			menu_btn, easy_btn, intermediate_btn, hard_btn;

	public CustomImages() {
		try {
			// General
			background = ImageIO.read(new File("TileBackground.png"));
			logo = ImageIO.read(new File("snake-logo.png"));

			// Game
			apple = ImageIO.read(new File("apple.png"));
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

			// Titles
			gameOverTitle = ImageIO.read(new File("TitleGameOver.png"));
			pausedTitle = ImageIO.read(new File("TitlePaused.png"));
			menuTitle = ImageIO.read(new File("TitleMenu.png"));

			// Buttons
			singleplayer_btn = ImageIO.read(new File("ButtonSingleplayer.png"));
			multiplayer_btn = ImageIO.read(new File("ButtonMultiplayer.png"));
			controls_btn = ImageIO.read(new File("ButtonControls.png"));
			highScores_btn = ImageIO.read(new File("ButtonHighScores.png"));
			quit_btn = ImageIO.read(new File("ButtonQuit.png"));
			play_btn = ImageIO.read(new File("ButtonPlay.png"));
			playAgain_btn = ImageIO.read(new File("ButtonPlayAgain.png"));
			menu_btn = ImageIO.read(new File("ButtonMenu.png"));
			easy_btn = ImageIO.read(new File("DifficultyEasy.png"));
			intermediate_btn = ImageIO.read(new File("DifficultyIntermediate.png"));
			hard_btn = ImageIO.read(new File("DifficultyHard.png"));

		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
	}

}
