package scrabble;

import java.awt.Point;
import java.io.IOException;
import java.util.Vector;

import game.*;

/**ScrabbleProxyGame*/
public class ScrabbleProxyGame extends ProxyGame implements ScrabbleGame {
	public static final int PORT_NUM = 60035;

	/** Initializes all of the variables.
@param hostname A String containing the hostname of the game. */
	public ScrabbleProxyGame (String hostname) {
		super(hostname);
	}

	/** 
	 * Decodes the GameState string
	 * 
	 * TODO: Test this
	 * @param str A string that represents the gamestate
	 * @return returns the gamestate
	 */
	protected GameState decodeState (String str) 
	{
	    Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	    ScrabbleBoard theBoard = new ScrabbleBoard();
	    int p0score;
	    int p1score;
	    int whoseMove;
	    
	    int sepLoc = str.indexOf("[");
	    int i = sepLoc+1;
	    while (str.charAt(i) != ']')
	    {
	        Boolean isBlank;
	        if (str.charAt(i+2) == '+')
	            isBlank = false;
	        else
	            isBlank = true;
	        hand.add(new ScrabbleTile(str.charAt(i),Integer.parseInt(Character.toString(str.charAt(i+1))),isBlank));
	        i += 3;
	    }
	    i+=2;//go past end and beginning brackets. Start at raw data.
	    
	    //Create a copy of the board
	    for (int q = 0; q < ScrabblePlayerUI.BOARD_SIZE; q++)
        {
            for (int k = 0; k < ScrabblePlayerUI.BOARD_SIZE; k++)
            {
                char letter = str.charAt(i);
                //Skip blank tiles
                if (letter == '|')
                    continue;
                int value = Integer.parseInt(Character.toString(str.charAt(i+1)));
                boolean isBlank;
                if (str.charAt(i+2) == '-')
                    isBlank = true;
                else
                    isBlank = false;
                ScrabbleTile theTile = new ScrabbleTile(letter,value,isBlank);
                theBoard.putTileAt(q, k, theTile);
                i += 3;
            }
        }
	    i += 2;//Go past end and beginning brackets. Start at data
	    
	    int scoreLocation = i;
	    while (str.charAt(scoreLocation) != ':')
	    {
	        scoreLocation++;
	    }
	    p0score = Integer.parseInt(str.substring(i,scoreLocation));
	    scoreLocation++;
	    i = scoreLocation;//Move past the delimiting colon
	    while (str.charAt(scoreLocation) != ']')
	    {
	        scoreLocation++;
	    }
	    p1score = Integer.parseInt(str.substring(i, scoreLocation));
	    i = scoreLocation+2; //move past end and beginning brackets
	    whoseMove = Integer.parseInt(Character.toString(str.charAt(i)));
	    Dictionary theDict = null;
        try
        {
            theDict = new Dictionary();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ScrabbleGameState(theBoard,hand,whoseMove,p0score,p1score);
	}

	/** 
	 * Encodes the action
	 *
	 * @param ga the game action to encode
	 * @return returns a string representing the game action 
	 */
	protected String encodeAction (GameAction ga) 
	{
	    String str = "";
	    int player = ga.getSource().getId();
	    str += player;
	    str += "|"; //Delimit
	    
	    if (ga instanceof ScrabbleMoveAction)
	    {
	        Vector<ScrabbleTile> playedTiles = ((ScrabbleMoveAction)ga).getTiles();
	        for (ScrabbleTile tile : playedTiles)
	        {
	            if (tile.isBlank())
	                str += "-";
	            else
	                str += "+";
	            str += tile.getLetter();
	            str += tile.getValue();
	        }
	        str += "|";//delimit
	        Vector<Point> positions = ((ScrabbleMoveAction) ga).getPositions();
	        for (Point p : positions)
	        {
	            str += "(";
	            str = str + p.x + "," + p.y + ")";
	        }
	        return str;
	    }
	    else if (ga instanceof ScrabbleDiscardAction)
	    {
	        Vector<ScrabbleTile> playedTiles = ((ScrabbleMoveAction)ga).getTiles();
            for (ScrabbleTile tile : playedTiles)
            {
                if (tile.isBlank())
                    str += "-";
                else
                    str += "+";
                str += tile.getLetter();
                str += tile.getValue();
            }
            
            return str;
	     
	    }
	    else
	        return null;
	}

	/** Gets the port number
@return returns the port number */
	protected int getAdmPortNum () {
		return PORT_NUM;
	}

	/** Gets the max players
@return returns the max players */
	public int maxPlayersAllowed () {
		return 2;
	}

	/** Gest the minimum players
@return meturns the minimum players */
	public int minPlayersAllowed () {
		return 2;
	}

	/** Gets if you can have a blank player
@return true if you can play with empty seats false otherwise. */
	public boolean nullPlayersAllowed () {
		return false;
	}

}