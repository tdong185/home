#include <stdio.h>
#include <stdlib.h>
#include <omp.h>


int main(int argc, char* argv[]) {
  int thread_count = strtol(argv[2],NULL,0);
  int num = strtol(argv[1],NULL,0);
  int floor = (num+1)/2;

  int prime[num - 1];
 
  double tstart = 0.0, ttaken;
	tstart = omp_get_wtime();

	#pragma omp parallel num_threads (thread_count)
  {
    #pragma omp for
    for (int i = 2 ; i <= num ; i++) {
      prime[i-2] = i;
    }

    #pragma omp for
		for (int i = 2 ; i <= floor ; i++) {
      if (prime[i-2] != 0) {
        for (int k = i-1 ; k < num-1 ; k++) {
          if (prime[k] % i == 0) {
  					prime[k] = 0;
  				}
  			}
      }
    }
  }

  ttaken = omp_get_wtime() - tstart;
  printf("Time take for the main part: %f\n", ttaken);

  char output[100] ="";
	FILE *fp;

  sprintf(output,"%d.txt",num);
  fp = fopen(output,"w");

	int previous = 2;
	int rank = 1;
	for (int i = 0; i < num-1; i++) {
		if (prime[i] != 0) {
			fprintf(fp, "%d, %d, %d\n", rank, prime[i], prime[i]-previous);
			rank++;
			previous = prime[i];
		}
	}
	fclose(fp);

}
