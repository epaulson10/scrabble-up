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
        return null;
    }

    /** Encodes the updated game state after a network player makes a move
     * to send the information to the next player
     * @param gs the GameState being encoded
     * @return A string containing the encoded game state 
     **/
    protected String encodeState (GameState gs) 
    {
        return null;
    }

    /**
     * Returns the port number used for the network game mode
     * @return the port number being used
     */
    protected int getAdmPortNum () 
    {
        return -1;
    }

    /** Adds tiles to the player's hand.
    * @param tilesToAdd  the tiles to add, contained in an array
    **/
    public void updateHand (Vector<ScrabbleTile> tilesToAdd) 
    {

    }

    /**
     *  Retrieves the player's hand.
     *  
     *  @return a copy of player's hand
     */
    public Vector<ScrabbleTile> getHand()
    {
        return null;
    }

}