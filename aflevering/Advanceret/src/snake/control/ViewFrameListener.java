package snake.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import snake.view.ViewFrame;


/**
 * Listener for top level key events. It should be added to almost all components in 
 * in the program, so these key presses always works.
 */
public class ViewFrameListener extends KeyAdapter {
	
	private ViewFrame view;
	
	public ViewFrameListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_M:
				view.getAudio().toggleMute();
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				break;
			default:
				break;
		}
	}
	
}
