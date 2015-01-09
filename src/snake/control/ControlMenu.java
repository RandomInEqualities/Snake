package snake.control;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import snake.model.Game;
import snake.view.Menu;
import snake.view.View;

public class ControlMenu extends MouseAdapter {

	private Game game;
	private View view;
	private int xfix = 1;
	private int yfix = 93;
	public ControlMenu(Game game, View view) {
		view.addMouseMotionListener(this);
		view.addMouseListener(this);
		this.view = view;
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {
		
		if (game.getState() == Game.State.MENU) {
			//singleplayer printer null - FEJL 
			System.out.println(view.getMenu().getSingleplayer());
			System.out.println((e.getX() - xfix) + " + " + (e.getY() - yfix));
			if (view.getMenu().getSingleplayer().contains(e.getX() - xfix, e.getY() - yfix)) {
	
				System.out.println("singleplayer");
				//go to singleplayer
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getMultiplayer().contains(e.getX() - xfix, e.getY() - yfix)) {
				System.out.println("multiplayer");
				//go to multiplayer
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getControls().contains(e.getX() - xfix, e.getY() - yfix)) {
				System.out.println("controls");
				//go to controls
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getHighscore().contains(e.getX() - xfix, e.getY() - yfix)) {
				System.out.println("highscore");
				//go to highscore
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if (view.getMenu().getQuit().contains(e.getX() - xfix, e.getY() - yfix)) {
				System.out.println("quit");
				//quit program
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (game.getState() == Game.State.MENU) {
			if (view.getMenu().getSingleplayer().contains(e.getX() - xfix, e.getY() - yfix) || view.getMenu().getSingleplayer().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (view.getMenu().getMultiplayer().contains(e.getX() - xfix, e.getY() - yfix) || view.getMenu().getMultiplayer().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (view.getMenu().getControls().contains(e.getX() - xfix, e.getY() - yfix) || view.getMenu().getControls().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (view.getMenu().getHighscore().contains(e.getX() - xfix, e.getY() - yfix) || view.getMenu().getHighscore().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (view.getMenu().getQuit().contains(e.getX() - xfix, e.getY() - yfix) || view.getMenu().getQuit().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else  {
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
}
