package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.model.*;
import snake.view.*;


public class OptionsMultiplayerListener extends KeyAdapter implements ActionListener {

	private GameMultiplayer game;
	private WindowControl control;
	private Audio audio;
	private BoardMultiplayer board;
	private OptionsMultiplayer options;
	private Difficulty difficulty;
	
	public OptionsMultiplayerListener(WindowControl control, Audio audio, BoardMultiplayer board, 
			OptionsMultiplayer options, GameMultiplayer game) {
		if (control == null || audio == null || board == null || options == null || game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.control = control;
		this.audio = audio;
		this.board = board;
		this.options = options;
		this.difficulty = Difficulty.EASY;
		board.setSnakeColor(Player.ONE, CustomColors.GREEN);
		board.setSnakeColor(Player.TWO, CustomColors.BLUE);
		options.actionPerformed(new ActionEvent(this, 0, "easy"));
		options.actionPerformed(new ActionEvent(this, 0, "green1"));
		options.actionPerformed(new ActionEvent(this, 0, "blue2"));
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		switch (command) {
			case "play":
				attemptGameStart();
				break;
			case "back":
				control.showMenu();
				break;
			case "kindergarten":
				difficulty = Difficulty.KINDERGARTEN;
				break;
			case "easy":
				difficulty = Difficulty.EASY;
				break;
			case "intermediate":
				difficulty = Difficulty.INTERMEDIATE;
				break;
			case "hard":
				difficulty = Difficulty.HARD;
				break;
			case "green1":
				board.setSnakeColor(Player.ONE, CustomColors.GREEN);
				break;
			case "blue1":
				board.setSnakeColor(Player.ONE, CustomColors.BLUE);
				break;
			case "red1":
				board.setSnakeColor(Player.ONE, CustomColors.RED);
				break;
			case "yellow1":
				board.setSnakeColor(Player.ONE, CustomColors.YELLOW);
				break;
			case "green2":
				board.setSnakeColor(Player.TWO, CustomColors.GREEN);
				break;
			case "blue2":
				board.setSnakeColor(Player.TWO, CustomColors.BLUE);
				break;
			case "red2":
				board.setSnakeColor(Player.TWO, CustomColors.RED);
				break;
			case "yellow2":
				board.setSnakeColor(Player.TWO, CustomColors.YELLOW);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				attemptGameStart();
				break;
			case KeyEvent.VK_M:
				audio.toggleMute();
				break;
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_BACK_SPACE:
				control.showMenu();
				break;
			default:
				break;
		}
	}
	
	public void attemptGameStart() {
		if (!options.hasBoardSizeInput()) {
			return;
		}
		game.setBoardSize(options.getBoardSizeInput());
		
		if (difficulty == Difficulty.KINDERGARTEN){
			game.disableTimedMovement(); 
		} 
		else if (difficulty == Difficulty.EASY){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(300);
		} 
		else if (difficulty == Difficulty.INTERMEDIATE){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(150);
		} 
		else if (difficulty == Difficulty.HARD){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(70);
		}
		
		control.showGameMultiplayer();
		game.start();
	}	
	
}
