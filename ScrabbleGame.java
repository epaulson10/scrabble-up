package scrabble;

import game.*;

/**Class representing the Scrabble game.*/

public interface ScrabbleGame extends Game
{
    public void drawInitialHand(ScrabblePlayer sp);
}