package snake.control;

import java.awt.Cursor;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;

import snake.model.Game;
import snake.view.*;

public class ControlGame extends MouseAdapter {

	private Game game;
	private View view;
	private int xfix = 10;
	private int yfix = 100;
	public ControlGame(Game game, View view) {
		view.addMouseMotionListener(this);
		view.addMouseListener(this);
		this.game = game;
		this.view = view;
	}

	public void mouseClicked(MouseEvent e) {
		if (game.getState() == Game.State.LOST) {
			if (view.getBoard().getPlayAgain().contains(e.getX() - xfix, e.getY() - yfix)) {
				game.restart();
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else if(view.getBoard().getMenu().contains(e.getX()- xfix, e.getY()-yfix)){
				game.openMenu();
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
