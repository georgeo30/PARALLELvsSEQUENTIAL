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
public class Parallel extends RecursiveTask<Vector> {
    CloudData obj;
    int hi;
    int lo;
    static final int SEQUENTIAL_CUTOFF=1000;
    int[] ind=new int[3];
    float xTotal=0;
    float yTotal=0;
    Vector wind=new Vector();
    Parallel(CloudData obj,int lo,int size){
        this.obj=obj;
        this.hi=size;
        this.lo=lo;
    }
    
    @Override
    protected Vector compute() {
        Vector current=new Vector();
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
			
		      for(int i=lo; i < hi; i++){
		        obj.locate(i, ind);
                        int t=ind[0];
                        int x=ind[1];
                        
                        int y=ind[2];
                        xTotal+=obj.advection[t][x][y].x;
                        
                        yTotal+=obj.advection[t][x][y].y;
                        //localAverage(t,x,y,obj);
                        current.x=xTotal;
                        current.y=yTotal;
                      }
                        return current;
		  }
		  else {
			  Parallel left = new Parallel(obj,lo,(hi+lo)/2);
			  Parallel right= new Parallel(obj,(hi+lo)/2,hi);
			  
			  // order of next 4 lines
			  // essential â€“ why?
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
                          float xV=(rightAns.x+leftAns.x);
                          
                          float yV=(rightAns.y+leftAns.y);
                          wind.x=xV;
                          wind.y=yV;
			  return wind;      
		  }
        
    }
    
   
}
