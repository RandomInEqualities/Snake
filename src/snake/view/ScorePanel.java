
package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import snake.model.*;

public class ScorePanel extends JPanel implements Observer {

	private Score score;
	private Game game;
	private static final Font DEFAULT_FONT = new Font("Sans_Serif", Font.BOLD, 15);
	private static final Color PANEL_COLOUR = new Color(0.2f, 0.286f, 0.3686f);
	private static final Color SCORE_COLOUR = new Color(0.9255f, 0.941f, 0.9451f);
	private static final long serialVersionUID = -8478516974010275721L;
	private BufferedImage image;
	
	public ScorePanel(Game game) {
		super();
		this.game = game;
		setBackground(PANEL_COLOUR);
		if (game == null) {
			throw new NullPointerException();
		}
		try {
			image = ImageIO.read(new File("snake-logo.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
		this.score = game.getScore();
		this.score.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 70);
	}
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		context.setFont(DEFAULT_FONT);
		context.setColor(SCORE_COLOUR);
		context.drawString("Score: " + score.getValue(), 20, 40);
		System.out.println(getSize().width);
		context.drawImage(image.getScaledInstance(200, 70, Image.SCALE_SMOOTH), getSize().width/2-200/2, 0, null);
	}
	
}
