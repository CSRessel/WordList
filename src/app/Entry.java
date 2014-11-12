/*
 * Entry
 * A class to store an entry for a frequency based list of words
 * Contains two values; the words and the frequency
 */

package app;

public class Entry
{
	//--------------------------------
	// Fields
	
	private String value;
	private int freq;
	
	//--------------------------------
	// Constructors
	
	public Entry(String v, int f)
	{
		this.value = v;
		this.freq = f;
	}
	
	//--------------------------------
	// Accessors
	
	public String getValue()
	{
		return value;
	}
	
	public int getFreq()
	{
		return freq;
	}
}
