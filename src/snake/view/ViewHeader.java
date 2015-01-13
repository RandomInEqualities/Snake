
package snake.view;
import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

import snake.model.GameSinglePlayer;


public class ViewHeader extends JPanel implements Observer {
	
	private static final long serialVersionUID = -3944388732646932230L;
	private static final int DEFAULT_LOGO_WIDTH = 300;
	private static final int DEFAULT_LOGO_HEIGHT = 80;
	
	private GameSinglePlayer game;
	private View view;
	private boolean showScore;
	private Image logo;
	private Font font;

	public ViewHeader(View view, GameSinglePlayer game, boolean showScore) {
		super();
		
		if (game == null) {
			throw new NullPointerException();
		}
		this.font = new Font("Sans_Serif", Font.BOLD, 20);
		this.view = view;
		this.game = game;
		this.showScore = showScore;
		this.logo = Images.LOGO.getScaledInstance(DEFAULT_LOGO_WIDTH, DEFAULT_LOGO_HEIGHT, Image.SCALE_SMOOTH);
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
		Graphics2D context2D = (Graphics2D)context;
		
		// Only show logo if board is wide enough to contain it.
		Dimension size = getSize();
		int logoWidth = logo.getWidth(null);
		if (size.width > logoWidth + 230 || (!showScore && size.width > logoWidth)) {
			context2D.drawImage(logo, size.width/2 - logo.getWidth(null)/2, 0, null);
		}
		
		// Update the score (if we need to show scores).
		if (showScore) {
			context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			context2D.setFont(this.font);
			int fontSize = font.getSize();
			context2D.setColor(Color.WHITE);
			context2D.drawString("Score: " + game.getScore(), fontSize, size.height/2 - fontSize/2);
		}
		
		// Sound icon
		Image soundIcon;		
		if (view.getAudio().isMuted()){
			soundIcon = Images.SOUND_OFF;
		} else {
			soundIcon = Images.SOUND_ON;
		}
		context2D.drawImage(soundIcon, size.width-Images.SOUND_OFF.getWidth()-10, 0, null);
		
		// Key info
		context2D.drawImage(Images.INFO_KEYS, size.width-Images.INFO_KEYS.getWidth()-10, 35, null);
	}
	
}
