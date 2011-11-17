package scrabble;

import game.*;
import java.util.*;
import java.awt.*;

/**A class representing a move action (submitting a play).*/
public class ScrabbleMoveAction extends GameMoveAction {
	private Vector<ScrabbleTile> tilesPlayed;
	private Vector<Point> positionsPlayed;

	/** Constructor.
	 * @param source  player who made this move
	 * @param initTiles  tiles used in this play
	 * @param initPositions  A vector of Point representing the positions of each tile in initTiles 
	 */
	public ScrabbleMoveAction (GamePlayer source, Vector<ScrabbleTile> initTiles, Vector<Point> positionsPlayed) 
	{
		super (source);
		tilesPlayed = initTiles;
		this.positionsPlayed = positionsPlayed;
	}

	/** Gets the tiles used in this play.

@return a Vector containing all tiles used in this play */
	public Vector<ScrabbleTile> getTiles () {
		return tilesPlayed;
	}

	/** Gets the positions of the tiles used in this play.

@return a Vector containing the board positions of the tiles used in this play */
	public Vector<Point> getPositions () {
		return positionsPlayed;
	}
}