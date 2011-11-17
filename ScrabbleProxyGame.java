package scrabble;

import game.*;

/**ScrabbleProxyGame*/
public class ScrabbleProxyGame extends ProxyGame implements ScrabbleGame {
	public static final int PORT_NUM = 60035;

	/** Initializes all of the variables.
@param hostname A String containing the hostname of the game. */
	public ScrabbleProxyGame (String hostname) {
		super(hostname);
	}

	/** Decodes the GameState string
@param str A string that represents the gamestate
@return returns the gamestate */
	protected GameState decodeState (String str) {
		return null;
	}

	/** Encodes the action
@param ga the game action to encode
@return returns a string representing the game action */
	protected String encodeAction (GameAction ga) {
		return null;
	}

	/** Gets the port number
@return returns the port number */
	protected int getAdmPortNum () {
		return 0;
	}

	/** Gets the max players
@return returns the max players */
	public int maxPlayersAllowed () {
		return 0;
	}

	/** Gest the minimum players
@return meturns the minimum players */
	public int minPlayersAllowed () {
		return 0;
	}

	/** Gets if you can have a blank player
@return true if you can play with empty seats false otherwise. */
	public boolean nullPlayersAllowed () {
		return false;
	}

}