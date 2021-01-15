package project3;

/**
 * This class uses the queue data structure to detect possible next locations in the 
 * current maze. Implements PossibleLocations
 * 
 * @author tracydong
 * @version 11/05/2017
 */

public class PossibleLocationsQueue implements PossibleLocations {

	/**
	 * Constructor for PossibleLocationsQueue with default capacity 10
	 */
	public PossibleLocationsQueue() {
	}
	
	/**
	 * Constructor for PossibleLocationsQueue with capacity based on parameter
	 * @param capacity
	 * 		how large to make the array
	 */
	public PossibleLocationsQueue(int capacity) {
		//set capacity as default 10 if invalid number
		if (capacity<=0) capacity = 10;
		data = (Location[]) new Location [capacity];
		this.capacity = capacity;
	}
	
	//private data fields
	private int size=0;
	private int front=0;
	private int capacity=10;
	//default capacity of data array is 10
	private Location[]data = (Location[]) new Location [capacity];	
	
	/**
	 * gets data array
	 * @return Location array
	 * 			array that stores Location objects
	 */
	public Location[] getData() {
		return data;
	}
	
	/**
	 * gets current Front (first added) 
	 * @return front 
	 * 			Location object that was added to array first
	 */
	public int getFront() {
		return front;
	}
	
	/**
	 * gets size of array data
	 * @return size
	 * 			number of Location objects in array data
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * gets capacity
	 * @return capacity
	 * 			number of objects that can be stored in array data 
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Add a Location object to the set.
	 * @param s
	 *    object to be added
	 */
	public void add(Location s) {
		//return and do nothing if s is null
		if (s==null) return;
		//when size reaches capacity of array, make the array larger through makeLarger method
		if(size == data.length) makeLarger();
		//add Location object s in last available position
		int avail = (front+size)%data.length;
		data[avail] = s;
		//increase size from adding another object
		size ++;
	}

	/**
	 * Increases the capacity of the array data, which holds objects of type Location by creating a new array
	 * to hold 1.5x more objects then copy over objects
	 */
	private void makeLarger() {
		//increase capacity by 1.5
		Location[]dataNew = (Location[]) new Location[(int) (capacity*1.5)];
		//copy over data
		for (int i = 0; i<size;i++) {
			dataNew[i] = data[(front+i)%data.length];
		}
		//reset datafields
		capacity = (int) (capacity*1.5);
		front = 0;
		data = dataNew;
	}

	/**
	 * Remove the next object from the set. The specific
	 * item returned is determined by the underlying structure
	 * of set representation.
	 * @return
	 *    the next object, or null if set is empty
	 */
	public Location remove() {
		//return null if empty
		if (size == 0) return null;
		//set tmp to be the Location object to return
		Location tmp = data[front];
		//set front to the next object to remove current front from array
		front = (front + 1)%data.length;
		//decrease size because removing an object
		size --;
		return tmp;
	}

	/**
	 * Determines if set is empty or not.
	 * @return
	 *    true, if set is empty,
	 *    false, otherwise.
	 */
	public boolean isEmpty() {
		//size being 0 means the array is empty
		return size == 0;
	}

}
