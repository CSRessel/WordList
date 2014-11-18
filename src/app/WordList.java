/*
 * WordList
 * A LinkedList of Entry objects
 */

package app;

import java.util.LinkedList;

// Parametized to explicitly require a LinkedList of type Entry
public class WordList<T extends Entry> extends LinkedList<Entry>
{
	// Only allow an Entry for a word composed of lowercase english letters to be added
	public boolean add(Entry e)
	{
		if (e.getWord().matches("[a-z]+"))
		{
			super.add(e);
			return true;
		}
		else
		{
			return false;
		}
	}
}