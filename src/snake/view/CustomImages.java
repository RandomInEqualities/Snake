package snake.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomImages {
	public BufferedImage apple, logo, headUp, headDown, headLeft, headRight,
			snakeCornerTL, snakeCornerTR, snakeCornerBL, snakeCornerBR,
			snakeHorizontal, snakeVertical, snakeTailU, snakeTailD, snakeTailL,
			snakeTailR, background, gameOverTitle, pausedTitle, gameWonTitle, menuTitle, controlsTitle,
			singleplayer_btn, multiplayer_btn, controls_btn, highScores_btn,
			quit_btn, play_btn, playAgain_btn, menu_btn, easy_btn,
			intermediate_btn, hard_btn, back_btn, controlsImage;

	public CustomImages() {
		try {
			// General
			background = ImageIO.read(new File("TileBackground.png"));
			logo = ImageIO.read(new File("snake-logo.png"));

			// Game
			apple = ImageIO.read(new File("apple.png"));
			headUp = ImageIO.read(new File("SnakeHeadU.png"));
			headDown = ImageIO.read(new File("SnakeHeadD.png"));
			headLeft = ImageIO.read(new File("SnakeHeadL.png"));
			headRight = ImageIO.read(new File("SnakeHeadR.png"));
			snakeCornerTL = ImageIO.read(new File("SnakeCornerTL.png"));
			snakeCornerTR = ImageIO.read(new File("SnakeCornerTR.png"));
			snakeCornerBL = ImageIO.read(new File("SnakeCornerBL.png"));
			snakeCornerBR = ImageIO.read(new File("SnakeCornerBR.png"));
			snakeHorizontal = ImageIO.read(new File("SnakeHorizontal.png"));
			snakeVertical = ImageIO.read(new File("SnakeVertical.png"));
			snakeTailU = ImageIO.read(new File("SnakeTailU.png"));
			snakeTailD = ImageIO.read(new File("SnakeTailD.png"));
			snakeTailL = ImageIO.read(new File("SnakeTailL.png"));
			snakeTailR = ImageIO.read(new File("SnakeTailR.png"));

			// Titles
			gameOverTitle = ImageIO.read(new File("TitleGameOver.png"));
			pausedTitle = ImageIO.read(new File("TitlePaused.png"));
			gameWonTitle = ImageIO.read(new File("TitleGameWon.png"));
			menuTitle = ImageIO.read(new File("TitleMenu.png"));
			controlsTitle = ImageIO.read(new File("TitleControls.png"));

			// Buttons
			singleplayer_btn = ImageIO.read(new File("ButtonSingleplayer.png"));
			multiplayer_btn = ImageIO.read(new File("ButtonMultiplayer.png"));
			controls_btn = ImageIO.read(new File("ButtonControls.png"));
			highScores_btn = ImageIO.read(new File("ButtonHighScores.png"));
			quit_btn = ImageIO.read(new File("ButtonQuit.png"));
			play_btn = ImageIO.read(new File("ButtonPlay.png"));
			playAgain_btn = ImageIO.read(new File("ButtonPlayAgain.png"));
			menu_btn = ImageIO.read(new File("ButtonMenu.png"));
			back_btn = ImageIO.read(new File("ButtonBack.png"));
			easy_btn = ImageIO.read(new File("DifficultyEasy.png"));
			intermediate_btn = ImageIO.read(new File("DifficultyIntermediate.png"));
			hard_btn = ImageIO.read(new File("DifficultyHard.png"));

			//Other
			controlsImage = ImageIO.read(new File("ControlsImage.png"));
		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
	}

}
