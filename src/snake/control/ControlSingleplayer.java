package snake.control;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.*;

import javax.swing.*;

import snake.view.*;

public class ControlSingleplayer implements ActionListener {
	private View view;
	private int speed;
	
	public ControlSingleplayer(View view) {
		this.view = view;
		this.speed = 300;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton easy, intermediate, hard;
		easy = view.getSingleplayer().getEasy();
		intermediate = view.getSingleplayer().getIntermediate();
		hard = view.getSingleplayer().getHard();
		if (e.getActionCommand() == "play") {
			String inputW = getInput(view.getSingleplayer().getWidthInput());
			String inputH = getInput(view.getSingleplayer().getHeightInput());
			
			if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
				view.getSingleplayer().setFilled(false);
				view.getSingleplayer().repaint();
			} else { // if input is correct
				int inputWidth = Integer.parseInt(inputW);
				int inputHeight = Integer.parseInt(inputH);
				if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5
						&& inputHeight <= 100) {
					view.getContentPane().removeAll();
					view.startGame(inputWidth, inputHeight, speed);
					view.getSingleplayer().setValid(true);
					view.getSingleplayer().setFilled(true);
				} else { // if input is invalid
					view.getSingleplayer().setValid(false);
					view.getSingleplayer().repaint();
				}
			}
		} else if (e.getActionCommand() == "easy"){
			easy.setBorderPainted(true);
			intermediate.setBorderPainted(false);
			hard.setBorderPainted(false);		
			speed = 300;
		} else if (e.getActionCommand() == "intermediate"){
			easy.setBorderPainted(false);
			intermediate.setBorderPainted(true);
			hard.setBorderPainted(false);
			speed = 150;
		} else if (e.getActionCommand() == "hard"){
			easy.setBorderPainted(false);
			intermediate.setBorderPainted(false);
			hard.setBorderPainted(true);
			speed = 70;
		} else if (e.getActionCommand() == "back"){
			view.getContentPane().removeAll();
			view.add(view.getHeader(), BorderLayout.NORTH);
			view.add(view.getMenu());
			view.revalidate();
			view.repaint();
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}