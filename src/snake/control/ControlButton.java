package snake.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import snake.model.Game;
import snake.view.*;

public class ControlButton extends MouseAdapter {

	private Game game;
	private View view;
	
	public ControlButton(Game game, View view) {
		
		view.addMouseListener(this);
		this.game = game;
		this.view = view;
	}

	public void mouseClicked(MouseEvent e) {
		if (game.getState() == Game.State.LOST){
		int xstart = (int)view.getBoard().getPlayAgain().getX();
		int ystart = (int)view.getBoard().getPlayAgain().getY() + view.getBoard().getY() + 25;
			if (e.getX() > xstart && e.getX() < xstart+150 && e.getY() > ystart && e.getY() < ystart+50) {
				game.restart();
			}
		}
	}
}
