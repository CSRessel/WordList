/*
 * Clifford Ressel
 * Entry
 * A wrapper class to store an entry in a list of words
 * Contains two values; the word and its frequency
 */

package app;

public class Entry implements Comparable<Entry>
{
	//--------------------------------
	// Fields
	
	private String word;
	private int freq;
	
	//--------------------------------
	// Constructors
	
	public Entry(String w, int f)
	{
		this.word = w;
		this.freq = f;
	}
	
	//--------------------------------
	// Accessors
	
	public String getWord()
	{
		return this.word;
	}
	
	public int getFreq()
	{
		return this.freq;
	}
	
	//--------------------------------
	// Mutators
	
	public void setWord(String s)
	{
		this.word = s;
	}
	
	public void setFreq(int i)
	{
		this.freq = i;
	}
	
	//--------------------------------
	// Interface Methods
	
	public int compareTo(Entry e)
	{
		// we want a higher frequency to have priority in the PriorityQueue used in the Tern and Trie
		if (this.freq > e.freq)
			return -1;
		else if (this.freq < e.freq)
			return 1;
		else
			return 0;
	}
}