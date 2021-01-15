package project1;

import java.util.ArrayList;

/**
 * This class extends ArrayList and creates a List of Color objects
 * 
 * @author tracydong
 * @version 09/19/2017
 */

public class ColorList extends ArrayList<Color>{

	/**
	 * ColorList constructor with no parameters
	 */
	public ColorList (){
	}

	/**
	 * Finds the color object in the this ColorList that has colorHexValue as its Hex value
	 * 
	 * @param colorHexValue a String to be searched for 
	 * 
	 * @return Color object found in this ColorList that is of hexvalue colorHexValue.
	 * 
	 */
	public Color getColorByHexValue(String colorHexValue){
		Color color = null;
		int colorListSize = this.size();
		colorHexValue = colorHexValue.toUpperCase();		

		// searches through the arraylist called colorList
		for (int i = 0; i<colorListSize;i++){
			// return the color object if its hexvalue equals, ignoring case,colorHexValue 
			if(this.get(i).getHexValue().equalsIgnoreCase(colorHexValue)){
				color= this.get(i);		
			}
		}
		return color;
	}

	/**
	 * Finds the color object in the this ColorList that has the same name as colorName
	 * 
	 * @param colorName String that is the color name to be searched for 
	 * 
	 * @return Color object found in this that has name colorName. 
	 */
	public Color getColorByName(String colorName){
		Color color = null;
		int colorListSize = this.size();
		
		// searches through the arraylist called colorList for the object with colorName
		for (int i = 0; i<colorListSize;i++){

			//if the color object constructor was created without a name  
			if (this.get(i).getName()==null) {
				color = this.get(i);
			}else {
				//if colorName is non-existent 
				if (this.get(i).getName()!=colorName) {
					color= null;
				}
				//ignoring case - tests if color object name equals colorName
				if(this.get(i).getName().equalsIgnoreCase(colorName)){
					color= this.get(i);
				}
			}
		}		
		return color;
	}	
}
