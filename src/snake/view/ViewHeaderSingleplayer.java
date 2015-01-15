package snake.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import snake.model.GameSingleplayer;

public class ViewHeaderSingleplayer extends ViewHeader implements Observer {

	private GameSingleplayer game;
	private boolean showScore;
	private Font font;
	
	public ViewHeaderSingleplayer(View view, GameSingleplayer game, Boolean showScore) {
		super(view, view.getAudio());
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
		System.out.println("paint");
		// Update the score
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
