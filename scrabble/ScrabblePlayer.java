package scrabble;

import game.*;

/**Interface representing a player. Should be subclassed by classes represeting individual player types.*/
public interface ScrabblePlayer extends GamePlayer {
	/** Adds tiles to the player's hand.

@param tilesToAdd  the tiles to add, contained in an array */
	public void updateHand (ScrabbleTile[] tilesToAdd);

}