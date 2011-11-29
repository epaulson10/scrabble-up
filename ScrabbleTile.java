package scrabble;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**Scrabble Tile

A scrabble tile used in playing the game.*/
class ScrabbleTile {
	private char letter;
	private int value;
	private boolean fromBlank;
	private Point location;

	/** Initializes letter to initLetter and value to initValue. If 
	 * initLetter is not a valid letter then letter is set to %.
	 * @param initLetter the letter that the tile represents.
	 * @param initValue the value of the tile.
	 * @param initFromBlank if the tile is coming from a blank 
	 */
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
		location = new Point();
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
	
	/**
	 * Determines whether this is a blank tile or not
	 * @return true if from a blank, false otherwise
	 */
	public boolean isBlank()
	{
	    return fromBlank;
	}

	/** The location of the tile's image */
	public String getFileName ()
	{
		if(!fromBlank)
			return "./src/scrabble/Tiles/ScrabbleTile"+letter+".jpg";
		else
			return "./src/scrabble/Tiles/ScrabbleTile"+letter+"blank.jpg";
	}
	
	/** 
	 * Set the tile's point 
	 * 
	 * @param x the x location
	 * @param y the y location
	 */
	public void setLocation(int x, int y)
	{
		location.setLocation(x,y);
	}
	
	/**
	 * get the location of this tile
	 * 
	 * @return Point representing where the tile is
	 * 
	 */
	public Point getLocation()
	{
		return location;
	}
	
	/**
	 * Determines if a given point is inside the tile
	 * 
	 * @return true if the point is inside the tile, false otherwise
	 */
	public boolean pointInside(int x, int y)
	{
	    if (x > location.x && x < location.x + ScrabblePlayerUI.TILE_SIZE &&
	        y > location.y && y < location.y+ ScrabblePlayerUI.TILE_SIZE)
	    {
	        return true;
	    }
	    else
	        return false;
	}
	
	/**
	 * Creates and returns the tile's image
	 * @return the tile's BufferedImage
	 */
	public BufferedImage getPicture()
	{
	    BufferedImage img = null;
	    try 
	    {
	        img = ImageIO.read(new File(getFileName()));
	    }
	    catch (IOException ioe)
	    {
	        ioe.printStackTrace();
	    }
	    
	    return img;
	}

	public String toString()
	{
		return ""+letter;
	}
}