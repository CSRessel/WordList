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
		this.size = 0;
		//this.root = new Node();
		
		ListIterator<Entry> it = list.listIterator();
		while (it.hasNext())
		{
			add(it.next());
		}
	}
	
	//--------------------------------
	// Accessors
	
	public int getSize()
	{
		return this.size;
	}

	//--------------------------------
	// Public Methods
	
	/**
	 * @param e an Entry to add
	 */
	public void add(Entry e)
	{
		if (e == null)
			throw new NullPointerException("Cannot insert null object into Tern.");
		if (e.getWord().length() == 0)
			throw new IllegalArgumentException("Cannot insert blank string into Tern.");
		
		Node n = insert(e, this.root, 0);
		
		n.freq = e.getFreq();
		
		this.size++;
	}
	
	/**
	 * @param s a String to search for
	 * @return if the Tern contains the given String
	 */
	public boolean contains(String s)
	{
		if (s == null)
			throw new NullPointerException("Cannot search for null String in Tern.");
		if (s.length() == 0)
			throw new IllegalArgumentException("Cannot search for blank String in Tern.");
		
		Node n = get(s, root, 0);
		
		if (n == null || n.freq == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * @param s a prefix
	 * @return the most frequent String with given prefix
	 */
	public String complete(String s)
	{
		Node n = get(s, root, 0);
		
		return "";
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
	
	private Node insert(Entry e, Node n, int index)
	{
		char c = e.getWord().charAt(index);
		Node tmp;
		
		if (n == null)
		{
			n = new Node();
			n.value = c;
		}
		
		if (c < n.value)
		{
			tmp = insert(e, n.loKid, index);
		}
		else if (c > n.value)
		{
			tmp = insert(e, n.hiKid, index);
		}
		else
		{
			if (e.getWord().length() > index + 1)
			{
				tmp = insert(e, n.eqKid, index + 1);
			}
			else
			{
				n.freq = e.getFreq();
				tmp = n;
			}
		}
		
		return tmp;
	}
	
	private Node get(String s, Node n, int index)
	{
		char c = s.charAt(index);
		
		if (n == null)
		{
			return null;
		}
		else if (c < n.value)
		{
			return get(s, n.loKid, index);
		}
		else if (c > n.value)
		{
			return get(s, n.hiKid, index);
		}
		else
		{
			if (s.length() > index + 1)
			{
				return get(s, n.eqKid, index + 1);
			}
			else
			{
				return n;
			}
		}
	}
}
