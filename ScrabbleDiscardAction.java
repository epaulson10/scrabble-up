package scrabble;

import game.*;
import java.util.*;

/**
 * Class representing a "discard" move (exchanging tiles).
 * This class is also used for passing a turn, as a pass is simply a discard of
 * zero tiles.
 */
class ScrabbleDiscardAction extends ScrabbleMoveAction 
{
	protected Vector<ScrabbleTile> tilesDiscarded;
	// variable to check
	protected boolean discardable;


	/** Constructor.
	 * @param source  the GamePlayer who made this move
	 * @param initTiles  the tiles discarded by the player 
	 */
	public ScrabbleDiscardAction (GamePlayer source, Vector<ScrabbleTile> initTiles) 
	{
		super(source, initTiles);
		this.tilesDiscarded = initTiles;
		
		
		/*added*/
		/****************************/
		ScrabblePlayer plyr = (ScrabblePlayer)source;
		this.discardable = plyr.getDiscard();
		/****************************/
	}

	/*added*/
	/****************************/
	
	/**
	 * Get the tiles to be discarded
	 * @return Vector of tiles to be discarded
	 */
	public Vector<ScrabbleTile> getTiles()
	{
		return tilesDiscarded;
	}
	
	/**
	 * Check if the deck is discardable
	 * @return Boolean if discardable
	 */
	public boolean isDiscard() {
		//maybe source.discardable
        return discardable;
    }
	/****************************/
}