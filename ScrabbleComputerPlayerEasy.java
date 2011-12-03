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
		Vector<GameMoveAction> moves = new Vector<GameMoveAction>();

		ScrabbleGameState myState = (ScrabbleGameState)game.getState(this, 0);
		ScrabbleBoard boardState = myState.getBoard();
		handContainer = myState.getHand().toArray(handContainer);

		for(int i = 0; i < 7; i++){
			System.out.print(handContainer[i]);
		}
		System.out.println();
		System.out.println();
		for(int i = 2; i < ScrabbleBoard.BOARD_HEIGHT-3; i++)
		{
			for(int j = 2; j < ScrabbleBoard.BOARD_WIDTH-3; j++)
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
						playSpots.add(new Point(i,j));
						playSpots.add(new Point(i,j+1));
						if(j+1 > ScrabbleBoard.BOARD_HEIGHT-1){
							continue;
						}
						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
							moves.add(move);
					}
				}
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
						playSpots.add(new Point(i,j));
						playSpots.add(new Point(i+1,j));
						if(i+1 > ScrabbleBoard.BOARD_WIDTH-1){
							continue;
						}

						ScrabbleMoveAction move = new ScrabbleMoveAction(this, play, playSpots);
						if(ScrabbleGameImpl.checkValidMove(playSpots,play))
							moves.add(move);
					}
				}

				{
					for(int k = 0; i < handContainer.length; i++)
					{
						Vector<ScrabbleTile> tile = new Vector<ScrabbleTile>(1);
						tile.add(handContainer[k]);
						Vector<Point> spot = new Vector<Point>(1);
						spot.add(new Point(i,j));
						ScrabbleMoveAction move = new ScrabbleMoveAction(this, tile, spot);
						if(ScrabbleGameImpl.checkValidMove(spot, tile))
							moves.add(move);
					}
				}
			}
		}
		if(moves.isEmpty())
		{
			Vector<ScrabbleTile> discard = new Vector<ScrabbleTile>();
			for(ScrabbleTile s: myState.getHand()){
				discard.add(s);
			}
			discard.remove(5);
			discard.remove(3);
			ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, discard);
			moves.add(move);
		}

		GameAction[] ga = new GameAction[0];
		ga = moves.toArray(ga);
		Random rand = new Random();
		game.applyAction(ga[rand.nextInt(ga.length)]);
		return;
	}
}