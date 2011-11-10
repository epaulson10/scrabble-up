package scrabble;

import game.*;
import java.util.*;

import ttt.TTTMoveAction;

/**ScrabbleGameImpl
Enforces the Scrabble game rules and sets the game state.*/
public class ScrabbleGameImpl extends GameImpl implements ScrabbleGame {
	private ScrabbleBoard board;
	private Vector<ScrabbleTile> bag;
	private int p0Score;
	private int p1Score;
	private int playerToMove;

	/** Constructor - initializes instance variables */
	public ScrabbleGameImpl () {

	}

	/** Determines if a given game player can make a move

@param gp the GamePlayer in question
@return true if the player can move, false otherwise */
	protected boolean canMove (GamePlayer gp) {
		return false;
	}

	/** Determines if the player has the ability to quit.

@param gp the GamePlayer in question
@return true if the player can quit, false otherwise */
	protected boolean canQuit (GamePlayer gp) {
		return false;
	}

	/** Determines if the game is over.

@return true if the game is over, false otherwise */
	public boolean gameOver () {
		return false;
	}

	/** Returns the current GameState relative to the given player.

@param gp the current GamePlayer
@param stateType This value is ignored in our implementation
@return the current GameState */
	protected GameState getGameState (GamePlayer gp, int stateType) {
		return null;
	}

	/** Initializes the starting state of the game */
	protected void initializeGame () {

	}

	/** Processes a GameMoveAction from a given player

@param thePlayer the player making a move
@param move the move the player is making
@return true if the move was valid, false otherwise */
	protected boolean makeMove (GamePlayer thePlayer, GameMoveAction move) 
	{
		/*added*/
		/***********************************************************************/
		ScrabbleMoveAction mv = (ScrabbleMoveAction)move;
		ScrabblePlayer plr = (ScrabblePlayer)thePlayer;
		// get the 0/1 id of our player
		int playerId = indexOf(thePlayer);

		// if the player is not a player for our game, indicate an illegal
		// move
		if (playerId < 0) return false;

		if(mv.isDiscard())
		{
			Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
			hand = plr.getHand();
			for(ScrabbleTile tile : mv.getTiles())
			{
				hand.remove(tile);
			}
			Random ran = new Random();
			int index;

			for(int i = 0; i < mv.getTiles().size(); i++)
			{
				index = ran.nextInt(bag.size());
				hand.add(bag.elementAt(index));
				bag.remove(index);
			}
			plr.updateHand(hand);
			plr.noDiscard();
		}

		else if(mv.isPlay())
		{

		}
		/***********************************************************************/
		return false;
	}

	/** Determines the score of a Scrabble play

@param move The ScrabbleMoveAction being analyzed
@return the score of the move */
	public int getMoveScore (ScrabbleMoveAction move) {
		return 0;
	}

	/** Checks to see if a player has won the game

@return true if someone has won, false otherwise */
	private static boolean checkWinner () {
		return false;
	}

	/** Returns an integer representing the player who has won the game. Returns -1 if no player has won.

@return The number representing the winning player or -1 if no one has won yet */
	public int getWinner () {
		return 0;
	}

	/** Returns the maximum allowed number of players
Note: this implemenation is for a two player game only

@return the max number of players */
	public int maxPlayersAllowed () {
		return 0;
	}

	/** Returns the minimum allowed  number of players
Note: This implementation is for a two player game only

@return the min number of players */
	public int minPlayersAllowed () {
		return 0;
	}

	/** Returns whether or not null players are allowed

@return true if null players are allowed, false if not */
	public boolean nullPlayersAllowed () {
		return false;
	}

}