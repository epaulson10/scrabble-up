package scrabble;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;

/**Class representing a human Scrabble player.*/
public class ScrabbleHumanPlayer extends GameHumanPlayer implements ScrabblePlayer, MouseMotionListener, ActionListener {
	private final static int GUI_HEIGHT = 720;
	private final static int GUI_WIDTH = 700;
	private String buttonNames[] = {"Quit", "Discard", "Pass", "Shuffle"};
	
	// Player's hand
	private Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	private ScrabblePlayerUI ui;
	



	/** Returns the initial height of the GUI.
	 * @return the initial height of the GUI, in pixels 
	 */
	protected int initialHeight () 
	{
	    return GUI_HEIGHT;
	}

	/** Returns the initial width of the GUI.
	 * @return the initial width of the GUI, in pixels 
	 **/
	protected int initialWidth () 
	{
	    return GUI_WIDTH;
	}

	/** Constructor */
	public ScrabbleHumanPlayer () 
	{
	    super();
	}

	/** Actions to be taken after the game is initialized */
	protected void setGameMore () 
	{

	}

	/** Gets the default title of the game window.
	 * @return the initial window title 
	 */
	protected String defaultTitle () {
	    return "Scrabble";
	}

	/** Creates a GUI object for this player.
	 * @return a new ScrabblePlayerUI (the board graphics)
	 */ 
	protected ScrabblePlayerUI createUI () 
	{
	    return new ScrabblePlayerUI(this);
	}

	/** Actions to be taken after we're notified of a state change. */
	public void stateChanged () 
	{
		
	}
	
	/**
	 * Get the player's hand
	 */
	public Vector<ScrabbleTile> getHand()
	{
		return hand;
	}
	
	/**
	 * Update the hand with a newHand
	 */
	public void updateHand(Vector<ScrabbleTile> newHand)
	{
		hand = newHand;
	}
	
	public void mouseClicked (MouseEvent me) {
		
	}

	public void mouseDragged (MouseEvent me) {
		
	}

	public void mouseMoved (MouseEvent me) {
		
	}

	/** Creates the graphical component of the application.
	 * 
	 * @return a JPanel with the game's GUI 
	 */
	protected Component createApplComponent () 
	{
	    JButton resignButton = new JButton("Resign");
	    JButton discardButton = new JButton("Discard");
	    JButton passButton = new JButton("Pass");
	    JButton shuffleButton = new JButton("Shuffle");

	    resignButton.setSize(300, 100);
	    resignButton.setMinimumSize(resignButton.getSize());
	    resignButton.setMaximumSize(resignButton.getSize());
	   
	    discardButton.setSize(300, 100);
        discardButton.setMinimumSize(discardButton.getSize());
        discardButton.setMaximumSize(discardButton.getSize());
	    
	    passButton.setSize(300, 100);
        passButton.setMinimumSize(passButton.getSize());
        passButton.setMaximumSize(passButton.getSize());
	    
	    shuffleButton.setSize(300, 100);
        shuffleButton.setMinimumSize(shuffleButton.getSize());
        shuffleButton.setMaximumSize(shuffleButton.getSize());
	    
	    Panel uiPanel = new Panel();
	    uiPanel.setSize(initialWidth(), initialHeight());
	    ui = createUI();
	    ui.addMouseListener(this);
	    Box vBox = Box.createVerticalBox();
	    uiPanel.add(vBox);
	    Box hBoxTop = Box.createHorizontalBox();
	    Box hBoxBot = Box.createHorizontalBox();
	    vBox.add(hBoxTop);
	    vBox.add(Box.createVerticalGlue());
	    vBox.add(hBoxBot);
	    
	    //Add UI buttons
	    hBoxBot.add(Box.createHorizontalGlue());
	    hBoxBot.add(resignButton);
	    hBoxBot.add(Box.createHorizontalGlue());
	    hBoxBot.add(discardButton);
	    hBoxBot.add(Box.createHorizontalGlue());
	    hBoxBot.add(passButton);
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxBot.add(shuffleButton);
        hBoxBot.add(Box.createHorizontalGlue());
	   
	    hBoxTop.add(ui);
	    
        return uiPanel;
	}
	
	/**
	 * This is an override of the superclass's actionPerformed method.
	 * This handles button press events.
	 * 
	 * @param ae The ActionEvent being passed in
	 */
	public void actionPerformed(ActionEvent ae)
	{
	   super.actionPerformed(ae);
	}

	

}