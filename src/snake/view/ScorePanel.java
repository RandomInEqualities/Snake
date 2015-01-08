
package snake.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import snake.model.*;

public class ScorePanel extends JPanel implements Observer {

	private Game game;
	private BoardPanel boardPanel;
	private BufferedImage logo;
	public static final Font DEFAULT_FONT = new Font("Sans_Serif", Font.BOLD, 15);
	private static final long serialVersionUID = -8478516974010275721L;
	
	public ScorePanel(Game game, BoardPanel boardPanel) {
		super();
		if (game == null || boardPanel == null) {
			throw new NullPointerException();
		}
		
		this.game = game;
		this.boardPanel = boardPanel;
		game.addObserver(this);
		setBackground(CustomColor.PANEL_COLOUR);
		
		try {
			logo = ImageIO.read(new File("snake-logo.png"));
		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
	}
	
	public @Override void update(Observable o, Object arg) {
		repaint();
	}
	
	public @Override Dimension getPreferredSize() {
		return new Dimension(400, 70);
	}
	
	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		context.setFont(DEFAULT_FONT);
		context.setColor(CustomColor.SCORE_COLOUR);
		if(boardPanel.getRectangleForBoard().width>350){ 
			//Only show logo if board is wide (to avoid overlap with score)
			context.drawImage(logo.getScaledInstance(200, 70, Image.SCALE_SMOOTH), boardPanel.getSize().width/2-100, 0, null);
		}
		if (game.getState() == Game.State.RUNNING){
			context.drawString("Score: " + game.getScore(), boardPanel.getSize().width/2-boardPanel.getRectangleForBoard().width/2+10, 40);
		}
	}
}
