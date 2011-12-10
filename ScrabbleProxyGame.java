package scrabble;

import java.awt.Point;
import java.io.IOException;
import java.util.Vector;

import game.*;

/**
* ScrabbleProxyGame
* 
* The only thing wrong with network play is that you can't perform an action
* which makes a popup box appear. When this happens, we're pretty sure a race condition
* occurs which we nor Nux have been able to find.
*/
public class ScrabbleProxyGame extends ProxyGame implements ScrabbleGame {
    public static final int PORT_NUM = 60035;

    /** Initializes all of the variables.
     * @param hostname A String containing the hostname of the game. 
     */
    public ScrabbleProxyGame (String hostname) {
        super(hostname);
    }

    /** 
     * Decodes the GameState string
     * 
     * The format of the encoding is:
     *  [hand tiles][board tiles][p0score:p1score][whoseMoveItIs]
     *  [+A1+E1+I1-*0+Z10+A1+O1][|||+A1+T2||||...][p0score:p1score][whoseMoveItIs]
     *  The plus sign in front of a tile means it did not come from a blank.
     *  THe - sign infront of a tile means it did come from a blank
     *  The * character represents a blank tile, they have a minus sign in front of them just to keep the spacing equal
     *  The board follows the same format as the hand except that the | character represents a blank space on the board
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
        //Start at the first character of data
        int i = 1;
        while (str.charAt(i) != ']')
        {
            Boolean isFromBlank;
            int value;

            //Check to see if it came from a blank
            if (str.charAt(i) == '+')
                isFromBlank = false;
            else
                isFromBlank = true;
            //check to see if the value is 1 or 2 digits, then take the appropriate substring
            if (str.charAt(i+3) != '+' && str.charAt(i+3) != '-' && str.charAt(i+3) != ']')
            {
                value = Integer.parseInt(str.substring(i+2, i+4));
            }
            else
                value = Integer.parseInt(str.substring(i+2, i +3));
            if (str.charAt(i+1) == '*')
                hand.add(new ScrabbleBlankTile());
            else
                hand.add(new ScrabbleTile(str.charAt(i+1),value,isFromBlank));
            if (value > 9)
                i += 4;
            else
                i += 3;
        }
        i+=2;//go past end and beginning brackets. Start at raw data.

        //Create a copy of the board
        for (int q = 0; q < ScrabblePlayerUI.BOARD_SIZE; q++)
        {
            for (int k = 0; k < ScrabblePlayerUI.BOARD_SIZE; k++)
            {
                char start = str.charAt(i);
                //Skip blank tiles
                if (start == '|')
                {
                    i++;
                    continue;
                }
                Boolean isFromBlank;
                if (str.charAt(i) == '+')
                    isFromBlank = false;
                else
                    isFromBlank = true;
                int value;
                if (str.charAt(i+3) != '+' && str.charAt(i+3) != '-' && str.charAt(i+3)!= '|' && str.charAt(i+3) != ']')
                {
                    value = Integer.parseInt(str.substring(i+2, i+4));
                }
                else
                    value = Integer.parseInt(str.substring(i+2, i +3));
                ScrabbleTile theTile = new ScrabbleTile(str.charAt(i+1),value,isFromBlank);
                theBoard.putTileAt(q, k, theTile);
                if (value > 9)
                    i += 4;
                else
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
        whoseMove = Integer.parseInt(str.substring(i,i+1));

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
            Vector<ScrabbleTile> playedTiles = ((ScrabbleDiscardAction)ga).getTiles();
            for (ScrabbleTile tile : playedTiles)
            {
                if (tile.isBlank())
                    str += "-";
                else
                    str += "+";
                if (tile instanceof ScrabbleBlankTile)
                    str += "*";
                else
                    str += tile.getLetter();
                str += tile.getValue();
            }

            return str;

        }
        else if (ga instanceof ScrabbleResignAction)
        {
            return "!!!";
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