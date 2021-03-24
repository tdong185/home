#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <mpi.h>

/*** Skeleton for Lab 1 ***/

/***** Globals ******/
float **a; /* The coefficients */
float *x;  /* The unknowns */
float *b;  /* The constants */
float err; /* The absolute relative error */
//float *absErrs;
int num = 0;  /* number of unknowns */
//int numErr = 0;
//int *recvcounts;
//int *displs;
int nit;


/****** Function declarations */
void check_matrix(); /* Check whether the matrix will converge */
void get_input();  /* Read input from file */
/********************************/



/* Function definitions: functions are ordered alphabetically ****/
/*****************************************************************/

/*
   Conditions for convergence (diagonal dominance):
   1. diagonal element >= sum of all other elements of the row
   2. At least one diagonal element > sum of all other elements of the row
 */
void check_matrix()
{
  int bigger = 0; /* Set to 1 if at least one diag element > sum  */
  int i, j;
  float sum = 0;
  float aii = 0;

  for(i = 0; i < num; i++)
  {
    sum = 0;
    aii = fabs(a[i][i]);

    for(j = 0; j < num; j++)
       if( j != i)
	 sum += fabs(a[i][j]);

    if( aii < sum)
    {
      printf("The matrix will not converge.\n");
      exit(1);
    }

    if(aii > sum)
      bigger++;

  }

  if( !bigger )
  {
     printf("The matrix will not converge\n");
     exit(1);
  }
}


/******************************************************/
/* Read input from file */
/* After this function returns:
 * a[][] will be filled with coefficients and you can access them using a[i][j] for element (i,j)
 * x[] will contain the initial values of x
 * b[] will contain the constants (i.e. the right-hand-side of the equations
 * num will have number of variables
 * err will have the absolute error that you need to reach
 */
void get_input(char filename[])
{
  FILE * fp;
  int i,j;

  fp = fopen(filename, "r");
  if(!fp)
  {
    printf("Cannot open file %s\n", filename);
    exit(1);
  }

 fscanf(fp,"%d ",&num);
 fscanf(fp,"%f ",&err);

 /* Now, time to allocate the matrices and vectors */
 a = (float**)malloc(num * sizeof(float*));
 if( !a)
  {
	printf("Cannot allocate a!\n");
	exit(1);
  }

 for(i = 0; i < num; i++)
  {
    a[i] = (float *)malloc(num * sizeof(float));
    if( !a[i])
  	{
		printf("Cannot allocate a[%d]!\n",i);
		exit(1);
  	}
  }

 x = (float *) malloc(num * sizeof(float));
 if( !x)
  {
	printf("Cannot allocate x!\n");
	exit(1);
  }


 b = (float *) malloc(num * sizeof(float));
 if( !b)
  {
	printf("Cannot allocate b!\n");
	exit(1);
  }

 /* Now .. Filling the blanks */

 /* The initial values of Xs */
 for(i = 0; i < num; i++)
	fscanf(fp,"%f ", &x[i]);

 for(i = 0; i < num; i++)
 {
   for(j = 0; j < num; j++)
     fscanf(fp,"%f ",&a[i][j]);

   /* reading the b element */
   fscanf(fp,"%f ",&b[i]);
 }

 fclose(fp);

}



int main(int argc, char *argv[])
{

 int nit = 0; /* number of iterations */


 FILE * fp;
 char output[100] ="";

 if( argc != 2)
 {
   printf("Usage: ./gsref filename\n");
   exit(1);
 }

 /* Read the input file and fill the global data structure above */
 get_input(argv[1]);

 /* Check for convergence condition */
 /* This function will exit the program if the coffeicient will never converge to
  * the needed absolute error.
  * This is not expected to happen for this programming assignment.
  */
 check_matrix();

 int my_rank;
 int comm_sz;
 int totalDone = 0;
 int localDone;
 
 float *xSum;
 xSum = (float *) malloc(num * sizeof(float));
 float *xTemp;
 xTemp = (float *)calloc(num, sizeof(float));


MPI_Init( &argc,&argv );

MPI_Comm_rank (MPI_COMM_WORLD, &my_rank);
MPI_Comm_size(MPI_COMM_WORLD, &comm_sz);



//used to calculate num X's for each process
int quotient = num/comm_sz;
int remainder = num%comm_sz;
int my_first;
int my_last;
int local_n;

if(my_rank < remainder) {
  my_first = my_rank * (quotient + 1);
  local_n = quotient+1;
  my_last = my_first + local_n;
} else {
  my_first = my_rank * quotient + remainder;
  local_n = quotient;
  my_last = my_first + local_n;
}

while (totalDone == 0) {
  int i,j =0;
//printf("hi");

  nit++;
  localDone = 1;

  for(i = my_first; i < my_last; i++) {
    xTemp[i] = b[i];
    for (j = 0 ; j < num; j++){
      if (i!=j){
        xTemp[i] -= a[i][j]*x[j];
      }
    }
    xTemp[i] = xTemp[i]/a[i][i];

    if((fabsf(xTemp[i] - x[i]) / xTemp[i]) > err){
  //    printf("%d",totalDone);
      localDone = 0;
//    } else {
//      localDone = 1;
    }

//    printf("for");

  }

  MPI_Allreduce(xTemp, xSum, num, MPI_FLOAT, MPI_SUM, MPI_COMM_WORLD);
  MPI_Allreduce(&localDone, &totalDone, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);


//  printf("hello\n" );




  for (i = 0 ; i < num ; i++)
        x[i] = xSum[i];
}

int i,j = 0;

if (my_rank ==0) {
//  for( i = 0 ; i < num;  i++) {
  //  printf("%f\n",x[i]);
//  }
  printf("total number of iterations: %d\n", nit);
}



free(xTemp);
free(xSum);

 MPI_Barrier(MPI_COMM_WORLD);
 MPI_Finalize();





 /* Writing results to file */
 sprintf(output,"%d.sol",num);
 fp = fopen(output,"w");
 if(!fp)
 {
   printf("Cannot create the file %s\n", output);
   exit(1);
 }

 for( i = 0; i < num; i++)
   fprintf(fp,"%f\n",x[i]);

// printf("total number of iterations: %d\n", nit);

 fclose(fp);

 exit(0);

}
