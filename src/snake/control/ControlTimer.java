
package snake.control;

import java.awt.event.*;

import javax.swing.Timer;

import snake.model.*;
import snake.view.*;

public class ControlTimer extends KeyAdapter implements ActionListener {
	
	private Game game;
	private Timer timer;
	private long lastUpdateTime = 0;
	private long gameUpdateInterval = 200;
	private static final int TIMER_INTERVAL = 16;
	private static final int TIMER_INITIAL_DELAY = 500;

	public ControlTimer(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		view.addKeyListener(this);
		
		// Create a timer object that java swing will call in a periodic
		// interval. The timer will then send an ActionEvent to this class.
		this.timer = new Timer(TIMER_INTERVAL, this);
		this.timer.setInitialDelay(TIMER_INITIAL_DELAY);
		this.timer.start();
	}
	
	public @Override void actionPerformed(ActionEvent event) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastUpdateTime;
		if (elapsedTime > gameUpdateInterval) {
			game.moveSnake(game.getSnake().getHeadDirection());
			lastUpdateTime = currentTime;
		}
    }

	public @Override void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.moveSnake(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				game.moveSnake(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.moveSnake(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.moveSnake(Direction.RIGHT);
				break;
			default:
				break;
		}
		
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void pauseTimer(Timer timer) {
		timer.stop();
	}

}
