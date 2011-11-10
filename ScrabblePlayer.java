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
	public Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	public boolean discardable = true;
	public void noDiscard();
	public void yesDiscard();
	public boolean getDiscard();
	public void updateHand(Vector<ScrabbleTile> newHand);
	public Vector<ScrabbleTile> getHand();
	/****************************/
}