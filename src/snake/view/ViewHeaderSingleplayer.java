package snake.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import snake.model.GameSinglePlayer;

public class ViewHeaderSingleplayer extends ViewHeader implements Observer {

	private GameSinglePlayer game;
	private View view;
	private boolean showScore;
	private Font font;
	
	public ViewHeaderSingleplayer(View view, GameSinglePlayer game, Boolean showScore) {
		super(view);
		this.view = view;
		this.game = game;
		this.showScore = showScore;
		if (game == null) {
			throw new NullPointerException();
		}

		game.addObserver(this);
		this.font = new Font("Sans_Serif", Font.BOLD, 20);
	}

	public void showScore() {
		showScore = true;
	}
	
	public void hideScore() {
		showScore = false;
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Dimension size = getSize();
		Graphics2D context2D = (Graphics2D) context;
		// Update the score (if we need to show scores).
		if (showScore) {
			context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			context2D.setFont(font);
			int fontSize = font.getSize();
			context2D.setColor(Color.WHITE);
			context2D.drawString("Score: " + game.getScore(), fontSize, size.height / 2 - fontSize / 2);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
