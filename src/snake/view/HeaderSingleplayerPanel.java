package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import snake.model.Event;
import snake.model.GameSingleplayer;


/**
 * JPanel for the header shown in multiplayer games. Derives from HeaderBasePanel and overrides
 * a few methods to add some scores.
 */
public class HeaderSingleplayerPanel extends HeaderBasePanel implements Observer {
	
	private static final Font scoreFont = new Font("Sans_Serif", Font.BOLD, 20);

	private GameSingleplayer game;
	
	public HeaderSingleplayerPanel(ViewFrame view) {
		super(view);
		this.game = null;
	}
	
	public void registerGame(GameSingleplayer newGame) {
		if (game != null) {
			game.deleteObserver(this);
		}
		if (newGame != null) {
			newGame.addObserver(this);
		}
		game = newGame;
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		if (game == null) {
			throw new NullPointerException("this panel should not be shown without a game");
		}
		super.paintComponent(context);
		Dimension size = getSize();
		Graphics2D context2D = (Graphics2D) context;
		
		// Update the score
		if(!game.isEnded()){
			context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			context2D.setFont(scoreFont);
			int fontSize = scoreFont.getSize();
			context2D.setColor(Color.WHITE);
			context2D.drawString("Score: " + game.getScore(), fontSize, size.height / 2 - fontSize / 2);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// For performance we only update the header when the game does something
		// other than moving the snake.
		if (o instanceof GameSingleplayer) {
			Event event = (Event)arg;
			if (event.getType() != Event.Type.MOVE) {
				repaint();
			}
		}
		else if (o instanceof Audio) {
			repaint();
		}
	}
}
