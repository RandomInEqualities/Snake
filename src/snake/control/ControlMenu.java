package snake.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import snake.view.*;

public class ControlMenu extends MouseAdapter {

	private View view;
	private ViewMenu viewMenu;
	
	public ControlMenu(View view) {
		this.view = view;
		this.viewMenu = view.getViewMenu();
		
		this.viewMenu.addMouseMotionListener(this);
		this.viewMenu.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent event) { 
		Point pos = event.getPoint();
		if (viewMenu.getSingleplayer().contains(pos)) {
			view.showSinglePlayerMenu();
		} 
		else if (viewMenu.getMultiplayer().contains(pos)) {
			// TODO multiplayer screen
			System.out.println("multiplayer");
		} 
		else if (viewMenu.getControls().contains(pos)) {
			// TODO control screen.
			System.out.println("controls");
		} 
		else if (viewMenu.getHighscore().contains(pos)) {
			// TODO high score screen.
			System.out.println("highscore");
		} 
		else if (viewMenu.getQuit().contains(pos)) {
			view.closeWindow();
		}
		viewMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		Point pos = event.getPoint();
		if (viewMenu.getSingleplayer().contains(pos) || viewMenu.getMultiplayer().contains(pos) || viewMenu.getControls().contains(pos)
				|| viewMenu.getHighscore().contains(pos) || viewMenu.getQuit().contains(pos)) {
			viewMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else  {
			viewMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}


