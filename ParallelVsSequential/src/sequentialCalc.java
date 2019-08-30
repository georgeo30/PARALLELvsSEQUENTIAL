/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Georgeo
 */
import java.util.Scanner;
import java.lang.Math;
public class sequentialCalc {
    /**
     * The average method is used to trace through the each time step and the matrix within it
     * It will calculate the total average of the x and y components and store it in a vector object that it sent as an argument to writeData method in cloud data
     * It also has a call to the localAverage method within this class to return the wind magnitude of the current element in the matrix
     * The classification is determined for each element using the wind magnitude and the uplift.
     * @param fileR 
     * @param fileW 
     */
    public static void average(String fileR,String fileW){
        
        float xTotal=0;
        float yTotal=0;
        Vector wind=new Vector();
        CloudData obj=new CloudData();
        obj.readData(fileR);
        tick();
        for(int t = 0; t < obj.dimt; t++)
            for(int x = 0; x < obj.dimx; x++)
                for(int y = 0; y < obj.dimy; y++){
                    xTotal+=obj.advection[t][x][y].x;
                    yTotal+=obj.advection[t][x][y].y;
                    localAverage(t,x,y,obj);
                    
                    
		}
        wind.x=xTotal/obj.dim();
        wind.y=yTotal/obj.dim();
        float time=tock();
        System.out.println("Run Took: "+time+ " seconds");
        obj.writeData(fileW, wind);
    }
    /**
     * LocalAverage method is used to calculate the wind magnitude of the current element in the matrix.
     * there are 9 different cases depending on which element we currently are on
     * @param t
     * @param x
     * @param y
     * @param obj
     * @return 
     */
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
    public static long startTime=0;
    /**
     * method used to start the timer
     */
    public static void tick(){
        startTime=System.currentTimeMillis();
    }
    /**
     * Method used to return the time taken in seconds
     * @return 
     */
    public static float tock(){
        return (System.currentTimeMillis()-startTime)/1000.0f;
    }
    /**
     * Main method currently gets the name of the file to read and written to.
     * makes a call to the average method
     * @param args 
     */
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        System.out.println("File in");
        String fileR=in.nextLine();
        System.out.println("File out");
        String fileW=in.nextLine();
        average(fileR,fileW);
        
                
    }
}
