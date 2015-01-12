package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import snake.view.*;

public class ControlMenu implements ActionListener {

	private View view;
	
	public ControlMenu(View view) {
		this.view = view;
		
		JButton singleplayer = view.getViewMenu().getSinglePlayerButton();
		JButton controls = view.getViewMenu().getControlsButton();
		JButton quit = view.getViewMenu().getQuitButton();
		
		singleplayer.addActionListener(this);
		singleplayer.setActionCommand("singleplayer");
		controls.addActionListener(this);
		controls.setActionCommand("controls");
		quit.addActionListener(this);
		quit.setActionCommand("quit");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "singleplayer") {
			view.showSinglePlayerMenu();
		} 
		else if (e.getActionCommand() == "controls"){
			view.showControlsMenu();
		} 
		else if (e.getActionCommand() == "quit"){
			view.closeWindow();
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}

