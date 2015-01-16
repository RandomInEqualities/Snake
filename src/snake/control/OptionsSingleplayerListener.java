package snake.control;

import java.awt.Dimension;
import java.awt.event.*;

import snake.model.*;
import snake.view.*;


public class OptionsSingleplayerListener extends OptionsListener {
	
	private GameSingleplayer game;
	private ViewFrame view;
	private OptionsSingleplayerPanel optionsPanel;
	
	public OptionsSingleplayerListener(ViewFrame view, OptionsSingleplayerPanel optionsPanel) {
		super(view, optionsPanel);
		this.game = null;
		this.view = view;
		this.optionsPanel = optionsPanel;
		
		// By the snake is green.
		BoardSingleplayerPanel boardPanel = view.getBoardSingleplayerPanel();
		boardPanel.setSnakeColor(ResourceColors.GREEN);
		optionsPanel.buttonPress("green");
	}
	
	public void registerGame(GameSingleplayer newGame) {
		game = newGame;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (game == null) {
			return;
		}
		super.actionPerformed(event);
		
		BoardSingleplayerPanel boardPanel = view.getBoardSingleplayerPanel();
		if (event.getActionCommand() == "green") {
			boardPanel.setSnakeColor(ResourceColors.GREEN);
		} 
		else if (event.getActionCommand() == "blue") {
			boardPanel.setSnakeColor(ResourceColors.BLUE);
		} 
		else if (event.getActionCommand() == "red"){
			boardPanel.setSnakeColor(ResourceColors.RED);
		} 
		else if (event.getActionCommand() == "yellow"){
			boardPanel.setSnakeColor(ResourceColors.YELLOW);
		}
		
		optionsPanel.buttonPress(event.getActionCommand());
	}
	
	public void startGame() {
		if (game == null)  {
			return;
		}
		
		Dimension newSize = optionsPanel.getChosenGameSize();
		game.setBoardSize(newSize.width, newSize.height);
		
		// Set difficulty.
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
		
		// Start the game and show it.
		game.start();
		view.showGame(game);
	}

}
