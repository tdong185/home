//package bankers;

import java.io.*;
import java.util.*;


public class Banker {
	//used to gather information from input file in command line
	public static Scanner scanner;
	public static File rn;

	//input file value that says the number of tasks
	static int numTasks;
	//input file value that says the number of resources
	static int numResourceTypes;

	//array that holds all the tasks
	static Task [] tasks;

	/*array of how many resources are available for each type. For example. resourcesAvail[0] will show how many resources
	 * of type 0 there are.
	 */
	static int [] resourcesAvail;

	//used to add pending resources that will be added in after cycle
	static int [] resourcesTemp;

	//used to keep track of how many cycles and how many tasks are finished
	static int cycle;
	static int numDone;

	//tasks that have been blocked in request phase because there are not enough resources
	static ArrayList<Task> blocked = new ArrayList<Task>();
	//tasks that are ready to go!
	static ArrayList<Task> delayed = new ArrayList<Task>();
	//the congregation blocked+delayed with blocked first
	static ArrayList<Task> nextRun = new ArrayList<Task>();

	public static void main(String[] args) throws FileNotFoundException {
		//read in input
		rn = new File (args[0]);
		scanner = new Scanner(rn);

		numTasks = scanner.nextInt();
		numResourceTypes = scanner.nextInt();
		resourcesAvail = new int[numResourceTypes];

		//used to add pending resources that will be added in after cycle
		resourcesTemp = new int[numResourceTypes];
		for (int i = 0 ; i < numResourceTypes; i++)  {
			resourcesAvail[i] = scanner.nextInt();
			resourcesTemp[i] = 0;
		}
		tasks = new Task[numTasks];
		for (int i = 0 ; i < numTasks; i ++) {
			Task temp =  new Task((i+1),numResourceTypes);
			tasks[i] = temp;
		}

		while (scanner.hasNext()) {
			String activity = scanner.next();
			int taskID = scanner.nextInt();
			int delay = scanner.nextInt();
			int resourceType = scanner.nextInt();
			int number = scanner.nextInt();

			Activity actTemp = new Activity(activity,delay,resourceType,number);
			tasks[taskID-1].activites.add(actTemp);

		}

		//calls the FIFO resource manager
		fifoAlgo();

		/*arrays used to keep track of FIFO and Banker algorithm tasks
		 *  and when each finishes and how long each had to wait.
		 *  Used for printing.
		 */
		int [] fifoPrintEnd = new int [numTasks];
		int [] fifoPrintWait = new int [numTasks];
		int [] bankerPrintEnd = new int [numTasks];
		int [] bankerPrintWait = new int [numTasks];

		//also used for printing total/summary line
		int fifoendtotal=0;
		int fifowaittotal=0;

		for (int i = 0 ; i < numTasks ; i++) {
			//do not count aborted tasks
			if (tasks[i].end !=0 ) {
				fifoPrintEnd[i] = tasks[i].end;
				fifoPrintWait[i] = tasks[i].wait;
				fifoendtotal += tasks[i].end;
				fifowaittotal += tasks[i].wait;
			}
		}


		//rest values for Banker algorithm
		//read in input
		rn = new File (args[0]);
		scanner = new Scanner(rn);

		numTasks = scanner.nextInt();
		numResourceTypes = scanner.nextInt();
		resourcesAvail = new int[numResourceTypes];

		//used to add pending resources that will be added in after cycle
		resourcesTemp = new int[numResourceTypes];
		for (int i = 0 ; i < numResourceTypes; i++)  {
			resourcesAvail[i] = scanner.nextInt();
			resourcesTemp[i] = 0;
		}
		tasks = new Task[numTasks];
		for (int i = 0 ; i < numTasks; i ++) {
			Task temp =  new Task((i+1),numResourceTypes);
			tasks[i] = temp;
		}

		while (scanner.hasNext()) {
			String activity = scanner.next();
			int taskID = scanner.nextInt();
			int delay = scanner.nextInt();
			int resourceType = scanner.nextInt();
			int number = scanner.nextInt();

			Activity actTemp = new Activity(activity,delay,resourceType,number);
			tasks[taskID-1].activites.add(actTemp);

		}

		//calls banker resource manager
		bankerAlgo();

		//also used for printing total/summary line
		int bankerendtotal=0;
		int bankerwaittotal=0;
		for (int i = 0 ; i < numTasks ; i++) {
			//ignore aborted task values
			if (tasks[i].end !=0 ) {
				bankerPrintEnd[i] = tasks[i].end;
				bankerPrintWait[i] = tasks[i].wait;
				bankerendtotal += tasks[i].end;
				bankerwaittotal += tasks[i].wait;
			}
		}

		//Print times ended, wait times, % waiting for FIFO, Bankers, and totals
		System.out.print("\t\tFIFO\t\t\tBANKER'S");
		for (int i = 0 ; i < numTasks; i++) {
			System.out.print("\nTask " + (i+1) + "\t\t" );
			//don't print aborted values
			if (fifoPrintEnd[i]==0) {
				System.out.print("aborted          \t");
			} else {
				System.out.print(fifoPrintEnd[i]+"     "+fifoPrintWait[i] + "     " + (int)((double)fifoPrintWait[i] / (double)fifoPrintEnd[i] *100) + "%\t\t");
			}
			if (bankerPrintEnd[i]==0) {
				System.out.print("aborted          \t");
			} else {
				System.out.print(bankerPrintEnd[i]+ "     " + bankerPrintWait[i] + "     " + (int)((double)bankerPrintWait[i] / (double)bankerPrintEnd[i] *100) + "%");
			}
		}
		System.out.print("\nTotal\t\t" + fifoendtotal + "     " + fifowaittotal + "     " + (int)((double)fifowaittotal / (double)fifoendtotal *100) + "%\t\t" +
				bankerendtotal + "    " + bankerwaittotal + "     " + (int)((double)bankerwaittotal / (double)bankerendtotal *100) + "%\n");

	}

	//FIFO resource manager. follows FIFO algorithm to cycle through tasks' activities
	public static void fifoAlgo() {
		//use nextRun to keep track of current active tasks
		for (int i = 0 ; i < tasks.length;i++) {
			nextRun.add(tasks[i]);
		}

		numDone = 0;
		cycle = 0;

		//continue while there are still active tasks
		while (nextRun.size() != 0) {
			//loop through all tasks and find the current activity for the task
			for (int i = 0 ; i < nextRun.size(); i++) {
				if (nextRun.get(i).activites.peek().action == 0) {
					initiateFIFO(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 1) {
					requestFIFO(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 2) {
					release(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 3) {
					terminate(nextRun.get(i));
				}
			}
			//end cycle so increment to next
			cycle++;

			//for FIFO, checking if there is a deadlock
			if ((blocked.size() > 0) && (delayed.size() == 0)) {
				deadlock();
			}

			//update what resources are available
			for (int i = 0 ; i < numResourceTypes; i++) {
				resourcesAvail[i] += resourcesTemp[i];
				resourcesTemp[i] = 0;
			}

			//clear list of activeTasks and add in blocked tasks first, then delayed/next tasks
			nextRun.clear();
			blocked.addAll(delayed);
			nextRun.addAll(blocked);
			blocked.clear();
			delayed.clear();
		}
	}

	//the inititiate activity. Gives task its initial claim or desired resource type
	public static void initiateFIFO(Task task) {
		//get initial claim value and set that as task's need value
		int claim = task.activites.peek().number;
		//the resource type
		int resType = task.activites.peek().resourceType - 1;
		task.need[resType] = claim;

		//this activity is completed. up next is the next activity
		task.activites.poll();
		//task completed so added to delayed (so that is is run after the blocked tasks)
		delayed.add(task);

	}

	//for FIFO manager that requests resources
	public static void requestFIFO(Task task) {
		int claim = task.activites.peek().number;

		//if there is a delay, decrease the delayed wait by 1 cycle and add task to delayed arraylist to be run next cycle
		if (task.activites.peek().delay > 0) {
			delayed.add(task);
			task.activites.peek().delay--;
		} else {
			//if enough resources are available grant the request
			if (resourcesAvail[task.activites.peek().resourceType -1] >= claim) {
				//decrease resources available
				resourcesAvail[task.activites.peek().resourceType -1] -=claim;
				//decrease resources needed by task
				task.need[task.activites.peek().resourceType -1]-=claim;
				//increase resources owned by task
				task.own[task.activites.peek().resourceType -1] += claim;
				//this activity is done. onwards to the next one!
				task.activites.poll();
				//add to delayed to task can be added back to active tasks list
				delayed.add(task);
			} else {
				//task is blocked and made to wait because there are not enough resources
				task.wait++;
				blocked.add(task);
			}
		}
	}

	//deadlock resolution following specs
	public static void deadlock() {
		// abort lowest numbered deadlocked task
		for (int i = 0; i < blocked.size(); i++) {
			//find lowest id
			int min = blocked.get(0).id;
			for (int j = 0; j < blocked.size(); j++) {
				if (blocked.get(j).id <  min) {
					min = blocked.get(j).id;
				}
			}
			//abort and release resources
			for (int j = 0; j < blocked.size(); j++) {
				if (blocked.get(j).id == min) {
					for (int k = 0; k < numResourceTypes; k++) {
						resourcesTemp[k] += blocked.get(j).own[k];
					}
					blocked.remove(j);
				}
			}
		}

	}

	//Banker algorithm resource manager that avoids deadlock and gives out resources conservatively
	public static void bankerAlgo() {
		//update nextRun to show active tasks
		for (int i = 0 ; i < tasks.length;i++) {
			nextRun.add(tasks[i]);
		}

		numDone = 0;
		cycle = 0;

		//continue while there are still active tasks
		while (nextRun.size() != 0) {
			for (int i = 0 ; i < nextRun.size(); i++) {
				//call function based on tasks' next activity
				if (nextRun.get(i).activites.peek().action == 0) {
					initiate(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 1) {
					request(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 2) {
					release(nextRun.get(i));
				} else if (nextRun.get(i).activites.peek().action == 3) {
					terminate(nextRun.get(i));

				}
			}
			cycle++;
			//update resources to include pending for next cycle
			for (int i = 0 ; i < numResourceTypes; i++) {
				resourcesAvail[i] += resourcesTemp[i];
				resourcesTemp[i] = 0;
			}

			//set up active tasks for next cycle, adding blocked tasks first
			nextRun.clear();
			blocked.addAll(delayed);
			nextRun.addAll(blocked);
			blocked.clear();
			delayed.clear();

		}
	}

	//for banker resource manager to distribute initial claim to tasks
	public static void initiate(Task task) {
		int claim = task.activites.peek().number;
		int resType = task.activites.peek().resourceType - 1;

		//cannot give resource if the claim is more than the number of resources available
		if (claim > resourcesAvail[resType]) {
			System.out.println("Banker aborts "+ task.id+ " before run begins:\n" +
					"       claim for resource "+ (resType+1) + "(" +claim+") exceeds number of units present ("+resourcesAvail[resType]+")");
		} else {
			//initial claim allowed. Tasks' needs are now updated
			task.need[resType] += claim;
			//next task!
			task.activites.poll();
			delayed.add(task);
		}
	}

	//for banker resource manager to use when tasks request more resources
	public static void request(Task task) {
		int claim = task.activites.peek().number;
		//delay until next cycle if delay value is greater than 0
		if (task.activites.peek().delay > 0) {
			delayed.add(task);
			task.activites.peek().delay--;
		} else {
			//if claim is too big, release resources and consider task aborted
			if(claim > task.need[task.activites.peek().resourceType-1]) {
				System.out.println("Banker aborts "+ task.id+ " during cycle " + cycle+"-"+(cycle+1)+ "\n" +
						"       claim for resource exceeds number of units present. "+task.own[task.activites.peek().resourceType-1]+ " available next cycle");

				resourcesTemp[task.activites.peek().resourceType -1] += claim;
				numDone++;
			} else {
				//check if giving resource is safe
				if (safe(task,claim)) {
					//if safe, updated resources available (when testing safe state, the safe() function already updates task's resources
					//owned and resources needed)
					resourcesAvail[task.activites.peek().resourceType -1] -=claim;
					//next task
					task.activites.poll();
					delayed.add(task);
				} else {
					//if not safe, undo the safe() function's resources owned/needed updates
					task.need[task.activites.peek().resourceType -1]+=claim;
					task.own[task.activites.peek().resourceType -1] -= claim;
					//put into blocked
					task.wait++;
					blocked.add(task);
				}
			}
		}
	}

	//function used by banker resource manager that checks if it will be a safe state when giving task resources
	public static boolean safe(Task task,int claim) {

		//copy of resourcesAvail[] that keeps track of available resources
		int [] work = new int [numResourceTypes];
		for (int i = 0 ; i < numResourceTypes; i++) {
			work[i] = resourcesAvail[i];
		}

		int rsrcType = task.activites.peek().resourceType-1;
		//if task is requesting more resources than available, suptract from need and own to counterbalance the addition and subtraction in the request() function -- lines 366-367
		if (claim > resourcesAvail[rsrcType]) {
			//add to blocked
			work[rsrcType] -= claim;
			task.own[rsrcType] += claim;
			task.need[rsrcType] -= claim;
			//not safe
			return false;
		}

		//grant resource to see if it will be a safe state
		work[rsrcType] -= claim;
		task.own[rsrcType] += claim;
		task.need[rsrcType] -= claim;

		//if there is a task that will lead to a safe state
		Task foundMatch = null;
		boolean isSafe = true;
		//number of tasks that are safe
		int numFinished = 0;
		//array to keep track of if task is in a safe state. at end, if all tasks are safe (true), then
		//granting resource remains in safe state
		boolean [] finish = new boolean[nextRun.size()];
		for (int i = 0 ; i < finish.length;i++) {
			finish[i] = false;
		}

		//continue while it is still possible to find a safe state
		while (isSafe) {
			//set to be false but change to true if a matching task is found
			isSafe = false;
			boolean matchFound;

			//loop through and check all tasks
			for (int i = 0 ; i < nextRun.size(); i++) {
				//a task that can finish and release resources
				foundMatch = nextRun.get(i);
				matchFound = true;
				//check all tasks
				for (int j = 0 ; j < numResourceTypes; j++) {
					//if there are no tasks that are not finished and can safely get resources needed then it is not a safe state
					if ((nextRun.get(i).need[j] > work[j]) && (finish[i]==false)) {
						foundMatch = null;
						matchFound = false;
					}
				}
				//if a task satisifies and can finish, release all resources
				if (matchFound) {
					isSafe = true;
					finish[i] = true;
					numFinished++;
					for ( int k = 0 ; k < numResourceTypes; k++) {
						work[k] += nextRun.get(i).own[k];
					}
				}
			}

			//if all tasks have finished, we are in a safe state
			if (numFinished >= nextRun.size()) {
				return true;
			}
		}

		//not all tasks have finished so we are not in a safe state :(
		return false;

	}

	//used to release resources
	public static void release(Task task) {
		int claim = task.activites.peek().number;

		//if there is a delay, continue next cycle
		if (task.activites.peek().delay > 0) {
			task.activites.peek().delay--;
			delayed.add(task);
		} else {
			//task releases by increasing its needs and decreasing its own. Resources available increase by number-released
			task.need[task.activites.peek().resourceType -1] += claim;
			task.own[task.activites.peek().resourceType -1] -= claim;
			resourcesTemp[task.activites.peek().resourceType -1] += claim;

			//next task
			task.activites.poll();
			delayed.add(task);
		}
	}

	//task is ready to finish!
	public static void terminate(Task task) {
		//if there is a delay, continue next cycle
		if (task.activites.peek().delay > 0 ) {
			task.activites.peek().delay--;
			delayed.add(task);
		} else {
			//task finished "previous" cycle but registers it now
			task.end = cycle;
			numDone++;
		}
	}
}
