package scrabble;

import game.*;
import java.util.*;

/**Class representing a Scrabble game state.*/
class ScrabbleGameState extends GameState {
	private Vector<ScrabbleTile> curHand;
	private ScrabbleBoard board;
	private int p0Score;
	private int p1Score;
	private int playerToMove;

	/** Constructor- initializes the instance variables for the GameState

@param initBoard The initial state of the game baord
@param initHand The initial state of the current player's hand
@param initPlayerToMove An integer representing the player who is to move next
@param initP0Score Player 0's Score
@param initP1Score Player 1's Score */
	public ScrabbleGameState (ScrabbleBoard initBoard, Vector<ScrabbleTile> initHand, int initPlayerToMove, int initP0Score, int initP1Score) {
		
	}

}