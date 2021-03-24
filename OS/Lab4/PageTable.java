//package paging;

public class PageTable {

	int s;
	int p;
	int endBuckets[];
	boolean hasReference[];
	int frames[];
	
	PageTable (int s, int p) {
		this.s = s;
		this.p = p;
		endBuckets = new int[(s/p)]; 
		hasReference = new boolean[(s/p)]; 
		
		for ( int i = 0 ; i < (s/p) ; i++) {			
			endBuckets[i] = ((i+1)*p)-1;
			hasReference[i] = false;
		}
		
	}
	
}
