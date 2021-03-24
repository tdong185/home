//package scheduling;

import java.util.Comparator;

public class ComparatorPSJF implements Comparator<Process> {

	public int compare(Process o1, Process o2 ){

		if(o1.CPUTimeNeeded < o2.CPUTimeNeeded){
			return -1;
		} else if (o1.CPUTimeNeeded > o2.CPUTimeNeeded) {
			return 1;
		} else {
			if (o1.id < o2.id) { 
				return -1;
			} else {
				return 1;
			}
		}
	}
}