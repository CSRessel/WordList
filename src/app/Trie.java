/*
 * Trie
 * A trie, specifically used as a word list
 */

package app;

import java.util.ListIterator;
import java.util.PriorityQueue;

public class Trie
{
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
	private Node root;
	
	//--------------------------------
	// Constructors
	
	public Trie(WordList<Entry> list)
	{
		size = 0;
		root = new Node(' ', 0);
		
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
		Node n = root;
		
		String w = e.getWord();
		int f = e.getFreq();
		
		for (char c : w.toCharArray())
		{			
			if (n.nodes[getCode(c)] == null)
			{
				n.nodes[getCode(c)] = new Node(c, 0);
				n = n.nodes[getCode(c)];
			}
			else
			{
				n = n.nodes[getCode(c)];
			}
		}
		
		n.freq = f;
	}
	
	/**
	 * @param s a prefix
	 * @return the most frequent String with given prefix
	 */
	public String getCompletion(String s)
	{
		Node n = root;
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
		findCompletions(n, candidates, 0);
		
		sB.append(candidates.peek().getWord());
		
		return sB.toString();
	}
	
	/**
	 * @param s a String to search for
	 * @return if the Trie contains the given String
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
	
	private PriorityQueue<Entry> getMaxFourChildren(Node node)
	{
		PriorityQueue<Entry> list = new PriorityQueue<Entry>();
		
		for (Node n : node.nodes)
		{
			list.add(new Entry(String.valueOf(node.value) + String.valueOf(n.value), n.freq));
		}
		
		PriorityQueue<Entry> finalList = new PriorityQueue<Entry>();
		
		for (int i = 0; i < 4; i++)
		{
			finalList.add(list.remove());
		}
		
		return finalList;
	}
	
	// the number of levels to recurse to when looking for the completion
	// with 6 levels, we recurse 4^6 (4096) times, which is very comfortable
	private static final int AUTOCOMPLETE_DEPTH = 6;
	
	private void findCompletions(Node n, PriorityQueue<Entry> candidates, int depth)
	{
		if (depth >= AUTOCOMPLETE_DEPTH)
			return;
		
		PriorityQueue<Entry> entries = getMaxFourChildren(n);
		
		for (Entry e : entries)
		{
			candidates.add(e);
			findCompletions(n.nodes[Character.getNumericValue(e.getWord().charAt(e.getWord().length() - 1))], candidates, depth++);
		}
	}
}
