package snake.control;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import snake.view.*;

public class ControlSingleplayer implements ActionListener {
	private View view;

	public ControlSingleplayer(View view) {
		this.view = view;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (getInput(view.getSingleplayer().getWidthInput()).isEmpty() || getInput(view.getSingleplayer().getHeightInput()).isEmpty()){ //if no input
			view.getSingleplayer().setFilled(false);
			view.getSingleplayer().repaint();
		} else if (getInput(view.getSingleplayer().getWidthInput()) !="" && getInput(view.getSingleplayer().getHeightInput())!=""){ //if input
			int inputWidth = Integer.parseInt(getInput(view.getSingleplayer().getWidthInput()));
			int inputHeight = Integer.parseInt(getInput(view.getSingleplayer().getHeightInput()));
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5 && inputHeight <= 100) {
				view.remove(view.getSingleplayer());
				view.remove(view.getHeader());
				view.startGame(inputWidth, inputHeight);
				view.getSingleplayer().setValid(true);
				view.getSingleplayer().setFilled(true);
			} else { // if input is invalid
				view.getSingleplayer().setValid(false);
				view.getSingleplayer().repaint();
			}
		}
	}
	
	/*public void mouseClicked(MouseEvent e) {
		if (view.getSingleplayer().getPlay_btn().contains(e.getX(), e.getY())) {
			int inputWidth = getInput(view.getSingleplayer().getWidthInput());
			int inputHeight = getInput(view.getSingleplayer().getHeightInput());
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5
					&& inputHeight <= 100) {
				view.remove(view.getSingleplayer());
				view.remove(view.getHeader());
				view.startGame(inputWidth, inputHeight);
			} else { // if input is invalid
				view.getSingleplayer().setValid(false);
				view.getSingleplayer().repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (view.getSingleplayer().getPlay_btn().contains(e.getX(), e.getY())) {
			view.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}*/

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");;
		return out;
	}
}