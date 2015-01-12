
package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import snake.view.View;


public class ControlMenuControls implements ActionListener {
	
	private View view;
	
	public ControlMenuControls(View view) {
		this.view = view;
		JButton back = view.getViewMenuControls().getBackButton();
		back.setActionCommand("back");
		back.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.showMenu();
	}
	
}
