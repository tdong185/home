//package bankers;

import java.util.*;

public class Task {

	int id;
	int delay;
	
	//list of activities for each task
	Queue <Activity> activites = new LinkedList<Activity>();
	
	//arrays to keep track of how many resources task owns and needs
	int [] own;
	int [] need;
	
	//when task finishes and how long it spends in blocked phase
	int end=0;
	int wait;
	
	//constructor that sets up array lengths using input file information
	public Task (int id, int resourceTypes) {
		this.id = id;
		own = new int[resourceTypes];
		need = new int[resourceTypes];
	}

	
	
}
