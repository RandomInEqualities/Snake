package snake.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.text.View;

import snake.model.Game;
import snake.view.BoardPanel;

public class ControlButton extends MouseAdapter {

	private Game game;
	private View view;
	private BoardPanel board;
	public ControlButton(Game game, View view) {
		this.view = view;
		this.game = game;
		board.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("clicked");
		int xstart = board.getRectangleForBoard().x + board.getRectangleForBoard().width / 2 - 150 / 2 - 100;
		int ystart = board.getRectangleForBoard().y + board.getRectangleForBoard().height / 2
				- 200 / 2 + 200 - 50 - 20;
		if (game.getState() == Game.State.LOST){
			if (e.getXOnScreen() > xstart && e.getXOnScreen() < xstart+150 && e.getYOnScreen() > ystart && e.getYOnScreen() < ystart+50) {
				System.out.println("clicked");
			}
		}
	}
}
