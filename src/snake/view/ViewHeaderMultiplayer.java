package snake.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.model.Player;

public class ViewHeaderMultiplayer extends ViewHeader implements Observer {

	private GameMultiplayer game;
	private boolean showScore;
	private Font font;
	
	public ViewHeaderMultiplayer(View view, GameMultiplayer game, Boolean showScore) {
		super(view.getAudio());
		this.game = game;
		if (game == null) {
			throw new NullPointerException();
		}

		game.addObserver(this);
		this.font = new Font("Sans_Serif", Font.BOLD, 20);
		this.showScore = showScore;
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
			context2D.drawString("Player 1: " + game.getScore(Player.ONE), fontSize, size.height / 2 - fontSize / 2);
			context2D.drawString("Player 2: " + game.getScore(Player.TWO), fontSize, size.height / 2 + fontSize);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
