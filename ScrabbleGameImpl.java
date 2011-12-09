package scrabble;

import game.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**ScrabbleGameImpl
Enforces the Scrabble game rules and sets the game state.*/
public class ScrabbleGameImpl extends GameImpl implements ScrabbleGame {
	private static ScrabbleBoard board;
	private Vector<ScrabbleTile> bag;
	private int p0Score;
	private int p1Score;
	private Vector<ScrabbleTile> p0Hand;
	private Vector<ScrabbleTile> p1Hand;
	private int playerToMove;
	private int winner;
	private static Dictionary dictionary;
	private static final boolean INVALID_MOVE = false;
	private static final boolean VALID_MOVE = true;

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
		p0Hand = new Vector<ScrabbleTile>();
		p1Hand = new Vector<ScrabbleTile>();
		playerToMove = 0;
		winner = -1;
		
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
        Collections.shuffle(bag);
        
        // Draw initial hands
        for (int i = 0; i < 7; i++)
        {
            ScrabbleTile drawnTile = bag.lastElement();
            p0Hand.add(drawnTile);
            bag.remove(drawnTile);
            drawnTile = bag.lastElement();
            p1Hand.add(drawnTile);
            bag.remove(drawnTile);
        }
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
	    Vector<ScrabbleTile> curHand = new Vector<ScrabbleTile>();
	    if (gp.getId() == 0)
	    {
	        curHand = p0Hand;
	    }
	    else
	    {
	        curHand = p1Hand;
	    }
	    
	    
		return new ScrabbleGameState(newBoard, curHand, playerToMove, p0Score, p1Score);
	}

	/** Initializes the starting state of the game */
	protected void initializeGame ()
	{
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
		Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
		
		if (plr.getId() == 0)
		{
		    hand = p0Hand;
		}
		else
		{
		    hand = p1Hand;
		}
		
		// get the 0/1 id of our player
		int playerID = plr.getId();

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
			if (plr instanceof ScrabbleHumanPlayer)
			{
			    ScrabblePlayerUI.putInHand(
			            ((ScrabbleHumanPlayer)plr).getHand());
			}
			
			// pass control to the other player
            playerToMove = 1 - playerToMove;
            
            notifyAllStateChanged();
			
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
			if (tiles.size() != pos.size())
			{
			    return false;
			}
			
			// check if it was a valid move
			if(checkValidMove(pos, tiles))
			{
				updateHand(hand, tiles, plr);
				if (plr instanceof ScrabbleHumanPlayer)
	            {
	                ScrabblePlayerUI.putInHand(
	                        ((ScrabbleHumanPlayer)plr).getHand());
	            }
							
				// get this move's score and add it to the appropriate
                int moveScore = getMoveScore(mv);
                // player's score
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
				notifyAllStateChanged();
				
				return true;
			}
			return false;
		}
		else if (move instanceof ScrabbleResignAction)
		{
		    winner = 1-thePlayer.getId();
		    notifyAllStateChanged();
		    return true;
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
		    if (!(plr instanceof ScrabbleProxyPlayer))
		    {
			    hand.remove(tile);
		    }
		    else
		    {
		        for (ScrabbleTile handTile : hand)
		        {
		            if (tile.getLetter() == handTile.getLetter() && tile.isBlank() == handTile.isBlank())
		            {
		                hand.remove(handTile);
		                break;
		            }
		        }
		    }
			// if bag is not empty
			if(bag.size() != 0)
			{
				index = ran.nextInt(bag.size());
				hand.add(bag.elementAt(index));
				bag.removeElementAt(index);
			}
		}
		
		if (plr.getId() == 0)
		{
		    p0Hand = hand;
		}
		else
		{
		    p1Hand = hand;
		}
		
		// Check if game-over conditions are met
        if (!gameOver() && checkWinner())
        {
            for (ScrabbleTile tile : p0Hand)
            {
                p0Score -= tile.getValue();
                if (bag.size() == 0)
                {
                    // Award points to opponent
                    p1Score += tile.getValue();
                }
            }
            for (ScrabbleTile tile : p1Hand)
            {
                p1Score -= tile.getValue();
                if (bag.size() == 0)
                {
                    // Award points to opponent
                    p0Score += tile.getValue();
                }
            }
            
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
        }
	}

	/**
	 * Checks if the move made by a player is valid
	 * @param pos vector of the positions of the tiles played
	 * @param tiles tiles played in move
	 * @return if valid move
	 */
	public static boolean checkValidMove(Vector<Point> pos, 
			Vector<ScrabbleTile> tiles) {
		
		// copy of the board before the move
		ScrabbleBoard copyBoard = new ScrabbleBoard();
        for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
        {
            for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
            {
                ScrabbleTile thisTile = board.getTileAt(row, col);
                ScrabbleTile newTile = null;
                if (thisTile != null)
                {
                    newTile = new ScrabbleTile(thisTile.getLetter(),
                        thisTile.getValue(), thisTile.isBlank());
                }
                // copy tile from master board to same position on copy board
                copyBoard.putTileAt(row, col, newTile);
            }
        }
		
        // Make sure played tiles are part of a contiguous row/column
        boolean isRow = true;
        boolean isColumn = true;
        int lastX = -1;
        int lastY = -1;
        for (Point p : pos)
        {
            if (lastX == -1)
            {
                lastX = p.x;
            }
            else
            {
                if (p.x != lastX)
                {
                    isColumn = false;
                }
            }
            if (lastY == -1)
            {
                lastY = p.y;
            }
            else
            {
                if (p.y != lastY)
                {
                    isRow = false;
                }
            }
        }
        
        if (!(isRow || isColumn))
        {
            return false;
        }
        
        if (isRow)
        {
            // Make sure there are no null spaces between the lowest-x tile
            // and highest-x tile
            int lowX = 14;
            int highX = 0;
            for (Point p : pos)
            {
                if (p.x < lowX)
                {
                    lowX = p.x;
                }
                if (p.x > highX)
                {
                    highX = p.x;
                }
            }
            for (int i = lowX; i <= highX; i++)
            {
                // This should be either a tile on the board or a position
                // played in this move
                if (copyBoard.getTileAt(lastY, i) == null &&
                    !pos.contains(new Point(i, lastY)))
                {
                    return INVALID_MOVE;
                }
            }
        }
        else if (isColumn)
        {
            // Make sure there are no null spaces between the lowest-y tile
            // and highest-y tile
            int lowY = 14;
            int highY = 0;
            for (Point p : pos)
            {
                if (p.y < lowY)
                {
                    lowY = p.y;
                }
                if (p.y > highY)
                {
                    highY = p.y;
                }
            }
            for (int i = lowY; i <= highY; i++)
            {
                // This should be either a tile on the board or a position
                // played in this move
                if (copyBoard.getTileAt(i, lastX) == null &&
                    !pos.contains(new Point(lastX, i)))
                {
                    return INVALID_MOVE;
                }
            }
        }
        
		// string to test
		String testString = "";

		// Make sure points are on the board
        for (Point p : pos)
        {
            if (p.x < 0 || p.x >= 15 || p.y < 0 || p.y >= 15)
            {
                return INVALID_MOVE;
            }
        }
        
		// counters to iterate through the characters of the word to check
		int rowCount;
		int colCount;
		Boolean valid = false;
		
		// if first turn (when space 7,7 is empty)
		if(copyBoard.getTileAt(7, 7) == null)
		{
			// check to make sure that one of the potential points
			// is at the center
			for(Point p :pos)
			{
				if(p.x == 7 && p.y == 7) valid = true;
			}
		}
		// else it is not the first turn
		else
		{
			// check that the played word is connected to another word
			// check each adjacent space for each potential position
			// and make sure that there is another tile.
			for(Point p : pos)
			{
				if(p.x-1 >=0 && copyBoard.getTileAt(p.y, p.x-1) != null)
				{	
					valid = true;
					break;
				}
				else if(p.x+1 <15 && copyBoard.getTileAt(p.y, p.x+1) != null)
				{
					valid = true;
					break;
				}
				else if(p.y-1 >= 0 && copyBoard.getTileAt(p.y-1, p.x) != null)
				{
					valid = true;
					break;
				}
				else if(p.y+1 < 15 && copyBoard.getTileAt(p.y+1, p.x) != null)
				{
					valid = true;
					break;
				}
			}
		}
		if(!valid) return INVALID_MOVE;
		
		// place the played word onto the copy of the board 
		for(int i = 0; i < tiles.size(); i++)
		{
		    ScrabbleTile thisTile = tiles.elementAt(i);
            ScrabbleTile newTile = null;
            if (thisTile != null)
            {
                newTile = new ScrabbleTile(thisTile.getLetter(),
                    thisTile.getValue(), thisTile.isBlank());
            }
            if (copyBoard.getTileAt(pos.elementAt(i).y, pos.elementAt(i).x)
                    != null)
            {
                return INVALID_MOVE;
            }
			copyBoard.putTileAt(pos.elementAt(i).y, pos.elementAt(i).x, newTile);
		}
		
		// check that all of the words on the board are still valid after
		// playing the played word
		for(int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
		{
			for(int row = 0; row < ScrabbleBoard.BOARD_WIDTH; row++)
			{
				// Find a letter tile
				if(copyBoard.getTileAt(row, col) != null)
				{
					testString = "";
					rowCount = row;
					colCount = col;
					int index = 0;
					
					// If this is the start of a word in the row check the word across
					if(col == 0 || copyBoard.getTileAt(rowCount, colCount-1) == null)
					{
						// get the whole word
						while(colCount < ScrabbleBoard.BOARD_WIDTH &&
						      copyBoard.getTileAt(rowCount,colCount) != null)
						{
							testString += Character.toString(copyBoard.getTileAt(rowCount, colCount).getLetter());
							colCount++;
							index++;
						}//while
						
						if(index > 1)
						{
							// check to see if word is valid
							if(!dictionary.isValidWord(testString))
							{
								return INVALID_MOVE;
							}//if
						}//if
						
						// reset variables
						testString = "";
						rowCount = row;
						colCount = col;
						index = 0;
					}//if
					
					// If this is the start of a word in the column check the word down
					if(row == 0 || copyBoard.getTileAt(rowCount-1, colCount) == null)
					{
						// get the whole word
						while(rowCount < ScrabbleBoard.BOARD_WIDTH &&
						      copyBoard.getTileAt(rowCount, colCount) != null)
						{
							testString += Character.toString(copyBoard.getTileAt(rowCount, colCount).getLetter());
							rowCount++;
							index++;
						}//while
						
						if(index > 1)
						{	
							// check to see if word is valid
							if(!dictionary.isValidWord(testString))
							{
								return INVALID_MOVE;
							}//if
						}//if
					}//if
				}//if
			}//for
		}//for
		// board = copyBoard;
		return VALID_MOVE;
	}
	
	/**
	 * Gets the score of the current move.
	 * @param positions positions of the tiles played
	 * @return the score of the move
	 */
	public int getMoveScore(Vector<Point> positions)
	{
		int score = 0;
		Point pos = positions.elementAt(0);
		
		// Bingo bonus
		if (positions.size() == 7)
		{
		    score += 50;
		}
		
		// if there is only one tile played
		if(positions.size() == 1)
		{
			// add the score of the tile played
			score += board.getTileAt(pos.x, pos.y).getValue();
			// add the score of connected tiles in the x-direction
			score += xDir(pos);
			// add the score of connected tiles in the y-direction
			score += yDir(pos);
		}
		// if the tiles were laid down in the same row
		else if(pos.x == positions.elementAt(1).x)
		{
			// add the score of the first tile
			score += board.getTileAt(pos.x, pos.y).getValue();
			// add all other tiles connected in the row
			score += yDir(positions.elementAt(0));
			
			for(Point pos2 : positions)
			{
				// add tiles values of tiles in adjacent col 
				score += xDir(pos2);
			}
		}
		// if the tiles were laid down in the same col
		else if(pos.y == positions.elementAt(1).y)
		{
			// add the score of the first tile
			score += board.getTileAt(pos.x, pos.y).getValue();
			// add all other tiles connected in col
			score += xDir(positions.elementAt(0));
			
			for(Point pos2 : positions)
			{
				// add tile values of tiles in adjacent row
				score += yDir(pos2);
			}
		}
		return score;
	}
	
	/**
	 * Helper method get the score in the x direction
	 * not including the tile at point pos
	 * @param pos point of the tile to check values around
	 * @return int of the total score in the row
	 */
	public int xDir(Point pos)
	{
		int score = 0;
		int x = pos.x - 1;;
		int y = pos.y;
		while(x >= 0 && board.getTileAt(x, y) != null)
		{
			score += board.getTileAt(x,y).getValue();
			x--;
		}
		x = pos.x + 1;
		y = pos.y;
		while(x < 15 && board.getTileAt(x, y) != null)
		{
			score += board.getTileAt(x,y).getValue();
			x++;
		}
		return score;
	}
	
	/**
	 * Helper method get the score in the y direction
	 * not including the tile at point pos
	 * @param pos point of the tile to check values around
	 * @return int of the total score in the col
	 */
	public int yDir(Point pos)
	{
		int score = 0;
		int x = pos.x;
		int y = pos.y -1;
		while(y >= 0 && board.getTileAt(x, y) != null)
		{
			score += board.getTileAt(x,y).getValue();
			y--;
		}
		x = pos.x;
		y = pos.y + 1;
		while(y < 15 && board.getTileAt(x, y) != null)
		{
			score += board.getTileAt(x,y).getValue();
			y++;
		}
		return score;
	}

	/**
	 * Determines the score of a Scrabble play.
	 * 
     * @param move The ScrabbleMoveAction being analyzed
     * @return the score of the move, or -1 if move is invalid
     */
	public static int getMoveScore (ScrabbleMoveAction move)
	{
	    // make copy of master board
        ScrabbleBoard newBoard = new ScrabbleBoard();
        for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
        {
            for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
            {
                ScrabbleTile thisTile = board.getTileAt(row, col);
                ScrabbleTile newTile = null;
                if (thisTile != null)
                {
                    newTile = new ScrabbleTile(thisTile.getLetter(),
                        thisTile.getValue(), thisTile.isBlank());
                }
                // copy tile from master board to same position on copy board
                newBoard.putTileAt(row, col, newTile);
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
            ScrabbleTile thisTile = tiles.get(i);
            if(thisTile == null)
            {
                return -1;
            }
            ScrabbleTile newTile = new ScrabbleTile(thisTile.getLetter(),
                    thisTile.getValue(), thisTile.isBlank());
            newBoard.putTileAt(row, col, newTile);
        }
        
        // Scan for words formed or modified:
        
        // Move's score so far
        int score = 0;
        // Bingo bonus
        if (tiles.size() == 7)
        {
            score += 50;
        }
        for (int row = 0; row < ScrabbleBoard.BOARD_HEIGHT; row++)
        {
            for (int col = 0; col < ScrabbleBoard.BOARD_WIDTH; col++)
            {
                if (newBoard.getTileAt(row, col) != null)
                {                    
                    // Could this tile begin a vertical word?
                    if (row-1 < 0 || newBoard.getTileAt(row-1, col) == null)
                    {
                        Vector<ScrabbleTile> word = new Vector<ScrabbleTile>();
                        Vector<Point> newPos = new Vector<Point>();
                        newPos.add(new Point(col,row));
                        word.add(newBoard.getTileAt(row,col));
                        // Get rest of tiles in this word
                        for (int dRow = 1; (row+dRow < ScrabbleBoard.BOARD_HEIGHT)
                                        && (newBoard.getTileAt(row+dRow, col) != null);
                                        dRow++)
                        {
                            newPos.add(new Point(col, row+dRow));
                            word.add(newBoard.getTileAt(row+dRow, col));
                        }//for
                        
                        // Is this a 2+ letter word?
                        if (word.size() > 1)
                        {
                            // Is this a new/changed word?
                            boolean isNew = false;
                            for (int i = 0; i < word.size(); i++)
                            {
                                if (positions.contains(newPos.get(i)))
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
                        Vector<Point> newPos = new Vector<Point>();
                        newPos.add(new Point(col,row));
                        word.add(newBoard.getTileAt(row,col));
                        // Get rest of tiles in this word
                        for (int dCol = 1; (col+dCol < ScrabbleBoard.BOARD_WIDTH)
                                        && (newBoard.getTileAt(row, col+dCol) != null);
                                        dCol++)
                        {
                            newPos.add(new Point(col+dCol, row));
                            word.add(newBoard.getTileAt(row, col+dCol));
                        }//for
                        
                        // Is this a 2+ letter word?
                        if (word.size() > 1)
                        {
                            // Is this a new/changed word?
                            boolean isNew = false;
                            for (int i = 0; i < word.size(); i++)
                            {
                                if (positions.contains(newPos.get(i)))
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
		if (bag.size() == 0 && (p0Hand.size() == 0 || p1Hand.size() == 0))
		{
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