package snake.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;

import snake.model.*;

public class Singleplayer extends JPanel{
	
	private static final long serialVersionUID = -5571239145421408870L;

	private Game game;
	private BoardPanel board;
	private CustomImages images;
	private Rectangle play;
	private Menu menu;

	public Singleplayer(Game game, BoardPanel board, Menu menu){
		this.game = game;
		this.board = board;
		this.menu = menu;
		images = new CustomImages();
		int size = 20;
		JFormattedTextField inputWidth = new JFormattedTextField("50");
		JFormattedTextField inputHeight = new JFormattedTextField("50");
		setTextFieldFormat(inputWidth);
		setTextFieldFormat(inputHeight);
		this.add(inputWidth);
		this.add(inputHeight);
		inputWidth.getCaret().setVisible(false);
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		board.drawBackground(context2D, getWidth(), getHeight());
		menu.drawBoard(context2D, getWidth());
		drawPlay(context2D);
		

	}
	
	private void drawPlay(Graphics2D context){
		int width = 150;
		int height = 40;
		int x = getSize().width/2-width/2;
		int y = getSize().height-height-100;
		context.setColor(CustomColor.PANEL_COLOUR);
		play = new Rectangle(x, y, width, height);
		context.fillRect(x, y, width, height);
	}
	public Rectangle getPlay_btn() {
		return play;
	}
	
	private void setTextFieldFormat(JFormattedTextField txt){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setColumns(3);
	}
	public void insertString(int offs, String str, AttributeSet a){
		
	}
}