package snake.control;

import java.awt.event.*;

import snake.view.ViewFrame;

public class ControlsListener extends KeyAdapter implements ActionListener {
	
	private ViewFrame view;
	
	public ControlsListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.showMenu();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			view.showMenu();
		}
	}
	
}
