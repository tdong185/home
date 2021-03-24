//package scheduling;

import java.io.*;
import java.util.*;

public class Scheduler {

	static boolean verbose = false;

	public static Scanner scanner;
	public static Process[] processes;


	public static void main(String[] args) throws FileNotFoundException {

		//		File rn = new File ("random-numbers.txt");
		File rn = new File ("random-numbers.txt");
		scanner = new Scanner(rn);

		File inputs = new File(args[0]);

		if (args.length == 1 ) {
		} else {
			inputs =  new File(args[1]);
			verbose = true;
		}

		Scanner inputScan = new Scanner(inputs);
		int numP = inputScan.nextInt();


		processes = new Process[numP];

		for (int i = 0 ; i < numP ; i ++ ) {
			Process p = new Process (inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),i);
			processes[i] = p;
		}
		FCFS();
////////
		rn= new File ("random-numbers.txt");
		scanner = new Scanner(rn);


		if (args.length == 1 ) {
		} else {
			inputs =  new File(args[1]);
			verbose = true;
		}

		inputScan = new Scanner(inputs);
		numP = inputScan.nextInt();


		processes = new Process[numP];

		for (int i = 0 ; i < numP ; i ++ ) {
			Process p = new Process (inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),i);
			processes[i] = p;
		}

		System.out.println("\n");

		RR();
////////
		rn= new File ("random-numbers.txt");
		scanner = new Scanner(rn);


		if (args.length == 1 ) {
		} else {
			inputs =  new File(args[1]);
			verbose = true;
		}

		inputScan = new Scanner(inputs);
		numP = inputScan.nextInt();


		processes = new Process[numP];

		for (int i = 0 ; i < numP ; i ++ ) {
			Process p = new Process (inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),i);
			processes[i] = p;
		}

		System.out.println("\n");
		Uni();
////////		
		rn= new File ("random-numbers.txt");
		scanner = new Scanner(rn);


		if (args.length == 1 ) {
		} else {
			inputs =  new File(args[1]);
			verbose = true;
		}

		inputScan = new Scanner(inputs);
		numP = inputScan.nextInt();


		processes = new Process[numP];

		for (int i = 0 ; i < numP ; i ++ ) {
			Process p = new Process (inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),inputScan.nextInt(),i);
			processes[i] = p;
		}

		System.out.println("\n");
		SJF();
	}

	//getting random number
	public static int randomOS(int U) {
		return ( 1 + ( scanner.nextInt() % U));
	}

	public static void RR() {
		Queue<Process> readyQueue = new LinkedList<Process> ();
		int [] blockTime = new int[processes.length]; 
		int [] cpuTime = new int[processes.length];
		Process currentRunning = new Process();
		currentRunning = null;
		int cycle = 0;
		int processesFin = 0;
		int cpuBurst = 0;
		int CPUutil = 0;
		int IOUtil = 0;

		ArrayList<Process> readySameTime = new ArrayList<Process>();

		for ( int i = 0 ; i < processes.length; i++) {
			blockTime[i] = 0;
			cpuTime[i] = 0;
		}

		System.out.print("The original input was:  " + processes.length + "   ");
		for (int i = 0 ; i < processes.length; i++) {
			System.out.print(processes[i].arrivalTime + " " + processes[i].B
					+ " " + processes[i].CPUTimeNeeded + " " + processes[i].IO + "   ");
		}
		System.out.print("\nThe (sorted) input is:   " + processes.length + "   ");
		ArrayList<Process> p = new ArrayList<Process>(Arrays.asList(processes));
		Collections.sort(p,new ComparatorPArriveSameTime());
		for (int i = 0 ; i < p.size();i++) {
			System.out.print(p.get(i).arrivalTime + " " + p.get(i).B
					+ " " + p.get(i).CPUTimeNeeded + " " + p.get(i).IO + "   ");

			p.get(i).priority = i;

		}

		System.out.println("\n");

		if (verbose) System.out.println("This detailed printout gives the state and remaining burst for each process\n" );

		while (processesFin < processes.length) {

			//print


			if(processesFin != processes.length) {
				//			System.out.print("Before cycle\t" + cycle + ":\t");
				String toPrint = "Before cycle\t" + cycle + ":\t";
				for (int j = 0 ; j < processes.length; j++){
					if (p.get(j).status == 0){
						toPrint += ("unstarted 0" );
					}else if(p.get(j).status == 1){
						toPrint += ("ready     0");
						p.get(j).waitTime++;
					}else if(p.get(j).status == 2){
						toPrint += ("running   " + currentRunning.quantum );
					}else if(p.get(j).status == 3){
						toPrint += ("blocked   " + blockTime[p.get(j).id] );
					}else{
						toPrint += ("terminated  0" );
					}
					if (j == processes.length-1) {
						toPrint += (".");
					} else {
						toPrint += ("\t");
					}
				}


				if (verbose) System.out.print(toPrint + "\n");

				if(toPrint.contains("blocked")) {
					IOUtil++;
				}


			}

			for (int i = 0 ; i < processes.length ; i ++) {

				//if blocked
				if(processes[i].status == 3) {
					processes[i].IOTime++;
					blockTime[i]--;
					if (blockTime[i] == 0) {
						processes[i].status = 1;
						//						readyQueue.add(processes[i]);
						readySameTime.add(processes[i]);
					}
				}

				//if unstarted
				if (processes[i].status == 0 && processes[i].arrivalTime == cycle) {					
					processes[i].status = 1;
					//					readyQueue.add(processes[i]);
					readySameTime.add(processes[i]);
				}
			}

			cycle++;

			if (currentRunning != null) {
				cpuTime[currentRunning.id]--;
				currentRunning.CPUTimeNeeded--;
				currentRunning.quantum--;
				CPUutil++;

				//process is done. terminated
				if (currentRunning.CPUTimeNeeded == 0) {	
					currentRunning.status = 4;
					currentRunning.finishingTime = cycle;
					processesFin++;
					currentRunning = null;

					//cpuBurst is done
				} else if(cpuTime[currentRunning.id] == 0) {
					currentRunning.status = 3;
					blockTime[currentRunning.id] = randomOS(currentRunning.IO);
					//					System.out.print("sdfsd " + currentRunning.status + " " + currentRunning.id);
					currentRunning = null; 	

					//cpuBurst is not done but quantum/time alloted is up
				} else if (currentRunning.quantum == 0) {
					currentRunning.status = 1;
					currentRunning.quantum = 2;

					readySameTime.add(processes[currentRunning.id]);
					//readyQueue.add(processes[currentRunning.id]);

					currentRunning = null;
				}
			}


			//sort readySameTime

			if (readySameTime.size() == 1) {
				readyQueue.add(readySameTime.get(0));
			} else {

				Collections.sort(readySameTime,new ComparatorPArriveSameTime());

				for (int i = 0 ; i < readySameTime.size(); i ++) {
					readyQueue.add(readySameTime.get(i));
				}

			}
			readySameTime.clear();


			if(currentRunning == null) {				
				if (!readyQueue.isEmpty()) {
					currentRunning = readyQueue.poll();
					currentRunning.status = 2;
					currentRunning.quantum = 2;

					if (cpuTime[currentRunning.id] == 0) {
						cpuBurst = randomOS(currentRunning.B);
					} else {
						cpuBurst = cpuTime[currentRunning.id];
					}

					if (cpuBurst <= 2) {
						currentRunning.quantum = cpuBurst;
					}

					cpuTime[currentRunning.id] = cpuBurst;
				} 
			}
		}

		System.out.println("The scheduling algorithm used was Round Robbin" );

		for ( int i = 0 ; i < p.size() ; i ++) {
			System.out.print("\n\nProcess " + i + ":\n\t(A,B,C,IO) = (" + p.get(i).arrivalTime + "," +
					p.get(i).B + "," + p.get(i).CPUTimeNeedOrig + "," + p.get(i).IO + ")");
			System.out.print("\n\tFinishing time: " + (p.get(i).finishingTime-1));
			System.out.print("\n\tTurnaround time: " + ((p.get(i).finishingTime-1) - p.get(i).arrivalTime));
			System.out.print("\n\tI/O time: " + p.get(i).IOTime);
			System.out.print("\n\tWaiting time: " + p.get(i).waitTime);
		}

		System.out.print("\n\nSummary Data:\n\tFinishing time:" + (cycle-1));
		System.out.print("\n\tCPU Utilization: " + (double)((double)CPUutil/(cycle-1)));
		System.out.print("\n\tI/O Utilization: " + (double)((double)IOUtil/(cycle-1)));
		System.out.print("\n\tThroughput: " + (double)((double)p.size()*100/(cycle-1)) + " processes per hundred cycles");

		int totalTurnAround = 0 ;
		int totalWait = 0 ;
		for ( int i = 0 ; i < p.size(); i++) {
			totalTurnAround += (p.get(i).finishingTime - p.get(i).arrivalTime);
			totalWait += p.get(i).waitTime;
		}

		System.out.print("\n\tAverage turnaround time: " + (double)(((double)totalTurnAround/(p.size()))-1));
		System.out.print("\n\tAverage waiting time: " + (double)(((double)totalWait/p.size())) + "\n");

	}

	public static void FCFS() {
		Queue<Process> readyQueue = new LinkedList<Process> ();
		int [] blockTime = new int[processes.length]; 
		int [] cpuTime = new int[processes.length];
		Process currentRunning = new Process();
		currentRunning = null;
		int cycle = 0;
		int processesFin = 0;
		int cpuBurst = 0;
		int CPUutil = 0;
		int IOUtil = 0;

		ArrayList<Process> readySameTime = new ArrayList<Process>();

		for ( int i = 0 ; i < processes.length; i++) {
			blockTime[i] = 0;
			cpuTime[i] = 0;
		}

		System.out.print("The original input was:  " + processes.length + "   ");
		for (int i = 0 ; i < processes.length; i++) {
			System.out.print(processes[i].arrivalTime + " " + processes[i].B
					+ " " + processes[i].CPUTimeNeeded + " " + processes[i].IO + "   ");
		}
		System.out.print("\nThe (sorted) input is:   " + processes.length + "   ");
		ArrayList<Process> p = new ArrayList<Process>(Arrays.asList(processes));
		Collections.sort(p,new ComparatorPArriveSameTime());
		for (int i = 0 ; i < p.size();i++) {
			System.out.print(p.get(i).arrivalTime + " " + p.get(i).B
					+ " " + p.get(i).CPUTimeNeeded + " " + p.get(i).IO + "   ");

			p.get(i).priority = i;

		}

		System.out.println("\n");

		if (verbose) System.out.println("This detailed printout gives the state and remaining burst for each process\n" );

		while (processesFin < processes.length) {

			//print


			if(processesFin != processes.length) {
				String toPrint = "Before cycle\t" + cycle + ":\t";
				for (int j = 0 ; j < processes.length; j++){
					if (p.get(j).status == 0){
						toPrint += ("unstarted 0" );
					}else if(p.get(j).status == 1){
						toPrint += ("ready     0");
						p.get(j).waitTime++;
					}else if(p.get(j).status == 2){
						toPrint += ("running   " + cpuTime[p.get(j).id] );
					}else if(p.get(j).status == 3){
						toPrint += ("blocked   " + blockTime[p.get(j).id] );
					}else{
						toPrint += ("terminated  0" );
					}
					if (j == processes.length-1) {
						toPrint += (".");
					} else {
						toPrint += ("\t");
					}
				}


				if (verbose) System.out.print(toPrint + "\n");

				if(toPrint.contains("blocked")) {
					IOUtil++;
				}


			}

			for (int i = 0 ; i < processes.length ; i ++) {

				//if blocked
				if(processes[i].status == 3) {
					processes[i].IOTime++;
					blockTime[i]--;
					if (blockTime[i] == 0) {
						processes[i].status = 1;
						readySameTime.add(processes[i]);
					}
				}

				//if unstarted
				if (processes[i].status == 0 && processes[i].arrivalTime == cycle) {					
					processes[i].status = 1;
					readySameTime.add(processes[i]);
				}
			}

			cycle++;

			if (currentRunning != null) {
				cpuTime[currentRunning.id]--;
				currentRunning.CPUTimeNeeded--;
				CPUutil++;

				//process is done. terminated
				if (currentRunning.CPUTimeNeeded == 0) {	
					currentRunning.status = 4;
					currentRunning.finishingTime = cycle;
					processesFin++;
					currentRunning = null;

					//cpuBurst is done
				} else if(cpuTime[currentRunning.id] == 0) {
					currentRunning.status = 3;
					blockTime[currentRunning.id] = randomOS(currentRunning.IO);
					currentRunning = null; 	

					//cpuBurst is not done but quantum/time alloted is up
				} 
			}


			//sort readySameTime

			if (readySameTime.size() == 1) {
				readyQueue.add(readySameTime.get(0));
			} else {

				Collections.sort(readySameTime,new ComparatorPArriveSameTime());

				for (int i = 0 ; i < readySameTime.size(); i ++) {
					readyQueue.add(readySameTime.get(i));
				}

			}
			readySameTime.clear();


			if(currentRunning == null) {				
				if (!readyQueue.isEmpty()) {
					currentRunning = readyQueue.poll();
					currentRunning.status = 2;

					if (cpuTime[currentRunning.id] == 0) {
						cpuBurst = randomOS(currentRunning.B);
					} else {
						cpuBurst = cpuTime[currentRunning.id];
					}

					cpuTime[currentRunning.id] = cpuBurst;
				} 
			}
		}

		System.out.println("The scheduling algorithm used was First Come First Served" );

		for ( int i = 0 ; i < p.size() ; i ++) {
			System.out.print("\n\nProcess " + i + ":\n\t(A,B,C,IO) = (" + p.get(i).arrivalTime + "," +
					p.get(i).B + "," + p.get(i).CPUTimeNeedOrig + "," + p.get(i).IO + ")");
			System.out.print("\n\tFinishing time: " + (p.get(i).finishingTime-1));
			System.out.print("\n\tTurnaround time: " + ((p.get(i).finishingTime-1) - p.get(i).arrivalTime));
			System.out.print("\n\tI/O time: " + p.get(i).IOTime);
			System.out.print("\n\tWaiting time: " + p.get(i).waitTime);
		}

		System.out.print("\n\nSummary Data:\n\tFinishing time:" + (cycle-1));
		System.out.print("\n\tCPU Utilization: " + (double)((double)CPUutil/(cycle-1)));
		System.out.print("\n\tI/O Utilization: " + (double)((double)IOUtil/(cycle-1)));
		System.out.print("\n\tThroughput: " + (double)((double)p.size()*100/(cycle-1)) + " processes per hundred cycles");

		int totalTurnAround = 0 ;
		int totalWait = 0 ;
		for ( int i = 0 ; i < p.size(); i++) {
			totalTurnAround += (p.get(i).finishingTime - p.get(i).arrivalTime);
			
			totalWait += p.get(i).waitTime;
		}

		System.out.print("\n\tAverage turnaround time: " + (double)(((double)totalTurnAround/(p.size()))-1));
		System.out.print("\n\tAverage waiting time: " + (double)(((double)totalWait/p.size())) + "\n");
	}

	public static void Uni() {
		Queue<Process> readyQueue = new LinkedList<Process> ();
		int [] blockTime = new int[processes.length]; 
		int [] cpuTime = new int[processes.length];
		Process currentRunning = new Process();
		currentRunning = null;
		int cycle = 0;
		int processesFin = 0;
		int cpuBurst = 0;
		int CPUutil = 0;
		int IOUtil = 0;

		//		ArrayList<Process> readySameTime = new ArrayList<Process>();

		for ( int i = 0 ; i < processes.length; i++) {
			blockTime[i] = 0;
			cpuTime[i] = 0;
		}

		System.out.print("The original input was:  " + processes.length + "   ");
		for (int i = 0 ; i < processes.length; i++) {
			System.out.print(processes[i].arrivalTime + " " + processes[i].B
					+ " " + processes[i].CPUTimeNeeded + " " + processes[i].IO + "   ");
		}
		System.out.print("\nThe (sorted) input is:   " + processes.length + "   ");
		ArrayList<Process> p = new ArrayList<Process>(Arrays.asList(processes));
		Collections.sort(p,new ComparatorPArriveSameTime());
		for (int i = 0 ; i < p.size();i++) {
			System.out.print(p.get(i).arrivalTime + " " + p.get(i).B
					+ " " + p.get(i).CPUTimeNeeded + " " + p.get(i).IO + "   ");

			p.get(i).priority = i;

		}

		System.out.println("\n");

		if (verbose) System.out.println("This detailed printout gives the state and remaining burst for each process\n" );

		while (processesFin < processes.length) {

			//print
			if(processesFin != processes.length) {
				String toPrint = "Before cycle\t" + cycle + ":\t";
				for (int j = 0 ; j < processes.length; j++){
					if (p.get(j).status == 0){
						toPrint += ("unstarted 0" );
					}else if(p.get(j).status == 1){
						toPrint += ("ready     0");
						p.get(j).waitTime++;
					}else if(p.get(j).status == 2){
						toPrint += ("running   " + cpuTime[p.get(j).id]);// + " cputimeleft " + p.get(j).CPUTimeNeeded);
					}else if(p.get(j).status == 3){
						toPrint += ("blocked   " + blockTime[p.get(j).id] );
					}else{
						toPrint += ("terminated  0" );
					}
					if (j == processes.length-1) {
						toPrint += (".");
					} else {
						toPrint += ("\t");
					}
				}
				if (verbose) System.out.print(toPrint + "\n");
				if(toPrint.contains("blocked")) {
					IOUtil++;
				}
			}

			for (int i = 0 ; i < processes.length ; i ++) {

				//if blocked
				if(processes[i].status == 3) {
					CPUutil--;
					processes[i].IOTime++;
					blockTime[i]--;
					processes[i].CPUTimeNeeded++;
					if (blockTime[i] == 0) {
						processes[i].status = 2;
						currentRunning = processes[i];
						cpuTime[currentRunning.id] = randomOS(currentRunning.B)+1;
					}
				}

				//if unstarted
				if (processes[i].status == 0 && processes[i].arrivalTime == cycle) {					
					processes[i].status = 1;
					readyQueue.add(processes[i]);
				}
			}

			cycle++;

			if (currentRunning != null) {
				cpuTime[currentRunning.id]--;
				currentRunning.CPUTimeNeeded--;
				CPUutil++;

				//process is done. terminated
				if (currentRunning.CPUTimeNeeded == 0) {	
					currentRunning.status = 4;
					currentRunning.finishingTime = cycle;
					processesFin++;
					currentRunning = null;

					//cpuBurst is done
				} else if(cpuTime[currentRunning.id] == 0) {
					currentRunning.status = 3;
					blockTime[currentRunning.id] = randomOS(currentRunning.IO);
				} 
			}


			if(currentRunning == null) {				
				if (!readyQueue.isEmpty()) {
					currentRunning = readyQueue.poll();
					currentRunning.status = 2;
					cpuTime[currentRunning.id] = randomOS(currentRunning.B);
				} 
			}
		}

		System.out.println("The scheduling algorithm used was Uniprocessor" );

		for ( int i = 0 ; i < p.size() ; i ++) {
			System.out.print("\n\nProcess " + i + ":\n\t(A,B,C,IO) = (" + p.get(i).arrivalTime + "," +
					p.get(i).B + "," + p.get(i).CPUTimeNeedOrig + "," + p.get(i).IO + ")");
			System.out.print("\n\tFinishing time: " + (p.get(i).finishingTime-1));
			System.out.print("\n\tTurnaround time: " + ((p.get(i).finishingTime-1) - p.get(i).arrivalTime));
			System.out.print("\n\tI/O time: " + p.get(i).IOTime);
			System.out.print("\n\tWaiting time: " + p.get(i).waitTime);
		}

		System.out.print("\n\nSummary Data:\n\tFinishing time:" + (cycle-1));
		System.out.print("\n\tCPU Utilization: " + (double)((double)CPUutil/(cycle-1)));
		System.out.print("\n\tI/O Utilization: " + (double)((double)IOUtil/(cycle-1)));
		System.out.print("\n\tThroughput: " + (double)((double)p.size()*100/(cycle-1)) + " processes per hundred cycles");

		int totalTurnAround = 0 ;
		int totalWait = 0 ;
		for ( int i = 0 ; i < p.size(); i++) {
			totalTurnAround += (p.get(i).finishingTime - p.get(i).arrivalTime);
			totalWait += p.get(i).waitTime;
		}

		System.out.print("\n\tAverage turnaround time: " + (double)(((double)totalTurnAround/(p.size()))-1));
		System.out.print("\n\tAverage waiting time: " + (double)(((double)totalWait/p.size())) + "\n");

	}
	
	public static void SJF() {
		

		ArrayList<Process> readyQueue = new ArrayList<Process> ();
		int [] blockTime = new int[processes.length]; 
		int [] cpuTime = new int[processes.length];
		Process currentRunning = new Process();
		currentRunning = null;
		int cycle = 0;
		int processesFin = 0;
		int cpuBurst = 0;
		int CPUutil = 0;
		int IOUtil = 0;

		ArrayList<Process> readySameTime = new ArrayList<Process>();

		for ( int i = 0 ; i < processes.length; i++) {
			blockTime[i] = 0;
			cpuTime[i] = 0;
		}

		System.out.print("The original input was:  " + processes.length + "   ");
		for (int i = 0 ; i < processes.length; i++) {
			System.out.print(processes[i].arrivalTime + " " + processes[i].B
					+ " " + processes[i].CPUTimeNeeded + " " + processes[i].IO + "   ");
		}
		System.out.print("\nThe (sorted) input is:   " + processes.length + "   ");
		ArrayList<Process> p = new ArrayList<Process>(Arrays.asList(processes));
		Collections.sort(p,new ComparatorPArriveSameTime());
		for (int i = 0 ; i < p.size();i++) {
			System.out.print(p.get(i).arrivalTime + " " + p.get(i).B
					+ " " + p.get(i).CPUTimeNeeded + " " + p.get(i).IO + "   ");

			p.get(i).priority = i;

		}

		System.out.println("\n");

		if (verbose) System.out.println("This detailed printout gives the state and remaining burst for each process\n" );

		while (processesFin < processes.length) {

			//print


			if(processesFin != processes.length) {
				String toPrint = "Before cycle\t" + cycle + ":\t";
				for (int j = 0 ; j < processes.length; j++){
					if (p.get(j).status == 0){
						toPrint += ("unstarted 0" );
					}else if(p.get(j).status == 1){
						toPrint += ("ready     0");
						p.get(j).waitTime++;
					}else if(p.get(j).status == 2){
						toPrint += ("running   " + cpuTime[p.get(j).id] );
					}else if(p.get(j).status == 3){
						toPrint += ("blocked   " + blockTime[p.get(j).id] );
					}else{
						toPrint += ("terminated  0" );
					}
					if (j == processes.length-1) {
						toPrint += (".");
					} else {
						toPrint += ("\t");
					}
					
				}

				
				

				if (verbose) System.out.print(toPrint + "\n");

				if(toPrint.contains("blocked")) {
					IOUtil++;
				}


			}

			for (int i = 0 ; i < processes.length ; i ++) {

				//if blocked
				if(processes[i].status == 3) {
					processes[i].IOTime++;
					blockTime[i]--;
					if (blockTime[i] == 0) {
						processes[i].status = 1;
						readySameTime.add(processes[i]);
					}
				}

				//if unstarted
				if (processes[i].status == 0 && processes[i].arrivalTime == cycle) {					
					processes[i].status = 1;
					readySameTime.add(processes[i]);
				}
			}

			cycle++;

			if (currentRunning != null) {
				cpuTime[currentRunning.id]--;
				currentRunning.CPUTimeNeeded--;
				CPUutil++;

				//process is done. terminated
				if (currentRunning.CPUTimeNeeded == 0) {	
					currentRunning.status = 4;
					currentRunning.finishingTime = cycle;
					processesFin++;
					currentRunning = null;

					//cpuBurst is done
				} else if(cpuTime[currentRunning.id] == 0) {
					currentRunning.status = 3;
					blockTime[currentRunning.id] = randomOS(currentRunning.IO);
					currentRunning = null; 	

					//cpuBurst is not done but quantum/time alloted is up
				} 
			}


			//sort readySameTime

			if (readySameTime.size() == 1) {
				readyQueue.add(readySameTime.get(0));
			} else {

				Collections.sort(readySameTime,new ComparatorPSJF());

				for (int i = 0 ; i < readySameTime.size(); i ++) {
					readyQueue.add(readySameTime.get(i));
				}

			}
			readySameTime.clear();

			Collections.sort(readyQueue,new ComparatorPSJF());

			if(currentRunning == null) {				
				if (!readyQueue.isEmpty()) {
					currentRunning = readyQueue.get(0);
					readyQueue.remove(0);
					currentRunning.status = 2;

					if (cpuTime[currentRunning.id] == 0) {
						cpuBurst = randomOS(currentRunning.B);
					} else {
						cpuBurst = cpuTime[currentRunning.id];
					}

					cpuTime[currentRunning.id] = cpuBurst;
				} 
			}
		}

		System.out.println("The scheduling algorithm used was Shortest Job First" );

		for ( int i = 0 ; i < p.size() ; i ++) {
			System.out.print("\n\nProcess " + i + ":\n\t(A,B,C,IO) = (" + p.get(i).arrivalTime + "," +
					p.get(i).B + "," + p.get(i).CPUTimeNeedOrig + "," + p.get(i).IO + ")");
			System.out.print("\n\tFinishing time: " + (p.get(i).finishingTime-1));
			System.out.print("\n\tTurnaround time: " + ((p.get(i).finishingTime-1) - p.get(i).arrivalTime));
			System.out.print("\n\tI/O time: " + p.get(i).IOTime);
			System.out.print("\n\tWaiting time: " + p.get(i).waitTime);
		}

		System.out.print("\n\nSummary Data:\n\tFinishing time:" + (cycle-1));
		System.out.print("\n\tCPU Utilization: " + (double)((double)CPUutil/(cycle-1)));
		System.out.print("\n\tI/O Utilization: " + (double)((double)IOUtil/(cycle-1)));
		System.out.print("\n\tThroughput: " + (double)((double)p.size()*100/(cycle-1)) + " processes per hundred cycles");

		int totalTurnAround = 0 ;
		int totalWait = 0 ;
		for ( int i = 0 ; i < p.size(); i++) {
			totalTurnAround += (p.get(i).finishingTime - p.get(i).arrivalTime);
			totalWait += p.get(i).waitTime;
		}

		System.out.print("\n\tAverage turnaround time: " + (double)(((double)totalTurnAround/(p.size()))-1));
		System.out.print("\n\tAverage waiting time: " + (double)(((double)totalWait/p.size())) + "\n");

	
		
	}
}


