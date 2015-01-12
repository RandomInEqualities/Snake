package snake.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

import snake.model.Game;


public class ViewMenuSinglePlayer extends JPanel implements FocusListener {
	
	private static final long serialVersionUID = 3906794996977484644L;
	
	private View view;
	private JFormattedTextField inputWidth, inputHeight;
	private boolean valid, filled;
	private JPanel panel;
	private JButton play, easy, intermediate, hard, back, green, blue, red, yellow;
	
	public ViewMenuSinglePlayer(View view, Game game){
		this.view = view;
		this.valid = true;
		this.filled = true;
		panel = new JPanel(); //Game options panel
		
		//Formatter (limit input to three digits)
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("###");
		} catch (java.text.ParseException exc) {
				throw new RuntimeException("Formatter error");
		}
		
		//Text input fields
		inputWidth = new JFormattedTextField(formatter);
		inputHeight = new JFormattedTextField(formatter);
		inputWidth.addFocusListener(this);
		inputHeight.addFocusListener(this);
		String gameWidth = Integer.toString(game.getBoard().getWidth());
		String gameHeight = Integer.toString(game.getBoard().getHeight());
		setTextFieldFormat(inputWidth, gameWidth);
		setTextFieldFormat(inputHeight, gameHeight);
		
		//Buttons
		back = new JButton(new ImageIcon(Images.BUTTON_BACK));
		view.getViewMenu().setButton(back);
		play = new JButton(new ImageIcon(Images.BUTTON_PLAY));
		view.getViewMenu().setButton(play);
		
		easy = new JButton(new ImageIcon(Images.DIFFICULTY_EASY));
		view.getViewMenu().setButton(easy);
		easy.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		intermediate = new JButton(new ImageIcon(Images.DIFFICULTY_INTERMEDIATE));
		view.getViewMenu().setButton(intermediate);
		intermediate.setBorderPainted(false);
		hard = new JButton(new ImageIcon(Images.DIFFICULTY_HARD));
		view.getViewMenu().setButton(hard);
		hard.setBorderPainted(false);
		
		green = new JButton();
		view.getViewMenu().setButton(green);
		green.setBackground(Colors.GREEN);
		green.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue = new JButton();
		view.getViewMenu().setButton(blue);
		blue.setBorderPainted(false);
		blue.setBackground(Colors.BLUE);
		red = new JButton();
		view.getViewMenu().setButton(red);
		red.setBorderPainted(false);
		red.setBackground(Colors.RED);
		yellow = new JButton();
		view.getViewMenu().setButton(yellow);
		yellow.setBorderPainted(false);
		yellow.setBackground(Colors.YELLOW);
		
		//adds
		this.add(panel);
		panel.add(inputWidth);
		panel.add(inputHeight);
		panel.add(easy);
		panel.add(intermediate);
		panel.add(hard);
		panel.add(green);
		panel.add(blue);
		panel.add(red);
		panel.add(yellow);
		this.add(back);
		this.add(play);
		
	}
	
	public JButton getPlayButton() {
		return play;
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	public JButton getEasyButton() {
		return easy;
	}
	
	public JButton getIntermediateButton() {
		return intermediate;
	}
	
	public JButton getHardButton() {
		return hard;
	}

	public JButton getGreenButton() {
		return green;
	}
	public JButton getBlueButton() {
		return blue;
	}
	public JButton getRedButton() {
		return red;
	}
	public JButton getYellowButton() {
		return yellow;
	}
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getViewMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getViewMenu().drawBoard(context2D, getWidth());
		
		//Set transparent panel in the board area
		Rectangle board = view.getViewMenu().getRectangleForMenu(getWidth());
		board.height = 400;
		panel.setBounds(board);
		panel.setOpaque(false);
		
		//Text
		context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context2D.setColor(Colors.PANEL_COLOUR);
		context2D.drawString("GAME DIMENSIONS", panel.getX()+20, panel.getY()+30);
		context2D.drawString("SNAKE COLOUR", panel.getX()+20, panel.getY()+150);
		context2D.drawString("DIFFICULTY", panel.getX()+20, panel.getY()+270);
		
		context2D.setFont(new Font("Sans_Serif", Font.PLAIN, 11));
		context2D.drawString("(Type in dimensions between 5 and 100)", panel.getX()+230, panel.getY()+26);
		
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 15));
		context2D.drawString("width", getWidth()/2-150, panel.getY()+70);
		context2D.drawString("height", getWidth()/2-8, panel.getY()+70);
		inputWidth.setBounds(150, panel.getY()+40, 50, 30);
		inputHeight.setBounds(panel.getWidth()-200, panel.getY()+40, 50, 30);

		//Buttons
		int buttonWidth = Images.BUTTON_PLAY.getWidth();
		int buttonHeight = Images.BUTTON_PLAY.getHeight();

		drawColourButtons(context2D);
		
		//Difficulty buttons
		int space = (panel.getWidth()-3*buttonWidth)/4; //space between buttons
		easy.setBounds(space, panel.getY()+280, buttonWidth, buttonHeight);
		intermediate.setBounds(2*space+buttonWidth, panel.getY()+280, buttonWidth, buttonHeight);
		hard.setBounds(3*space+2*buttonWidth, panel.getY()+280, buttonWidth, buttonHeight);
	
		//Play button and back button
		int xBack = getSize().width/2-buttonWidth-10;
		int xPlay = getSize().width/2+10;
		int yPlay = view.getViewMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		back.setBounds(xBack, yPlay, buttonWidth, buttonHeight);
		play.setBounds(xPlay, yPlay, buttonWidth, buttonHeight);
		
		//Error message
		int y = 110;
		context.setColor(Color.RED);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		if (!valid){
			int x = getWidth()/2-40;
			context.drawString("Invalid input", x, y);
		} else if (!filled){
			int x = getWidth()/2-80;
			context.drawString("Choose a width and a height", x, y);
		}
	}
	
	private void setTextFieldFormat(JFormattedTextField txt, String value){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFocusLostBehavior(JFormattedTextField.COMMIT); //Commit the new input
		txt.setValue(value);
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

	@Override
	public void focusGained(FocusEvent e) {
		String text = ((JTextComponent) e.getSource()).getText();
		((JTextComponent) e.getSource()).setText(text.trim()); //remove whitespace from text fields
	}

	@Override
	public void focusLost(FocusEvent e) {
		view.requestFocus();
	}
	
	public JButton getEasy(){
		return easy;
	}
	
	public JButton getIntermediate(){
		return intermediate;
	}
	
	public JButton getHard(){
		return hard;
	}
	
	public void drawColourButtons(Graphics2D context){
		int gap = 10;
		int sizeColour = 30;
		int xBlue = panel.getWidth()/2-sizeColour-gap/2;
		int yColour = panel.getY()+180;
		green.setBounds(xBlue-gap-sizeColour, yColour, sizeColour, sizeColour);
		blue.setBounds(xBlue, yColour, sizeColour, sizeColour);
		red.setBounds(xBlue+gap+sizeColour, yColour, sizeColour, sizeColour);
		yellow.setBounds(xBlue+2*gap+2*sizeColour, yColour, sizeColour, sizeColour);
	}
	
	public JPanel getPanel(){
		return panel;
	}
}
