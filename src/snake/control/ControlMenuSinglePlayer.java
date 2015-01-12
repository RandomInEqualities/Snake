
package snake.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import snake.model.*;
import snake.view.*;


public class ControlMenuSinglePlayer extends KeyAdapter implements ActionListener {
	
	private Game game;
	private View view;
	private int speed;
	private ViewMenuSinglePlayer viewMenuSinglePlayer;

	public ControlMenuSinglePlayer(Game game, View view) {
		this.game = game;
		this.view = view;
		this.speed = 300;
		this.viewMenuSinglePlayer = view.getViewMenuSinglePlayer();
		this.view.addKeyListener(this);
		
		JButton play = this.viewMenuSinglePlayer.getPlayButton();
		JButton back = this.viewMenuSinglePlayer.getBackButton();
		JButton easy = this.viewMenuSinglePlayer.getEasyButton();
		JButton intermediate = this.viewMenuSinglePlayer.getIntermediateButton();
		JButton hard = this.viewMenuSinglePlayer.getHardButton();
		play.addActionListener(this);
		back.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);
		play.setActionCommand("play");
		back.setActionCommand("back");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton easy, intermediate, hard;
		easy = viewMenuSinglePlayer.getEasy();
		intermediate = viewMenuSinglePlayer.getIntermediate();
		hard = viewMenuSinglePlayer.getHard();
		if (e.getActionCommand() == "play") {
			playGame();
		} 
		else if (e.getActionCommand() == "easy"){
			easy.setBorderPainted(true);
			intermediate.setBorderPainted(false);
			hard.setBorderPainted(false);		
			speed = 300;
		} 
		else if (e.getActionCommand() == "intermediate"){
			easy.setBorderPainted(false);
			intermediate.setBorderPainted(true);
			hard.setBorderPainted(false);
			speed = 150;
		} 
		else if (e.getActionCommand() == "hard"){
			easy.setBorderPainted(false);
			intermediate.setBorderPainted(false);
			hard.setBorderPainted(true);
			speed = 70;
		} 
		else if (e.getActionCommand() == "back"){
			view.showMenu();
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		if (view.inGame()) {
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
	
	public void playGame() {
		String inputW = getInput(viewMenuSinglePlayer.getWidthInput());
		String inputH = getInput(viewMenuSinglePlayer.getHeightInput());
		
		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuSinglePlayer.setFilled(false);
			viewMenuSinglePlayer.repaint();
		} 
		else { 
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5 && inputHeight <= 100) {
				game.restart(inputWidth, inputHeight);
				view.showGame();
				viewMenuSinglePlayer.setValid(true);
				viewMenuSinglePlayer.setFilled(true);
			} 
			else { 
				// if input is invalid
				viewMenuSinglePlayer.setValid(false);
				viewMenuSinglePlayer.repaint();
			}
		}
	}
	
	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}