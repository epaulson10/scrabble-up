package scrabble;

/**Board

Represents the Scrabble Game Board*/
public class ScrabbleBoard {
	private ScrabbleTile[][] tilePositions;
	private int[][] bonusPositions;

	/** Initializes variables. All tilePositions and bonusPositions are null */
	public ScrabbleBoard () {
		
	}

	/** Returns the tile at the specified position
@param row the row that is being looked in
@param col the colum that is being looked in
@return A copy of the ScrabbleTile that is in the specified row and column
@throws If invalid row or col is out of bounds then a
nonValidBoardSpaceException is thrown */
	public ScrabbleTile getTileAt (int row, int col) {
		return null;
	}

	/** Returns the integer code of the bonus at the specified tile
@param row the row that is being looked in
@param col the colum that is being looked in
@return The integer code of the bonus.
@throws If invalid row or col is out of bounds then a
nonValidBoardSpaceException is thrown */
	public int getBonusAt (int row, int col) {
		return 0;
	}

}