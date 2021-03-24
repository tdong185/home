//package paging;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Paging {

	public static Scanner scanner;

	public static void main(String[] args) throws FileNotFoundException {

		int m = Integer.parseInt(args[0]);
		int p = Integer.parseInt(args[1]);
		int s = Integer.parseInt(args[2]);
		int j = Integer.parseInt(args[3]);
		int 	n = Integer.parseInt(args[4]);
		String r = args[5].toLowerCase();

		int numFrames = m/p;
		int numPages = s/p;
		int numProcesses;

		File rn = new File ("random-numbers.txt");
		scanner = new Scanner(rn);
		//	double y = scanner.nextInt()/(Integer.MAX_VALUE+1d);


		Queue <Process> processes = new LinkedList<Process>();

		if ( j == 1) {
			numProcesses = 1;
			Process temp = new Process(m,0,s,1,0,0);
			processes.add(temp);

		} else if (j==2) {
			numProcesses = 4;
			for ( int i = 0 ; i < numProcesses ; i++) {
				Process temp = new Process(m,i,s,1,0,0);
				processes.add(temp);
			}
		}else if (j==3) {
			numProcesses = 4;
			for ( int i = 0 ; i < numProcesses ; i++) {
				Process temp = new Process(m,i,s,0,0,0);
				processes.add(temp);
			}
		} else {
			numProcesses = 4;
			processes.add(new Process(m,0,s,.75,.25,0));
			processes.add(new Process(m,1,s,.75,0,.25));
			processes.add(new Process(m,2,s,.75,.125,.125));
			processes.add(new Process(m,3,s,.5,.125,.125));
		}

		//		System.out.println("dsfsd" + numProcesses);

		ArrayList <PageTable> pgTables = new ArrayList<PageTable>();

		for ( int i = 0 ; i < numProcesses ; i++) {
			PageTable temp = new PageTable(s,p);
			pgTables.add(temp);
		}


		int eviction[] = new int[numProcesses*numPages] ;
		int residency[] = new int[numProcesses*numPages];
		int loaded[] = new int[numProcesses*numPages];
		int faults[] = new int[numProcesses*numPages];

		for ( int i = 0 ; i < numProcesses*numPages;i++) {
			eviction[i] = 0 ;
			residency[i] = 0 ;
			loaded[i] = 0 ;
			faults[i] = 0;
		}

		int frameTableFilled = 0;
		ProcessPage frameTable[];
		int counter = numFrames-1;
		//		System.out.print("numframes" + numFrames);
		frameTable = new ProcessPage[numFrames];
		for ( int i = 0 ; i < numFrames; i ++) {
			frameTable[i] = null;
		}

		int time = 1;


		if (r.contains("random")) {

			while (processes.size()!=0) {
				int q = 3;
				while ( q > 0 ) {

					//do stuff here
					for ( int i  = 0 ; i < numPages ;i ++) {
						if ( processes.peek().word <= pgTables.get(processes.peek().id-1).endBuckets[i]) {
							if (pgTables.get(processes.peek().id-1).hasReference[i]) {
								//hit
				//				System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
				//						+ i + ") at time "+time + ": Hit in frame " + processes.peek().frames[i]);


								i = numPages;

							} else {
								//page fault
								//	System.out.println("faults " +( processes.peek().id));
								faults[processes.peek().id-1]++;
								if (frameTableFilled == numFrames) {
									//do page eviction

									int randomNumReplacement = scanner.nextInt()%numFrames;

			//						System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
			//								+ i +  ") at time "+time + ": Fault, evicting page " +  frameTable[randomNumReplacement].page +
			//								" of " + (frameTable[randomNumReplacement].process+1) +
			//								" from frame " + frameTable[randomNumReplacement].frame+ " .");

									loaded[(processes.peek().id-1)*numPages+i] = time;

									residency[frameTable[randomNumReplacement].process*numPages+frameTable[randomNumReplacement].page] += (time - loaded[frameTable[randomNumReplacement].process*numPages+frameTable[randomNumReplacement].page]);

			//						System.out.println("evict " + frameTable[randomNumReplacement].process+frameTable[randomNumReplacement].page);
									eviction[frameTable[randomNumReplacement].process]++;

									pgTables.get(frameTable[frameTable[randomNumReplacement].frame].process).hasReference[frameTable[frameTable[randomNumReplacement].frame].page] = false;

									ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
									tempPP.frame = frameTable[randomNumReplacement].frame;

									frameTable[frameTable[randomNumReplacement].frame] = tempPP;

									processes.peek().frames[i] =
											frameTable[randomNumReplacement].frame;

									pgTables.get(processes.peek().id-1).hasReference[i] = true;


									//		processes.peek().loaded = time;

									i = numPages;

								} else {
				//					System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
				//							+ i +  ") at time "+time + ": Fault, using free frame " + counter + ".");

									pgTables.get(processes.peek().id-1).hasReference[i] = true;
									ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
									tempPP.start = time-1;
									tempPP.frame = counter;

									frameTable[counter] = tempPP;
									processes.peek().frames[i] = counter;
									//processes.peek().loaded = time;
									loaded[(processes.peek().id-1)*numPages+i] = time;

									counter--;
									frameTableFilled++;
									i = numPages;


								}
							}
						}
					}


					nextWord(processes.peek(),s);
					time++;

					processes.peek().n++;
					if (processes.peek().n == n) {
						q = -1;
					}
					q--;
				}

				if (processes.peek().n==n) {
					processes.poll();
				} else {
					Process temp = processes.peek();
					processes.poll();
					processes.add(temp);
				}

			}


		}



		if (r.contains("fifo")) {

			Queue <ProcessPage> Fifo = new LinkedList<ProcessPage>();


			while (processes.size()!=0) {
				int q = 3;
				while ( q > 0 ) {

					//do stuff here
					for ( int i  = 0 ; i < numPages ;i ++) {
						if ( processes.peek().word <= pgTables.get(processes.peek().id-1).endBuckets[i]) {
							if (pgTables.get(processes.peek().id-1).hasReference[i]) {
								//hit
								//					System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
								//							+ i + ") at time "+time + ": Hit in frame " + processes.peek().frames[i]);


								i = numPages;

							} else {
								//page fault

								faults[processes.peek().id-1]++;
								if (frameTableFilled == numFrames) {
									//do page eviction



									//					System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
									//							+ i +  ") at time "+time + ": Fault, evicting page " +
									//							//i + " of " + processes.peek().id + " from frame " + longestI+".");
									//							Fifo.peek().page
									//							+ " of " + (Fifo.peek().process+1) +
									//							" from frame " +Fifo.peek().frame+ " .");

									eviction[Fifo.peek().process]++;

									loaded[(processes.peek().id-1)*numPages + i] = time;

									residency[Fifo.peek().process*numPages+Fifo.peek().page] += (time - loaded[Fifo.peek().process*numPages+Fifo.peek().page]);






									pgTables.get(frameTable[Fifo.peek().frame].process).hasReference[frameTable[Fifo.peek().frame].page] = false;


									ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
									tempPP.frame = Fifo.peek().frame;

									frameTable[Fifo.peek().frame] = tempPP;
									processes.peek().frames[i] = Fifo.peek().frame;

									pgTables.get(processes.peek().id-1).hasReference[i] = true;

									Fifo.poll();
									Fifo.add(tempPP);

									/*
										pgTables.get(frameTable[longestI].process).hasReference[frameTable[longestI].page] = false;


										ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
										tempPP.start = time-1;

										frameTable[longestI] = tempPP;

										pgTables.get(processes.peek().id-1).hasReference[i] = true;


									 */


									i = numPages;

								} else {
									//				System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
									//						+ i +  ") at time "+time + ": Fault, using free frame " + counter + ".");

									loaded[(processes.peek().id-1)*numPages+i] = time;

									pgTables.get(processes.peek().id-1).hasReference[i] = true;
									ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
									tempPP.start = time-1;
									tempPP.frame = counter;

									frameTable[counter] = tempPP;
									processes.peek().frames[i] = counter;

									Fifo.add(tempPP);

									counter--;
									frameTableFilled++;
									i = numPages;
								}
							}
						}
					}


					nextWord(processes.peek(),s);
					time++;

					processes.peek().n++;
					if (processes.peek().n == n) {
						q = -1;
					}
					q--;
				}

				if (processes.peek().n==n) {
					processes.poll();
				} else {
					Process temp = processes.peek();
					processes.poll();
					processes.add(temp);
				}

			}


		}





		if (r.contains("lru")) {
			while (processes.size()!=0) {
				int q = 3;
				while ( q > 0 ) {

					//do stuff here
					for ( int i  = 0 ; i < numPages ;i ++) {
						if ( processes.peek().word <= pgTables.get(processes.peek().id-1).endBuckets[i]) {
							if (pgTables.get(processes.peek().id-1).hasReference[i]) {
								//hit
								//				System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
								//						+ i + ") at time "+time + ": Hit in frame " + processes.peek().frames[i]);



								if (frameTable[processes.peek().frames[i]] != null ) {
									frameTable[processes.peek().frames[i]].start = time;
									//System.out.println("reached " + frameTable[processes.peek().frames[i]].start + " iterator " + processes.peek().frames[i]);

								}

								i = numPages;

							} else {
								//page fault

								faults[processes.peek().id-1]++;

								int longest = time;
								int longestI = 0;

								if (frameTableFilled == numFrames) {
									//do page eviction

									if (r.contains("lru")) {

										for ( int z = 0 ; z < numFrames; z++) {
											if (frameTable[z].start < longest) {
												longest = frameTable[z].start;
												longestI = z;
											}
										}

										//							System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
										//									+ i +  ") at time "+time + ": Fault, evicting page " +
										//									//i + " of " + processes.peek().id + " from frame " + longestI+".");
										//									frameTable[longestI].page + " of " + (frameTable[longestI].process+1) + " from frame " +longestI+ " .");

										eviction[frameTable[longestI].process]++;
										loaded[(processes.peek().id-1)*numPages+i] = time;

										residency[frameTable[longestI].process*numPages+frameTable[longestI].page] += (time - loaded[frameTable[longestI].process*numPages+frameTable[longestI].page]);

										//	System.out.println(((processes.peek().id-1)*numPages+i) + " " + residency[frameTable[longestI].process*numPages+frameTable[longestI].page]);

										pgTables.get(frameTable[longestI].process).hasReference[frameTable[longestI].page] = false;


										ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
										tempPP.start = time-1;

										frameTable[longestI] = tempPP;

										pgTables.get(processes.peek().id-1).hasReference[i] = true;

									}


									processes.peek().frames[i] = longestI;
									i = numPages;

								} else {
									//						System.out.println(processes.peek().id + " " + "references word " + processes.peek().word + " (page "
									//								+ i +  ") at time "+time + ": Fault, using free frame " + counter + ".");

									//loaded[processes.peek().id-1] = time;
									loaded[(processes.peek().id-1)*numPages+i] = time;

									pgTables.get(processes.peek().id-1).hasReference[i] = true;
									ProcessPage tempPP = new ProcessPage(processes.peek().id-1, i);
									tempPP.start = time-1;

									frameTable[counter] = tempPP;
									processes.peek().frames[i] = counter;

									counter--;
									frameTableFilled++;
									i = numPages;
								}
							}
						}
					}


					nextWord(processes.peek(),s);
					time++;

					processes.peek().n++;
					if (processes.peek().n == n) {
						q = -1;
					}
					q--;
				}

				if (processes.peek().n==n) {
					processes.poll();
				} else {
					Process temp = processes.peek();
					processes.poll();
					processes.add(temp);
				}

			}
		}

		System.out.println("The machine size is "+m +".\nThe page size is "+ p+".\nThe process size is "+s+".\nThe job mix number is "+
				j+".\nThe number of references per process is "+n+".\nThe replacement algorithm is "+r+".\nThe level of debugging output is 0."
				);

		System.out.println();



		int totalFaults = 0;
		double totalRes=0;
		double totalRes1=0;

		ArrayList <Integer> faultsPrint = new ArrayList<Integer>();
		ArrayList <Double> resPrint = new ArrayList<Double>();

		for (int i = 0 ; i < numProcesses;i++) {
			faultsPrint.add(0);
			resPrint.add(0.0);

		}

		for (int i = 0 ; i < numProcesses; i++ ) {

			for (int k = 0 ; k < numProcesses*numPages; k++) {
				if (k/numPages == i) {
					//	System.out.println(faults[k] +" fautls k" + " i " +i);
					//	int z = faultsPrint.get(i) + faults[k];
					double y = resPrint.get(i) + residency[k];
					//	faultsPrint.set(i,z);
					resPrint.set(i,y);
					//			k = numProcesses;
				}
			}

		}

		for ( int i = 0; i < numProcesses*numPages;i++) {
			totalFaults+=faults[i];
			totalRes+= (double)(residency[i]);
			totalRes1+= (double)(eviction[i]);
		}



		for (int i = 0 ; i < numProcesses;i++) {
			if (resPrint.get(i) == 0 ) {
				System.out.println("Process " + (i+1) +" had "+  faults[i] +" faults. With no evictions, the average residence is undefined.");
			}
			else System.out.println("Process " + (i+1) +" had "+ faults[i] +" faults and " + resPrint.get(i)/eviction[i] + " average residency.");
		}

		if ((totalRes) == 0.0) {
			System.out.println("The total number of faults is " + totalFaults+".  With no evictions, the overall average residence is undefined.");
		}
		else System.out.println("The total number of faults is " + totalFaults+" and the overall average residency is "+ (totalRes/totalRes1));
	}




	public static void nextWord (Process p, int s) {
		int y = scanner.nextInt();
		//	System.out.println(y);
		double x= y/(Integer.MAX_VALUE+1d);
		//	System.out.println(x);
		if ( x < p.a) {
			p.word = (p.word + 1 ) % s;
		} else if ( x < (p.a + p.b)) {
			p.word = (p.word - 5 + s ) % s;
		} else if ( x < (p.a + p.b + p.c)) {
			p.word = (p.word + 4 ) % s;
		} else {
			p.word = (int) (scanner.nextInt() % s);
			//System.out.println("special "+p.word);
		}
	}

}
