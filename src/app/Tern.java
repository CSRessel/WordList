/*
 * Clifford Ressel
 * Tern
 * A ternary search tree, specifically used as a word list
 */

package app;

import java.util.ListIterator;
import java.util.PriorityQueue;

public class Tern
{
	// a node for the Tern
	private class Node
	{
		private char value;
		private int freq;
		
		private Node loKid, hiKid, eqKid;
	}
	
	private int size;
	private int nodes;
	private Node root;
	
	//--------------------------------
	// Constructors
	
	/**
	 * Constructs new Tern with Entry objects in given WordList added
	 * @param list a WordList of Entry objects to add
	 */
	public Tern(WordList<Entry> list)
	{
		this.size = 0;
		this.root = null;
		//this.root = new Node();
		
		ListIterator<Entry> it = list.listIterator();
		for (Entry e : list)
		{
			add(e);
		}
	}
	
	//--------------------------------
	// Accessors
	
	/**
	 * @return number of words in the Tern
	 */
	public int getSize()
	{
		return this.size;
	}
	
	/**
	 * @return number of nodes in the Tern
	 */
	public int getNodes()
	{
		return this.nodes;
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
		
		if (!contains(e.getWord()))
		{
			this.root = insert(e.getWord(), this.root, e.getFreq(), 0);
			this.size++;
		}
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
		
		Node n = get(s, this.root, 0);
		
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
		Node n = get(s, this.root, 0).eqKid;

		StringBuilder sB = new StringBuilder();
		sB.append(s);
		
		PriorityQueue<Entry> candidates = new PriorityQueue<Entry>();
		findCompletions(n, candidates, "");
		
		sB.append(candidates.peek().getWord());
		
		return sB.toString();
	}
	
// To be implemented:
//	/**
//	 * @param s a String not present in the Tern
//	 * @return the closest, most frequent (in that order) String in the Tern
//	 */
//	public String getClosest(String s)
//	{
//		return "";
//	}
	
	//--------------------------------
	// Private Helper Methods
	
	// a recursive method to insert a String into the Tern
	private Node insert(String s, Node n, int freq, int index)
	{
		char c = s.charAt(index);
		
		if (n == null)
		{
			n = new Node();
			n.value = c;
			this.nodes++;
		}
		
		if (c < n.value)
		{
			n.loKid = insert(s, n.loKid, freq, index);
		}
		else if (c > n.value)
		{
			n.hiKid = insert(s, n.hiKid, freq, index);
		}
		else
		{
			if (s.length() > index + 1)
			{
				n.eqKid = insert(s, n.eqKid, freq, index + 1);
			}
			else
			{
				n.freq = freq;
			}
		}
		
		return n;
	}
	
	// finds the Node corresponding to the given String
	private Node get(String s, Node n, int index)
	{
		char c = s.charAt(index);
		
		if (n == null)
		{
			return null;
		}
		
		if (c < n.value)
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
	
	// a recursive method to add all words denoted by sub trees of the given node to the given list
	private void findCompletions(Node n, PriorityQueue<Entry> candidates, String prefix)
	{
		if (n == null)
			return;
		
		candidates.add(new Entry(prefix + n.value, n.freq));
		
		findCompletions(n.loKid, candidates, prefix);
		findCompletions(n.eqKid, candidates, prefix + n.value);
		findCompletions(n.hiKid, candidates, prefix);
	}
}