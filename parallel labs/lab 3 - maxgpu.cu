#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cuda.h>

__global__ void getmaxcu(unsigned int * num, unsigned int size, unsigned int * max)
{
//  printf("threadid %d blockid %d blockdim %d\n", threadIdx.x , blockIdx.x , blockDim.x);
    int index = threadIdx.x + blockIdx.x * blockDim.x;

    int counter = size/blockDim.x;

    int start = index*counter;
    int stop = start+counter;
    unsigned int localMax = 0;

    for (int i = start ; i < stop ;i++) {
      if(num[i] > localMax) {
        localMax = num[i];
      }
    }
    max[index] = localMax;
}

//unsigned int getmax(unsigned int *, unsigned int);

int main(int argc, char *argv[])
{
    unsigned int size = 0;  // The size of the array
    unsigned int i;  // loop index
    unsigned int * numbers; //pointer to the array

    if(argc !=2)
    {
       printf("usage: maxseq num\n");
       printf("num = size of the array\n");
       exit(1);
    }

    size = atol(argv[1]);

    numbers = (unsigned int *)malloc(size * sizeof(unsigned int));
    if( !numbers )
    {
       printf("Unable to allocate mem for an array of size %u\n", size);
       exit(1);
    }

    srand(time(NULL)); // setting a seed for the random number generator
    // Fill-up the array with random numbers from 0 to size-1
    for( i = 0; i < size; i++)
       numbers[i] = rand()  % size;

    unsigned int totalSize = size* sizeof(unsigned int);
    unsigned int * num_d;
    cudaDeviceProp prop;
    cudaGetDeviceProperties(&prop, 0);

    int numThreads = prop.maxThreadsPerMultiProcessor;
    int numBlocks = numThreads/prop.maxThreadsPerBlock;
    int numThreadspBlock = numThreads/numBlocks;
    if(size < numThreadspBlock) {
      numBlocks = 1;
      numThreadspBlock = size;
      numThreads = size;
    }

    unsigned int * maxArray;
    maxArray = (unsigned int *)malloc(numThreads * sizeof(unsigned int));
    unsigned int * maxArray_d;

    cudaMalloc((void **) &num_d, totalSize);
    cudaMalloc((void **) &maxArray_d, numThreads*sizeof(unsigned int));
    cudaMemcpy(num_d, numbers, totalSize, cudaMemcpyHostToDevice);
    cudaMemcpy(maxArray_d, maxArray, numThreads*sizeof(unsigned int), cudaMemcpyHostToDevice);

    //getmaxcu<<<numBlocks,numThreadspBlock>>>(num_d, size, maxArray_d);
    getmaxcu<<<(numBlocks+numThreadspBlock-1)/numThreadspBlock,numThreadspBlock>>>(num_d, size, maxArray_d);
    cudaMemcpy(maxArray, maxArray_d, numBlocks*numThreadspBlock*sizeof(unsigned int),cudaMemcpyDeviceToHost);

    unsigned int max = 0 ;
    for (int i = 0 ; i < numBlocks*numThreadspBlock; i++) {
      if (maxArray[i] > max){
        max = maxArray[i];
      }
    }

    printf(" parallel %u\n", max);
//    printf(" serial %u\n", getmax(numbers, size));


    free(numbers);
    cudaFree(maxArray_d);
    cudaFree(num_d);
    exit(0);
}


/*
   input: pointer to an array of long int
          number of elements in the array
   output: the maximum number of the array


unsigned int getmax(unsigned int num[], unsigned int size)
{
  unsigned int i;
  unsigned int max = num[0];

  for(i = 1; i < size; i++)
	if(num[i] > max)
	   max = num[i];

  return( max );

}
*/
