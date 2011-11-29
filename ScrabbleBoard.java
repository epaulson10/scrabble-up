package scrabble;

/**Board

Represents the Scrabble Game Board*/
public class ScrabbleBoard
{	
	public static final int BOARD_HEIGHT = 15;
	public static final int BOARD_WIDTH = 15;
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
	 * @return A copy of the ScrabbleTile that is in the specified row and column
	 * @throws If invalid row or col is out of bounds then a
	 * nonValidBoardSpaceException is thrown 
	 * */
	public ScrabbleTile getTileAt (int row, int col)
	{	
		if(tilePositions[row][col] == null)
			return null;
		else
			return tilePositions[row][col];
	}

	/** Returns the integer code of the bonus at the specified tile
@param row the row that is being looked in
@param col the column that is being looked in
@return The integer code of the bonus.
	 * @throws NonValidBoardSpaceException 
@throws If invalid row or col is out of bounds then a
nonValidBoardSpaceException is thrown */
	public int getBonusAt (int row, int col)
	{

		return bonusPositions[row][col];
	}
	
	/** Sets the tile at the given location
@param row the row that is being set
@param col the column that is being set
@param sct the scrabble tile that is being placed
	 * @throws NonValidBoardSpaceException
	 */
	public void putTileAt(int row, int col, ScrabbleTile sct) 
		{
			// if tilePosition == null
			tilePositions[row][col] = sct;
			// CHANGED: Allow null positions without error
			if (sct != null)
			{
			    sct.setLocation(row*ScrabblePlayerUI.TILE_SIZE, col*ScrabblePlayerUI.TILE_SIZE);
			}
		}
	
	/**Returns an array of strings to locate the tile image folders
@return a two dimensional array of Strings with the file locations of the
		image file
	 */
	public String[][] getTileImages()
	{
		String[][] images = new String[BOARD_WIDTH][BOARD_HEIGHT];
		
		ScrabbleTile tester;
		for(int i = 0; i<BOARD_WIDTH; i++)
		{
			for(int j = 0; j<BOARD_HEIGHT; j++)
			{
				tester = tilePositions[i][j];
				if(tester == null)
				{
					images[i][j] =  "Tiles/ScrabbleTileBlank.jpg";
				}
				else
				{
					images[i][j] = tester.getFileName();
				}
			}
		}
		
		return images;
	}
}