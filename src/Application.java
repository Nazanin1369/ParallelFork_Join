import java.util.concurrent.*;
import java.util.Arrays;
import java.util.Random;
/**
 * PROGRAM MergeSort using Fork/Join
 * @author Nazanin
 * @version 1.0.0
 */
public class Application
{

	public static int n = 2999999;
	public static int A[] = new int[n];
	public static void main(String[] args) 
	{
		//Initialization
		Random random = new Random();
		long parallel_time, seq_time;
		for(int i = 0; i < n; i++)
		{
			A[i] = random.nextInt(10);
		}
		System.out.println("Length of array : " + n);
		
		/**
		 *  sequential Part
		 **/
		long seq_start = System.currentTimeMillis();
		Arrays.sort(A, 0, A.length - 1);
		long seq_end = System.currentTimeMillis();
		seq_time = seq_end - seq_start;
		System.out.println("Sequential sort time : " + seq_time + " milliseconds.");
		
		/**
		 * Parallel Part
		 **/
		
		MergeTask task = new MergeTask(A, 0, n-1);
		// create thread pool
		ForkJoinPool pool = new ForkJoinPool();
		// start execution of recursive sort task
		long par_start = System.currentTimeMillis();
		pool.invoke(task);
		long par_end = System.currentTimeMillis();
		parallel_time = par_end - par_start;
		System.out.println("\nParallel time : " + parallel_time + " milliseconds.");
		System.out.println("Length of smallest arrays : " + 2000000);
		do
		{
			System.out.printf("\nMain : Thread count : %d\n", pool.getActiveThreadCount());
			System.out.printf("Main : Thread steal : %d\n", pool.getStealCount());
			System.out.printf("Main : Parallelism : %d\n", pool.getParallelism());
			try
			{
				TimeUnit.SECONDS.sleep(5);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}while(! task.isDone());
		pool.shutdown();
	}
}
