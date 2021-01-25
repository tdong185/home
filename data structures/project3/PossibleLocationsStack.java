package project3;

/**
 * This class uses the stack data structure to detect possible next steps based on the current position in 
 * the maze. Implements PossibleLocations
 * 
 * @author tracydong
 * @version 11/05/2017
 */

public class PossibleLocationsStack implements PossibleLocations {

	/** Nested inner class Node that links together Location objects with the nexted Location object.   
	 * 
	 * @author Based on nested inner Node Class found in Data Structures & Algorithms in Java, 6e
	 * by Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser. Revised by Tracy Dong. 
	 */
	
	public static class Node{
		//private data fields.
		private Location element;
		private Node next;

		/** 
		 * Constructor for Node<E>
		 * @param e
		 * @param n
		 */
		public Node(Location e, Node n) {
			element =e;
			next =n;
		}

		/** sets element data field for Node<E> 
		 * @param e
		 */
		public void setElement(Location e) {
			this.element = e;
		}
		
		/**
		 * gets Location object. The data field is called element
		 * @return Location element
		 */
		public Location getElement() {
			return element;
		}
		
		/**
		 * gets the next Node with element Location
		 * @return the next Node
		 */
		public Node getNext(){
			return next;
		}

		/**
		 * sets the next Node as n
		 *  @param n
		 */
		public void setNext(Node n) {
			this.next = n;
		}		
	}	

	/**
	 * gets size. Size represents the number of Location objects stored inside the Linked List
	 * @return size - the number of Location objects in this List
	 */
	public int getSize() {
		return size;
	}

	/**
	 * returns the top most Node
	 * @return head - the top Node
	 */
	public Node getHead() {
		return head;
	}
	
	//private data fields 
	private int size=0;
	private Node head = null;
	
	/**
	 * Constructor for PossibleLocationsStack 
	 */
	public PossibleLocationsStack(){	
	}	
	
	/**
	 * Add a Location object to the set.
	 * @param s
	 *    object to be added
	 */
	public void add(Location s) {
		// returns to whatever called method if s is nul
		if (s==null) return;
		//creates new Node with element Location s and next as current head
		//sets new Node as head
		head = new Node (s,head);
		//increases size by one
		size++;
	}

	/**
	 * Removes the most recently added Node. If the list is empty, return null	 
	 * @return Location that is removed. If empty list, return null
	 */
	public Location remove() {
		//return null if size is 0 (empty list)
		if(this.isEmpty()) return null;
		//create Location object to hold element of current head
		Location answer = head.getElement();
		//set head to next to remove current head from List
		head = head.getNext();
		//decrease size by 1 to account for removing one Node
		size --;
		return answer;
	}

	/**
	 * Determines if set is empty or not.
	 * @return
	 *    true, if set is empty,
	 *    false, otherwise.
	 */
		public boolean isEmpty() {
		return size == 0;
	}

}
