import java.util.Arrays;
import java.util.concurrent.*;

public class MergeTask extends RecursiveAction
{
	private static final long serialVersionUID = 1L;
	int[] A;
	int start, end;

	public MergeTask(int[] A, int start, int end)
	{
		this.A = A;
		this.start = start;
		this.end = end;
	}
	
	public void compute()
	{
		int portion = 2000000;
		MergeTask left, right;
		int mid;
		if( (end - start) < portion )
		{
			Arrays.sort(A, start, end);
		}
		else
		{
			mid = (start + end) / 2;
			left = new MergeTask(A, start, mid);
			right = new MergeTask(A, mid + 1, end);
			invokeAll(left, right);
			seqMerge(mid);
			
		}
	}
	
	private void seqMerge(int middle)
	{
		if (A[middle - 1] < A[middle]) 
		{
	         return; // the arrays are already correctly sorted, so we can skip the merge
	    }
		
        int[] copy = new int[end - start];
        System.arraycopy(A, start, copy, 0, copy.length);
        int copyLow = 0;
        int copyHigh = end - start;
        int copyMiddle = middle - start;
        for (int i = start, p = copyLow, q = copyMiddle; i < end; i++) 
        {
           if (q >= copyHigh || (p < copyMiddle && copy[p] < copy[q]) ) 
           {
                A[i] = copy[p++];
           } else 
           {
                A[i] = copy[q++];
           }
        }
   }
}
