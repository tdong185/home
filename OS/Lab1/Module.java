package lab1;

import java.util.*; 


public class Module {
	
	HashMap<String, Integer> definitions = new HashMap<String, Integer>(); 
	HashMap<String, Integer> uses = new HashMap<String, Integer>(); 
	ArrayList addresses = new ArrayList ();

	public Module (HashMap<String, Integer> d, HashMap<String, Integer> u, ArrayList a) {
		this.definitions = d;
		this.uses = u;
		this.addresses = a;
	}
	
}
