package scrabble;

import java.awt.Point;

/**Scrabble Tile

A scrabble tile used in playing the game.*/
class ScrabbleTile {
	private char letter;
	private int value;
	private boolean fromBlank;
	private Point location;

	/** Initializes letter to initLetter and value to initValue. If 
initLetter is not a valid letter then letter is set to %.
@param initLetter the letter that the tile represents.
@param initValue the value of the tile.
@param initFromBlank if the tile is coming from a blank */
	public ScrabbleTile (char initLetter, int initValue, boolean initFromBlank) 
	{
		if(Character.isLetter(initLetter))
			letter = Character.toUpperCase(initLetter);
		else if(initLetter == ' ')
			letter = initLetter;
		else
			letter = '%';
		if(initValue > 0)
			value = initValue;
		else
			value = 0;
		fromBlank = initFromBlank;
	}

	/** Gets the letter of the tile
@return the letter the tile represents */
	public char getLetter ()
	{
		return letter;
	}

	/** Gets the point value of the tile
@return the value of the tile in points */
	public int getValue ()
	{
		return value;
	}

	/** The location of the tile's image */
	public String getFileName ()
	{
		if(!fromBlank)
			return "Tiles/ScrabbleTile"+letter+".jpg";
		else
			return "Tiles/ScrabbleTile"+letter+"blank.jpg";
	}
	
	/** Set the tile's point */
	public void setLocation(int x, int y)
	{
		location.x = x;
		location.y = y;
	}
	
	public Point getLocation()
	{
		return location;
	}

	public String toString()
	{
		return ""+letter;
	}
}