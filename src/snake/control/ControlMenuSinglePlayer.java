
package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import snake.model.*;
import snake.view.*;


public class ControlMenuSinglePlayer implements ActionListener {
	
	private Game game;
	private View view;
	private ViewMenuSinglePlayer viewMenuSinglePlayer;

	public ControlMenuSinglePlayer(Game game, View view) {
		this.game = game;
		this.view = view;
		this.viewMenuSinglePlayer = view.getViewMenuSinglePlayer();
		this.viewMenuSinglePlayer.getPlayButton().addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (getInput(viewMenuSinglePlayer.getWidthInput()) == "" || getInput(viewMenuSinglePlayer.getHeightInput())==""){
			viewMenuSinglePlayer.setFilled(false);
			viewMenuSinglePlayer.repaint();
		} else if (getInput(viewMenuSinglePlayer.getWidthInput()) !="" && getInput(viewMenuSinglePlayer.getHeightInput())!=""){ 
			
			int inputWidth = Integer.parseInt(getInput(viewMenuSinglePlayer.getWidthInput()));
			int inputHeight = Integer.parseInt(getInput(viewMenuSinglePlayer.getHeightInput()));
			
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5 && inputHeight <= 100) {
				game.restart(inputWidth, inputHeight);
				view.showGame();
			} 
			else { // if input is invalid
				viewMenuSinglePlayer.setValid(false);
				viewMenuSinglePlayer.repaint();
			}
		}
	}

	// Get input
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = "";

		// Remove whitespace
		for (int i = 0; i < in.length(); i++) {
			if (!Character.isWhitespace(in.charAt(i))) {
				out += in.charAt(i);
			}
		}
		return out;
	}
}