package scrabble;

import game.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**ScrabbleGameImpl
Enforces the Scrabble game rules and sets the game state.*/
public class ScrabbleGameImpl extends GameImpl implements ScrabbleGame {
	private ScrabbleBoard board;
	private Vector<ScrabbleTile> bag;
	private int p0Score;
	private int p1Score;
	private int playerToMove;
	private Dictionary dictionary;

	/** Constructor - initializes instance variables */
	public ScrabbleGameImpl () {
		try {
			dictionary = new Dictionary();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		// typecast the GamePlayer to a ScrabblePlayer
		ScrabblePlayer plr = (ScrabblePlayer)thePlayer;
		
		// Create a new Vector to store the player's hand
		Vector<ScrabbleTile> hand = plr.getHand();
		
		// get the 0/1 id of our player
		int playerID = indexOf(thePlayer);

		// if the player is not a player for our game, indicate an illegal
		// move
		if (playerID < 0 || playerID > 1) return false;

		// if the move is a discard move
		if(move instanceof ScrabbleDiscardAction)
		{
			// Typecast the GameMoveAction to a ScrabbleDiscardAction
			ScrabbleDiscardAction discMv = (ScrabbleDiscardAction)move;
			
			// Tiles to be discarded
			Vector<ScrabbleTile> discTiles = discMv.getTiles();
			
			// for each tile to remove
			// add it to the bag
			for(ScrabbleTile tile : discTiles)
			{
				bag.add(tile);
			}
			// remove the tiles played and pick
			// new tiles from the bag
			updateHand(hand, discTiles, plr);
			
			return true;
		}

		else if(move instanceof ScrabbleMoveAction)
		{
			// Typecast the GameMoveAction to a ScrabbleMoveAction
			ScrabbleMoveAction mv = (ScrabbleMoveAction)move;
			// vector of the position where the tiles were played
			Vector<Point> pos = mv.getPositions();
			// vector of the tiles played
			Vector<ScrabbleTile> tiles = mv.getTiles();
			
			// if number of tiles and number of positions are unequal, this
			// was a bad move
			if (tiles.size() != pos. size())
			{
			    return false;
			}
			
			// check if it was a valid move
			if(checkValMove(pos, tiles))
			{
				updateHand(hand, tiles, plr);
				
				// apply move to master board
				for (int i = 0; i < tiles.size(); i++)
				{
				    Point curPos = pos.get(i);
				    ScrabbleTile curTile = tiles.get(i);
				    board.putTile(curPos.y, curPos.x, curTile);
				}
				
				// move was legal
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Removes tiles played from the player's hand
	 * and adds new tiles to the players hand
	 * CAVEAT: Might move all code into the Player's version
	 * @param hand Vector of current tiles in plyrs hand
	 * @param tiles Vector of tiles played during move
	 * @param plr Player who made the move
	 */
	private void updateHand(Vector<ScrabbleTile> hand,
			Vector<ScrabbleTile> tiles, ScrabblePlayer plr) 
	{
		// create a random number generator to get a random tile
		// and an index to store random num
		Random ran = new Random();
		int index;
		
		// for each tile played remove it from the player's hand
		// and add a new tile to the player's hand
		for(ScrabbleTile tile : tiles)
		{
			hand.remove(tile);
			index = ran.nextInt(bag.size());
			hand.add(bag.elementAt(index));
			bag.removeElementAt(index);
		}
		// update the player's hand with this new hand
		plr.updateHand(hand);
	}

	private boolean checkValMove(Vector<Point> pos, 
			Vector<ScrabbleTile> tiles) {
		
		char word[] = new char[10];
		int count1;
		int count2;
		for(int i = 0; i < tiles.size(); i++)
		{
			board.putTile(pos.elementAt(i).x, pos.elementAt(i).y, tiles.elementAt(i));
		}
		
		for(int i = 0; i < board.size; i++)
		{
			for(int j = 0; j < board.size; j++)
			{
				if(board.getTileAt(i, j) != null);
				{
					count1 = i;
					count2 = j;
					int index = 0;
					while(board.getTileAt(count1,count2) != null)
					{
						word[index] = board.getTileAt(count1, count2).getLetter();
						count2++;
						index++;
					}
					if(!dictionary.isValidWord(word.toString()))
					{
						return false;
					}
					resetWord(word);
					count1 = i;
					count2 = j;
					index = 0;
					while(board.getTileAt(count1++, count2) != null)
					{
						word[index] = board.getTileAt(count1, count2).getLetter();
						count1++;
						index++;
					}
					if(!dictionary.isValidWord(word.toString()))
					{
						return false;
					}
					return true;
				}	
			}
		}
		return false;
	}

	private void resetWord(char[] word) {
		// TODO Auto-generated method stub
		for(int i = 0; i < word.length; i++)
		{
			word[i] = 0;
		}
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