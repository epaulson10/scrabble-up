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
     * the network and returns a game action.
     * @param s The string containing the encoded gameAction
     * @return the decoded gameAction
     */
    protected GameAction decodeAction (String s) 
    {
        int playerID = Integer.parseInt(s.substring(0,1));
        int i = 2;
        Vector<ScrabbleTile> actionTiles = new Vector<ScrabbleTile>();
        Vector<Point> positions = new Vector<Point>();
        
        while (s.charAt(i) != '|' && i < s.length())
        {
            boolean isBlank;
            int value;
            char letter;
            if (s.charAt(i) == '+')
                isBlank = false;
            else
                isBlank = true;
            letter = s.charAt(i+1);
            if (s.charAt(i + 3) != '+' && s.charAt(i+3) != '-')
                value = Integer.parseInt(s.substring(i+2, i+4));
            value = Integer.parseInt(s.substring(i+2, i+3));
            if (value > 9) //If a double digit number, move forward past both digits
                i += 4;
            else
                i += 3;
            actionTiles.add(new ScrabbleTile(letter,value,isBlank));
        }
        
        if (i > s.length())
            return new ScrabbleDiscardAction(this, actionTiles);
        
        i = s.indexOf('(') + 1;
        
        while(i < s.length())
        {
            int x = i;
            int y = i + 2;
            positions.add(new Point(x,y));
            i += 5; //move to the next point
        }
        
       
            return new ScrabbleMoveAction(this, actionTiles, positions);
      
    }

    /** Encodes the updated game state after a network player makes a move
     * to send the information to the next player
     * TODO: Test this.
     * 
     * @param gs the GameState being encoded
     * @return A string containing the encoded game state 
     **/
    protected String encodeState (GameState gs) 
    {
        ScrabbleGameState state = (ScrabbleGameState)gs;
        String str = "[";
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