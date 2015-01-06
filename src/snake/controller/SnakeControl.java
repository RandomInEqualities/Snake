package snake.controller;
import java.awt.event.*;
import snake.model.*;
import snake.view.*;

public class SnakeControl implements KeyListener {
	private Game game;
	private GameView view;
	private Direction dir;

	public SnakeControl(Game game, GameView view) {
		this.game = game;
		this.view = view;
		this.dir = Direction.LEFT;
		view.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Field head = game.getPlayer().getSnake().get(0);;
		switch (e.getKeyCode()) {
		case 37:
			if (dir != Direction.RIGHT) {
				if (head.getRow() - 1 >= 0) {
					game.getPlayer().move(-1, 0);
				} else {
					game.getPlayer().move(game.getWidth() - 1, 0);
				}
				dir = Direction.LEFT;
			}
		case 38:
			if (dir != Direction.DOWN) {
				if (head.getColumn() - 1 >= 0) {
					game.getPlayer().move(0, -1);
				} else {
					game.getPlayer().move(0, game.getHeight() - 1);
				}
				dir = Direction.UP;
			}
		case 39:
			if (dir != Direction.LEFT) {
				if (head.getRow() + 1 <= game.getWidth()) {
					game.getPlayer().move(1, 0);
				} else {
					game.getPlayer().move(-game.getWidth() + 1, 0);
				}
				dir = Direction.RIGHT;
			}
		case 40:
			if (dir != Direction.UP) {
				if (head.getColumn() + 1 <= game.getHeight()) {
					game.getPlayer().move(0, 1);
				} else {
					game.getPlayer().move(0, -game.getHeight() + 1);
				}
				dir = Direction.DOWN;
			}
		}
		//game.view.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
