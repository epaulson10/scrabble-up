package scrabble;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.Box;

/**Class representing a human Scrabble player.*/
public class ScrabbleHumanPlayer extends GameHumanPlayer implements ScrabblePlayer, MouseMotionListener {
	private final static int GUI_HEIGHT = 720;
	private final static int GUI_WIDTH = 700;
	
	// Player's hand
	public Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
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
	 * @return a new ScrabblePlayerUI *
	 * /
	protected ScrabblePlayerUI createUI () {
	    return null;
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

@return a JPanel with the game's GUI */
	protected Component createApplComponent () 
	{
	    Panel uiPanel = new Panel();
	    ui = makeUI();
	    ui.addMouseListener(this);
        uiPanel.add(ui);
       
        return uiPanel;
	}
	
	protected ScrabblePlayerUI makeUI()
	{
	    return new ScrabblePlayerUI(this);
	}

	

}