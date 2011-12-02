package scrabble;

import game.*;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/** * Class representing a skilled AI player. Here is it's basic winning strategy:
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerHard extends ScrabbleComputerPlayer {

    public ScrabbleComputerPlayerHard() {
        
    }
    
    /**  * Respond to a move request by making a move. */
    protected void doRequestMove () {
    	ScrabbleGameState myState = (ScrabbleGameState)game.getState(this, 0);
    	Vector<ScrabbleTile> hand = myState.getHand();
    	if(!hand.contains(new ScrabbleTile('s', 1, false)))
    	{
    		easyMove();
    	}
    	
    	
    	ScrabbleTile[] handContainer = new ScrabbleTile[7];
        hand.toArray(handContainer);
        
        Vector<String> combinations = new Vector<String>();
        Vector<GameMoveAction> moves = new Vector<GameMoveAction>();
        
        ScrabbleBoard boardState = myState.getBoard();
        Dictionary dict = myState.getDictionary();
        
        for(int i = 2; i < ScrabbleBoard.BOARD_HEIGHT-3; i++)
        {
        	for(int j = 2; j < ScrabbleBoard.BOARD_WIDTH-3; j++)
        	{
        		if(boardState.getTileAt(i, j) != null)
        		{
        			int curR = i;
        			int curC = j;
        			String right = "";
        			String down = "";
        			while(boardState.getTileAt(curR,curC) != null)
        			{
        				right += boardState.getTileAt(curR, curC).toString();
        				curC++;
        			}
        			if(curC < ScrabbleBoard.BOARD_WIDTH-1)
        			{
        				right += "s";
        				if(dict.isValidWord(right))
        				{
        					for(int k = 0; k < handContainer.length; k++)
            				{
            					for(int l = 0; l < handContainer.length; l++)
            					{
            						if(k == l || 
            								handContainer[k].toString().toUpperCase().equals("S") ||
            								handContainer[l].toString().toUpperCase().equals("S"))
            						{
            							continue;
            						}
            						String word = "s" +
            									handContainer[k] + handContainer[l];
            						combinations.add(word);
            					}
            				}
            				for(String s: combinations)
            				{
            					if (dict.isValidWord(s))
            					{
            						int loc0 = findTile("s", handContainer);
            						int loc1 = findTile(s.substring(1,2), handContainer);
            						int loc2 = findTile(s.substring(2), handContainer);
            						
            						Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
            						play.add(handContainer[loc0]);
            						play.add(handContainer[loc1]);
            						play.add(handContainer[loc2]);
            						Vector<Point> playSpots = new Vector<Point>(3);
            						playSpots.add(new Point(curR,curC));
            						playSpots.add(new Point(curR+1,curC));
            						playSpots.add(new Point(curR+2, curC));
            						
            						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
            						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
            						moves.add(move);
            						
            					}
            				}
        				}
        			}
        			curC = j;
        			while(boardState.getTileAt(curR,curC) != null)
        			{
        				down += boardState.getTileAt(curR, curC).toString();
        				curR++;
        			}
        			if(curC < ScrabbleBoard.BOARD_HEIGHT-1)
        			{
        				down += "s";
        				if(dict.isValidWord(down))
        				{
        					//Attempt making word going RIGHT!!! with S as
        					//starting letter
        					for(int k = 0; k < handContainer.length; k++)
            				{
            					for(int l = 0; l < handContainer.length; l++)
            					{
            						if(k == l || 
            								handContainer[k].toString().toUpperCase().equals("S") ||
            								handContainer[l].toString().toUpperCase().equals("S"))
            						{
            							continue;
            						}
            						String word = "s" +
            									handContainer[k] + handContainer[l];
            						combinations.add(word);
            					}
            				}
            				for(String s: combinations)
            				{
            					if (dict.isValidWord(s))
            					{
            						int loc0 = findTile("s", handContainer);
            						int loc1 = findTile(s.substring(1,2), handContainer);
            						int loc2 = findTile(s.substring(2), handContainer);
            						
            						Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
            						play.add(handContainer[loc0]);
            						play.add(handContainer[loc1]);
            						play.add(handContainer[loc2]);
            						Vector<Point> playSpots = new Vector<Point>(3);
            						playSpots.add(new Point(curR,curC));
            						playSpots.add(new Point(curR,curC+1));
            						playSpots.add(new Point(curR, curC+2));
            						
            						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
            						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
            						moves.add(move);
            						
            					}
            				}
        				}
        			}
        		}
        	}
        }
        if(moves.isEmpty())
    	easyMove();
        
        
        ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, myState.getHand());
        moves.add(move);
        
        GameAction[] ga = new GameAction[0];
        ga = moves.toArray(ga);
        Random rand = new Random();
        game.applyAction(ga[rand.nextInt(ga.length)]);
    }
    
    
    
    
    protected void easyMove() 
    {
    	ScrabbleTile[] handContainer = new ScrabbleTile[7];

        Vector<String> combinations = new Vector<String>();
        Vector<GameMoveAction> moves = new Vector<GameMoveAction>();
        
        ScrabbleGameState myState = (ScrabbleGameState)game.getState(this, 0);
        ScrabbleBoard boardState = myState.getBoard();
        handContainer = myState.getHand().toArray(handContainer);
        
        for(int i = 2; i < ScrabbleBoard.BOARD_HEIGHT-3; i++)
        {
        	for(int j = 2; j < ScrabbleBoard.BOARD_WIDTH-3; j++)
        	{
        		if(boardState.getTileAt(i, j) != null)
        		{
        			boolean down = checkDown(boardState, i, j);
        			boolean right = checkRight(boardState, i, j);
        			if(down)
        			{
        				for(int k = 0; k < handContainer.length; k++)
        				{
        					for(int l = 0; l < handContainer.length; l++)
        					{
        						if(k == l)
        						{
        							continue;
        						}
        						String word = "" + boardState.getTileAt(i, j) +
        									handContainer[k] + handContainer[l];
        						combinations.add(word);
        					}
        				}
        				Dictionary dict = myState.getDictionary();
        				for(String s: combinations)
        				{
        					if (dict.isValidWord(s))
        					{
        						int loc1 = findTile(s.substring(1,2), handContainer);
        						int loc2 = findTile(s.substring(2), handContainer);
        						Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(2);
        						play.add(handContainer[loc1]);
        						play.add(handContainer[loc2]);
        						Vector<Point> playSpots = new Vector<Point>(2);
        						playSpots.add(new Point(i+1,j));
        						playSpots.add(new Point(i+2,j));
        						
        						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
        						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
        						moves.add(move);
        						
        					}
        				}
        			}
        			if(right)
        			{
        				for(int k = 0; k < handContainer.length; k++)
        				{
        					for(int l = 0; l < handContainer.length; l++)
        					{
        						if(k == l)
        						{
        							continue;
        						}
        						String word = "" + boardState.getTileAt(i, j) +
        									handContainer[k] + handContainer[l];
        						combinations.add(word);
        					}
        				}
        				for(String s: combinations)
        				{
        					if (myState.getDictionary().isValidWord(s))
        					{
        						int loc1 = findTile(s.substring(1,2), handContainer);
        						int loc2 = findTile(s.substring(2), handContainer);
        						Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(2);
        						play.add(handContainer[loc1]);
        						play.add(handContainer[loc2]);
        						Vector<Point> playSpots = new Vector<Point>(2);
        						playSpots.add(new Point(i,j+1));
        						playSpots.add(new Point(i,j+2));
        						
        						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
        						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
        						moves.add(move);
        						
        					}
        				}
        			}
        		}
        	}
        }
       
        
        ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, myState.getHand());
        moves.add(move);
        
        GameAction[] ga = new GameAction[0];
        ga = moves.toArray(ga);
        Random rand = new Random();
        game.applyAction(ga[rand.nextInt(ga.length)]);
        
    }

	private int findTile(String s, ScrabbleTile[] hand) {
		for(int i = 0; i<hand.length; i++)
		{
			if((""+hand[i]).equals(s))
			{
				return i;
			}
		}
		
		return -1;
	}

	private boolean checkRight(ScrabbleBoard boardState, int i, int j) {
		int checkBounds = j+3;
		if (checkBounds > ScrabbleBoard.BOARD_WIDTH)
		{
			checkBounds = ScrabbleBoard.BOARD_WIDTH;
		}
		
		for(int k = j; k<checkBounds; k++)
		{
			if(boardState.getTileAt(i,k) != null)
			{
				return false;
			}
		}
				return true;

	}

	private boolean checkDown(ScrabbleBoard boardState, int i, int j) {
		int checkBounds = i+3;
		if (checkBounds > ScrabbleBoard.BOARD_HEIGHT)
		{
			checkBounds = ScrabbleBoard.BOARD_HEIGHT;
		}
		
		for(int k = i; k<checkBounds; k++)
		{
			if(boardState.getTileAt(k,j) != null)
			{
				return false;
			}
		}
		
		
		return true;
	}


}