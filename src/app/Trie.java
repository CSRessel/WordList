/*
 * Clifford Ressel
 * Trie
 * A trie, specifically used as a word list
 */

package app;

import java.util.ListIterator;
import java.util.PriorityQueue;

public class Trie
{
	// a node for the Trie
	private class Node
	{
		private char value;
		private int freq;
		
		private Node[] nodes;
		
		private Node(char v, int f)
		{
			this.value = v;
			this.freq = f;
			this.nodes = new Node[26];
		}
	}
	
	private int size;
	private int nodes;
	private Node root;
	
	//--------------------------------
	// Constructors
	
	/**
	 * Constructs new Trie with Entry objects in given WordList added
	 * @param list a WordList of Entry objects to add
	 */
	public Trie(WordList<Entry> list)
	{
		this.size = 0;
		this.root = new Node(' ', 0);
		
		ListIterator<Entry> it = list.listIterator();
		while (it.hasNext())
		{
			add(it.next());
		}
	}
	
	//--------------------------------
	// Accessors
	
	/**
	 * @return number of words in the Trie
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * @return number of nodes in the Trie
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
			throw new NullPointerException("Cannot insert null object into Trie.");
		if (e.getWord().length() == 0)
			throw new IllegalArgumentException("Cannot insert blank string into Trie.");
		
		Node n = this.root;
		
		String w = e.getWord();
		int f = e.getFreq();
		
		for (char c : w.toCharArray())
		{			
			if (n.nodes[getCode(c)] == null)
			{
				n.nodes[getCode(c)] = new Node(c, 0);
				nodes++;
				n = n.nodes[getCode(c)];
			}
			else
			{
				n = n.nodes[getCode(c)];
			}
		}
		
		n.freq = f;
		
		this.size++;
	}
	
	/**
	 * @param s a String to search for
	 * @return if the Trie contains the given String
	 */
	public boolean contains(String s)
	{
		if (s == null)
			throw new NullPointerException("Cannot search for null String in Trie.");
		if (s.length() == 0)
			throw new IllegalArgumentException("Cannot search for blank String in Trie.");
		
		Node n = this.root;
		
		for (char c : s.toCharArray())
		{
			if (n.nodes[getCode(c)] == null)
				return false;
			else
				n = n.nodes[getCode(c)];
		}
		
		return n.freq > 0;
	}
	
	/**
	 * @param s a prefix
	 * @return the most frequent String with given prefix
	 */
	public String complete(String s)
	{
		Node n = this.root;
		StringBuilder sB = new StringBuilder();
		
		for (char c : s.toCharArray())
		{
			sB.append(c);
			
			if (n.nodes[getCode(c)] == null)
			{
				return "";
			}
			else
			{
				n = n.nodes[getCode(c)];
			}
		}
		
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
	
	// returns an int between 0 and 25 (inclusive) representing the given char
	private int getCode(char c)
	{
		return Character.getNumericValue(c) - Character.getNumericValue('a');
	}
	
	// a recursive method to add all words denoted by sub trees of the given node to the given list
	private void findCompletions(Node node, PriorityQueue<Entry> candidates, String prefix)
	{				
		PriorityQueue<Entry> entries = new PriorityQueue<Entry>();
		
		for (Node n : node.nodes)
		{
			if (n == null)
				continue;
			
			entries.add(new Entry(prefix + n.value, n.freq));
		}
		
		for (Entry e : entries)
		{
			candidates.add(e);
			char c = e.getWord().charAt(e.getWord().length() - 1);
			findCompletions(node.nodes[getCode(c)], candidates, prefix + c);
		}
	}
}