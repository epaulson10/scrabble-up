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
        int numOfTiles = hand.size();

        ScrabbleTile[] handContainer = new ScrabbleTile[numOfTiles];
        hand.toArray(handContainer);


        Vector<GameMoveAction> moves = new Vector<GameMoveAction>();

        GameAction[] ga = new GameAction[0];

        ScrabbleMoveAction myMove = null;
        for(int i = 0; (i < handContainer.length && handContainer[i] != null); i++)
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

        //      moves = get7s(handContainer);
        //      if(!moves.isEmpty())
        //      {
        //          ga = moves.toArray(ga);
        //          game.applyAction(ga[rand.nextInt(ga.length)]);
        //          return;
        //      }

        //      moves = get6s(handContainer);
        //      if(!moves.isEmpty())
        //      {
        //          ga = moves.toArray(ga);
        //          game.applyAction(ga[rand.nextInt(ga.length)]);
        //          return;
        //      }
        //
//      if(numOfTiles > 4)
//      {
//          moves = get5s(handContainer);
//          if(!moves.isEmpty())
//          {
//              ga = moves.toArray(ga);
//              myMove = (ScrabbleMoveAction) highestScore(ga);
//              if (ga != null)
//              {
//              game.applyAction(highestScore(ga));
//              return;
//              }
//          }
//      }

        if(numOfTiles > 3)
        {
            moves = get4s(handContainer);
            if(!moves.isEmpty())
            {
                ga = moves.toArray(ga);
                myMove = (ScrabbleMoveAction) highestScore(ga);
                if (ga != null)
                {
                game.applyAction(myMove);
                return;
                }
            }
        }
        if(numOfTiles > 2)
        {
            moves = get3s(handContainer);
            if(!moves.isEmpty())
            {
                ga = moves.toArray(ga);
                myMove = (ScrabbleMoveAction) highestScore(ga);
                if (ga != null)
                {
                game.applyAction(myMove);
                return;
                }
            }
        }
        if(numOfTiles > 1)
        {
            moves = get2s(handContainer);
            if(!moves.isEmpty())
            {
                ga = moves.toArray(ga);
                myMove = (ScrabbleMoveAction) highestScore(ga);
                if (ga != null)
                {
                game.applyAction(myMove);
                return;
                }
            }
        }

        moves = get1s(handContainer);
        if(!moves.isEmpty())
        {
            ga = moves.toArray(ga);
            myMove = (ScrabbleMoveAction) highestScore(ga);
            if (ga != null)
            {
            game.applyAction(myMove);
            return;
            }
        }

        Vector<ScrabbleTile> discard = new Vector<ScrabbleTile>();
        for(ScrabbleTile s: myState.getHand()){
            discard.add(s);
        }
        discard.remove(5);
        discard.remove(3);
        ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, discard);


        game.applyAction(move);
    }


    private GameAction highestScore(GameAction[] ga) {
        int highestCurScore = 0;
        GameAction highestScoringMove = null;

        for(GameAction action : ga)
        {
            if(action == null)
                continue;
            int curScore = ScrabbleGameImpl.getMoveScore((ScrabbleMoveAction)action);
            if(curScore > highestCurScore)
            {
                highestCurScore = curScore;
                highestScoringMove = action;
            }
        }
        return highestScoringMove;
    }

    private Vector<GameMoveAction> get1s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    Vector<ScrabbleTile> play = new Vector<ScrabbleTile>();
                    play.add(hand[k]);
                    Vector<Point> place = new Vector<Point>();
                    place.add(new Point(i,j));
                    ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                    if(ScrabbleGameImpl.checkValidMove(place, play))
                    {
                        legalMoves.add(move);
                    }
                }
            }
        }   
        return legalMoves;
    }

    private Vector<GameMoveAction> get2s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }
                        Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(2);
                        play.add(hand[k]);
                        play.add(hand[l]);
                        if(j+1 < ScrabbleBoard.BOARD_HEIGHT)
                        {
                            Vector<Point> place = new Vector<Point>(2);
                            place.add(new Point(i,j));
                            place.add(new Point(i,j+1));
                            ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                            if(ScrabbleGameImpl.checkValidMove(place, play))
                            {
                                legalMoves.add(move);
                            }
                        }
                        if(i+1 < ScrabbleBoard.BOARD_WIDTH)
                        {
                            Vector<Point> place = new Vector<Point>(2);
                            place.add(new Point(i,j));
                            place.add(new Point(i+1,j));
                            ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                            if(ScrabbleGameImpl.checkValidMove(place, play))
                            {
                                legalMoves.add(move);
                            }
                        }
                    }
                }
            }   
        }
        return legalMoves;
    }


    private Vector<GameMoveAction> get3s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }

                        for(int m = 0; m < hand.length; m++)
                        {
                            if(m ==k || m == l)
                            {
                                continue;
                            }

                            Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
                            play.add(hand[k]);
                            play.add(hand[l]);
                            play.add(hand[m]);
                            if(j+2 < ScrabbleBoard.BOARD_HEIGHT)
                            {
                                Vector<Point> place = new Vector<Point>(3);
                                place.add(new Point(i,j));
                                place.add(new Point(i,j+1));
                                place.add(new Point(i,j+2));
                                ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                if(ScrabbleGameImpl.checkValidMove(place, play))
                                {
                                    legalMoves.add(move);
                                }
                            }
                            if(i+2 < ScrabbleBoard.BOARD_WIDTH)
                            {
                                Vector<Point> place = new Vector<Point>(3);
                                place.add(new Point(i,j));
                                place.add(new Point(i+1,j));
                                place.add(new Point(i+2,j));
                                ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                if(ScrabbleGameImpl.checkValidMove(place, play))
                                {
                                    legalMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }   
        }
        return legalMoves;
    }


    private Vector<GameMoveAction> get4s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }

                        for(int m = 0; m < hand.length; m++)
                        {
                            if(m == k || m == l)
                            {
                                continue;
                            }

                            for(int n = 0; n < hand.length; n++)
                            {
                                if(n == k || n == l || n == m)
                                {
                                    continue;
                                }


                                Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
                                play.add(hand[k]);
                                play.add(hand[l]);
                                play.add(hand[m]);
                                play.add(hand[n]);
                                if(j+3 < ScrabbleBoard.BOARD_HEIGHT)
                                {
                                    Vector<Point> place = new Vector<Point>(3);
                                    place.add(new Point(i,j));
                                    place.add(new Point(i,j+1));
                                    place.add(new Point(i,j+2));
                                    place.add(new Point(i,j+3));
                                    ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                    if(ScrabbleGameImpl.checkValidMove(place, play))
                                    {
                                        legalMoves.add(move);
                                    }
                                }
                                if(i+3 < ScrabbleBoard.BOARD_WIDTH)
                                {
                                    Vector<Point> place = new Vector<Point>(3);
                                    place.add(new Point(i,j));
                                    place.add(new Point(i+1,j));
                                    place.add(new Point(i+2,j));
                                    place.add(new Point(i+3,j));
                                    ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                    if(ScrabbleGameImpl.checkValidMove(place, play))
                                    {
                                        legalMoves.add(move);
                                    }
                                }
                            }
                        }
                    }
                }
            }   
        }
        return legalMoves;
    }

    private Vector<GameMoveAction> get5s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }

                        for(int m = 0; m < hand.length; m++)
                        {
                            if(m == k || m == l)
                            {
                                continue;
                            }

                            for(int n = 0; n < hand.length; n++)
                            {
                                if(n == k || n == l || n == m)
                                {
                                    continue;
                                }

                                for(int o = 0; o < hand.length; o++)
                                {
                                    if(o == k || o == l || o == m || o == n)
                                    {
                                        continue;
                                    }

                                    Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
                                    play.add(hand[k]);
                                    play.add(hand[l]);
                                    play.add(hand[m]);
                                    play.add(hand[n]);
                                    play.add(hand[o]);
                                    if(j+4 < ScrabbleBoard.BOARD_HEIGHT)
                                    {
                                        Vector<Point> place = new Vector<Point>(3);
                                        place.add(new Point(i,j));
                                        place.add(new Point(i,j+1));
                                        place.add(new Point(i,j+2));
                                        place.add(new Point(i,j+3));
                                        place.add(new Point(i,j+4));
                                        ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                        if(ScrabbleGameImpl.checkValidMove(place, play))
                                        {
                                            legalMoves.add(move);
                                        }
                                    }
                                    if(i+4 < ScrabbleBoard.BOARD_WIDTH)
                                    {
                                        Vector<Point> place = new Vector<Point>(3);
                                        place.add(new Point(i,j));
                                        place.add(new Point(i+1,j));
                                        place.add(new Point(i+2,j));
                                        place.add(new Point(i+3,j));
                                        place.add(new Point(i+4,j));
                                        ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                        if(ScrabbleGameImpl.checkValidMove(place, play))
                                        {
                                            legalMoves.add(move);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }   
            }
        }
        return legalMoves;
    }


    private Vector<GameMoveAction> get6s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }

                        for(int m = 0; m < hand.length; m++)
                        {
                            if(m == k || m == l)
                            {
                                continue;
                            }

                            for(int n = 0; n < hand.length; n++)
                            {
                                if(n == k || n == l || n == m)
                                {
                                    continue;
                                }

                                for(int o = 0; o < hand.length; o++)
                                {
                                    if(o == k || o == l || o == m || o == n)
                                    {
                                        continue;
                                    }

                                    for(int p = 0; p < hand.length; p++)
                                    {
                                        if(p == k || p == l || p == m 
                                                || p == n || p == o)
                                        {
                                            continue;
                                        }

                                        Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
                                        play.add(hand[k]);
                                        play.add(hand[l]);
                                        play.add(hand[m]);
                                        play.add(hand[n]);
                                        play.add(hand[o]);
                                        play.add(hand[p]);
                                        if(j+5 < ScrabbleBoard.BOARD_HEIGHT)
                                        {
                                            Vector<Point> place = new Vector<Point>(3);
                                            place.add(new Point(i,j));
                                            place.add(new Point(i,j+1));
                                            place.add(new Point(i,j+2));
                                            place.add(new Point(i,j+3));
                                            place.add(new Point(i,j+4));
                                            place.add(new Point(i,j+5));
                                            ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                            if(ScrabbleGameImpl.checkValidMove(place, play))
                                            {
                                                legalMoves.add(move);
                                            }
                                        }
                                        if(i+5 < ScrabbleBoard.BOARD_WIDTH)
                                        {
                                            Vector<Point> place = new Vector<Point>(3);
                                            place.add(new Point(i,j));
                                            place.add(new Point(i+1,j));
                                            place.add(new Point(i+2,j));
                                            place.add(new Point(i+3,j));
                                            place.add(new Point(i+4,j));
                                            place.add(new Point(i+5,j));
                                            ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                            if(ScrabbleGameImpl.checkValidMove(place, play))
                                            {
                                                legalMoves.add(move);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }   
                }
            }
        }
        return legalMoves;
    }

    private Vector<GameMoveAction> get7s(ScrabbleTile[] hand)
    {
        Vector<GameMoveAction> legalMoves = new Vector<GameMoveAction>();

        for(int i = 0; i < ScrabbleBoard.BOARD_WIDTH; i++)
        {
            for(int j = 0; j <ScrabbleBoard.BOARD_HEIGHT; j++)
            {
                for(int k = 0; k < hand.length; k++)
                {
                    for(int l = 0; l < hand.length; l++)
                    {
                        if(l == k)
                        {
                            continue;
                        }

                        for(int m = 0; m < hand.length; m++)
                        {
                            if(m == k || m == l)
                            {
                                continue;
                            }

                            for(int n = 0; n < hand.length; n++)
                            {
                                if(n == k || n == l || n == m)
                                {
                                    continue;
                                }

                                for(int o = 0; o < hand.length; o++)
                                {
                                    if(o == k || o == l || o == m || o == n)
                                    {
                                        continue;
                                    }

                                    for(int p = 0; p < hand.length; p++)
                                    {
                                        if(p == k || p == l || p == m 
                                                || p == n || p == o)
                                        {
                                            continue;
                                        }

                                        for(int q = 0; q < hand.length; q++)
                                        {
                                            if (q == k || q == l || q == m ||
                                                    q == n || q == o ||
                                                    q == p)
                                            {
                                                continue;
                                            }


                                            Vector<ScrabbleTile> play = new Vector<ScrabbleTile>(3);
                                            play.add(hand[k]);
                                            play.add(hand[l]);
                                            play.add(hand[m]);
                                            play.add(hand[n]);
                                            play.add(hand[o]);
                                            play.add(hand[p]);
                                            play.add(hand[q]);
                                            if(j+6 < ScrabbleBoard.BOARD_HEIGHT)
                                            {
                                                Vector<Point> place = new Vector<Point>(3);
                                                place.add(new Point(i,j));
                                                place.add(new Point(i,j+1));
                                                place.add(new Point(i,j+2));
                                                place.add(new Point(i,j+3));
                                                place.add(new Point(i,j+4));
                                                place.add(new Point(i,j+5));
                                                place.add(new Point(i,j+6));
                                                ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                                if(ScrabbleGameImpl.checkValidMove(place, play))
                                                {
                                                    legalMoves.add(move);
                                                }
                                            }
                                            if(i+6 < ScrabbleBoard.BOARD_WIDTH)
                                            {
                                                Vector<Point> place = new Vector<Point>(3);
                                                place.add(new Point(i,j));
                                                place.add(new Point(i+1,j));
                                                place.add(new Point(i+2,j));
                                                place.add(new Point(i+3,j));
                                                place.add(new Point(i+4,j));
                                                place.add(new Point(i+5,j));
                                                place.add(new Point(i+6,j));
                                                ScrabbleMoveAction move = new ScrabbleMoveAction(this, play,place);
                                                if(ScrabbleGameImpl.checkValidMove(place, play))
                                                {
                                                    legalMoves.add(move);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }   
                    }
                }
            }
        }
        return legalMoves;
    }
}