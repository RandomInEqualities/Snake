package snake.control;

import java.awt.event.*;
import snake.view.*;

public class Control extends KeyAdapter implements ActionListener{
	
	private View view;
	
	public Control(View view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_M:
				toggleSound();
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				break;
			default:
				break;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("pressed");
		if (e.getActionCommand() == "mute"){
			toggleSound();
		}
		view.requestFocus();
	}
	
	private void toggleSound(){
		ViewAudio audio = view.getAudio();
		audio.setMuted(!audio.isMuted());
		view.getHeader().repaint();
	}
}
