
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;

/**
 * TextBuddy is a program that can modify and manipulate texts. Users can also
 * save and retrieve texts.
 * 
 * The texts are single line of characters, as shown below: "add <text>" ----
 * add text to the file content "delete <lineNumber>" ---- delete the text at
 * the specified line entered "display" ---- display current content of file
 * "clear" ---- clear the content of the file exit ---- exits program
 * 
 * The text file is saved upon exiting
 * 
 * . Below is an example of how TextBuddy is used: c:> TextBuddy.exe
 * mytextfile.txt(OR c:> java TextBudy mytextfile.txt) Welcome to TextBuddy.
 * mytextfile.txt is ready for use command: add little brown fox added to
 * mytextfile.txt: "little brown fox" command: display 1. little brown fox
 * command: add jumped over the moon added to mytextfile.txt:
 * "jumped over the moon" command: display 1. little brown fox 2. jumped over
 * the moon command: delete 2 deleted from mytextfile.txt:
 * "jumped over the moon" command: display 1. little brown fox command: clear
 * all content deleted from mytextfile.txt command: display mytextfile.txt is
 * empty command: exit
 * 
 * @author Sim Zhi Wei Jonathan A0125258E
 * 
 */

public class TextBuddy2 {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_COMMAND = "command: ";
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEARED = "all content deleted from %1$s";
	private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_EMPTY_DISPLAY = "%1$s is empty";
	private static final String MESSAGE_ERROR = "File not found";
	private static final String MESSAGE_SORTED = "contents sorted alphabetically";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = args[0];
		welcomeMsg(fileName);
		executeCommand(fileName, sc);
	}

	private static void welcomeMsg(String fileName) {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}
	//System.out.println(String.format(MESSAGE_WELCOME, fileName));
	/**
	 * Executes the commands the user give
	 */

	private static void executeCommand(String fileName, Scanner sc) {

		printCommand();
		while (sc.hasNext()) {
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				String userCommand = sc.next();

				if (userCommand.equals("exit")) {
					exitReaders(pw, br);
					break;
				}

				else if (userCommand.equals("sort")) {
					sort(fileName, pw, br);
				}

				else if (userCommand.equals("add")) {
					String text = sc.next();
					add(fileName, pw, text);
					System.out.println(String.format(MESSAGE_ADDED, fileName, text));
				}

				else if (userCommand.equals("display")) {
					display(fileName, br);
				}

				else if (userCommand.equals("delete")) {
					String text = sc.next();
					pw.close();
					delete(fileName, text, br);
				}

				else if (userCommand.equals("clear")) {
					clear(fileName, pw);
					System.out.println(String.format(MESSAGE_CLEARED, fileName));
				}

				else if (userCommand.equals("search")) {
					String text = sc.next();
					search(fileName, br, pw, text);
				}

				exitReaders(pw, br);
				printCommand();
			}

			catch (IOException e) {
				System.out.println(MESSAGE_ERROR);
			}
		}
		sc.close();
	}

	private static void printCommand() {
		System.out.print(MESSAGE_COMMAND);
	}

	private static void exitReaders(PrintWriter pw, BufferedReader br) throws IOException {
		pw.close();
		br.close();
	}

	protected static String add(String fileName, PrintWriter pw, String text) {
		pw.println(text);
		pw.flush();
		return String.format(MESSAGE_ADDED, fileName, text);
	}

	private static String addLineNo(String line, int lineNum) {
		return (lineNum + ". " + line);
	}

	/**
	 * prints the content of the file line by line Each line is printed on a
	 * separate line and assigned a line number. If the file is empty, no lines
	 * are printed.
	 *
	 * @param file
	 *            to be displayed
	 * @throws IOException
	 *             if an input or output exception occurred
	 */
	protected static String display(String fileName, BufferedReader br) throws IOException {

		int lineIndex = 1;
		String line;
		String lineNo;

		while ((line = br.readLine()) != null) {
			lineNo = addLineNo(line, lineIndex++);
			System.out.println(lineNo);
		}

		if (lineIndex == 1) {
			br.close();
			System.out.println(String.format(MESSAGE_EMPTY_DISPLAY, fileName));
			return String.format(MESSAGE_EMPTY_DISPLAY, fileName);
		}

		br.close();
		return null;
	}

	/**
	 * Sorts the file alphabetically.
	 *
	 * @param file
	 *            the File object to be sorted
	 * @return the list containing the sorted lines
	 * @throws IOException
	 *             if an input or output error occurred
	 */
	protected static ArrayList<String> sort(String fileName, PrintWriter pw, BufferedReader br) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		Collections.sort(lines);
		clear(fileName, pw);

		for (String element : lines) {
			add(fileName, pw, element);
		}
		System.out.println(MESSAGE_SORTED);
		return lines;
	}

	/**
	 * Search for all lines containing a keyword in the file corresponding to
	 * the supplied File. Print out the lines that contains the keyword and
	 * returns true
	 *
	 * @param file
	 *            the File object to be searched
	 * @param the
	 *            keyword to search for in the file
	 * @return true if the keyword is found in the a specific line of the list
	 *         and false if the keyword is not found throughout the list
	 * @throws IOException
	 *             if an input or output exception occurred
	 */
	protected static boolean search(String FileName, BufferedReader br, PrintWriter pw, String text)
			throws IOException {
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		boolean check = false;
		for (String element : lines) {
			if (element.contains(text)) {
				System.out.println(element);
				check = true;
			}
		}
		return check;
	}

	/**
	 * Deletes a line from the file
	 *
	 * @param file
	 *            the File object to be deleted
	 * @param delete
	 *            the line number of the line to be deleted
	 * @return the line deleted
	 * @throws IOException
	 *             if an input or output exception occurred
	 */
	protected static String delete(String fileName, String text, BufferedReader br) throws IOException {

		int index = Integer.parseInt(text);
		String stringToDelete = new String();
		PrintWriter temp = new PrintWriter("temp.txt");
		stringToDelete = deleteString(br, index, stringToDelete, temp);
		exitReaders(temp, br);
		replaceFile(fileName);
		System.out.println(String.format(MESSAGE_DELETED, fileName, stringToDelete));
		return String.format(MESSAGE_DELETED, fileName, stringToDelete);

	}

	/**
	 * deletes the string from a temp file that is a copy of the current file
	 */
	private static String deleteString(BufferedReader br, int index, String stringToDelete, PrintWriter temp)
			throws IOException {

		String lineToDelete = null;
		for (int i = 1; (lineToDelete = br.readLine()) != null; i++) {
			if (i == index) {
				stringToDelete = lineToDelete;
			} else {
				temp.println(lineToDelete);
			}
		}
		return stringToDelete;
	}

	/**
	 * replaces current file with the created temp file
	 */
	private static void replaceFile(String fileName) {
		File textFile = new File(fileName);
		File tempFile = new File("temp.txt");
		textFile.delete();
		tempFile.renameTo(textFile);
	}

	/**
	 * Clears all content from the file.
	 *
	 * @param file
	 *            the File object to be cleared
	 * @throws IOException
	 *             if an input or output exception occurred
	 */
	protected static String clear(String fileName, PrintWriter pw) throws IOException {
		pw = new PrintWriter(fileName);
		pw.close();
		return String.format(MESSAGE_CLEARED, fileName);
	}
}
