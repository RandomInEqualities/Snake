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
	private int xfix = 0;
	private int yfix = 0;
	public ControlMenu(Game game, View view) {
		view.addMouseMotionListener(this);
		view.addMouseListener(this);
		this.game = game;
		this.view = view;
	}

	public void mouseClicked(MouseEvent e) {
		if (game.getState() == Game.State.MENU) {
			if (view.getMenu().getSingleplayer().contains(e.getX() - xfix, e.getY() - yfix)) {
				//go to singleplayer
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if(view.getBoard().getMenu().contains(e.getX()- xfix, e.getY()-yfix)){
				//return to menu
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (game.getState() == Game.State.LOST) {
			if (view.getBoard().getPlayAgain().contains(e.getX() - xfix, e.getY() - yfix) || view.getBoard().getMenu().contains(e.getX()- xfix, e.getY()-yfix)) {
				view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else  {
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
}
