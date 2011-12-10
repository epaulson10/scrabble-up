package scrabble;

import game.*;

import java.awt.Point;
import java.util.*;

/**Class representing a player playing over the network.*/
class ScrabbleProxyPlayer extends ProxyPlayer implements ScrabblePlayer {

    /** Constructor - Calls super */
    public ScrabbleProxyPlayer () 
    {
        super();
    }

    /**
     * Decodes information about game actions that was encoded to be sent over 
     * the network and returns a game action. See the documentation on
     * encodeAction in ScrabbleProxyGame to see how the encoded string
     * appears
     * 
     * @param s The string containing the encoded gameAction
     * @return the decoded gameAction
     */
    protected GameAction decodeAction (String s) 
    {
        if (s.equals("!!!"))
            return new ScrabbleResignAction(this);
        
        //Get the player id
        int playerID = Integer.parseInt(s.substring(0,1));
        int i = 2;
        Vector<ScrabbleTile> actionTiles = new Vector<ScrabbleTile>();
        Vector<Point> positions = new Vector<Point>();
        
        //Decipher the tile representations
        while (i < s.length() && s.charAt(i) != '|')
        {
            boolean isBlank;
            int value;
            char letter;
            if (s.charAt(i) == '+')
                isBlank = false;
            else
                isBlank = true;
            letter = s.charAt(i+1);
            //The conditions here account for tiles having either a 2 or 1 digit value
            if (s.length() >= i+4 && s.charAt(i + 3) != '+' && s.charAt(i+3) != '-' && s.charAt(i+3) != '|')
                value = Integer.parseInt(s.substring(i+2, i+4));
            else
                value = Integer.parseInt(s.substring(i+2, i+3));
            
            if (value > 9) //If a double digit number, move forward past both digits
                i += 4;
            else
                i += 3;
            if (letter == '*')
                actionTiles.add(new ScrabbleBlankTile());
            else
                actionTiles.add(new ScrabbleTile(letter,value,isBlank));
        }
        
        //If the string ends, it must be a discard action
        if (i >= s.length())
            return new ScrabbleDiscardAction(this, actionTiles);
        
        //Decipher the point representations
        i = s.indexOf("(") + 1;
        
        while(i < s.length())
        {
            int x, y;
            if (s.charAt(i+1) == ',')
            {
                x = Integer.parseInt(s.substring(i,i+1));
                i += 2;
            }
            else
            {
                x = Integer.parseInt(s.substring(i,i+2));
                i += 3;
            }
            if (s.charAt(i+1) == ')')
            {
                y = Integer.parseInt(s.substring(i,i+1));
                i += 3;
            }
            else
            {
                y = Integer.parseInt(s.substring(i,i+2));
                i += 4;
            }
            positions.add(new Point(x,y));
            
    
        }
        
       
        return new ScrabbleMoveAction(this, actionTiles, positions);
      
    }

    /** Encodes the updated game state after a network player makes a move
     * to send the information to the next player
     * 
     * The format of the encoding is:
     *  [hand tiles][board tiles][p0score:p1score][whoseMoveItIs]
     *  [+A1+E1+I1-*0+Z10+A1+O1][|||+A1+T2||||...][p0score:p1score][whoseMoveItIs]
     *  The plus sign in front of a tile means it did not come from a blank.
     *  THe - sign infront of a tile means it did come from a blank
     *  The * character represents a blank tile, they have a minus sign in front of them just to keep the spacing equal
     *  The board follows the same format as the hand except that the | character represents a blank space on the board
     * 
     * @param gs the GameState being encoded
     * @return A string containing the encoded game state 
     **/
    protected String encodeState (GameState gs) 
    {
        ScrabbleGameState state = (ScrabbleGameState)gs;
        String str = "[";
        //encode tiles in the hand
        for (ScrabbleTile tile : state.getHand())
        {
            if (tile.isBlank() || tile instanceof ScrabbleBlankTile)
                str += "-";
            else
                str += "+";
            //asterisks represent blank tiles
            if (tile instanceof ScrabbleBlankTile)
                str += "*";
            else
                str += tile.getLetter();
            str += tile.getValue();
        }
        str += "]";
        //Encode tiles on the board
        str += "[";
        for (int i = 0; i < ScrabblePlayerUI.BOARD_SIZE; i++)
        {
            for (int k = 0; k < ScrabblePlayerUI.BOARD_SIZE; k++)
            {
                ScrabbleTile tile = state.getBoard().getTileAt(i, k);
                if (tile != null)
                {
                    if (tile.isBlank())
                    {
                        str+= "-";
                    }
                    else
                        str+="+";
                    str += tile.getLetter();
                    str += tile.getValue();
                    
                }
                else
                    str += "|"; //represent an empty board space with this character
            }
        }
        //Encode scores/whose move it's supposed to be
        str += "][";
        str += state.getScore(0);
        str += ":";
        str += state.getScore(1);
        str += "][";
        str += state.whoseMove();
        str += "]";
        
        return str;
    }

    /**
     * Returns the port number used for the network game mode
     * @return the port number being used
     */
    protected int getAdmPortNum () 
    {
        return ScrabbleProxyGame.PORT_NUM;
    }

}