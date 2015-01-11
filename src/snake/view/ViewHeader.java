
package snake.view;
import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

import snake.model.Game;


public class ViewHeader extends JPanel implements Observer {
	
	private static final long serialVersionUID = -3944388732646932230L;
	private static final int DEFAULT_LOGO_WIDTH = 300;
	private static final int DEFAULT_LOGO_HEIGHT = 80;
	
	private Game game;
	private boolean showScore;
	private Image logo;
	private Font font;

	public ViewHeader(Game game, boolean showScore) {
		super();
		
		if (game == null) {
			throw new NullPointerException();
		}
		
		this.game = game;
		this.showScore = showScore;
		this.logo = Images.LOGO.getScaledInstance(DEFAULT_LOGO_WIDTH, DEFAULT_LOGO_HEIGHT, Image.SCALE_SMOOTH);
		this.font = new Font("Sans_Serif", Font.BOLD, 20);
		game.addObserver(this);
		setBackground(Colors.PANEL_COLOUR);
	}
	
	public void showScore() {
		showScore = true;
	}
	
	public void hideScore() {
		showScore = false;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (showScore) {
			repaint();
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 90);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		
		// Only show logo if board is wide enough to contain it.
		Dimension size = getSize();
		int logoWidth = logo.getWidth(null);
		if (size.width > logoWidth + 100 || (!showScore && size.width > logoWidth)) {
			context.drawImage(logo, size.width/2 - logo.getWidth(null)/2, 0, null);
		}
		
		// Update the score (if we need to show scores).
		if (showScore) {
			int fontSize = font.getSize();
			context.setColor(Color.WHITE);
			context.drawString("Score: " + game.getScore(), fontSize, size.height/2 - fontSize/2);
		}
	}
	
}
