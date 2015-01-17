package snake.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

import snake.model.Board;


/**
 * Base JPanel for the option menus. The single and multiplayer option menus derive from
 * this. This class allows choosing a game size and game difficulty.
 */
public abstract class OptionsBasePanel extends JPanel implements FocusListener {
	
	private static final Font TEXT_FONT_LARGE = new Font("Sans_Serif", Font.PLAIN, 20);
	private static final Font TEXT_FONT_MEDIUM = new Font("Sans_Serif", Font.PLAIN, 15);
	private static final Font TEXT_FONT_SMALL = new Font("Sans_Serif", Font.PLAIN, 11);
	private static final Border THICK_BUTTON_BORDER = new LineBorder(ResourceColors.PANEL_COLOR, 3);
	
	protected JPanel gameOptionsPanel;
	protected JButton buttonPlay;
	protected JButton buttonKindergarten;
	protected JButton buttonEasy;
	protected JButton buttonIntermediate;
	protected JButton buttonHard;
	protected JButton buttonBack;
	
	private JFormattedTextField inputFieldWidth;
	private JFormattedTextField inputFieldHeight;
	private JLabel errorField;
	private boolean inputFieldInValid;
	private boolean inputFieldEmpty;
	
	public OptionsBasePanel() {
		this.inputFieldInValid = false;
		this.inputFieldEmpty = false;
		this.gameOptionsPanel = new JPanel();
		this.errorField = new JLabel();
		
		// Formatter (limit text field input to three digits).
		try {
			this.inputFieldWidth = new JFormattedTextField(new MaskFormatter("###"));
			this.inputFieldHeight = new JFormattedTextField(new MaskFormatter("###"));
		} 
		catch (java.text.ParseException exc) {
			throw new RuntimeException("Formatter error");
		}

		// Text input fields.
		inputFieldWidth.addFocusListener(this);
		inputFieldHeight.addFocusListener(this);
		setTextField(inputFieldWidth, "20");
		setTextField(inputFieldHeight, "20");
		errorField.setForeground(Color.RED);
		
		// Buttons.
		buttonPlay = MenuPanel.createMenuButton(ResourceImages.BUTTON_PLAY);
		buttonBack = MenuPanel.createMenuButton(ResourceImages.BUTTON_BACK);
		buttonKindergarten = createDifficultyButton(ResourceImages.DIFFICULTY_KINDERGARTEN);
		buttonEasy = createDifficultyButton(ResourceImages.DIFFICULTY_EASY);
		buttonIntermediate = createDifficultyButton(ResourceImages.DIFFICULTY_INTERMEDIATE);
		buttonHard = createDifficultyButton(ResourceImages.DIFFICULTY_HARD);
		
		buttonPlay.setActionCommand("play");
		buttonBack.setActionCommand("back");
		buttonKindergarten.setActionCommand("kindergarten");
		buttonEasy.setActionCommand("easy");
		buttonIntermediate.setActionCommand("intermediate");
		buttonHard.setActionCommand("hard");
		
		gameOptionsPanel.add(inputFieldWidth);
		gameOptionsPanel.add(inputFieldHeight);
		gameOptionsPanel.add(errorField);
		gameOptionsPanel.add(buttonKindergarten);
		gameOptionsPanel.add(buttonEasy);
		gameOptionsPanel.add(buttonIntermediate);
		gameOptionsPanel.add(buttonHard);
		gameOptionsPanel.add(buttonBack);
		gameOptionsPanel.add(buttonPlay);
		gameOptionsPanel.setOpaque(false);
		
		add(gameOptionsPanel);
	}
	
	// Used to implement group toggling behaviour. The control object should call this method
	// when a button is pressed.
	public void buttonPress(String button) {
		if (button == "kindergarten") {
			setActiveButton(buttonKindergarten, buttonEasy, buttonIntermediate, buttonHard);
		}
		else if (button == "easy") {
			setActiveButton(buttonEasy, buttonKindergarten, buttonIntermediate, buttonHard);
		}
		else if (button == "intermediate") {
			setActiveButton(buttonIntermediate, buttonEasy, buttonKindergarten, buttonHard);
		}
		else if (button == "hard") {
			setActiveButton(buttonHard, buttonEasy, buttonIntermediate, buttonKindergarten);
		}
	}
	
	// Set the default values in the game size fields.
	public void setDefaultGameSizeInput(int width, int height) {
		setTextField(inputFieldWidth, Integer.toString(width));
		setTextField(inputFieldHeight, Integer.toString(height));
	}
	
	// Get the value in the game size fields. Returns null and displays an error message in the 
	// window if the input fields have invalid data.
	public Dimension getChosenGameSize() {
		String widthRaw = inputFieldWidth.getText().replace(" ", "");
		String heightRaw = inputFieldHeight.getText().replace(" ", "");
		if (widthRaw.isEmpty() || heightRaw.isEmpty()) {
			inputFieldEmpty = true;
			inputFieldInValid = false;
			repaint();
			return null;
		}
		
		int width = 0;
		int height = 0;
		try {
			width = Integer.parseInt(widthRaw);
			height = Integer.parseInt(heightRaw);
		}
		catch (NumberFormatException error) {
			inputFieldEmpty = false;
			inputFieldInValid = true;
			repaint();
			return null;
		}
		
		if (width < Board.MIN_WIDTH || width > Board.MAX_WIDTH || height < Board.MIN_HEIGHT || height > Board.MAX_HEIGHT) {
			inputFieldEmpty = false;
			inputFieldInValid = true;
			repaint();
			return null;
		}
		
		inputFieldEmpty = false;
		inputFieldInValid = false;
		return new Dimension(width, height);
	}
	
	// Place caret after the number when focus in the input fields is gained.
	@Override
	public void focusGained(FocusEvent event) {
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                JTextField text = (JTextField)event.getSource();
                
                // Text without whitespace.
                String s = text.getText().replace(" ", "");
                
                // Position caret.
                text.setCaretPosition(s.length());
            }
        });
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		// Background
		Rectangle menuRect = MenuPanel.computeMenuRectangle(getSize());
		MenuPanel.drawTileBackground(context2D, getVisibleRect());
		MenuPanel.drawMenuBackground(context2D, menuRect);
		
		// Set transparent panel in the board area.
		gameOptionsPanel.setBounds(menuRect);
		
		drawText(context2D);
		drawDifficultyButtons();
		drawPlayAndBackButtons();
		drawErrorMessage(inputFieldInValid, inputFieldEmpty);
		
		// Text fields.
		int xWidth = gameOptionsPanel.getWidth()/2 - inputFieldWidth.getWidth() - 50;
		int xHeight = gameOptionsPanel.getWidth()/2 + 50;
		int y = 50;
		inputFieldWidth.setLocation(xWidth, y);
		inputFieldHeight.setLocation(xHeight, y);
	}
	
	private void drawText(Graphics2D context){
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		int panelX = gameOptionsPanel.getX();
		int panelY = gameOptionsPanel.getY();
		int panelWidth = gameOptionsPanel.getWidth();
		
		context.setFont(TEXT_FONT_LARGE);
		context.setColor(ResourceColors.PANEL_COLOR);
		context.drawString("GAME DIMENSIONS", panelX + 20, panelY + 30);
		context.drawString("SNAKE COLOR", panelX + 20, panelY + 150);
		context.drawString("DIFFICULTY", panelX + 20, panelY + 270);
		
		context.setFont(TEXT_FONT_SMALL);
		String message = "(Type in dimensions between " + Board.MIN_WIDTH + " and " + Board.MAX_WIDTH + ")";
		context.drawString(message, panelX + 230, panelY + 26);
		
		context.setFont(TEXT_FONT_MEDIUM);
		context.drawString("width", getWidth()/2 - 150, panelY + 70);
		context.drawString("height", getWidth()/2 - 8, panelY + 70);
		inputFieldWidth.setBounds(150, panelY + 40, 50, 30);
		inputFieldHeight.setBounds(panelWidth - 200, panelY + 40, 50, 30);
	}
	
	private void drawErrorMessage(boolean inValidInput, boolean emptyInput) {
		if (inValidInput){
			int x = gameOptionsPanel.getWidth()/2 - 80;
			int y = 90;
			errorField.setLocation(x,  y);
			errorField.setText("Invalid width and height");
		} 
		else if (emptyInput){
			int x = gameOptionsPanel.getWidth()/2 - 80;
			int y = 90;
			errorField.setLocation(x, y);
			errorField.setText("Choose width and height");
		} 
		else {
			errorField.setText("");
		}
	}
	
	private void drawPlayAndBackButtons() {
		int width = gameOptionsPanel.getWidth();
		int height = gameOptionsPanel.getHeight();
		int xBack = width/2 - buttonBack.getWidth() - 10;
		int xPlay = width/2 + 10;
		int y = height - buttonPlay.getHeight() - 20;
		buttonBack.setLocation(xBack, y);
		buttonPlay.setLocation(xPlay, y);
	}
	
	private void drawDifficultyButtons() {
		int spaceBetweenButtons = (gameOptionsPanel.getWidth() - 4*buttonEasy.getWidth())/5;
		int y = 300;
		buttonKindergarten.setLocation(spaceBetweenButtons, y);
		buttonEasy.setLocation(2*spaceBetweenButtons + buttonEasy.getWidth(), y);
		buttonIntermediate.setLocation(3*spaceBetweenButtons + 2*buttonIntermediate.getWidth(), y);
		buttonHard.setLocation(4*spaceBetweenButtons + 3*buttonHard.getWidth(), y);
	}
	
	protected void setActiveButton(JButton buttonPressed, JButton button1, JButton button2, JButton button3){
		buttonPressed.setBorderPainted(true);
		buttonPressed.setBorder(THICK_BUTTON_BORDER);
		button1.setBorderPainted(false);
		button2.setBorderPainted(false);
		button3.setBorderPainted(false);
	}
	
	protected static JButton createDifficultyButton(BufferedImage image) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(THICK_BUTTON_BORDER);
		button.setBorderPainted(false);
		button.setFocusable(false);
		return button;
	}
	
	protected static JButton createColorButton(BufferedImage image) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(THICK_BUTTON_BORDER);
		button.setBorderPainted(false);
		button.setFocusable(false);
		return button;
	}
	
	private void setTextField(JFormattedTextField textField, String value) {
		textField.setFont(TEXT_FONT_LARGE); 
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setValue(value);
		textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
	}
	
}
