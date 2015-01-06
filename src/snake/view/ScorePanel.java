package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import snake.model.Game;
import snake.model.Score;

public class ScorePanel extends JPanel implements Observer {

	private Score score;
	private static final Font FONT = new Font("Serif", Font.PLAIN, 15);
	private static final Dimension SIZE = new Dimension(400,30);
	private static final long serialVersionUID = -8478516974010275721L;
	
	public ScorePanel(Score score) {
		super();
		this.score = score;
		setPreferredSize(SIZE);
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		context.setFont(FONT);
		context.drawString("Score: " + score.getScore(), 10, 20);
	}
	
}
