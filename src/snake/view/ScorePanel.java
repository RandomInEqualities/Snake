
package snake.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import snake.model.*;

public class ScorePanel extends JPanel implements Observer {

	private Game game;
	
	private static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 15);
	private static final Dimension DEFAULT_SIZE = new Dimension(400,30);
	private static final long serialVersionUID = -8478516974010275721L;
	
	public ScorePanel(Game game) {
		super();
		setPreferredSize(DEFAULT_SIZE);
		
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		context.setFont(DEFAULT_FONT);
		context.drawString("Score: " + game.getScore(), 10, 20);
	}
	
}
