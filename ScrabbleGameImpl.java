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
	private int winner;
	private Dictionary dictionary;
	private boolean handIsEmpty;

	/** Constructor - initializes instance variables */
	public ScrabbleGameImpl () {
		try {
			dictionary = new Dictionary();
		} catch (IOException e) {
			e.printStackTrace();
		}
		board = new ScrabbleBoard();
		bag = new Vector<ScrabbleTile>();
		p0Score = 0;
		p1Score = 0;
		playerToMove = 0;
		winner = -1;
		handIsEmpty = false;
	}

	/** Determines if a given game player can make a move

@param gp the GamePlayer in question
@return true if the player can move, false otherwise */
	protected boolean canMove (GamePlayer gp) {
		return gp.getId() == playerToMove;
	}

	/** Determines if the player has the ability to quit.

@param gp the GamePlayer in question
@return true if the player can quit, false otherwise */
	protected boolean canQuit (GamePlayer gp) {
		return true;
	}

	/** Determines if the game is over.

@return true if the game is over, false otherwise */
	public boolean gameOver ()
	{
	    // If a player has won, the game is over
		return winner >= 0;
	}

	/** Returns the current GameState relative to the given player.

@param gp the current GamePlayer
@param stateType This value is ignored in our implementation
@return the current GameState */
	protected GameState getGameState (GamePlayer gp, int stateType) {
	    // make copy of master board
	    ScrabbleBoard newBoard = new ScrabbleBoard();
	    for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
	    {
	        for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
	        {
	            // copy tile from master board to same position on copy board
	            newBoard.putTileAt(row, col, board.getTileAt(row, col));
	        }
	    }
	    
	    // get the player's hand
	    ScrabblePlayer sp = (ScrabblePlayer)gp;
	    Vector<ScrabbleTile> curHand = sp.getHand();
	    
		return new ScrabbleGameState(newBoard, curHand, playerToMove, p0Score, p1Score);
	}

	/** Initializes the starting state of the game */
	protected void initializeGame ()
	{
	    // Populate bag with tiles:
	    bag.removeAllElements();
	    // letters to add to bag; pipe chars separate letters of different
	    // value
	    String letterDist = "  |eeeeeeeeeeeeaaaaaaaaaiiiiiiiiioooooooo"
	                      + "nnnnnnrrrrrrttttttllllssssuuuu|ddddggg|bbcc"
	                      + "mmpp|ffhhvvwwyy|k|||jx||qz";
	    int curValue = 0;
	    
	    for (int i = 0; i < letterDist.length(); i++)
	    {
	        char curChar = letterDist.charAt(i);
	        if (curChar == ' ')
	        {
	            bag.add(new ScrabbleBlankTile());
	        }
	        else if (curChar == '|')
	        {
	            // reached a value threshold; following letters will be worth
	            // more points
	            
	            curValue++;
	        }
	        else
	        {
	            bag.add(new ScrabbleTile(curChar, curValue, false));
	        }
	    }
	    
	    // set other instance vars
	    p0Score = 0;
        p1Score = 0;
        playerToMove = 0;
        winner = -1;
	}

	/** 
	 * Processes a GameMoveAction from a given player
	 * @param thePlayer the player making a move
	 * @param move the move the player is making
	 * @return true if the move was valid, false otherwise 
	 */
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
			
			// pass control to the other player
            playerToMove = 1 - playerToMove;
			
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
			if(checkValidMove(pos, tiles))
			{
				updateHand(hand, tiles, plr);
				
				// get this move's score and add it to the appropriate
				// player's score
				int moveScore = getMoveScore(mv);
				if (playerID == 0)
				{
				    p0Score += moveScore;
				}
				else
				{
				    p1Score += moveScore;
				}
				
				// apply move to master board
				for (int i = 0; i < tiles.size(); i++)
				{
				    Point curPos = pos.get(i);
				    ScrabbleTile curTile = tiles.get(i);
				    board.putTileAt(curPos.y, curPos.x, curTile);
				}
				
				// pass control to the other player
				playerToMove = 1 - playerToMove;
				
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
		// remove that tile from the bag
		for(ScrabbleTile tile : tiles)
		{
			hand.remove(tile);
			// if bag is not empty
			if(bag.size() != 0)
			{
				index = ran.nextInt(bag.size());
				hand.add(bag.elementAt(index));
				bag.removeElementAt(index);
			}
		}
		
		// Check if game-over conditions are met
		if (bag.size() == 0 && hand.size() == 0)
		{
		    handIsEmpty = true;
		}
		
		// update the player's hand with this new hand
		plr.updateHand(hand);
	}

	/**
	 * Checks if the move made by a player is valid
	 * @param pos vector of the positions of the tiles played
	 * @param tiles tiles played in move
	 * @return if valid move
	 */
	private boolean checkValidMove(Vector<Point> pos, 
			Vector<ScrabbleTile> tiles) {
		
		// copy of the board before the move
		ScrabbleBoard copyBoard = board;
		
		// array for the word to check
		char word[] = new char[ScrabbleBoard.BOARD_HEIGHT];
		
		// counter 
		int count1;
		int count2;
		for(int i = 0; i < tiles.size(); i++)
		{
			copyBoard.putTileAt(pos.elementAt(i).x, pos.elementAt(i).y, tiles.elementAt(i));
		}
		
		for(int i = 0; i < ScrabbleBoard.BOARD_HEIGHT; i++)
		{
			for(int j = 0; j < ScrabbleBoard.BOARD_WIDTH; j++)
			{
				if(copyBoard.getTileAt(i, j) != null);
				{
					count1 = i;
					count2 = j;
					int index = 0;
					while(copyBoard.getTileAt(count1,count2) != null)
					{
						word[index] = copyBoard.getTileAt(count1, count2).getLetter();
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
					while(copyBoard.getTileAt(count1++, count2) != null)
					{
						word[index] = copyBoard.getTileAt(count1, count2).getLetter();
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

	/**
	 * Determines the score of a Scrabble play.
	 * 
     * @param move The ScrabbleMoveAction being analyzed
     * @return the score of the move, or -1 if move is invalid
     */
	public int getMoveScore (ScrabbleMoveAction move)
	{
	    // make copy of master board
        ScrabbleBoard newBoard = new ScrabbleBoard();
        for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
        {
            for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
            {
                // copy tile from master board to same position on copy board
                newBoard.putTileAt(row, col, board.getTileAt(row, col));
            }
        }
        
        // Get tiles played and add them to the test board
        Vector<ScrabbleTile> tiles = move.getTiles();
        Vector<Point> positions = move.getPositions();
        if (tiles.size() != positions.size())
        {
            // bad move action
            return -1;
        }
        for (int i = 0; i < tiles.size(); i++)
        {
            int row = positions.get(i).y;
            int col = positions.get(i).x;
            if (newBoard.getTileAt(row, col) != null)
            {
                // Trying to play a tile on an occupied space
                return -1;
            }
            ScrabbleTile tilePlayed = tiles.get(i);
            newBoard.putTileAt(row, col, tilePlayed);
        }
        
        // Scan for words formed or modified:
        
        // Move's score so far
        int score = 0;
        for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
        {
            for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
            {
                if (board.getTileAt(row, col) != null)
                {                    
                    // Could this tile begin a vertical word?
                    if (row-1 < 0 || newBoard.getTileAt(row-1, col) == null)
                    {
                        Vector<ScrabbleTile> word = new Vector<ScrabbleTile>();
                        word.add(newBoard.getTileAt(row,col));
                        // Get rest of tiles in this word
                        for (int dRow = 1; (row+dRow < ScrabbleBoard.BOARD_HEIGHT)
                                        && (newBoard.getTileAt(row+dRow, col) != null);
                                        dRow++)
                        {
                            word.add(newBoard.getTileAt(row+dRow, col));
                        }//for
                        
                        // Is this a 2+ letter word?
                        if (word.size() > 1)
                        {
                            // Is this a new/changed word?
                            boolean isNew = false;
                            for (int i = 0; i < word.size(); i++)
                            {
                                if (tiles.contains(word.get(i)))
                                {
                                    isNew = true;
                                }//if
                            }//for
                            
                            if (isNew)
                            {
                                // Add value of each letter to the score
                                for (int i = 0; i < word.size(); i++)
                                {
                                    score += word.get(i).getValue();
                                }//for
                            }//if
                        }//if
                    }//if
                    
                    // Could this tile begin a horizontal word?
                    if (col-1 < 0 || newBoard.getTileAt(row, col-1) == null)
                    {
                        Vector<ScrabbleTile> word = new Vector<ScrabbleTile>();
                        word.add(newBoard.getTileAt(row,col));
                        // Get rest of tiles in this word
                        for (int dCol = 1; (col+dCol < ScrabbleBoard.BOARD_WIDTH)
                                        && (newBoard.getTileAt(row, col+dCol) != null);
                                        dCol++)
                        {
                            word.add(newBoard.getTileAt(row, col+dCol));
                        }//for
                        
                        // Is this a 2+ letter word?
                        if (word.size() > 1)
                        {
                            // Is this a new/changed word?
                            boolean isNew = false;
                            for (int i = 0; i < word.size(); i++)
                            {
                                if (tiles.contains(word.get(i)))
                                {
                                    isNew = true;
                                }//if
                            }//for
                            
                            if (isNew)
                            {
                                // Add value of each letter to the score
                                for (int i = 0; i < word.size(); i++)
                                {
                                    score += word.get(i).getValue();
                                }//for
                            }//if
                        }//if
                    }//if
                }//if
            }//for
        }//for
        
        // Return total move score
        return score;
	}

	/** Checks to see if a player has won the game

@return true if someone has won, false otherwise */
	protected boolean checkWinner()
	{
		if (bag.size() == 0 && handIsEmpty)
		{
		    if (p0Score > p1Score)
		    {
		        winner = 0;
		    }
		    else if (p1Score > p0Score)
		    {
		        winner = 1;
		    }
		    else
		    {
		        winner = 2;
		    }
		    return true;
		}
		else
		{
		    winner = -1;
		    return false;
		}
	}

	/** Returns an integer representing the player who has won the game. Returns -1 if no player has won.

@return The number representing the winning player, -1 if no one has won yet, 2 for a tie*/
	public int getWinner () {
		return winner;
	}

	/** Returns the maximum allowed number of players
Note: this implemenation is for a two player game only

@return the max number of players */
	public int maxPlayersAllowed () {
		return 2;
	}

	/** Returns the minimum allowed  number of players
Note: This implementation is for a two player game only

@return the min number of players */
	public int minPlayersAllowed () {
		return 2;
	}

	/** Returns whether or not null players are allowed

@return true if null players are allowed, false if not */
	public boolean nullPlayersAllowed () {
		return false;
	}

}