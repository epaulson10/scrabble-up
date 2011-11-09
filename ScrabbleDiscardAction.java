package scrabble;

import game.*;
import java.util.*;

/**
 * Class representing a "discard" move (exchanging tiles).
 * This class is also used for passing a turn, as a pass is simply a discard of
 * zero tiles.
 */
class ScrabbleDiscardAction extends GameMoveAction 
{
	protected Vector<ScrabbleTile> tilesDiscarded;


	/** Constructor.
	 * @param source  the GamePlayer who made this move
	 * @param initTiles  the tiles discarded by the player 
	 */
	public ScrabbleDiscardAction (GamePlayer source, Vector<ScrabbleTile> initTiles) 
	{
		super(source);
		for(ScrabbleTile tile : initTiles)
		{
			ScrabblePlayer.curHand.remove(tile);
		}
		Random ran = new Random();
		int index;
		for(int i; i < initTiles.size(); i++)
		{
			index = ran.nextInt(bag.size());
			ScrabblePlayer.curHand.add(bag.elementAt(index));
		}
	}

}