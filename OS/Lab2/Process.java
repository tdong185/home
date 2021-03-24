//package scheduling;

public class Process {

	int arrivalTime;
	int B;
	int CPUTimeNeeded;
	int CPUTimeNeedOrig;
	int IO;

	int IOTime=0;
	int waitTime=0;
	
	int status = 0; 
		// 0 = unstarted
		// 1 = ready
		// 2 = running 
		// 3 = blocked
		// 4 = terminated
	int id;
	
	int quantum;
	
	int priority;
	
	int finishingTime;
	
	public Process(int A, int B, int C, int IO, int ID) {
		this.arrivalTime = A;
		this.B = B;
		this.CPUTimeNeeded = C;
		this.CPUTimeNeedOrig = C;
		this.IO = IO;
		this.id = ID;
	}

	public Process() {
		// TODO Auto-generated constructor stub
	}
	
}



/*
public static void RR() {
	Queue<Process> readyQueue = new LinkedList<Process> ();
	int [] blockTime = new int[processes.length]; 
	int [] cpuTime = new int[processes.length];
	boolean currentlyRunning = false;
	int cycle = 0;
	int finishedProcesses = 0;

	System.out.print("Before cycle \t" + cycle + ":\t" );
	for (int i = 0 ; i <processes.length; i++) {
		System.out.print("unstarted 0 \t");
	}

	while (finishedProcesses < processes.length) {

		for (int i = 0 ; i < processes.length; i++) {

			//if blocked, check for how long
			if (processes[i].status == 3) {
				blockTime[i]--;
				if (blockTime[i] == 0 ) {
					processes[i].status = 1;
					//					readyQueue.add(processes[i]);
				}	
			}


			if (processes[i].status == 2) {
				currentlyRunning = true;
				cpuTime[i]--;
				processes[i].CPUTimeNeeded --;

				if (processes[i].CPUTimeNeeded == 0 ) {
					processes[i].status = 4;
					finishedProcesses++;
					currentlyRunning = false;
					processes[i].finishTime = cycle;
				}
				else if (cpuTime[i] == 0) {
					processes[i].status++;
					blockTime[i] = randomOS(processes[i].IO);
					currentlyRunning = false;
				}				
			}			



			//if unstarted, change to ready
			if (processes[i].status == 0 && processes[i].arrivalTime <= cycle) {
				processes[i].status++;
			}


			if (processes[i].status == 1) {
				readyQueue.add(processes[i]);
			}


		}			





		if (!currentlyRunning) {

			Process toRun = new Process();

			if (readyQueue.size() != 0) {
				toRun = readyQueue.poll();

				toRun.status = 2;
				cpuTime[toRun.id] = randomOS(processes[toRun.id].B);
				currentlyRunning = true;

				for (Process x: readyQueue) {
					if (x != toRun) {
						x.waitTime++;
					}
				}
			}
		}


		if (verbose) {


			System.out.println();
			String toPrint = "Before cycle \t" + (cycle+1) + ":\t";

			for (int i = 0; i < processes.length; i++){
				if (processes[i].status == 0) {
					toPrint += "unstarted 0";
				}
				if (processes[i].status == 1) {
					toPrint += "ready 0";
				}
				if (processes[i].status == 2) {
					toPrint += "running " + cpuTime[i];
				}
				if (processes[i].status == 3) {
					toPrint += "blocked " + blockTime[i];
				}

			}

			System.out.println(toPrint);

		}

		cycle++;

	}

}




public static void RR() {
	int cycle = 0;
	int finP = 0;
	int cpuUtil = 0;
	int ioUtil = 0;
	int cpuCutOffwithQ = 2;
	int [] blockTime = new int[processes.length]; 
	int [] cpuTime = new int[processes.length];
	Process currentRunning = null;

	Queue <Process> readyQ = new LinkedList<Process>();

	if (verbose) {
		System.out.print("Before Cycle \t" + cycle + ":\t");
		for (int i = 0; i < processes.length; i++){
			if (i == processes.length) {
				System.out.println("unstarted 0.");
			} else {
				System.out.print("unstarted 0" + "\t");
			}	
		}		
	}

	while (finP <= processes.length) {

		for (int i = 0 ; i < processes.length; i++) {
			if (blockTime[i] == 0 ) {
				processes[i].status = 1;
				readyQ.add(processes[i]);
			}	

			if (processes[i].arrivalTime == cycle) {
				readyQ.add(processes[i]);
				processes[i].status = 1;
			}

			cycle++;
		}

		if (currentRunning != null) {
			currentRunning.CPUTimeNeeded--;
			cpuUtil++;
			// if no process is in the middle of running
		} else if (readyQ.size() != 0) {
			currentRunning = readyQ.poll();
			currentRunning.status = 2;

			if (cpuTime[currentRunning.id] == 0) {
				cpuTime[currentRunning.id] = Math.min(randomOS(currentRunning.B) , currentRunning.CPUTimeNeeded);

				if (cpuTime[currentRunning.id] < 2) {
					cpuCutOffwithQ = cpuTime[currentRunning.id];
				} 

			} else {
				if (cpuTime[currentRunning.id] > 2) {
					cpuCutOffwithQ = 2;
				} else {
					cpuCutOffwithQ = cpuTime[currentRunning.id];
				}

			}

			currentRunning.CPUTimeNeeded--;
			cpuUtil++;
		}

		if (verbose) {
			System.out.print("Before Cycle\t" + cycle + ":");
			for (int j = 0 ; j < processes.length; j++){
				if (processes[j].status == 0){
					System.out.print("unstarted 0" + "\t");
				}else if(processes[j].status == 1){
					System.out.print("ready     0" + "\t");
					processes[j].waitTime++;
				}else if(processes[j].status == 2){
					System.out.print("running   " + cpuCutOffwithQ + "\t");
				}else if(processes[j].status == 3){
					System.out.print("blocked   " + blockTime[processes[j].id] + "\t");
				}else{
					System.out.print("finished  0" + "\t");
				}

			}
			System.out.print("\n");
		}

		for (int i = 0; i < blockTime.length; i++){
			if (blockTime[i] > 0){
				blockTime[i] --;
				processes[i].IOTime++;
				ioUtil ++;
			}
		}


	}


}

 */
