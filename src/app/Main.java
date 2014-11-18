package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
	private static final String usage = "[ tern | trie ] [ contains | complete ] string";
	private static final long MEGABYTE = 1024L * 1024L;
	
	public static long bytesToMegabytes(long bytes)
	{
		return bytes / MEGABYTE;
	}
	
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
		
//		BufferedReader br = new BufferedReader(new FileReader("en.txt"));
//		WordList<Entry> words = new WordList<Entry>();
//		words.add(new Entry("first", 10000));
//		words.add(new Entry("fire", 1000000));
		
		System.out.println("> file read");
		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		
		long preTrie = runtime.totalMemory() - runtime.freeMemory();	
		Trie trie = new Trie(words);
		long postTrie = runtime.totalMemory() - runtime.freeMemory();
		
		System.out.println("> trie created");
		
		long preTern = runtime.totalMemory() - runtime.freeMemory();
		Tern tern = new Tern(words);
		long postTern = runtime.totalMemory() - runtime.freeMemory();
		
		System.out.println("> tern created");
		
		System.out.println();
		System.out.println("trie: " + bytesToMegabytes(postTrie - preTrie) + " MB");
		System.out.println("tern: " + bytesToMegabytes(postTern - preTern) + " MB");
		System.out.println();
		System.out.println();
		
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
				System.out.println(usage);
				continue;
			}
			else if (input.equals("quit"))
			{
				break;
			}
			
			String[] comms = input.split(" ");
			
			if (comms[0].equals("tern"))
			{
				if (comms[1].equals("contains"))
				{
					long startTime = System.nanoTime();
					boolean result = tern.contains(comms[2]);
					long endTime = System.nanoTime();

					System.out.println(result);
					System.out.println((endTime - startTime)/1e6 + " ms");
				}
				else if (comms[1].equals("complete"))
				{
					long startTime = System.nanoTime();
					String result = tern.complete(comms[2]);
					long endTime = System.nanoTime();
										
					if (result.equals(""))
					{
						System.out.println("no results found");
					}
					else
					{
						System.out.println(tern.complete(comms[2]));
					}
					
					System.out.println((endTime - startTime)/1e6 + " ms");
				}
			}
			else if (comms[0].equals("trie"))
			{
				if (comms[1].equals("contains"))
				{
					long startTime = System.nanoTime();
					boolean result = trie.contains(comms[2]);
					long endTime = System.nanoTime();
					
					System.out.println(result);
					System.out.println((endTime - startTime)/1e6 + " ms");
				}
				else if (comms[1].equals("complete"))
				{
					long startTime = System.nanoTime();
					String result = trie.complete(comms[2]);
					long endTime = System.nanoTime();
					
					if (result.equals(""))
					{
						System.out.println("no results found");
					}
					else
					{
						System.out.println(tern.complete(comms[2]));
					}
					
					System.out.println((endTime - startTime)/1e6 + " ms");
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