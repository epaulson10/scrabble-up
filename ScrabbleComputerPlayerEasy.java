package scrabble;

import java.awt.Point;
import game.*;

import java.util.Random;
import java.util.Vector;

/** * Class representing an unskilled AI player.
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerEasy extends ScrabbleComputerPlayer {

	public ScrabbleComputerPlayerEasy() 
	{

	}

	/**  * Respond to a move request by making a move. */
	protected void doRequestMove ()
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
								Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(2);
								play.add(handContainer[k]);
								play.add(handContainer[l]);
								Vector<Point> playSpots = new Vector<Point>(2);
								playSpots.add(new Point(i,j+1));
								playSpots.add(new Point(i,j+2));

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
								Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(2);
								play.add(handContainer[k]);
								play.add(handContainer[l]);
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
		return;
	}
	private boolean checkRight(ScrabbleBoard boardState, int i, int j) {
		int checkBounds = j+3;
		if (checkBounds > ScrabbleBoard.BOARD_WIDTH-1)
		{
			checkBounds = ScrabbleBoard.BOARD_WIDTH-1;
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
		if (checkBounds > ScrabbleBoard.BOARD_HEIGHT-1)
		{
			checkBounds = ScrabbleBoard.BOARD_HEIGHT-1;
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