package scrabble;

import game.*;

/** * Main driver class that sets up a game of Scrabble
 * 
 * (Include description of Scrabble)
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
class ScrabbleDriver implements GameDriver {
	private String[] names = {"human", "AI player (easy)", "AI player (hard)"};

	/** 	 */
	public static void main (String[] args) {
		
	}

	/**  * Creates a new game of Scrabble
 *
 * @param numPlayers the number of players (will be ignored, game always has two players. */
	public Game createGame (int numPlayers) {
		return null;
	}

	/**  * Creates a new player.
 *
 * @param name A String representing the requested type of player
 * @return a new GamePlayer of the requested type. */
	public GamePlayer createLocalPlayer (String name) {
		return null;
	}

	/**  * Creates a new proxy game.
 *
 * @param hostName the IP address of the hsot machine
 * @return a new ProxyGame */
	public ProxyGame createRemoteGame (String hostName) {
		return null;
	}

	public ProxyPlayer createRemotePlayer () {
		return null;
	}

	/**  * Returns possible player type choices.
 */
	public String[] localPlayerChoices () {
		return null;
	}

}

