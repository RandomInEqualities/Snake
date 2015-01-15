package snake.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

import snake.model.Board;


public class ViewOptions extends JPanel implements FocusListener {
	
	private View view;
	private boolean valid, filled;
	protected JPanel panelOptions;
	private JButton buttonPlay;
	private JButton buttonKindergarten;
	private JButton buttonEasy;
	private JButton buttonIntermediate;
	private JButton buttonHard;
	private JButton buttonBack;
	private JFormattedTextField inputFieldWidth;
	private JFormattedTextField inputFieldHeight;
	
	public ViewOptions(View view, Board board){
		this.view = view;
		this.valid = true;
		this.filled = true;
		panelOptions = new JPanel(); //Game options panel

		// Formatter (limit input to three digits)
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("###");
		} catch (java.text.ParseException exc) {
			throw new RuntimeException("Formatter error");
		}

		// Text input fields
		inputFieldWidth = new JFormattedTextField(formatter);
		inputFieldHeight = new JFormattedTextField(formatter);
		
		inputFieldWidth.addFocusListener(this);
		inputFieldHeight.addFocusListener(this);
		String gameWidth = Integer.toString(board.getWidth());
		String gameHeight = Integer.toString(board.getHeight());
		setTextFieldFormat(inputFieldWidth, gameWidth);
		setTextFieldFormat(inputFieldHeight, gameHeight);
		
		createButtons();
		
		this.add(panelOptions);
		panelOptions.add(inputFieldWidth);
		panelOptions.add(inputFieldHeight);
		panelOptions.add(buttonKindergarten);
		panelOptions.add(buttonEasy);
		panelOptions.add(buttonIntermediate);
		panelOptions.add(buttonHard);
		this.add(buttonBack);
		this.add(buttonPlay);
		
	}
	
	public JButton getPlayButton() {
		return buttonPlay;
	}
	
	public JButton getBackButton() {
		return buttonBack;
	}
	
	public JButton getKindergartenButton() {
		return buttonKindergarten;
	}
	
	public JButton getEasyButton() {
		return buttonEasy;
	}
	
	public JButton getIntermediateButton() {
		return buttonIntermediate;
	}
	
	public JButton getHardButton() {
		return buttonHard;
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		Rectangle menuRect = ViewMenu.computeMenuRectangle(getSize());
		
		// Background
		ViewMenu.drawTileBackground(context2D, getVisibleRect());
		ViewMenu.drawMenuBackground(context2D, menuRect);
		
		// Set transparent panel in the board area
		menuRect.height = 400;
		panelOptions.setBounds(menuRect);
		panelOptions.setOpaque(false);
		
		drawText(context2D);
		setDifficultyButtons();
		setPlayAndBackButtons();
		printErrorMessage(context2D);
	}
	
	private void setTextFieldFormat(JFormattedTextField text, String value) {
		text.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setFocusLostBehavior(JFormattedTextField.COMMIT); // Commit the new input
		text.setValue(value);
	}
	 
    public JFormattedTextField getWidthInput() {
    	return inputFieldWidth;
    }
	
    public JFormattedTextField getHeightInput() {
    	return inputFieldHeight;
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
		return buttonEasy;
	}
	
	public JButton getIntermediate(){
		return buttonIntermediate;
	}
	
	public JButton getHard(){
		return buttonHard;
	}
	
	public JPanel getPanel(){
		return panelOptions;
	}
	
	public void createButtons(){
		buttonPlay = new JButton(new ImageIcon(Images.BUTTON_PLAY));
		buttonBack = new JButton(new ImageIcon(Images.BUTTON_BACK));
		ViewMenu.setMenuButtonParameters(buttonBack);
		ViewMenu.setMenuButtonParameters(buttonPlay);
		
		buttonKindergarten = new JButton(new ImageIcon(Images.DIFFICULTY_KINDERGARTEN));
		buttonEasy = new JButton(new ImageIcon(Images.DIFFICULTY_EASY));
		buttonIntermediate = new JButton(new ImageIcon(Images.DIFFICULTY_INTERMEDIATE));
		buttonHard = new JButton(new ImageIcon(Images.DIFFICULTY_HARD));
		setDifficultyButtonParameters(buttonKindergarten);
		setDifficultyButtonParameters(buttonEasy);
		setDifficultyButtonParameters(buttonIntermediate);
		setDifficultyButtonParameters(buttonHard);
		
		buttonPlay.setActionCommand("play");
		buttonBack.setActionCommand("back");
		buttonKindergarten.setActionCommand("kindergarten");
		buttonEasy.setActionCommand("easy");
		buttonIntermediate.setActionCommand("intermediate");
		buttonHard.setActionCommand("hard");
		
		// Default difficulty = kindergarten
		buttonKindergarten.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
	}
	
	private void printErrorMessage(Graphics2D context){
		int y = 110;
		context.setColor(Color.RED);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		if (!valid){
			int x = getWidth()/2-40;
			context.drawString("Invalid input", x, y);
		} 
		else if (!filled){
			int x = getWidth()/2-80;
			context.drawString("Choose a width and a height", x, y);
		}
	}
	
	private void setPlayAndBackButtons(){
		// Play button and back button
		int width = getWidth();
		int height = getHeight();
		int xBack = width - buttonBack.getWidth() - 10;
		int xPlay = width + 10;
		int y = height - buttonPlay.getHeight() - 20;
		buttonBack.setLocation(xBack, y);
		buttonPlay.setLocation(xPlay, y);
	}
	
	private void setDifficultyButtons(){
		// Difficulty buttons
		//space between buttons
		int buttonWidth = buttonEasy.getWidth();
		int space = (panelOptions.getWidth()-4*buttonWidth)/5; 
		buttonKindergarten.setLocation(space, panelOptions.getY()+280);
		buttonEasy.setLocation(2*space+buttonWidth, panelOptions.getY()+280);
		buttonIntermediate.setLocation(3*space+2*buttonWidth, panelOptions.getY()+280);
		buttonHard.setLocation(4*space+3*buttonWidth, panelOptions.getY()+280);
	}
	
	private void drawText(Graphics2D context2D){
		context2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context2D.setColor(Colors.PANEL_COLOUR);
		context2D.drawString("GAME DIMENSIONS", panelOptions.getX()+20, panelOptions.getY()+30);
		context2D.drawString("SNAKE COLOUR", panelOptions.getX()+20, panelOptions.getY()+150);
		context2D.drawString("DIFFICULTY", panelOptions.getX()+20, panelOptions.getY()+270);
		
		context2D.setFont(new Font("Sans_Serif", Font.PLAIN, 11));
		String message = "(Type in dimensions between " + Board.MIN_WIDTH + " and " + Board.MAX_WIDTH + ")";
		context2D.drawString(message, panelOptions.getX()+230, panelOptions.getY()+26);
		
		context2D.setFont(new Font("Sans_Serif", Font.BOLD, 15));
		context2D.drawString("width", getWidth()/2-150, panelOptions.getY()+70);
		context2D.drawString("height", getWidth()/2-8, panelOptions.getY()+70);
		inputFieldWidth.setBounds(150, panelOptions.getY()+40, 50, 30);
		inputFieldHeight.setBounds(panelOptions.getWidth()-200, panelOptions.getY()+40, 50, 30);
	}
	
	private static void setDifficultyButtonParameters(JButton button){
		int width = Images.DIFFICULTY_EASY.getWidth();
		int height = Images.DIFFICULTY_EASY.getHeight();
		button.setPreferredSize(new Dimension(width, height));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
	}
	
	protected static void setColourButtonParameters(JButton button){
		int sizeColour = 30;
		button.setPreferredSize(new Dimension(sizeColour, sizeColour));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
	}
	
}
