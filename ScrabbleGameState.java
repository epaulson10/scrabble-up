package scrabble;

import game.*;
import java.util.*;

/**Class representing a Scrabble game state.*/
class ScrabbleGameState extends GameState
{
	private Vector<ScrabbleTile> curHand;
	private ScrabbleBoard board;
	private int p0Score;
	private int p1Score;
	private int playerToMove;
	private Dictionary dictionary;

	/**
	 * Constructor- initializes the instance variables for the GameState
     * @param initBoard The initial state of the game baord
     * @param initHand The initial state of the current player's hand
     * @param initPlayerToMove An integer representing the player who is to move next
     * @param initP0Score Player 0's Score
     * @param initP1Score Player 1's Score
     */
	public ScrabbleGameState (ScrabbleBoard initBoard,
	                          Vector<ScrabbleTile> initHand,
	                          int initPlayerToMove, int initP0Score,
	                          int initP1Score, Dictionary initDictionary)
	{
		// Initialize instance vars
	    board = initBoard;
	    curHand = initHand;
	    playerToMove = initPlayerToMove;
	    p0Score = initP0Score;
	    p1Score = initP1Score;
	    dictionary = initDictionary;
	}
	
	/**
	 * Returns the board of this game state.
	 * @return The board of this game state.
	 */
	public ScrabbleBoard getBoard()
	{
	    return board;
	}
	
	/**
     * Returns the current hand.
     * @return The current hand
     */
	public Vector<ScrabbleTile> getHand()
	{
	    return curHand;
	}
	
	/**
     * Given a player ID, returns that player's score.
     * 
     * @param whose  ID of the player whose score to retrieve (0 or 1)
     * @return the requested player's score, or -1 for an invalid ID
     */
	public int getScore(int whose)
	{
	    if (whose == 0)
	    {
	        return p0Score;
	    }
	    else if (whose == 1)
	    {
	        return p1Score;
	    }
	    else
	    {
	        // Bad selection
	        return -1;
	    }
	}
	
	/**
	 * Gets the ID of the player to move.
	 * 
	 * @return the ID of the turn player
	 */
	public int whoseMove()
	{
	    return playerToMove;
	}
	
	/**
	 * Retrieves a pointer to the dictionary.
	 * 
	 * @return a pointer to the dictionary
	 */
	public Dictionary getDictionary()
	{
	    return dictionary;
	}

}