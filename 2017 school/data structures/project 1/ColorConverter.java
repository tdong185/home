package project1;

import java.io.*;
import java.util.Scanner;

/**
 * This class contains the main method. It inputs the file from which the colorList object is created and 
 * asks for user input that specifies the hexvalue of a color 
 * 
 * @author tracydong
 * @version 09/11/2017
 */

public class ColorConverter {
	public static void main(String[] args){		

		//returns error and terminates if no arguments
		if (args.length < 1) {		
			System.err.println("Usage Error: the program expects file name as an argument.");
			System.exit(0);
		}

		try {		
			//input and read file contents into Color objects
			File file =  new File(args[0]);
			Scanner scanner;
			scanner = new Scanner(file);
			ColorList colorList = new ColorList(); 
			while (scanner.hasNextLine()){
				String colorListFromFile = scanner.nextLine();
				String[] line = colorListFromFile.split(",");
				Color color = new Color(line[1].trim(),line[0].trim());
				//add color to colorList
				colorList.add(color);
			}

			//the following takes in the user input
			do {
				System.out.println("Enter the color in HEX format (#RRGGBB) or \"quit\" to stop:");
				Scanner userInput = new Scanner(System.in);
				String hexvalue = userInput.next();

				//loop will continue running until user enters 'quit'
				if (hexvalue.equalsIgnoreCase("quit")) {
					System.exit(0);
				} else {
					//if (testHexValid is false, then print error statement )
					//if hexvalue is not a valid hexValue and not quit throw exception 
					try {
						System.out.println("Color information:");

						if (colorList.getColorByHexValue(hexvalue)==null) {
							Color color1 = new Color(hexvalue);
							System.out.println(color1);
						} else { 
							System.out.println(colorList.getColorByHexValue(hexvalue));
						} 

					} catch (IllegalArgumentException e) { 
						System.err.print("Error: This is not a valid color specification.");
					}
				}
			} while (true);
		} catch (FileNotFoundException e) {
			//if file path is incorrect
			System.err.println("Error: the file CSS_colors.txt cannot be opened.");
			System.exit(0);
		} 
	}
}


