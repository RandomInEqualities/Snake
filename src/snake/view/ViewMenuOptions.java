package snake.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

import snake.model.Board;
import snake.model.GameSinglePlayer;


public class ViewMenuOptions extends JPanel implements FocusListener {
	private View view;
	private JFormattedTextField inputWidth, inputHeight;
	private boolean valid, filled;
	protected JPanel panel;
	private JButton play, kindergarten, easy, intermediate, hard, back;
	private int buttonWidth, buttonHeight, difficultyWidth, difficultyHeight;
	
	public ViewMenuOptions(View view, Board board){
		this.view = view;
		this.valid = true;
		this.filled = true;
		panel = new JPanel(); //Game options panel
		
		// Button size
		buttonWidth = Images.BUTTON_PLAY.getWidth();
		buttonHeight = Images.BUTTON_PLAY.getHeight();
		difficultyWidth = Images.DIFFICULTY_EASY.getWidth();
		difficultyHeight = Images.DIFFICULTY_EASY.getHeight();

		// Formatter (limit input to three digits)
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("###");
		} catch (java.text.ParseException exc) {
			throw new RuntimeException("Formatter error");
		}

		// Text input fields
		inputWidth = new JFormattedTextField(formatter);
		inputHeight = new JFormattedTextField(formatter);
		
		inputWidth.addFocusListener(this);
		inputHeight.addFocusListener(this);
		String gameWidth = Integer.toString(board.getWidth());
		String gameHeight = Integer.toString(board.getHeight());
		setTextFieldFormat(inputWidth, gameWidth);
		setTextFieldFormat(inputHeight, gameHeight);
		
		createButtons();
		
		this.add(panel);
		panel.add(inputWidth);
		panel.add(inputHeight);
		panel.add(kindergarten);
		panel.add(easy);
		panel.add(intermediate);
		panel.add(hard);
		this.add(back);
		this.add(play);
		
	}
	
	public JButton getPlayButton() {
		return play;
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	public JButton getKindergartenButton() {
		return kindergarten;
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
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		// Background
		view.getViewMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getViewMenu().drawBoard(context2D, getWidth());
		
		// Set transparent panel in the board area
		Rectangle board = view.getViewMenu().getRectangleForMenu(getWidth());
		board.height = 400;
		panel.setBounds(board);
		panel.setOpaque(false);
		
		drawText(context2D);
		setDifficultyButtons();
		setPlayBackButtons();
		printErrorMessage(context2D);
	}
	
	private void setTextFieldFormat(JFormattedTextField text, String value) {
		text.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setFocusLostBehavior(JFormattedTextField.COMMIT); // Commit the new input
		text.setValue(value);
	}
	 
    public JFormattedTextField getWidthInput() {
    	return inputWidth;
    }
	
    public JFormattedTextField getHeightInput() {
    	return inputHeight;
    }
    
    public void setValid(boolean b) {
    	valid = b;
    }

	public void setFilled(boolean b) {
		filled = b;		
	}

	// Place caret after the number when focus in the text field is gained
	@Override
	public void focusGained(FocusEvent e) {
		SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                JTextField text = (JTextField)e.getSource();
                String s = text.getText().replace(" ", ""); //get text without whitespace
                text.setCaretPosition(s.length()); //position caret
            }
        });
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
	
	public JPanel getPanel(){
		return panel;
	}
	
	public void createButtons(){
		back = new JButton(new ImageIcon(Images.BUTTON_BACK));
		view.getViewMenu().setCommonButtonParameters(back);
		play = new JButton(new ImageIcon(Images.BUTTON_PLAY));
		view.getViewMenu().setCommonButtonParameters(play);
		
		kindergarten = new JButton(new ImageIcon(Images.DIFFICULTY_KINDERGARTEN));
		view.getViewMenu().setCommonButtonParameters(kindergarten);
		kindergarten.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3)); //default difficulty = kindergarten
		easy = new JButton(new ImageIcon(Images.DIFFICULTY_EASY));
		view.getViewMenu().setOptionButton(easy);
		intermediate = new JButton(new ImageIcon(Images.DIFFICULTY_INTERMEDIATE));
		view.getViewMenu().setOptionButton(intermediate);
		hard = new JButton(new ImageIcon(Images.DIFFICULTY_HARD));
		view.getViewMenu().setOptionButton(hard);
	}
	private void printErrorMessage(Graphics2D context){
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
	private void setPlayBackButtons(){
		// Play button and back button
		int xBack = getSize().width/2-buttonWidth-10;
		int xPlay = getSize().width/2+10;
		int yPlay = view.getViewMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		back.setBounds(xBack, yPlay, buttonWidth, buttonHeight);
		play.setBounds(xPlay, yPlay, buttonWidth, buttonHeight);
	}
	
	private void setDifficultyButtons(){
		// Difficulty buttons
		int space = (panel.getWidth()-4*difficultyWidth)/5; //space between buttons
		kindergarten.setBounds(space, panel.getY()+280, difficultyWidth, difficultyHeight);
		easy.setBounds(2*space+difficultyWidth, panel.getY()+280, difficultyWidth, difficultyHeight);
		intermediate.setBounds(3*space+2*difficultyWidth, panel.getY()+280, difficultyWidth, difficultyHeight);
		hard.setBounds(4*space+3*difficultyWidth, panel.getY()+280, difficultyWidth, difficultyHeight);
	}
	
	private void drawText(Graphics2D context2D){
		context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context2D.setColor(Colors.PANEL_COLOUR);
		context2D.drawString("GAME DIMENSIONS", panel.getX()+20, panel.getY()+30);
		context2D.drawString("SNAKE COLOUR", panel.getX()+20, panel.getY()+150);
		context2D.drawString("DIFFICULTY", panel.getX()+20, panel.getY()+270);
		
		context2D.setFont(new Font("Sans_Serif", Font.PLAIN, 11));
		String message = "(Type in dimensions between " + Board.MIN_WIDTH + " and " + Board.MAX_WIDTH + ")";
		context2D.drawString(message, panel.getX()+230, panel.getY()+26);
		
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 15));
		context2D.drawString("width", getWidth()/2-150, panel.getY()+70);
		context2D.drawString("height", getWidth()/2-8, panel.getY()+70);
		inputWidth.setBounds(150, panel.getY()+40, 50, 30);
		inputHeight.setBounds(panel.getWidth()-200, panel.getY()+40, 50, 30);
	}
}
