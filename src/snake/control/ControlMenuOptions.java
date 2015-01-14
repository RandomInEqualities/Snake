package snake.control;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuOptions extends KeyAdapter implements ActionListener {
	private Game game;
	private View view;
	private ViewMenuOptions viewMenuOptions;
	private JButton play, back, kindergarten, easy, intermediate, hard;
	private Border thickBorder;
	
	public ControlMenuOptions(View view, Game game) {
		this.view = view;
		this.view.addKeyListener(this);
		play = this.viewMenuOptions.getPlayButton();
		back = this.viewMenuOptions.getBackButton();
		kindergarten = this.viewMenuOptions.getKindergartenButton();
		easy = this.viewMenuOptions.getEasyButton();
		intermediate = this.viewMenuOptions.getIntermediateButton();
		hard = this.viewMenuOptions.getHardButton();
		
		play.addActionListener(this);
		back.addActionListener(this);
		kindergarten.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);

		play.setActionCommand("play");
		back.setActionCommand("back");
		kindergarten.setActionCommand("kindergarten");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");

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
			viewMenuOptions.setValid(true);
			viewMenuOptions.setFilled(true);
		} 
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}

		switch (event.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				view.showMenu();
				viewMenuOptions.setValid(true);
				viewMenuOptions.setFilled(true);
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
				game.setBoard(new Board(inputWidth, inputHeight));
				game.start();
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
