package snake.control;

import java.awt.Dimension;
import java.awt.event.*;

import snake.model.*;
import snake.view.*;


public class OptionsMultiplayerListener extends OptionsListener {

	private GameMultiplayer game;
	private ViewFrame view;
	private OptionsMultiplayerPanel optionsPanel;
	
	public OptionsMultiplayerListener(ViewFrame view, OptionsMultiplayerPanel optionsPanel) {
		super(view, optionsPanel);
		this.game = null;
		this.view = view;
		this.optionsPanel = optionsPanel;
		
		// By default the snakes are green and yellow.
		BoardMultiplayerPanel boardPanel = view.getBoardMultiplayerPanel();
		boardPanel.setSnakeColor(Player.ONE, ResourceColors.GREEN);
		boardPanel.setSnakeColor(Player.TWO, ResourceColors.BLUE);
		optionsPanel.buttonPress("green");
		optionsPanel.buttonPress("blue2");
	}
	
	public void registerGame(GameMultiplayer newGame) {
		game = newGame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (game == null) {
			return;
		}
		super.actionPerformed(event);
		
		BoardMultiplayerPanel boardPanel = view.getBoardMultiplayerPanel();
		if (event.getActionCommand() == "green") {
			boardPanel.setSnakeColor(Player.ONE, ResourceColors.GREEN);
		} 
		else if (event.getActionCommand() == "blue") {
			boardPanel.setSnakeColor(Player.ONE, ResourceColors.BLUE);
		} 
		else if (event.getActionCommand() == "red"){
			boardPanel.setSnakeColor(Player.ONE, ResourceColors.RED);
		} 
		else if (event.getActionCommand() == "yellow"){
			boardPanel.setSnakeColor(Player.ONE, ResourceColors.YELLOW);
		} 
		if (event.getActionCommand() == "green2") {
			boardPanel.setSnakeColor(Player.TWO, ResourceColors.GREEN);
		} 
		else if (event.getActionCommand() == "blue2") {
			boardPanel.setSnakeColor(Player.TWO, ResourceColors.BLUE);
		} 
		else if (event.getActionCommand() == "red2"){
			boardPanel.setSnakeColor(Player.TWO, ResourceColors.RED);
		} 
		else if (event.getActionCommand() == "yellow2"){
			boardPanel.setSnakeColor(Player.TWO, ResourceColors.YELLOW);
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
