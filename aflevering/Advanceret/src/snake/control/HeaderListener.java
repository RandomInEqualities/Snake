package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import snake.view.ViewFrame;


/**
 * Listener for the mute button in the header panels.
 */
public class HeaderListener implements ActionListener {

	private ViewFrame view;
	
	public HeaderListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "mute"){
			view.getAudio().toggleMute();
		}
	}
	
}
