package snake.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class GameWithTimer extends Game implements ActionListener {
	
	private Timer timer;
	private long lastUpdateTime = 0;
	private long updateInterval = 200;
	private static final int TIMER_INTERVAL = 16;
	private static final int TIMER_INITIAL_DELAY = 500;

	public GameWithTimer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameWithTimer(int width, int height) {
		super(width, height);
		
		// Create a timer object that java swing will call in a periodic
		// interval. The timer will then send an ActionEvent to this class.
		this.timer = new Timer(TIMER_INTERVAL, this);
		this.timer.setInitialDelay(TIMER_INITIAL_DELAY);
		this.timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastUpdateTime;
		if (elapsedTime > updateInterval) {
			moveSnake(getSnake().getHeadDirection());
		}
	}
	
	@Override
	public void moveSnake(Direction moveDirection) {
		if (getSnake().canMove(moveDirection)) {
			lastUpdateTime = System.currentTimeMillis();
		}
		super.moveSnake(moveDirection);
	}
	
}
