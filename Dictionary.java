package scrabble;

import java.util.*;
import java.io.*;

/** Dictionary
 * 
 * Contains  a list of words that are considered legal. Contains the
 * words dictated by Tournament Word List (TWL).
 */
public class Dictionary {

	public static HashSet<String> wordList;

	/** 
	 * Initializes the hashSet wordList to include all of the words
	 * within the TWL. Takes approximately 326 milliseconds.
	 * @throws IOException 
	 */
	public Dictionary() throws IOException 
	{
		// bufferReader for the file containing the TWL reading one word per
		// line and storing it into the hashSet
		BufferedReader in = new BufferedReader(new FileReader("word_list.txt"));
		String word = in.readLine();
		while(word != null)
		{
			wordList.add(word);
			word = in.readLine();
		}
	}

	/** 
	 * checks to see if the word submitted is inside of the wordList.
	 * @param word - A String that is equal to the word to be checked.
	 * @return true of word is contained in the wordList. False otherwise.
	 * public static boolean isValidWord(String word) 
	 */
	public static boolean isValidWord (String word) 
	{
		if(wordList.contains(word)) return true;
		else return false;
	}

}