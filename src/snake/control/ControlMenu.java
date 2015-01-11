package snake.control;

import java.awt.event.*;

import javax.swing.*;

import snake.view.*;

public class ControlMenu implements ActionListener {
	private View view;
	
	public ControlMenu(View view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "singleplayer") {
			view.remove(view.getMenu());
			view.add(view.getSingleplayer());
			view.revalidate();
			view.repaint();
		} else if(e.getActionCommand() == "controls"){
			view.remove(view.getMenu());
			view.add(view.getControls());
			view.revalidate();
			view.repaint();
		} else if (e.getActionCommand() == "quit"){
			System.exit(0);
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}