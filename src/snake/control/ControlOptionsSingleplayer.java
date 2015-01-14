package snake.control;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import snake.model.*;
import snake.view.*;

public class ControlOptionsSingleplayer extends ControlOptions {
	
	private View view;
	private ViewOptionsSingleplayer viewMenuSingleplayer;
	private JButton green, blue, red, yellow;
	ViewBoardSingleplayer boardView;
	private GameSingleplayer game;
	
	public ControlOptionsSingleplayer(GameSingleplayer game, View view, ViewOptionsSingleplayer menuView, ViewBoardSingleplayer boardView) {
		super(view, menuView);
		this.view = view;
		this.game = game;
		this.boardView = boardView;
		this.viewMenuSingleplayer = menuView;
		this.view.addKeyListener(this);
		
		green = this.viewMenuSingleplayer.getGreenButton();
		blue = this.viewMenuSingleplayer.getBlueButton();
		red = this.viewMenuSingleplayer.getRedButton();
		yellow = this.viewMenuSingleplayer.getYellowButton();
		game.disableTimedMovement(); //default difficulty = kindergarten
		
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);

		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand() == "green") {
			setActiveButton(green, blue, red, yellow);
			boardView.setSnakeColor(new Color(84, 216, 81));
		} 
		else if (event.getActionCommand() == "blue") {
			setActiveButton(blue, green, red, yellow);
			boardView.setSnakeColor(new Color(80, 152, 218));
		} 
		else if (event.getActionCommand() == "red"){
			setActiveButton(red, green, blue, yellow);
			boardView.setSnakeColor(new Color(237, 75, 66));
		} 
		else if (event.getActionCommand() == "yellow"){
			setActiveButton(yellow, green, blue, red);
			boardView.setSnakeColor(new Color(243, 196, 67));
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		
		if (view.getViewState() != View.State.IN_MENU_SINGLEPLAYER) {
			return;
		}

		switch (event.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				view.showMenu();
				viewMenuSingleplayer.setValid(true);
				viewMenuSingleplayer.setFilled(true);
				break;
			case KeyEvent.VK_ENTER:
				playGame();
				break;
			default:
				break;
		}
	}
	
	public void playGame() {
		String inputW = getInput(viewMenuSingleplayer.getWidthInput());
		String inputH = getInput(viewMenuSingleplayer.getHeightInput());

		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuSingleplayer.setFilled(false);
			viewMenuSingleplayer.repaint();
		} 
		else {
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= Board.MIN_WIDTH && inputWidth <= Board.MAX_WIDTH && inputHeight >= Board.MIN_HEIGHT && inputHeight <= Board.MAX_HEIGHT) {
				view.showGame(boardView);
				game.setBoardSize(inputWidth, inputHeight);
				setSpeed();
				game.reset();
				game.start();
				viewMenuSingleplayer.setValid(true);
				viewMenuSingleplayer.setFilled(true);
			} 
			else {
				// input is invalid
				viewMenuSingleplayer.setValid(false);
				viewMenuSingleplayer.repaint();
			}
		}
	}
	
	public void setSpeed(){
		if (getDifficulty() == Difficulty.KINDERGARTEN){
			game.disableTimedMovement(); 
		} else if (getDifficulty() == Difficulty.EASY){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(300);
		} else if (getDifficulty() == Difficulty.INTERMEDIATE){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(150);
		} else if (getDifficulty() == Difficulty.HARD){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(70);
		}
	}
}