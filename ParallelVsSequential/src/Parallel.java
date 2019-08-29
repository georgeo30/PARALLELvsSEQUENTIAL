/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author George
 */
import java.util.concurrent.RecursiveTask;
public class Parallel extends RecursiveTask<Float> {
    CloudData obj;
    int hi;
    int lo;
    static final int SEQUENTIAL_CUTOFF=500;
    
    Parallel(CloudData obj,int lo,int size){
        this.obj=obj;
        this.hi=size;
        this.lo=lo;
    }

    @Override
    protected Float compute() {
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
			  int ans = 0;
		      for(int i=lo; i < hi; i++)
		        ans += arr[i];
		      return ans;
		  }
		  else {
			  Parallel left = new SumArray(arr,lo,(hi+lo)/2);
			  Parallel right= new SumArray(arr,(hi+lo)/2,hi);
			  
			  // order of next 4 lines
			  // essential â€“ why?
			  left.fork();
			  int rightAns = right.compute();
			  int leftAns  = left.join();
			  return leftAns + rightAns;     
		  }
        
    }
}
