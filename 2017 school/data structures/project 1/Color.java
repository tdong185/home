package project1;

/**
 * This class contains represents a color and its hexvalue, RGB code, and name if provided
 * 
 * @author tracydong
 * @version 09/19/2017
 */

public class Color implements Comparable<Color>{
	/**
	 * constructor that creates a Color object given a specific hexValue 
	 * 
	 * @param colorHexValue
	 */
	public Color (String colorHexValue) {
		hexValue = colorHexValue.toUpperCase();
		//test if hexValue is valid
		this.testHexValid();
		//creates RGB from hexValue
		red = this.hexToRGB(hexValue.substring(1,3));		
		green = this.hexToRGB(hexValue.substring(3,5));	
		blue = this.hexToRGB(hexValue.substring(5,7));	

	}
	
	/**
	 * constructor that creates a Color object given a specific hexValue and colorName
	 * 
	 * @param colorHexValue
	 * @param colorName
	 */
	public Color (String colorHexValue, String colorName) {
		hexValue = colorHexValue.toUpperCase();
		//test if hexValue is valid
		this.testHexValid();
		name = colorName;
		//creates RGB from hexValue
		red = this.hexToRGB(hexValue.substring(1,3));		
		green = this.hexToRGB(hexValue.substring(3,5));	
		blue = this.hexToRGB(hexValue.substring(5,7));	

	}
	
	/**
	 * constructor that creates a Color object given a specific set of RGB
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public Color (int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		//tests if RGB is valid
		this.testRGBValid(red);
		this.testRGBValid(green);
		this.testRGBValid(blue);
		hexValue = this.rgbToHex();	
	}
	
	//data fields and acessor methods
	private int red;
	private int green;
	private int blue;

	int getRed() {
		return red; 
	}
	int getGreen() {
		return green; 
	}
	int getBlue() {
		return blue; 
	}

	private String name = null;
	private String hexValue;

	String getName(){
		return name;
	}
	String getHexValue(){
		return hexValue;
	}

	/**
	 * Tests if the RGB value received is valid.
	 * 
	 * @param rgbValue RGB value to be tested.
	 * 
	 * @return true if rgbValue is a valid one 
	 * (one between 0 and 225, inclusive), false, otherwise.
	 */
	private boolean testRGBValid(int rgbValue) {
		boolean rgbValid = true;
		//invalid RGB values
		if ((rgbValue>255)||(rgbValue<0)) {
			rgbValid = false;
			throw new IllegalArgumentException("RGB value must be between 0 and 225, inclusive");
		}
		return rgbValid;
	}

	/**
	 * Overriden method that compares the hexValue of two Color objects
	 * 
	 *  @param Color object o that is used for comparison
	 *  
	 *  @return negative if less than, 0 if equal, positive if greater than
	 */
	@Override
	public int compareTo(Color o) {
		return this.getHexValue().compareToIgnoreCase(o.getHexValue());
	}

	/**
	 * Overriden method that tests if the hexValue of two Color objects are equal
	 * 
	 * @param Object col that is used for comparison
	 * 
	 * @return true if the two hexValues are equal, false if they are not equal
	 */
	@Override
	public boolean equals(Object col){
		boolean equal=false;
		if ((col instanceof Color)) {
			if (this.getHexValue().equalsIgnoreCase(((Color) col).getHexValue())){
				equal = true;
			}
		}	
		return equal;
	}

	/**
	 * Overriden method that displays the hexValue, RGB code, and if available the name
	 * 
	 * @return String that contains the hexValue, RGB code, and if available the name
	 */
	@Override
	public String toString(){
		String finalString = null;
		String HexValueToDisplay = this.getHexValue().toUpperCase();
		//create String of RGB colors 
		String red = Integer.toString(this.getRed());
		String green = Integer.toString(this.getGreen());
		String blue = Integer.toString(this.getBlue());
		String Name = this.getName();

		//String to represents hexValue, RGB, and colorName if exists in correct format
		finalString = HexValueToDisplay + ", (" +  String.format("%3s",(red)) +"," + 
				String.format("%3s",(green)) + ","+String.format("%3s",(blue)) +")";

		if (!(Name==null)){
			finalString = finalString + ", "+ Name;
		}

		return finalString;
	}

	/**
	 * tests if hexValue is valid. Must start with '#', be of length 7, and contain characters A-F,0-9
	 * 
	 * @return true if hexValue is valid, false if not valid
	 */
	private boolean testHexValid() {
		//possible character values in hexValue
		String alphnumer = "ABCDEF1234567890";
		//make hex characters all uppercase
		String hex = this.hexValue.toUpperCase();
		boolean valid = false;

		while (valid == false) {	
			//test if first character is '#'
			if (hex.charAt(0) == '#') {
				//test if length is correct
				if (hex.length()==7) {
					//test if each character in hex is valid
					int counter=0;
					for (int i=1; i<hex.length(); i++) {
						if ((alphnumer.indexOf(hex.charAt(i)))>=0) {
							counter++;		
						} else {
							throw new IllegalArgumentException("Each character of HexValue must be an "
									+ "integer 0-9,inclusive or a character A-F, inclusive, case insensitive");
						}
					}
					if (counter == 6) {
						valid =  true;
					}
				} else {
					throw new IllegalArgumentException("Value is not of proper length");
				}
			} else {
				throw new IllegalArgumentException("HexValue does not start with '#'");
			}
		}
		return valid;
	}

	/**
	 * Converts the hexidecimal to decimal (RGB format)
	 * 
	 * @param hexValue that is a 2 digit hexidecimal. It is a substring of the Color object's hexValue data field
	 *
	 * @return Integer that represents either red, green, or blue
	 */
	private int hexToRGB(String hexValue){	
		int RGB0;
		hexValue.toUpperCase();
		//finds the appropriate ones digit value 
		switch (hexValue.charAt(0)) {
		case '1': RGB0 = (1 * 16);
		break;
		case '2': RGB0 = (2*16);
		break;
		case '3': RGB0 = (3*16);
		break;
		case '4': RGB0 = (4*16);
		break;
		case '5': RGB0 = (5*16);
		break;
		case '6': RGB0 = (6*16);
		break;
		case '7': RGB0 = (7*16);
		break;
		case '8': RGB0 = (8*16);
		break;
		case '9': RGB0 = (9*16);
		break;
		case 'A': RGB0 = (10*16);
		break;
		case 'B': RGB0 = (11*16);
		break;
		case 'C': RGB0 = (12*16);
		break;
		case 'D': RGB0 = (13*16);
		break;
		case 'E': RGB0 = (14*16);
		break;
		case 'F': RGB0 = (15*16);
		break;
		default: RGB0 = 0;
		break;
		}

		//finds the appropriate tens digit value 
		int RGB1;
		switch (hexValue.charAt(1)) {
		case '1': RGB1 = (1);
		break;
		case '2': RGB1 = (2);
		break;
		case '3': RGB1 = (3);
		break;
		case '4': RGB1 = (4);
		break;
		case '5': RGB1 = (5);
		break;
		case '6': RGB1 = (6);
		break;
		case '7': RGB1 = (7);
		break;
		case '8': RGB1 = (8);
		break;
		case '9': RGB1 = (9);
		break;
		case 'A': RGB1 = (10);
		break;
		case 'B': RGB1 = (11);
		break;
		case 'C': RGB1 = (12);
		break;
		case 'D': RGB1 = (13);
		break;
		case 'E': RGB1 = (14);
		break;
		case 'F': RGB1 = (15);
		break;
		default: RGB1 = 0;
		break;
		}

		return (RGB0+RGB1);
	}

	/**
	 * Computes the hexValue given RGB code
	 * 
	 * @return String that represents the hexValue
	 */
	private String rgbToHex(){
		String Hex="#";
		//computes RGB to one hexValue
		int redTens = red/16;
		int redOnes = red%16;
		int greenTens = green/16;
		int greenOnes = green%16;
		int blueTens = blue/16;
		int blueOnes = blue%16;
		Hex += decToHex(redTens) + decToHex(redOnes) + decToHex(greenTens) + decToHex(greenOnes) + 
				decToHex(blueTens) + decToHex(blueOnes);
		return Hex;
	}

	/**
	 * Converts a decimal (RGB) to hexidecimal (hexValue)
	 * 
	 * @param decNumber Integer that is RGB in decimals
	 * 
	 * @return String that represents the hexValue
	 */
	private String decToHex(int decNumber){
		String hexNumber;
		switch (decNumber){
		case 1: hexNumber = "1";
		break;
		case 2: hexNumber = "2";
		break;
		case 3: hexNumber = "3";
		break;
		case 4: hexNumber = "4";
		break;
		case 5: hexNumber = "5";
		break;
		case 6: hexNumber = "6";
		break;
		case 7: hexNumber = "7";
		break;
		case 8: hexNumber = "8";
		break;
		case 9: hexNumber = "9";
		break;
		case 10: hexNumber = "A";
		break;
		case 11: hexNumber = "B";
		break;
		case 12: hexNumber = "C";
		break;
		case 13: hexNumber = "D";
		break;
		case 14: hexNumber = "E";
		break;
		case 15: hexNumber = "F";
		break;
		default: hexNumber = "0";
		break;
		}
		return hexNumber;
	}
}


