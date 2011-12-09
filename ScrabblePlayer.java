package scrabble;

import java.util.*;

import game.*;

/**Interface representing a player. Should be subclassed by classes representing individual player types.*/
public interface ScrabblePlayer extends GamePlayer 
{
	// TODO remove this once we're sure it's unnecessary
	// Vector representing the player's hand of tiles
	// public Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	// Get the player's hand
	// public Vector<ScrabbleTile> getHand();
	// Update the hand with a newHand
	// public void updateHand(Vector<ScrabbleTile> newHand);
}