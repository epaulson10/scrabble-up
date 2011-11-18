package scrabble;

import game.*;
import java.util.*;

/**
 * Class representing a "discard" move (exchanging tiles). The discard action
 * ends the player's turn.
 * This class is also used for passing a turn, as a pass is simply a discard of
 * zero tiles.
 */
class ScrabbleDiscardAction extends GameMoveAction 
{
	// Tiles to be discarded
	protected Vector<ScrabbleTile> tilesDiscarded;

	/** Constructor.
	 * @param source  the GamePlayer who made this move
	 * @param initTiles  the tiles discarded by the player 
	 */
	public ScrabbleDiscardAction (GamePlayer source, Vector<ScrabbleTile> initTiles) 
	{
		super(source);
		this.tilesDiscarded = initTiles;
	}
	
	/**
	 * Get the tiles to be discarded
	 * @return Vector of tiles to be discarded
	 */
	public Vector<ScrabbleTile> getTiles()
	{
		return tilesDiscarded;
	}
}