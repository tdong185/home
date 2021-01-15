package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class contains the main method. It inputs the file from which the Collision and CollisionsData objects are created and 
 * asks for user input that specifies the zipcode and date range 
 * 
 * @author tracydong
 * @version 10/08/2017
 */
public class CollisionInfo {

	public static void main(String[] args) {

		//returns error and terminates if no arguments
		if (args.length < 1) {		
			System.err.println("”Error: missing name of the input file");
			System.exit(0);
		}

		try {		
			//input and read file contents into Color objects
			File file =  new File(args[0]);
			Scanner scanner;
			scanner = new Scanner(file);
			String nextLine = scanner.nextLine();

			//create CollisionsData object			
			CollisionsData collisionsAVL = new CollisionsData();

			//read in next line. If entries are invalid, skip and go to next
			while (scanner.hasNextLine()){
				nextLine = scanner.nextLine();
				//returns arraylist holding file information
				ArrayList<String> arraylist = splitCSVLine(nextLine);
				try {
					//create Collision object 
					Collision collision = new Collision(arraylist);							

					//add collision to CollisionsData object 	
					collisionsAVL.add(collision);
				} catch (IllegalArgumentException x){
					continue;
				}
			}
			//continue until told to 'quit'
			do {
				//the following takes in the user input
				System.out.println("Enter a zip code (’quit’ to exit):");
				Scanner userInputZip = new Scanner(System.in);
				String zipCode = userInputZip.next();
				//loop will continue running until user enters 'quit'
				if (zipCode.equalsIgnoreCase("quit")) {
					System.exit(0);
				} else {
					while (!zipValid(zipCode)) {
						System.out.println("Invalid zip code. Try again.");
						userInputZip = new Scanner(System.in);
						zipCode = userInputZip.next();
						if (zipCode.equalsIgnoreCase("quit")) {
							System.exit(0);
						}
					}
				}

				try {
					//ask user for beginning date range
					System.out.println("Enter start date (MM/DD/YYYY):");
					Scanner userInputStart = new Scanner(System.in);
					String startDate = userInputStart.next();
					Date startDateDate = new Date(startDate);

					//ask user for end date range
					System.out.println("Enter end date (MM/DD/YYYY):");
					Scanner userInputEnd = new Scanner(System.in);
					String endDate = userInputEnd.next();
					Date endDateDate = new Date(endDate);

					//makes sure beginning date is before end date
					if(endDateDate.compareTo(startDateDate) < 0) throw new IllegalArgumentException();

					//generate report to print/display
					System.out.println(collisionsAVL.getReport(zipCode, startDateDate, endDateDate));

				}catch (IllegalArgumentException x) {
					System.out.println("Invalid date format. Try again.\n");
				}
			} while (true);
		} catch (FileNotFoundException e) {
			//if file path is incorrect
			System.err.println("Error: file collisions.csv does not exist.");
			System.exit(0);
		} 

	}


	/**
	 * Checks if the unique key is not empty
	 * @param String s. The unique key
	 * @return boolean True/False. true if unique key is not empty. False if empty.
	 */
	public static boolean UniqueIDValid(String s) {
		if(s==null) return false;
		if(s.length()<1) return false;
		return true;
	}

	/**
	 * Checks if the number of persons/pedestrians/cyclists/motorists injured/killed is a non-negative integer 
	 * @param String s. The number of persons/pedestrians/cyclists/motorists injured/killed
	 * @return boolean True or False. True if it is a valid, positive integer. False otherwise
	 */
	public static boolean fatalitiesInjuriesValid(String s) {
		for (int i = 0; i<s.length();i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		try { 
			if (Integer.parseInt(s) < 0) {
				System.err.println("cannot be negativ");
				return false;
			}
		}
		catch (InputMismatchException ex) {
			System.err.println("not an integer");
			return false;
		}
		return true;
	}

	/**
	 * Checks if date is valid
	 * @author modified from Date.java class created by Joanna Klukowska. Modified by Tracy Dong
	 * @param String s. String representation of the date
	 * @return boolean true/false. True if String s is of the correct format to be changed into an instance of the Date clss. False if
	 * String s is of an invalid format. 
	 */
	public static boolean dateValid(String s) {
		int year;
		int month;
		int day;
		if(s.length()!=10) {
			System.err.println("date is not right length");
			return false;
		}
		Scanner tokenizer = new Scanner(s);
		try {
			tokenizer.useDelimiter("/");

			month = tokenizer.nextInt();
			day = tokenizer.nextInt(); 
			year = tokenizer.nextInt() ;
			tokenizer.close();
		}
		catch (InputMismatchException ex ) {
			System.err.println("year, month, day should be numbers");
			return false; 
		}
		catch (NoSuchElementException ex ){
			System.err.println("invalid date format");
			return false; 
		}

		if (month < 1 || month > 12) {
			System.err.println("month < 1 or > 12 detected");
			return false;
		}
		if (day < 1 || day > 31) {
			System.err.println ("day < 1 or > 31 detected");
			return false;
		}
		if (year < 1900 || year > 2020) {
			System.err.println("year < 1900 or > 2020 detected");
			return false;
		}
		return true;


		//		if (s == null) {
		//			return false;		
		//		}
		//		if (s.length() != 10) {
		//			return false;
		//		}
		//		if (s.charAt(2) != '/') {
		//			return false;
		//		}
		//		if (s.charAt(5) != '/') {
		//			return false;
		//		}

		// if month, day, year aren't digits return false


	}

	/**
	 * Checks if zip code input from CSV file is valid. Zip code must be 5 digits long
	 * @param String s (zip code)
	 * @return boolean true/false. True if the zip code is valid. False if the zip code is not valid 
	 */
	public static boolean zipValid(String s) {
		if(s.length() != 5) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (!Character.isDigit(s.charAt(i))) {				
				return false;
			}
		}
		return true;
	}

	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){

		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
					// add it to the current entry 
					nextWord.append( nextChar );
				}else { // skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar); 
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 

		}
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}


}
