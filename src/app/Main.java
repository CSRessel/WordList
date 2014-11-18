package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
	private static final String usage = "[ tern | trie ] [ find | complete ] string";
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("processing word list...");
		
		WordList<Entry> words = new WordList<Entry>();
		BufferedReader br = new BufferedReader(new FileReader("en.txt"));
		String line;
		while ((line = br.readLine()) != null)
		{
			String word = line.substring(0, line.indexOf(' '));
			int freq = Integer.valueOf(line.substring(line.indexOf(' ') + 1));
			
			words.add(new Entry(word, freq));
		}
		br.close();
		
		System.out.println("> file read");
		
//		WordList<Entry> words = new WordList<Entry>();
//		words.add(new Entry("first", 10000));
//		words.add(new Entry("fire", 1000000));
		
		Trie trie = new Trie(words);
		
		System.out.println("> trie created");
		
		Tern tern = new Tern(words);
		
		System.out.println("> tern created");
		
		String input;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter a command; type \"help\" for usage");

		do {
			System.out.print("> ");
			input = br.readLine();
			
			if (!input.matches("[a-z]+ [a-z]+ .+") && !input.equals("help") && !input.equals("quit"))
			{
				System.out.println("invalid input");
				continue;
			}
			else if (input.equals("help"))
			{
				System.out.print(usage);
			}
			else if (input.equals("quit"))
			{
				break;
			}
			
			String[] comms = input.split(" ");
			
			if (comms[0].equals("tern"))
			{
				if (comms[1].equals("find"))
				{
					System.out.println(tern.contains(comms[2]));
				}
				else if (comms[1].equals("complete"))
				{
					System.out.println(tern.complete(comms[2]));
				}
			}
			else if (comms[0].equals("trie"))
			{
				if (comms[1].equals("find"))
				{
					System.out.println(trie.contains(comms[2]));
				}
				else if (comms[1].equals("complete"))
				{
					System.out.println(trie.complete(comms[2]));
				}
			}
			else
			{
				System.out.println("invalid input");
			}
		} while (true);
		
		br.close();
	}
}
