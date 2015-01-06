package snake.controller;
import java.awt.event.*;
import snake.model.*;
import snake.view.*;

public class Control implements KeyListener {
	private Game game;
	private GameView view;
	private Direction dir;

	public Control(Game game, GameView view) {
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
				if (head.getColumn() - 1 >= 0) {
					game.getPlayer().move(-1, 0);
				} else {
					System.out.println("out");
					game.getPlayer().move(game.getSize().width - 1, 0);
				}
				dir = Direction.LEFT;
			}
			break;
		case 38:
			if (dir != Direction.DOWN) {
				if (head.getRow() - 1 >= 0) {
					game.getPlayer().move(0, -1);
				} else {
					game.getPlayer().move(0, game.getSize().height - 1);
				}
				dir = Direction.UP;
			}
			break;
		case 39:
			if (dir != Direction.LEFT) {
				if (head.getColumn() + 1 <= game.getSize().width) {
					game.getPlayer().move(1, 0);
				} else {
					game.getPlayer().move(-game.getSize().width + 1, 0);
				}
				dir = Direction.RIGHT;
			}
			break;
		case 40:
			if (dir != Direction.UP) {
				if (head.getRow() + 1 <= game.getSize().height) {
					game.getPlayer().move(0, 1);
				} else {
					game.getPlayer().move(0, -game.getSize().height);
				}
				dir = Direction.DOWN;
			}
			break;
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
