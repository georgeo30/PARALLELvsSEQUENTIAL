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
    
    Parallel(CloudData obj,int lo,int size){
        this.obj=obj;
        this.hi=size;
        this.lo=lo;
    }
    
    @Override
    protected Vector compute() {
        
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
			
		      for(int i=lo; i < hi; i++){
		        obj.locate(i, ind);
                        int t=ind[0];
                        int x=ind[1];
                        
                        int y=ind[2];
                        xTotal+=obj.advection[t][x][y].x;
                        
                        yTotal+=obj.advection[t][x][y].y;
                        localAverage(t,x,y,obj);
                        
                      }
                        return new Vector(xTotal,yTotal);
		  }
		  else {
			  Parallel left = new Parallel(obj,lo,(hi+lo)/2);
			  Parallel right= new Parallel(obj,(hi+lo)/2,hi);
			  
			  // order of next 4 lines
			  // essential â€“ why?
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
                                                   
			  return new Vector(rightAns.x+leftAns.x,rightAns.y+leftAns.y);      
		  }
        
    }
    public static void localAverage(int t,int x,int y,CloudData obj){
        float localAvgX=0;
        float localAvgY=0;
        if(x==0&&y==0){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y+1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y+1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y+1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y+1].y)/4;
            
        }
        else if(x==obj.dimx-1&&y==obj.dimy-1){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y-1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y-1].y)/4;
            
        }
        else if(x==0&&y==obj.dimy-1){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y)/4;
            
        }
        else if(x==obj.dimx-1&&y==0){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y+1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y+1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y+1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y+1].y)/4;
            
        }
        else if(x==0){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x+obj.advection[t][x+1][y+1].x+obj.advection[t][x][y+1].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y+obj.advection[t][x+1][y+1].y+obj.advection[t][x][y+1].y)/6;
        }
        else if(y==0){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y+1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y+1].x+obj.advection[t][x+1][y].x+obj.advection[t][x+1][y+1].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y+1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y+1].y+obj.advection[t][x+1][y].y+obj.advection[t][x+1][y+1].y)/6;
        }
        else if(x==obj.dimx-1){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y+1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y+1].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y+1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y+1].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y)/6;
        }
        else if(y==obj.dimy-1){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y)/6;
        }
        else{
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x+obj.advection[t][x-1][y+1].x+obj.advection[t][x][y+1].x+obj.advection[t][x+1][y+1].x)/9;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y+obj.advection[t][x-1][y+1].y+obj.advection[t][x][y+1].y+obj.advection[t][x+1][y+1].y)/9;

        }
        float magnitude=(float)Math.sqrt((localAvgX*localAvgX)+(localAvgY*localAvgY));
        if(Math.abs(obj.convection[t][x][y])>magnitude){
                        obj.classification[t][x][y]=0;
                    }
                    else if((magnitude>0.2)/**&&(magnitude>=Math.abs(obj.convection[t][x][y]))*/){
                        obj.classification[t][x][y]=1;
                    }
                    else{
                        obj.classification[t][x][y]=2;
                    }
    }
   
}
