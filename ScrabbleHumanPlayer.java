package scrabble;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**Class representing a human Scrabble player.*/
public class ScrabbleHumanPlayer extends GameHumanPlayer implements ScrabblePlayer, MouseMotionListener {
	public final static int GUI_HEIGHT = 720;
	public final static int GUI_WIDTH = 700;
	
	/*added*/
	public boolean discardable = true;
	public Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();


	/** Returns the initial height of the GUI.

@return the initial height of the GUI, in pixels */
	protected int initialHeight () {
		return 0;
	}

	/** Returns the initial width of the GUI.

@return the initial width of the GUI, in pixels */
	protected int initialWidth () {
	    return 0;
	}

	/** Constructor */
	public ScrabbleHumanPlayer () {
		
	}

	/** Actions to be taken after the game is initialized */
	protected void setGameMore () {
		
	}

	/** Gets the default title of the game window.

@return the initial window title */
	protected String defaultTitle () {
	    return null;
	}

	/** Creates a GUI object for this player.

@return a new ScrabblePlayerUI */
	protected ScrabblePlayerUI createUI () {
	    return null;
	}

	/** Actions to be taken after we're notified of a state change. */
	public void stateChanged () {
		
	}
	
	/*added*/
	/****************************/
	public void noDiscard()
	{
		discardable = false;
	}
	public void yesDiscard()
	{
		discardable = true;
	}
	
	public boolean getDiscard()
	{
		return discardable;
	}
	
	public Vector<ScrabbleTile> getHand()
	{
		return hand;
	}
	
	public void updateHand(Vector<ScrabbleTile> newHand)
	{
		hand = newHand;
	}
	
	
	/****************************/
	public void mouseClicked (MouseEvent me) {
		
	}

	public void mouseDragged (MouseEvent me) {
		
	}

	public void mouseMoved (MouseEvent me) {
		
	}

	/** Creates the graphical component of the application.

@return a JPanel with the game's GUI */
	protected Component createApplComponent () {
	    return null;
	}

	/** Adds tiles to the player's hand.

@param tilesToAdd  the tiles to add, contained in an array */
	public void updateHand (ScrabbleTile[] tilesToAdd) {
		
	}

}