package snake.control;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import snake.view.*;

public class ControlMenu extends MouseAdapter {

	private View view;
	public ControlMenu(View view) {
		this.view = view;
		view.getMenu().addMouseMotionListener(this);
		view.getMenu().addMouseListener(this);
	}

	//Menu button events
	public void mouseClicked(MouseEvent e) { 
			if (view.getMenu().getSingleplayer().contains(e.getX(), e.getY())) {
				view.remove(view.getMenu());
				view.add(view.getSingleplayer());
				view.revalidate();
				view.repaint();
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			} else if (view.getMenu().getMultiplayer().contains(e.getX(), e.getY())) {
				System.out.println("multiplayer");
				//go to multiplayer
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getControls().contains(e.getX(), e.getY())) {
				System.out.println("controls");
				//go to controls
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getHighscore().contains(e.getX(), e.getY())) {
				System.out.println("highscore");
				//go to highscore
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getQuit().contains(e.getX(), e.getY())) {
				System.exit(0);
			}
		}

	@Override
	public void mouseMoved(MouseEvent e) {
			if (view.getMenu().getSingleplayer().contains(e.getX(), e.getY()) 
					|| view.getMenu().getMultiplayer().contains(e.getX(), e.getY())
					|| view.getMenu().getControls().contains(e.getX(), e.getY())
					|| view.getMenu().getHighscore().contains(e.getX(), e.getY())
					|| view.getMenu().getQuit().contains(e.getX(), e.getY())) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else  {
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
