package snake.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;


public class ViewOptionsDifficulty extends JPanel {
	
	public enum Difficulty {
		KINDERGARTEN,
		EASY,
		INTERMEDIATE,
		HARD
	}
	
	private final int BUTTON_WIDTH = Images.DIFFICULTY_EASY.getWidth();
	private final int BUTTON_HEIGHT = Images.DIFFICULTY_EASY.getHeight();
	private static final Insets BUTTON_MARGIN = new Insets(5, 5, 5, 5);
	private static final Font font = new Font("Sans_Serif", Font.PLAIN, 20);
	
	private JToggleButton buttonKindergarten;
	private JToggleButton buttonEasy;
	private JToggleButton buttonIntermediate;
	private JToggleButton buttonHard;

	public ViewOptionsDifficulty() {
		super();
		
		buttonKindergarten = createDifficultyButton(Images.DIFFICULTY_KINDERGARTEN, false);
		buttonEasy = createDifficultyButton(Images.DIFFICULTY_EASY, true);
		buttonIntermediate = createDifficultyButton(Images.DIFFICULTY_INTERMEDIATE, false);
		buttonHard = createDifficultyButton(Images.DIFFICULTY_HARD, false);
		
		setOpaque(false);
		setLayout(new GridBagLayout());
		
		JLabel text = new JLabel("DIFFICULTY");
		text.setFont(font);
		
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.insets = BUTTON_MARGIN;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.gridwidth = 4;
		constraint.weightx = 1.0;
		add(text, constraint);
		constraint.gridx = 0;
		constraint.gridy = 1;
		constraint.gridwidth = 1;
		constraint.weightx = 0.0;
		add(buttonKindergarten, constraint);
		constraint.gridx = 1;
		constraint.gridy = 1;
		constraint.weightx = 0.0;
		add(buttonEasy, constraint);
		constraint.gridx = 2;
		constraint.gridy = 1;
		constraint.weightx = 0.0;
		add(buttonIntermediate, constraint);
		constraint.gridx = 3;
		constraint.gridy = 1;
		constraint.weightx = 0.0;
		add(buttonHard, constraint);
		
		// Add control to each button such that only one can be selected at a time.
		JToggleButton[] buttons = {buttonKindergarten, buttonEasy, buttonIntermediate, buttonHard};
		ActionListener buttonListener = new ActionListener(){
		    public void actionPerformed(ActionEvent event) {
		    	// Find the button that was clicked.
		    	JToggleButton button = (JToggleButton)event.getSource();
		    	button.setBorderPainted(true);
		    	
		    	// Make sure the other buttons is not selected.
		    	for (JToggleButton otherButton : buttons) {
		    		if (button != otherButton) {
		    			otherButton.setBorderPainted(false);
		    			otherButton.setSelected(false);
		    		}
		    	}
		    }
		};
		buttonKindergarten.addActionListener(buttonListener);
		buttonEasy.addActionListener(buttonListener);
		buttonIntermediate.addActionListener(buttonListener);
		buttonHard.addActionListener(buttonListener);
		
	}
	
	public Difficulty getSelectedDifficulty() {
		if (buttonKindergarten.isSelected()) {
			return Difficulty.KINDERGARTEN;
		}
		else if (buttonEasy.isSelected()) {
			return Difficulty.EASY;
		}
		else if (buttonIntermediate.isSelected()) {
			return Difficulty.INTERMEDIATE;
		}
		else if (buttonHard.isSelected()) {
			return Difficulty.HARD;
		}
		else {
			throw new RuntimeException("unable to find difficulty");
		}
	}
	
	private JToggleButton createDifficultyButton(BufferedImage buttonImage, boolean selected) {
		JToggleButton button = new JToggleButton(new ImageIcon(buttonImage), selected);
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		if (selected) {
			button.setBorderPainted(true);
		}
		else {
			button.setBorderPainted(false);
		}
		return button;
	}
	
}
