package snake.control;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuSingleplayer extends KeyAdapter implements ActionListener {

	private Game game;
	private View view;
	private ViewMenuSingleplayer viewMenuSingleplayer;
	private JButton play, back, kindergarten, easy, intermediate, hard, green, blue, red, yellow;
	private Border thickBorder;
	
	public ControlMenuSingleplayer(Game game, View view) {
		this.game = game;
		this.view = view;
		this.viewMenuSingleplayer = view.getViewMenuSingleplayer();
		this.view.addKeyListener(this);
		play = this.viewMenuSingleplayer.getPlayButton();
		back = this.viewMenuSingleplayer.getBackButton();
		kindergarten = this.viewMenuSingleplayer.getKindergartenButton();
		easy = this.viewMenuSingleplayer.getEasyButton();
		intermediate = this.viewMenuSingleplayer.getIntermediateButton();
		hard = this.viewMenuSingleplayer.getHardButton();
		green = this.viewMenuSingleplayer.getGreenButton();
		blue = this.viewMenuSingleplayer.getBlueButton();
		red = this.viewMenuSingleplayer.getRedButton();
		yellow = this.viewMenuSingleplayer.getYellowButton();
		game.disableTimedMovement(); //default difficulty = kindergarten
		
		play.addActionListener(this);
		back.addActionListener(this);
		kindergarten.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);
		
		play.setActionCommand("play");
		back.setActionCommand("back");
		kindergarten.setActionCommand("kindergarten");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");
		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		
		thickBorder = new LineBorder(Colors.PANEL_COLOUR, 3);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "play") {
			playGame();
		} 
		else if (event.getActionCommand() == "kindergarten") {
			setActiveButton(kindergarten, easy, intermediate, hard);
			setKindergardenDifficulty();
		} 
		else if (event.getActionCommand() == "easy") {
			setActiveButton(easy, kindergarten, intermediate, hard);
			setEasyDifficulty();
		} 
		else if (event.getActionCommand() == "intermediate"){
			setActiveButton(intermediate, kindergarten, easy, hard);
			setIntermediatDifficulty();
		} 
		else if (event.getActionCommand() == "hard") {
			setActiveButton(hard, kindergarten, easy, intermediate);
			setHardDifficulty();
		} 
		else if (event.getActionCommand() == "back") {
			view.showMenu();
		} 
		else if (event.getActionCommand() == "green") {
			setActiveButton(green, blue, red, yellow);
			view.getViewBoard().setSnakeColour(84, 216, 81);
		} 
		else if (event.getActionCommand() == "blue") {
			setActiveButton(blue, green, red, yellow);
			view.getViewBoard().setSnakeColour(80, 152, 218);
		} 
		else if (event.getActionCommand() == "red"){
			setActiveButton(red, green, blue, yellow);
			view.getViewBoard().setSnakeColour(237, 75, 66);
		} 
		else if (event.getActionCommand() == "yellow"){
			setActiveButton(yellow, green, blue, red);
			view.getViewBoard().setSnakeColour(243, 196, 67);
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
				break;
			case KeyEvent.VK_ENTER:
				playGame();
				break;
			default:
				break;
		}
	}
	
	private void setKindergardenDifficulty() {
		game.disableTimedMovement();
	}
	
	private void setEasyDifficulty() {
		game.enableTimedMovement();
		game.setTimedMovementSpeed(300);
	}
	
	private void setIntermediatDifficulty() {
		game.enableTimedMovement();
		game.setTimedMovementSpeed(150);
	}
	
	private void setHardDifficulty() {
		game.enableTimedMovement();
		game.setTimedMovementSpeed(70);
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
			if (inputWidth >= Board.MIN_WIDTH && inputWidth <= Board.MAX_WIDTH && 
					inputHeight >= Board.MIN_HEIGHT && inputHeight <= Board.MAX_HEIGHT) {
				game.restart(inputWidth, inputHeight);
				view.showGame();
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

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
	
	protected void setActiveButton(JButton active, JButton b1, JButton b2, JButton b3){
		active.setBorderPainted(true);
		active.setBorder(thickBorder);
		b1.setBorderPainted(false);
		b2.setBorderPainted(false);
		b3.setBorderPainted(false);
		active.repaint();
		
		// Request focus so we still receive keyboard events.
		view.requestFocus();
	}
	
}
