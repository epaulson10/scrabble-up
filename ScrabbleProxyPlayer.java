package scrabble;

import game.*;
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
        int player = Integer.parseInt(s.substring(0,1));
        int i = 2;
        
        return null;
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
            str += tile.getLetter();
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
                    str += tile.getLetter();
                    str += tile.getValue();
                    if (tile.isBlank())
                    {
                        str+= "-";
                    }
                    else
                        str+="+";
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