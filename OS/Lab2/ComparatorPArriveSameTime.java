//package scheduling;

import java.util.Comparator;

public class ComparatorPArriveSameTime implements Comparator<Process> {

	public int compare(Process o1, Process o2 ){

		if(o1.arrivalTime < o2.arrivalTime){
			return -1;
		} else if (o1.arrivalTime > o2.arrivalTime) {
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
/*
if (readySameTime.size() == 1) {
	readyQueue.add(readySameTime.get(0));
} else {

	Collections.sort(readySameTime,new ComparatorPArriveSameTime());

	for (int i = 0 ; i < readySameTime.size(); i ++) {
		readyQueue.add(readySameTime.get(i));
	}
	readySameTime.clear();
}
*/