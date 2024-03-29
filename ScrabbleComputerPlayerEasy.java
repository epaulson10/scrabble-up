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
        handContainer = myState.getHand().toArray(handContainer);
        
        
        //Discards any of the blank tiles in the hand
        for(int i = 0; (i < 7 && handContainer[i] != null); i++)
        {
            if(handContainer[i].getValue() == 0)
            {
                Vector<ScrabbleTile> discard = new Vector<ScrabbleTile>();
                discard.add(handContainer[i]);
                ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, discard);


                game.applyAction(move);
                return;
                
            }
        }
        
        
        //Builds all valid combinations for plays.
        if(myState.getHand().size() > 1)
        {
            for(int i = 0; i < 7; i++){
                System.out.print(handContainer[i]);
            }
            System.out.println();
            System.out.println();
            for(int i = 0; i < ScrabbleBoard.BOARD_HEIGHT-3; i++)
            {
                for(int j = 0; j < ScrabbleBoard.BOARD_WIDTH-3; j++)
                {
                    for(int k = 0; k < handContainer.length; k++)
                    {
                        for(int l = 0; l < handContainer.length; l++)
                        {
                        	//makes sure the same tile isn't being used in two locations.
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
                    //Makes single tile moves
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
        }
        //Discards tiles from hand if there are no available moves
        if(moves.isEmpty())
        {
            Vector<ScrabbleTile> discard = new Vector<ScrabbleTile>();
            for(ScrabbleTile s: myState.getHand()){
                discard.add(s);
            }
            
            if(discard.size()> 5)
            {
                discard.remove(3);
            }
            if(discard.size() > 2)
            {
                discard.remove(0);
            }
            ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, discard);
            moves.add(move);
        }

        //Chooses a random valid move to make.
        GameAction[] ga = new GameAction[0];
        ga = moves.toArray(ga);
        Random rand = new Random();
        game.applyAction(ga[rand.nextInt(ga.length)]);
        return;
    }
}