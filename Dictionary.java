package scrabble;

import java.util.*;
import java.io.*;

/** Dictionary
 * 
 * Contains  a list of words that are considered legal.
 * The file "word-list.txt" contains the Tournament Word List (TWL) 
 * with one word per line. Provides a method to check if a certain
 * word is within the dictionary or not.
 */
public class Dictionary 
{

	public static HashSet<String> wordList;

	/** 
	 * Initializes the hashSet wordList to include all of the words
	 * within the TWL.
	 * @throws IOException 
	 */
	public Dictionary() throws IOException 
	{
		// read one word per line and store it into the hashSet
		BufferedReader in = new BufferedReader(new FileReader("word_list.txt"));
		String word = in.readLine();
		while(word != null)
		{
			wordList.add(word);
			word = in.readLine();
		}
	}

	/** 
	 * checks to see if the word submitted is in the TWL
	 * @param word - A String that is equal to the word to be checked.
	 * @return true of word is contained in the wordList. False otherwise.
	 */
	public static boolean isValidWord (String word) 
	{
		if(wordList.contains(word)) return true;
		else return false;
	}

}