
package snake.control;

import java.awt.event.*;

import snake.view.View;


public class ControlControls extends KeyAdapter implements ActionListener {
	
	private View view;
	
	public ControlControls(View view) {
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
