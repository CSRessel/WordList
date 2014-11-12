package app;

public class Trie
{
	private class Node
	{
		private char value;
		private Node[] nodes = new Node[26];
		private boolean isEnd;
	}
	
	private int size;
	private Node root;
	
	//--------------------------------
	// Accessors
	
	public int getSize()
	{
		return size;
	}
	
	//--------------------------------
	// Methods
	
}
