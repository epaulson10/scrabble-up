package scrabble;

/**Board

Represents the Scrabble Game Board*/
public class ScrabbleBoard
{
	
	private final int BOARD_HEIGHT = 15;
	private final int BOARD_WIDTH = 15;
	private ScrabbleTile[][] tilePositions = 
			new ScrabbleTile[BOARD_WIDTH][BOARD_HEIGHT]; //[col][row]
	private int[][] bonusPositions =
			new int [BOARD_WIDTH][BOARD_HEIGHT];//[col][row]
	
	

	/** Initializes variables. All tilePositions and bonusPositions are null */
	public ScrabbleBoard () 
	{
		for(int i = 0; i < BOARD_WIDTH; i++)
		{
			for(int j = 0; j < BOARD_HEIGHT; j++)
			{
				tilePositions[i][j] = null;
				bonusPositions[i][j] = 0;
			}
		}
	}

	/** Returns the tile at the specified position
@param row the row that is being looked in [0,BOARD_HEIGHT)
@param col the column that is being looked in [0, BOARD_WIDTH)
@return A copy of the ScrabbleTile that is in the specified row and column
@throws If invalid row or col is out of bounds then a
nonValidBoardSpaceException is thrown */
	public ScrabbleTile getTileAt (int row, int col)
									throws NonValidBoardSpaceException
	{
		if(row >= BOARD_HEIGHT || row < 0)
		{
			throw new NonValidBoardSpaceException("Invalid Row");
		}
		if(col >= BOARD_HEIGHT || col < 0)
		{
			throw new NonValidBoardSpaceException("Invalid Column");
		}
		
		return tilePositions[col][row];
	}

	/** Returns the integer code of the bonus at the specified tile
@param row the row that is being looked in
@param col the column that is being looked in
@return The integer code of the bonus.
	 * @throws NonValidBoardSpaceException 
@throws If invalid row or col is out of bounds then a
nonValidBoardSpaceException is thrown */
	public int getBonusAt (int row, int col) throws NonValidBoardSpaceException
	{
		if(row >= BOARD_HEIGHT || row < 0)
		{
			throw new NonValidBoardSpaceException("Invalid Row");
		}
		if(col >= BOARD_HEIGHT || col < 0)
		{
			throw new NonValidBoardSpaceException("Invalid Column");
		}
		
		return bonusPositions[col][row];
	}

}