package scrabble;

import java.awt.Point;
import java.util.Vector;

/** * Class representing an unskilled AI player.
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerEasy extends ScrabbleComputerPlayer {

    public ScrabbleComputerPlayerEasy() {
        
    }

    /**  * Respond to a move request by making a move. */
    protected void doRequestMove () {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        game.applyAction(new ScrabbleDiscardAction(this, hand));
        ScrabbleTile[] handContainer = new ScrabbleTile[7];
        hand.toArray(handContainer);
        
        Vector<String> combinations = new Vector<String>();
        
        ScrabbleGameState myState = (ScrabbleGameState)game.getState(this, 0);
        ScrabbleBoard boardState = myState.getBoard();
        
        for(int i = 2; i < ScrabbleBoard.BOARD_HEIGHT-2; i++)
        {
        	for(int j = 2; j < ScrabbleBoard.BOARD_WIDTH-2; j++)
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
        				for(String s: combinations)
        				{
        					if (Dictionary.wordList.contains(s))
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
        						game.applyAction(move);
        						
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
        						game.applyAction(move);
        						
        					}
        				}
        			}
        		}
        	}
        }
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
		if (checkBounds > ScrabbleBoard.BOARD_WIDTH)
		{
			checkBounds = ScrabbleBoard.BOARD_WIDTH;
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