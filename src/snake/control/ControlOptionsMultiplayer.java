package snake.control;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

import snake.control.ControlOptions.Difficulty;
import snake.model.*;
import snake.view.*;

public class ControlOptionsMultiplayer extends ControlOptions {

	private GameMultiplayer game;
	private View view;
	private ViewOptionsMultiplayer viewMenuMultiplayer;
	private JButton green, blue, red, yellow, green2, blue2, red2, yellow2;
	private ViewBoardMultiplayer boardView;
	private ViewHeaderMultiplayer headerView;
	
	public ControlOptionsMultiplayer(GameMultiplayer game, View view, ViewOptionsMultiplayer menuView, ViewBoardMultiplayer boardView, ViewHeaderMultiplayer headerView) {
		super(view, menuView);
		this.game = game;
		this.view = view;
		this.viewMenuMultiplayer = menuView;
		this.boardView = boardView;
		this.headerView = headerView;
		this.view.addKeyListener(this);
		
		green = this.viewMenuMultiplayer.getGreenButton();
		blue = this.viewMenuMultiplayer.getBlueButton();
		red = this.viewMenuMultiplayer.getRedButton();
		yellow = this.viewMenuMultiplayer.getYellowButton();
		green2 = this.viewMenuMultiplayer.getGreenButton2();
		blue2 = this.viewMenuMultiplayer.getBlueButton2();
		red2 = this.viewMenuMultiplayer.getRedButton2();
		yellow2 = this.viewMenuMultiplayer.getYellowButton2();
		
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);
		green2.addActionListener(this);
		blue2.addActionListener(this);
		red2.addActionListener(this);
		yellow2.addActionListener(this);
		
		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		green2.setActionCommand("green2");
		blue2.setActionCommand("blue2");
		red2.setActionCommand("red2");
		yellow2.setActionCommand("yellow2");
		game.disableTimedMovement();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand() == "green") {
			setActiveButton(green, blue, red, yellow);
			boardView.setSnakeColor(Player.ONE, new Color(84, 216, 81));
		} 
		else if (event.getActionCommand() == "blue") {
			setActiveButton(blue, green, red, yellow);
			boardView.setSnakeColor(Player.ONE, new Color(80, 152, 218));
		} 
		else if (event.getActionCommand() == "red"){
			setActiveButton(red, green, blue, yellow);
			boardView.setSnakeColor(Player.ONE, new Color(237, 75, 66));
		} 
		else if (event.getActionCommand() == "yellow"){
			setActiveButton(yellow, green, blue, red);
			boardView.setSnakeColor(Player.ONE, new Color(243, 196, 67));
		} 
		if (event.getActionCommand() == "green2") {
			setActiveButton(green2, blue2, red2, yellow2);
			boardView.setSnakeColor(Player.TWO, new Color(84, 216, 81));
		} 
		else if (event.getActionCommand() == "blue2") {
			setActiveButton(blue2, green2, red2, yellow2);
			boardView.setSnakeColor(Player.TWO, new Color(80, 152, 218));
		} 
		else if (event.getActionCommand() == "red2"){
			setActiveButton(red2, green2, blue2, yellow2);
			boardView.setSnakeColor(Player.TWO, new Color(237, 75, 66));
		} 
		else if (event.getActionCommand() == "yellow2"){
			setActiveButton(yellow2, green2, blue2, red2);
			boardView.setSnakeColor(Player.TWO, new Color(243, 196, 67));
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		
		if (view.getViewState() != View.State.IN_MENU_MULTIPLAYER) {
			return;
		}

		switch (event.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				playGame();
				break;
			default:
				break;
		}
	}
	
	public void playGame() {
		String inputW = getInput(viewMenuMultiplayer.getWidthInput());
		String inputH = getInput(viewMenuMultiplayer.getHeightInput());

		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuMultiplayer.setFilled(false);
			viewMenuMultiplayer.repaint();
		} 
		else {
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= Board.MIN_WIDTH && inputWidth <= Board.MAX_WIDTH && inputHeight >= Board.MIN_HEIGHT && inputHeight <= Board.MAX_HEIGHT) {
				view.showGame(headerView, boardView);
				game.setBoardSize(inputWidth, inputHeight);
				setSpeed();
				
				game.reset();
				game.start();
				viewMenuMultiplayer.setValid(true);
				viewMenuMultiplayer.setFilled(true);
			} 
			else {
				// input is invalid
				viewMenuMultiplayer.setValid(false);
				viewMenuMultiplayer.repaint();
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