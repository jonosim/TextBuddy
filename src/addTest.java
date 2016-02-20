import static org.junit.Assert.*;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class addTest {
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEARED = "all content deleted from %1$s";
	private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";

	@Test
	public void testAdd() throws IOException {
		
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
		String input = "text";
		String expected = String.format(MESSAGE_ADDED, fileName, input);
		String actual = TextBuddy2.add(fileName, pw, input);
		
		assertEquals(expected,actual);
		TextBuddy2.clear(fileName, pw);
	}
	
	/**
	 * add a two lines of text and sort it alphabetically. 
	 * the list is then sorted alphabetically and checked if it is sorted correctly
	 */
	@Test
	public void testAdd_Sort_Display() throws IOException {
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String input1 = "bcda";
		String input2 = "abcd";

		TextBuddy2.add(fileName, pw, input1);
		TextBuddy2.add(fileName, pw, input2);
		ArrayList<String> actual = TextBuddy2.sort(fileName,pw,br);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("abcd");
		expected.add("bcda");
		assertEquals(expected, actual);
		
		
		TextBuddy2.clear(fileName, pw);	
	}
	
	/**
	 * add a text and search for a keyword that is in the added text
	 */
	@Test
	public void testAdd_Search() throws IOException {
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String input = "text";
		
		TextBuddy2.add(fileName, pw, input);		
		assertEquals(true ,(TextBuddy2.search(fileName,br,pw,"ext")));
		
		TextBuddy2.clear(fileName, pw);
	}

	@Test
	public void testClear() throws IOException{
	
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
		String expected = String.format(MESSAGE_CLEARED, fileName);
		String actual =TextBuddy2.clear(fileName, pw);
		
		assertEquals(expected ,actual);	
	}
	
	@Test
	public void testDisplay() throws FileNotFoundException,IOException{
		
		String fileName = "mytextbuddy.txt";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));	
		String input = "text";
		
		TextBuddy2.add(fileName, pw, input);
		String displayMessage = TextBuddy2.display(fileName,br);
		assertNull(displayMessage);
		TextBuddy2.clear(fileName, pw);
	}
	
	/**
	 * checks if the list is sorted alphabetically
	 */
	@Test
	public void testSort() throws IOException {
		
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		TextBuddy2.clear(fileName,pw);
		TextBuddy2.add(fileName,pw, "c");
		TextBuddy2.add(fileName,pw, "b");
		TextBuddy2.add(fileName,pw, "a");
		ArrayList<String> actual = TextBuddy2.sort(fileName,pw,br);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("a");
		expected.add("b");
		expected.add("c");
		assertEquals(expected, actual);
	
		TextBuddy2.clear(fileName, pw);
	}
	
	@Test
	public void testSearch() throws IOException {
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String text = "text";
		
		TextBuddy2.add(fileName,pw, text);
		assertEquals(true,(TextBuddy2.search(fileName,br,pw,"ext")));
		TextBuddy2.clear(fileName, pw);
		
	}
	@Test
	public void testDelete() throws IOException {
		String fileName = "mytextbuddy.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));	
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		String input = "text";
		TextBuddy2.add(fileName, pw, input);
		
		String expected = String.format(MESSAGE_DELETED, fileName,input);
		String actual = TextBuddy2.delete(fileName,"1" ,br);
		
		assertEquals(expected,actual);
		TextBuddy2.clear(fileName, pw);
	
	}
}
