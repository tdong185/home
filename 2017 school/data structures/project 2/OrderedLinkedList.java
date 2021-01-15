package project2;

public class OrderedLinkedList<E extends Comparable<E>> implements OrderedList<E>{

	/** Nested inner class Node that links together generic element with the nexted generic element.   
	 * 
	 * @author Based on nested inner Node Class found in Data Structures & Algorithms in Java, 6e
	 * by Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser. Revised by Tracy Dong. 
	 */
	public static class Node<E>{
		//private data fields.
		private E element;
		private Node<E> next;

		/** 
		 * Constructor for Node<E>
		 * @param e
		 * @param n
		 */
		public Node(E e, Node<E>n) {
			element =e;
			next =n;
		}

		/** sets element data field for Node<E> 
		 * @param e
		 */
		private void setElement(E e) {
			this.element = e;
		}
		private E getElement() {
			return element;
		}
		private Node<E> getNext(){
			return next;
		}
		private void setNext(Node<E>n) {
			this.next = n;
		}		
	}	

	//instance variables of OrderedLinkedList
	private int size=0;
	private Node<E> head = null;
	public OrderedLinkedList(){	}

	/**
	 * Returns the number of elements in this list.
	 * @return the number of elements in this list
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds the specified element to  this list in a sorted order. 
	 *
	 * <p>The element added must implement Comparable<E> interface. This list 
	 * does not permit null elements. 
	 *
	 * @param e element to be appended to this list
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws ClassCastException if the class of the specified element
	 *         does not implement Comparable<E> 
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean add(E e) throws ClassCastException{
		Node<E> nodeToAdd = new Node<E>(e, (Node<E>)null);
		//throws NullPointerException if specified element is null
		if (e==null) throw new NullPointerException();
		//if colorList is empty, nodeToAdd is now head
		if (size==0) {
			this.head = nodeToAdd;
			size++;			
			return true;			
		}

		Node<E> current = this.head;

		//if nodeToAdd is less than head, nodeToAdd is new head
		if(nodeToAdd.getElement().compareTo(head.getElement())<0) {
			nodeToAdd.setNext(head);
			head = nodeToAdd;
			size++;
			return true;
		}

		//goes through colorList 
		while(nodeToAdd.getElement().compareTo(current.getElement())>0) {
			//if reaches end of colorList nodeToAdd is added as last node
			if(current.getNext()==null) {
				current.setNext(nodeToAdd);
				size++;
				return true;
			}
			//adds nodeToAdd even if nodeToAdd equals a node in colorList
			if(current.getNext().getElement().equals(nodeToAdd.getElement())) {
				nodeToAdd.setNext(current.getNext());
				current.setNext(nodeToAdd);
				size++;
			}
			if (nodeToAdd.getElement().compareTo(current.getNext().getElement())<0) {
				nodeToAdd.setNext(current.getNext());
				current.setNext(nodeToAdd);
				size++;
				return true;
			}
			current = current.getNext();
		}	
		return false;
	}

	/**
	 * Returns a shallow copy of this list. (The elements
	 * themselves are not cloned.)
	 *
	 * @return a shallow copy of this list instance
	 * @throws CloneNotSupportedException - if the object's class does 
	 *         not support the Cloneable interface
	 * @author method based off SingleyLinkedList<E> clone() method found in Data Structures & Algorithms in Java, 6e
	 * by Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser. Revised by Tracy Dong  	
	 */
	public Object clone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		OrderedLinkedList<E> other = (OrderedLinkedList<E>) super.clone();
		if(other==null) throw new NullPointerException();
		if (size>0) {
			other.head = new Node<>(head.getElement(),null);
			Node<E> walk = head.getNext();
			Node<E> otherTail = other.head;
			while(walk !=null) {
				Node<E> newest = new Node<> (walk.getElement(),null);
				otherTail.setNext(newest);
				otherTail = newest;
				walk = walk.getNext();
			}
		}
		return (Object)other;
	} 

	/**
	 * Compares the specified object with this list for equality.  Returns
	 * {@code true} if and only if the specified object is also a list, both
	 * lists have the same size, and all corresponding pairs of elements in
	 * the two lists are <i>equal</i>.  (Two elements {@code e1} and
	 * {@code e2} are <i>equal</i> if {@code e1.equals(e2)}.)  
	 * In other words, two lists are defined to be
	 * equal if they contain the same elements in the same order.<p>
	 *
	 * @param o the object to be compared for equality with this list
	 * @return {@code true} if the specified object is equal to this list
	 * @author method based off SingleyLinkedList<E> clone() method found in Data Structures & Algorithms in Java, 6e
	 * by Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser.
	 */
	public boolean equals (Object o) {
		if(o == null) return false;
		if(getClass() != o.getClass()) return false;
		OrderedLinkedList<E> other = (OrderedLinkedList<E>) o;
		if (size!= other.size) return false;
		Node<E> walkA = head;
		Node<E> walkB = other.head;
		while (walkA != null) {
			if (!walkA.getElement().equals(walkB.getElement())) return false;
			walkA = walkA.getNext();
			walkB = walkB.getNext();
		}
		return true;
	}

	/**
	 * Removes all of the elements from this list.
	 * The list will be empty after this call returns.
	 */
	public void clear() {	
		this.head = null;
		size = 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 *
	 * @param o element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 * @throws ClassCastException if the type of the specified element
	 *         is incompatible with this list
	 * @throws NullPointerException if the specified element is null 
	 */
	public boolean contains(Object o) throws ClassCastException{
		if (o ==null) throw new NullPointerException();
		Node<E> current = this.head;
		//create new nodeToTest based on parameter
		Node<E> nodeToTest = new Node<E>((E)o, null);
		//while colorList still has more elements, if nodeToTest equals an element, return true
		while (current!=null) { 
			if (nodeToTest.getElement().equals(current.getElement())) {
				return true;
			}
			current = current.getNext();
		} 
		return false;
	}

	/**
	 * Returns a string representation of this list.  The string
	 * representation consists of a list of the list's elements in the
	 * order they are stored in this list, enclosed in square brackets
	 * (<tt>"[]"</tt>).  Adjacent elements are separated by the characters
	 * <tt>", "</tt> (comma and space).  Elements are converted to strings 
	 * by the {@code toString()} method of those elements.
	 *
	 * @return a string representation of this list
	 */
	@Override
	public String toString(){
		String finalString="[";
		//add in elements to finalString
		if (this.size>0) {
			for (int i = 0; i<this.size()-1;i++) {
				E elmnt = this.get(i);
				elmnt.toString();
				finalString +=elmnt + ", ";
			}
			finalString+=this.get(this.size()-1).toString();
		}
		finalString+="]";
		return finalString;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 * <tt>(index < 0 || index >= size())</tt>
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		int i = 1;
		Node<E> current = head;
		//return head
		if (index == 0) {return current.getElement();}
		current = current.getNext();
		//increment through colorList index amount of times
		while (i<index) {
			current = current.getNext();
			i++;	
		}	
		return current.getElement();
	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 *
	 * @param o element to search for
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 */
	public int indexOf(Object o) {
		//counter keeps track of the poition current points to
		int counter = 0;
		Node<E> current = this.head;
		//while there are more elements in colorList
		while(current!=null) {
			//if the element equals o
			if (current.getElement().equals(o)) {
				return counter;
			}
			current = current.getNext();
			counter++;
		}
		return -1;
	}

	/**
	 * Removes the element at the specified position in this list.  Shifts any
	 * subsequent elements to the left (subtracts one from their indices if such exist).
	 * Returns the element that was removed from the list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException  if the index is out of range 
	 * <tt>(index < 0 || index >= size())</tt>
	 */
	public E remove(int index) throws NullPointerException {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}	
		E toReturn = this.get(index);
		E current = this.get(index-1);
		//set next to skip the current next based on index
		((Node<E>) current).setNext(((Node<E>) current).getNext().getNext());
		size--;
		return toReturn;
	}

	/**
	 * Removes the first occurrence of the specified element from this list,
	 * if it is present.  If this list does not contain the element, it is
	 * unchanged.  More formally, removes the element with the lowest index
	 * {@code i} such that
	 * <tt>(o.equals(get(i))</tt>
	 * (if such an element exists).  Returns {@code true} if this list
	 * contained the specified element (or equivalently, if this list
	 * changed as a result of the call).
	 *
	 * @param o element to be removed from this list, if present
	 * @return {@code true} if this list contained the specified element
	 * @throws ClassCastException if the type of the specified element
	 *         is incompatible with this list
	 * @throws NullPointerException if the specified element is null and this
	 *         list does not permit null elements
	 */
	public boolean remove(Object o) throws ClassCastException {
		if(o==null) throw new NullPointerException();
		Node<E> current = this.head;	
		//if o equals the head
		if(current.getElement().equals(o)) {
			//remove head by setting null
			if (this.size()==1) {
				head = null;
				size--;
				return true;
			} else {
				head = current.getNext();
				size--;
				return true;
			}
		}		
		while (current!=null) {
			if(current.getNext()==null) {
				return false;
			}
			if ((o.equals(current.getNext().getElement()))){
				current.setNext(current.getNext().getNext());
				size--;
				return true;
			}
			current = current.getNext();
		}	
		return false;
	}	
}
