package snake.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

import snake.model.Game;


public class ViewMenuSinglePlayer extends JPanel{

	private Game game;
	private Rectangle play;
	private JFormattedTextField inputWidth, inputHeight;
	private boolean valid;
	private boolean filled;
	private static final long serialVersionUID = 2158879219007803798L;
	private JButton playButton;
	
	public ViewMenuSinglePlayer(Game game, View view){
		this.game = game;
		this.valid = true;
		this.filled = true;
		this.playButton = playButton();
		//this.setLayout(new GridLayout(8,1));
		
		chooseSize();
		
		this.add(this.playButton);

	}
	
	public JButton getPlayButton() {
		return playButton;
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		
		//Button
		//drawPlay(context2D);
		if (!valid){
			int x = getSize().width/2-20;
			int y = 150;
			context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
			context.setColor(Color.RED);
			context.drawString("Invalid input", x, y);
		} else if (!filled){
			int x = getSize().width/2-20;
			int y = 150;
			context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
			context.setColor(Color.RED);
			context.drawString("Type in game size", x, y);
		}
	}
	
	private JButton playButton(){
		JButton play = new JButton(new ImageIcon(Images.BUTTON_PLAY));
		play.setContentAreaFilled(false);
		play.setPreferredSize(new Dimension(140, 50));
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return play;
	}
	
	public Rectangle getPlay_btn() {
		return play;
	}
	
	private void chooseSize(){
		//Labels
		JLabel sizeLabel = new JLabel("GAME DIMENSIONS", SwingConstants.CENTER);
		JLabel sizeInfo = new JLabel("(Type in dimensions between 5 and 100)", SwingConstants.CENTER);
		JLabel xLabel = new JLabel("x");
		JLabel yLabel = new JLabel("y");
		JLabel gap = new JLabel("          ");
		customizeText(sizeLabel);
		customizeText(xLabel);
		customizeText(yLabel);
		customizeText(gap);
		
		//Formatter (limit input to three digits)
		MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter("###");
	    } catch (java.text.ParseException exc) {
	    	throw new RuntimeException("Formatter error");
	    }
	    
	    //Input fields
		inputWidth = new JFormattedTextField(formatter);
		inputHeight = new JFormattedTextField(formatter);
		inputWidth.setValue(game.getBoard().getWidth());
		inputHeight.setValue(game.getBoard().getHeight());
		setTextFieldFormat(inputWidth);
		setTextFieldFormat(inputHeight);
		
		JPanel sizeText = new JPanel();
		((FlowLayout)sizeText.getLayout()).setVgap(20);
		sizeText.add(sizeLabel);
		sizeText.add(sizeInfo);
		sizeText.setOpaque(false);
		
		JPanel sizePanel = new JPanel();
		sizePanel.add(xLabel);
		sizePanel.add(inputWidth);
		sizePanel.add(gap);
		sizePanel.add(yLabel);
		sizePanel.add(inputHeight);
		sizePanel.setOpaque(false);

		this.add(sizeText);
		this.add(sizePanel);

	}
	
	private void setTextFieldFormat(JFormattedTextField txt){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setColumns(3);
		txt.setFocusLostBehavior(JFormattedTextField.COMMIT);
	}

	private void customizeText(JLabel label){
		label.setForeground(Colors.PANEL_COLOUR);
		label.setFont(new Font("Sans_Serif", Font.BOLD, 20));
	}
	
	 
    public JFormattedTextField getWidthInput(){
    	return inputWidth;
    }
	
    public JFormattedTextField getHeightInput(){
    	return inputHeight;
    }
    
    public void setValid(boolean b){
    	valid = b;
    }

	public void setFilled(boolean b) {
		filled = b;		
	}
}