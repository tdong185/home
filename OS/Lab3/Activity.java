//package bankers;

public class Activity {

	//input from input file
	int action;
	int delay;
	int resourceType;
	int number;
	
	//constructor the fills in information from input file
	public Activity (String act, int delay, int resourceType, int number) {
		
		//type of activity
		if (act.contains("initiate")) {
			action = 0 ;
		} else if (act.contains("request")) {
			action = 1;
		} else if (act.contains("release")) {
			action = 2;
		} else {
			action = 3;
		}
		
		this.delay = delay;
		this.resourceType = resourceType;
		this.number = number;
		
	}
	
}
