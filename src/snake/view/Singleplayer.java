package snake.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import snake.control.ControlSingleplayer;


public class Singleplayer extends JPanel implements FocusListener{
	private View view;
	private CustomImages images = new CustomImages();;
	//private Rectangle play;
	private JFormattedTextField inputWidth, inputHeight;
	private boolean valid, filled;
	private JPanel panel;
	private JButton play, easy, intermediate, hard;
	
	public Singleplayer(View view){
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
		setTextFieldFormat(inputWidth);
		setTextFieldFormat(inputHeight);
		
		//Buttons
		play = new JButton(new ImageIcon(images.play_btn));
		view.getMenu().setButton(play);
		
		easy = new JButton(new ImageIcon(images.easy_btn));
		view.getMenu().setButton(easy);
		intermediate = new JButton(new ImageIcon(images.intermediate_btn));
		view.getMenu().setButton(intermediate);
		intermediate.setBorderPainted(false);
		hard = new JButton(new ImageIcon(images.hard_btn));
		view.getMenu().setButton(hard);
		hard.setBorderPainted(false);
		
		//Control buttons
		ControlSingleplayer controlSingleplayer = new ControlSingleplayer(view);
		play.addActionListener(controlSingleplayer);
		play.setActionCommand("play");
		easy.addActionListener(controlSingleplayer);
		easy.setActionCommand("easy");
		intermediate.addActionListener(controlSingleplayer);
		intermediate.setActionCommand("intermediate");
		hard.addActionListener(controlSingleplayer);
		hard.setActionCommand("hard");
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getMenu().drawBoard(context2D, getWidth());
		
		//Set transparent panel in the board area
		Rectangle board = view.getMenu().getRectangleForMenu(getWidth());
		board.height = 400;
		panel.setBounds(board);
		panel.setOpaque(false);
		this.add(panel);
		
		//Text
		((Graphics2D) context).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.setColor(CustomColor.PANEL_COLOUR);
		context.drawString("GAME DIMENSIONS", panel.getX()+20, panel.getY()+30);
		context.drawString("SNAKE COLOUR", panel.getX()+20, panel.getY()+150);
		context.drawString("DIFFICULTY", panel.getX()+20, panel.getY()+270);
		
		context.setFont(new Font("Sans_Serif", Font.PLAIN, 11));
		context.drawString("(Type in dimensions between 5 and 100)", panel.getX()+230, panel.getY()+26);
		
		context.setFont(new Font("Sans_Serif", Font.BOLD, 15));
		context.drawString("width", getWidth()/2-150, panel.getY()+70);
		context.drawString("height", getWidth()/2-8, panel.getY()+70);
		inputWidth.setBounds(150, panel.getY()+40, 50, 30);
		inputHeight.setBounds(panel.getWidth()-200, panel.getY()+40, 50, 30);
		panel.add(inputWidth);
		panel.add(inputHeight);

		
		//Buttons
		int buttonWidth = images.play_btn.getWidth();
		int buttonHeight = images.play_btn.getHeight();

		//Add difficulty buttons
		int space = (panel.getWidth()-3*buttonWidth)/4; //space between buttons
		easy.setBounds(space, panel.getY()+280, buttonWidth, buttonHeight);
		intermediate.setBounds(2*space+buttonWidth, panel.getY()+280, buttonWidth, buttonHeight);
		hard.setBounds(3*space+2*buttonWidth, panel.getY()+280, buttonWidth, buttonHeight);
		panel.add(easy);
		panel.add(intermediate);
		panel.add(hard);
		
		//Add play button
		int xPlay = getSize().width/2-buttonWidth/2;
		int yPlay = view.getMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		play.setBounds(xPlay, yPlay, buttonWidth, buttonHeight);
		this.add(play);
		
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
	
	private void setTextFieldFormat(JFormattedTextField txt){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFocusLostBehavior(JFormattedTextField.COMMIT); //Commit the new input
		txt.setValue("100");
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
}