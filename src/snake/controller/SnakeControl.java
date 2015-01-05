package snake.controller;
import java.awt.event.*;

public class SnakeControl implements KeyListener {
	private SnakeGame game;
	private View view;
	private Direction dir;

	public SnakeControl(SnakeGame game, View view) {
		this.game = game;
		this.view = view;
		this.dir = Direction.LEFT;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37:
			if (dir != Direction.RIGHT) {
				if (game.player.getSnake().get(0).getRow() - 1 >= 0) {
					game.player.move(-1, 0);
				} else {
					game.player.move(game.level.length - 1, 0);
				}
				dir = Direction.LEFT;
			}
		case 38:
			if (dir != Direction.DOWN) {
				if (game.player.getSnake().get(0).getColumn() - 1 >= 0) {
					game.player.move(0, -1);
				} else {
					game.player.move(0, game.level.height - 1);
				}
				dir = Direction.UP;
			}
		case 39:
			if (dir != Direction.LEFT) {
				if (game.player.getSnake().get(0).getRow() + 1 <= game.level.length) {
					game.player.move(1, 0);
				} else {
					game.player.move(-game.level.length + 1, 0);
				}
				dir = Direction.RIGHT;
			}
		case 40:
			if (dir != Direction.UP) {
				if (game.player.getSnake().get(0).getColumn + 1 <= game.level.height) {
					game.player.move(0, 1);
				} else {
					game.player.move(0, -game.level.height + 1);
				}
				dir = Direction.DOWN;
			}
		}
		game.view.repaint();
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
