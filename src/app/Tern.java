/*
 * Tern
 * A ternary search tree, specifically used as a word list
 */

package app;

import java.util.ListIterator;

public class Tern
{
	private class Node
	{
		private char value;
		private int freq;
		
		private Node loKid, hiKid, eqKid;
	}
	
	private int size;
	private Node root;
	
	//--------------------------------
	// Constructors
	
	public Tern(WordList<Entry> list)
	{
		size = 0;
		root = new Node();
		
		ListIterator<Entry> it = list.listIterator();
		while (it.hasNext())
		{
			this.add(it.next());
		}
	}
	
	//--------------------------------
	// Accessors
	
	public int getSize()
	{
		return size;
	}

	//--------------------------------
	// Public Methods
	
	/**
	 * @param e an Entry to add
	 */
	public void add(Entry e)
	{
		
	}
	
	/**
	 * @param s a prefix
	 * @return the most frequent String with given prefix
	 */
	public String getCompletion(String s)
	{
		return "";
	}
	
	/**
	 * @param s a String to search for
	 * @return if the Tern contains the given String
	 */
	public boolean contains(String s)
	{
		return true;
	}
	
	/**
	 * @param s a String not present in the Tern
	 * @return the closest, most frequent (in that order) String in the Tern
	 */
	public String getClosest(String s)
	{
		return "";
	}
	
	//--------------------------------
	// Private Helper Methods
	
	private int getCode(char c)
	{
		return Character.getNumericValue(c) - Character.getNumericValue('a');
	}
}
