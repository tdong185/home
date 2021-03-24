//package paging;

public class Process {

	int word = 0 ;
	double a;
	double b;
	double c;
	int id;
	int n = 0;
	int frames[];
	int frame;
	int pageFaults = 0;
//	int residency = 0;
//	int loaded;
	
	Process (int m, int k, int s, double a, double b, double c) {
		id = k+1;
		word = (111*(k+1))%s;
		frames = new int[(s/id)];
		
		this.a = a;
		this.b = b;
		this.c = c;
	}

}
