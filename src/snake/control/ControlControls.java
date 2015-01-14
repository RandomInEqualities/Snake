
package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import snake.view.View;


public class ControlControls extends KeyAdapter implements ActionListener {
	
	private View view;
	
	public ControlControls(View view) {
		this.view = view;
		JButton back = view.getViewMenuControls().getBackButton();
		back.addActionListener(this);
		this.view.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.showMenu();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			view.showMenu();
		}
	}
	
}
