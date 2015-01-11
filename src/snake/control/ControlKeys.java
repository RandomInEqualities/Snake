package snake.control;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.*;

import snake.model.*;
import snake.model.Game.State;
import snake.view.*;

public class ControlKeys extends KeyAdapter {

	private Game game;
	private ControlTimer controlTimer;
	private View view;

	public ControlKeys(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		view.addKeyListener(this);
		this.view = view;
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent event) {

		switch (event.getKeyCode()) {
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			if (game.getState() == State.LOST) {
				game.restart();
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				view.getBoard().remove(view.getBoard().getPlayAgain());
				view.getBoard().remove(view.getBoard().getMenu());
			}
			break;
		case KeyEvent.VK_M:
			if (!game.isMuted) {
				game.isMuted = true;
			} else {
				game.isMuted = false;
			}
			break;

		case KeyEvent.VK_P:
			if (game.getState() == State.RUNNING) {
				game.setState(State.PAUSED);
				view.getBoard().repaint();
			} else if (game.getState() == State.PAUSED) {
				game.setState(State.RUNNING);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			view.getContentPane().removeAll();
			view.add(view.getHeader(), BorderLayout.NORTH);
			view.add(view.getMenu());
			view.revalidate();
			view.repaint();
			break;
		}

	}

}
