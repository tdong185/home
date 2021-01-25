package project5;
/**
 * This class represents an AVL tree that stores Collision objects. It is based on the BST_Recursive class created by Joanna Klukowska.
 * @author tracydong
 * @version 10/08/2017
 */
public class CollisionsData   {

	//private data fields that will be updated by getReport() method
	private int numCollisions;
	
	private int fatalitiesPersons;
	private int fatalitiesPedestrians;
	private int fatalitiesCyclists;
	private int fatalitiesMotorists;
	
	private int injuriesPersons;
	private int injuriesPedestrians;
	private int injuriesCyclists;
	private int injuriesMotorists;
	
	// root of the tree
	protected Node<Collision> root;
	// current number of nodes in the tree
	protected int numOfElements;
	//helper variable used by the remove methods 
	private boolean found;

	/**
	 * Default constructor that creates an empty tree.
	 */
	public CollisionsData() {
		this.root = null;
		numOfElements = 0;
	}

	/**
	 * calculates the balance factor. Based on code from Joanna Klukowska
	 * @param n Node<Collision> to start calculating balance factor from
	 * @return an int that represents the balance factor of Node<Collision> n
	 */
	private int balanceFactorCalc(Node<Collision> n) {
		if (n==null) return 0;
		if ( n.right == null )
			return -n.height;
		if ( n.left == null )
			return n.height;
		return n.right.height - n.left.height;
	}
	
	/**
	 * updates the height of the node. Based on code from Joanna Klukowska
	 * @param node Node<Collision> to start updating height from
	 * @return an int that represents the height of Node<Collision>node
	 */
	private int updateHeight(Node<Collision>node) {
		
		if (node.left == null && node.right ==null) {
			return 0;
		}
		else if (node.left == null) {
			return node.right.height + 1;
		} else if (node.right == null) {
				return node.left.height + 1;
		} else {
			return Math.max( node.right.height, node.left.height) + 1;
		}
	}
	
	/**
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change. Based on code from Joanna Klukowska
	 * 
	 * @param item the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = add (root, item);
//		updateHeight(root);
//		balance(root);
	}

	/*
	 * Actual recursive implementation of add.  Based on code from Joanna Klukowska
	 * 
	 * @param item the new element to be added to the tree
	 */
	private Node<Collision> add(Node<Collision> node, Collision item) {
		if (node == null) { 
			numOfElements++;
			return new Node<Collision>(item);
		}
		//if item < node.data
		if (node.data.compareTo(item) > 0) {
			node.left = add(node.left, item);
		} else if (node.data.compareTo(item) < 0) {
			node.right = add(node.right, item);
		}

		node.height = updateHeight (node);		
		node = balance(node);

		return node; 
	}
	/**
	 * balances AVL tree when balance factor is too high or low. Based on pseudocode from Joanna Klukowska
	 * @param node
	 * @return node. Updated after appropriate rotation
	 */
	private Node<Collision> balance(Node<Collision>node) {
		//choose rotatoin based on balance factor of subroots
		if (balanceFactorCalc(node) == -2) {
			if (balanceFactorCalc(node.left) <= 0) {
				return balanceLL(node);
			} else {
				return balanceLR(node);
			}
		}
		if (balanceFactorCalc(node) == 2) {
			if (balanceFactorCalc(node.right) >= 0) {
				return balanceRR(node);
			} else {
				return balanceRL(node);
			}
		}
		return node;
	}

	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged. Based on code from Joanna Klukowska
	 * 
	 * @param target the item to be removed from this tree 
	 */
	public boolean remove(Collision target)
	{
		root = recRemove(target, root);
		updateHeight(root);
		balance(root);
		return found;
	}
	
	/*
	 * Actual recursive implementation of remove method: find the node to remove. 
	 * Balances after removal
	 * Based on code from Joanna Klukowska
	 * 
	 * @param target the item to be removed from this tree 
	 */
	private Node<Collision> recRemove(Collision target, Node<Collision> node)
	{
		if (node == null)
			found = false;
		else if (target.compareTo(node.data) < 0)
			node.left = recRemove(target, node.left);
		else if (target.compareTo(node.data) > 0)
			node.right = recRemove(target, node.right );
		else {
			node = removeNode(node);
			found = true;
		}
		if (found) {
			node.height = updateHeight(node);
			balance(node);
		}
		return node;
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.  
	 * Based on code from Joanna Klukowska
	 * 
	 * @param target the item to be removed from this tree 
	 * @return a reference to the node itself, or to the modified subtree 
	 */
	private Node<Collision> removeNode(Node<Collision> node)
	{
		Collision data;
		if (node.left == null)
			return node.right ;
		else if (node.right  == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}
	
	/**
	 * Performs LL Rotation
	 * Based on pseudocode from Joanna Klukowska
	 * 
	 * @param node
	 * @return newly updated subroot node
	 */
	private Node<Collision> balanceLL(Node<Collision>node){
		Node<Collision> B = node.left;
		
		//reset new children
		node.left = B.right;
		B.right = node;
		
		//update heights
		node.height = updateHeight(node);
		B.height = updateHeight(B);
		
		return B;
	}

	/**
	 * Performs LR Rotation
	 * Based on pseudocode from Joanna Klukowska
	 * 
	 * @param node
	 * @return newly updated subroot node
	 */
	private Node<Collision> balanceLR(Node<Collision>node){
		Node<Collision> B = node.left;
		Node<Collision> C = B.right;
		
		//reset new children
		node.left = C.right;
		B.right = C.left;
		C.left = B;
		C.right = node;
		
		//update heights
		node.height = updateHeight(node);
		B.height = updateHeight(B);
		C.height = updateHeight(C);
		
		return C;
	}

	/**
	 * Performs RR Rotation
	 * Based on pseudocode from Joanna Klukowska
	 * 
	 * @param node
	 * @return newly updated subroot node
	 */
	private Node<Collision> balanceRR(Node<Collision>node){
		Node<Collision> B = node.right;
	
		//reset new children
		node.right = B.left;
		B.left = node;
		
		//update heights
		node.height = updateHeight(node);
		B.height = updateHeight(B);
		
		return B;
	}

	/**
	 * Performs RL Rotation
	 * Based on pseudocode from Joanna Klukowska
	 * 
	 * @param node
	 * @return newly updated subroot node
	 */
	private Node<Collision> balanceRL(Node<Collision>node){
		Node<Collision> B = node.right;
		Node<Collision> C = B.left;
		
		//reset new children
		node.right = C.left;
		B.left = C.right;
		C.left = node;
		C.right = B;
		
		//update heights
		node.height = updateHeight(node);
		B.height = updateHeight(B);
		C.height = updateHeight(C);
		
		return C;
	}
		
	
	/*
	 * Returns the information held in the rightmost node of subtree  
	 * Based on code from Joanna Klukowska
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node 
	 * @return returns data stored in the rightmost node of subtree  
	 */
	private Collision getPredecessor(Node<Collision> subtree)
	{
		if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node<Collision> temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}

	/**
	 * Determines the number of elements stored in this AVL.
	 * Based on code from Joanna Klukowska
	 * 
	 * @return number of elements in this AVL
	 */
	public int size() {
		return numOfElements;
	}


	/**
	 * Produces tree like string representation of this AVL.
	 * Based on code from Joanna Klukowska
	 * 
	 * @return string containing tree-like representation of this AVL.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 * Based on code from Joanna Klukowska
	 * 
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   AVL
	 */
	private void preOrderPrint(Node<Collision> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	/**
	 * Uses binary search find subroot with zipcode that matches zip
	 * @param node root to start searching
	 * @param zip. Zipcode to match
	 * @return node subroot with matching zipcode
	 */
	public Node<Collision> findZip(Node<Collision>node, String zip){
		if(node != null) {
			//when zipcode is equal, return node
			if (node.data.getZip().equals(zip)) {
				return node;
			}
			//look at left child if zip is smaller
			if(node.data.getZip().compareTo(zip)>0) {
				return findZip(node.left,zip);
			}
			//look at right child if zip is greater
			if (node.data.getZip().compareTo(zip)<0) {
				return findZip(node.right,zip);
			}
		}
		return null;
	}
	
	/**
	 * Uses binary search to find collisions with equal zipcodes and dates within dateBegin, dateEnd range
	 * @param node subroot to begin searching
	 * @param zip zipcode to match
	 * @param dateBegin Date object that is the beginning of the search range, inclusive
	 * @param dateEnd Date object that is the end of the search range, inclusive
	 */
	private void findDate(Node<Collision>node , String zip, Date dateBegin, Date dateEnd){
				
		if(node == null) return;
		//find a fitting object
		if (node.data.getZip().equals(zip) && node.data.getDate().compareTo(dateBegin) >= 0 && node.data.getDate().compareTo(dateEnd)<= 0 ){
			//update global data fields
			numCollisions++;	
			fatalitiesPersons += node.data.getPersonsKilled();
			fatalitiesPedestrians += node.data.getPedestriansKilled();
			fatalitiesCyclists += node.data.getCyclistsKilled();
			fatalitiesMotorists += node.data.getMotoristsKilled();			
			injuriesPersons += node.data.getPersonsInjured();
			injuriesPedestrians += node.data.getPedestriansInjured();
			injuriesCyclists += node.data.getCyclistsInjured();
			injuriesMotorists += node.data.getMotoristsInjured();
			//check left subchild
			findDate(node.left,zip, dateBegin, dateEnd);
			//check right subchild
			findDate(node.right,zip, dateBegin, dateEnd);
			return;
		}
		//look to left side if too large
		if(node.data.getDate().compareTo(dateBegin) < 0) {
			findDate(node.right, zip,dateBegin, dateEnd);
			return;
		}
		//look to right side if too small
		if(node.data.getDate().compareTo(dateBegin) > 0) {
			findDate(node.left,zip,dateBegin,dateEnd);
			return;
		}
		
	}
	
	/**
	 * Searches AVL to find information about the numbers of fatalities and injuries for a given zip code and date range
	 * @param zip String representation of zipcode to find
	 * @param dateBegin Date object that is the beginning of the search range, inclusive
	 * @param dateEnd  Date object that is the end of the search range, inclusive
	 * @return String that contains information about the numbers of fatalities and injuries for a given zip code and date range
	 */
	public String getReport (String zip, Date dateBegin, Date dateEnd ) {
		//call search
		this.findDate(this.findZip(root, zip),zip, dateBegin, dateEnd);
				
		//create string
		String s = "Motor Vehicle Collisions for zipcode " + zip + " (" + dateBegin + " - " + dateEnd + ")";
		s += "\n" + "====================================================================";
		s += "\n" + "Total number of collisions: " + numCollisions;
		s += "\n" + "Number of fatalities: " +  fatalitiesPersons;
		s += "\n" + "pedestrians: " + fatalitiesPedestrians;
		s += "\n" + "cyclists: " +  fatalitiesCyclists;
		s += "\n" + "motorists: " + fatalitiesMotorists;
		s += "\n" + "Number of injuries: " + injuriesPersons;
		s += "\n" + "pedestrians: " + injuriesPedestrians;
		s += "\n" + "cyclists: " + injuriesCyclists;
		s += "\n" + "motorists: " + injuriesMotorists;
				
		return s;
	}
	
	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees. 
	 * 
	 * @author Joanna Klukowska
	 *
	 * @param <Collision> a reference type that implements Comparable<Collision> interface 
	 */
	protected static class Node <Collision extends Comparable <Collision>> 
	implements Comparable <Node <Collision> > {


		protected Node <Collision> left;  //reference to the left subtree 
		protected Node <Collision> right; //reference to the right subtree
		protected Collision data;            //data item stored in the node

		protected int height; 
		protected int balanceFactor = balanceFactorCalc(this);

		/**
		 * Constructs a BSTNode initializing the data part 
		 * according to the parameter and setting both 
		 * references to subtrees to null.
		 * @param data
		 *    data to be stored in the node
		 */
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0; 
		}

		private int balanceFactorCalc(Node<Collision> n) {
			if ( n.right == null )
				return -n.height;
			if ( n.left == null )
				return n.height;
			return n.right.height - n.left.height;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Node<Collision> other) {
			return this.data.compareTo(other.data);
		} 

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}



	}

}
