package scrabble;

import java.util.*;

import game.*;

/**Interface representing a player. Should be subclassed by classes represeting individual player types.*/
public interface ScrabblePlayer extends GamePlayer {
	/** Adds tiles to the player's hand.

@param tilesToAdd  the tiles to add, contained in an array */
	public void updateHand (ScrabbleTile[] tilesToAdd);
	
	/*added*/
	/****************************/
	// Vector representing the player's hand of tiles
	public Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	// Is the player able to discard tiles
	public boolean discardable = true;
	// Set it so the player cannot discard
	public void noDiscard();
	// Set it so the player can discard
	public void yesDiscard();
	// Get the current discard state
	public boolean getDiscard();
	// Get the player's hand
	public Vector<ScrabbleTile> getHand();
	// Update the hand with a newHand
	public void updateHand(Vector<ScrabbleTile> newHand);
	/****************************/
}