package project5;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents a collision and its details as provided. 
 * 
 * @author tracydong
 * @version 12/08/2017
 */
public class Collision implements Comparable<Collision> {

	/**
	 * Create a Collision object
	 * @param entries
	 * @throws IllegalArgumentException
	 */
	
	public Collision (ArrayList<String> entries) throws IllegalArgumentException {
		//check if size of array contains unique key
		if (entries.size() < 24) {
			throw new IllegalArgumentException();
		}

		//instantiate data fields
		this.zip = entries.get(3);
		this.date = new Date(entries.get(0));
		this.key = entries.get(23);
		this.personsInjured = Integer.parseInt(entries.get(10));
		this.personsKilled = Integer.parseInt(entries.get(11));
		this.pedestriansInjured = Integer.parseInt(entries.get(12));
		this.pedestriansKilled = Integer.parseInt(entries.get(13));
		this.cyclistsInjured = Integer.parseInt(entries.get(14));
		this.cyclistsKilled = Integer.parseInt(entries.get(15));
		this.motoristsInjured = Integer.parseInt(entries.get(16));
		this.motoristsKilled = Integer.parseInt(entries.get(17));

		//check if data fields are valid. If not, throw an excpetion that is caught in the main method
		if (entries.size()>24) { 
			if(zipValid(entries.get(3))) {
				if(dateValid(entries.get(0))) {
					if(UniqueIDValid(entries.get(23))) {
						if(fatalitiesInjuriesValid(entries.get(10)) && 
								fatalitiesInjuriesValid(entries.get(12)) &&
								fatalitiesInjuriesValid(entries.get(14)) &&
								fatalitiesInjuriesValid(entries.get(16)) &&
								fatalitiesInjuriesValid(entries.get(11)) &&
								fatalitiesInjuriesValid(entries.get(13)) &&
								fatalitiesInjuriesValid(entries.get(15)) &&
								fatalitiesInjuriesValid(entries.get(17))) {

						}
					}
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	//private data fields
	private String zip;
	private Date date;
	private String key;
	private int personsInjured;
	private int pedestriansInjured;
	private int cyclistsInjured;
	private int motoristsInjured;
	private int personsKilled;
	private int pedestriansKilled;
	private int cyclistsKilled;
	private int motoristsKilled;

	/**
	 * Returns the zipcode of the collision
	 * @return the zipcode
	 */
	public String getZip() {
		return this.zip;		
	}
	/**
	 * Returns the date of the collision
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}
	/**
	 * Returns the unique key
	 * @return the unique key
	 */
	public String getKey() {
		return this.key;
	}
	/**
	 * Returns the number of people injured
	 * @return the number of people injured
	 */
	public int getPersonsInjured() {
		return this.personsInjured;
	}
	/**
	 * Returns the number of pedestrians injured
	 * @return the number of pedestrians injured
	 */
	public int getPedestriansInjured() {
		return this.pedestriansInjured;
	}
	/**
	 * Returns the number of cyclists injured
	 * @return the number of cyclists injured
	 */
	public int getCyclistsInjured() {
		return this.cyclistsInjured;
	}
	/**
	 * Returns the number of motorists injured
	 * @return the number of motorists injured
	 */
	public int getMotoristsInjured() {
		return this.motoristsInjured;
	}
	/**
	 * Returns the number of people killed
	 * @return the number of people killed
	 */
	public int getPersonsKilled() {
		return this.personsKilled;
	}
	/**
	 * Returns the number of pedestrians killed 
	 * @return the number of pedestrians killed
	 */
	public int getPedestriansKilled() {
		return this.pedestriansKilled;
	}
	/**
	 * Returns the number of cyclists killed
	 * @return the number of cyclists killed
	 */
	public int getCyclistsKilled() {
		return this.cyclistsKilled;
	}
	/**
	 * Returns the number of motorists killed
	 * @return the number of motorists killed
	 */
	public int getMotoristsKilled() {
		return this.motoristsKilled;
	}


	/**
	 * Checks if the unique key is not empty
	 * @param String s. The unique key
	 * @return boolean True/False. true if unique key is not empty. False if empty.
	 */
	public boolean UniqueIDValid(String s) {
		if(s==null) return false;
		if(s.length()<1) return false;
		return true;
	}

	/**
	 * Checks if the number of persons/pedestrians/cyclists/motorists injured/killed is a non-negative integer 
	 * @param String s. The number of persons/pedestrians/cyclists/motorists injured/killed
	 * @return boolean True or False. True if it is a valid, positive integer. False otherwise
	 */
	public boolean fatalitiesInjuriesValid(String s) {
		for (int i = 0; i<s.length();i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}


		if (Integer.parseInt(s) < 0) {
			System.err.println("cannot be negativ");
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
	public boolean dateValid(String s) {
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
	public boolean zipValid(String s) {
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
	 * Overriden method that compares the zipcode, then date, then unique key of two Collision objects
	 * 
	 *  @param Collision object o that is used for comparison
	 *  
	 *  @return negative if less than, 0 if equal, positive if greater than
	 */
	@Override
	public int compareTo(Collision o) {
		if(Integer.parseInt(this.getZip()) < Integer.parseInt(o.getZip())) {
			return -1;
		} else if (Integer.parseInt(this.getZip()) > Integer.parseInt(o.getZip())){
			return 1;
		} else {
			if (!this.getDate().equals(o.getDate())){
				return this.getDate().compareTo(o.getDate());
			} else {
				if(Integer.parseInt(this.getKey()) < Integer.parseInt(o.getKey())) {
					return -1;
				} else if(Integer.parseInt(this.getKey()) > Integer.parseInt(o.getKey())) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * Overriden method that tests if the zipcode, date, and unique key of two Collision objects are equal
	 * 
	 * @param Object o that should be of type Collision. o is used for comparison
	 * 
	 * @return true if the zipcode, date, and unique are equal, false if they are not equal
	 */
	@Override 
	public boolean equals(Object o) {
		boolean equal=false;
		if ((o instanceof Collision)) {
			if (this.getZip().equalsIgnoreCase(((Collision)o).getZip())) {
				if (this.getDate().equals(((Collision)o).getDate())) {
					if (this.getKey().equalsIgnoreCase(((Collision)o).getKey())) {
						equal = true;
					}
				}

			}
		}
		return equal;
	}

}

