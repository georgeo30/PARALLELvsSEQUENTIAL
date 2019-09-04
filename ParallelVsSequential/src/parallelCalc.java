/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Georgeo
 */
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
public class parallelCalc {
    static final ForkJoinPool fjPool = new ForkJoinPool();
    /**
     * call to start the parallel program
     * @param obj
     * @param size
     * @return 
     */
	static Vector sum(CloudData obj,int size){
	  return fjPool.invoke(new Parallel(obj,0,size));
	}
        /**
         * main method
         * consists of reading the file
         * makes a call to sum
         * to start the parallel part
         * time for parallel part is calculated by getting an average of 500 times.
         * writes the data to the file
         * @param args 
         */
    public static void main(String[] args) {
        Vector wind=new Vector();
        CloudData obj=new CloudData();
        Scanner in =new Scanner(System.in);
        System.out.println("File in");
        String fileR=in.nextLine();
        System.out.println("File out");
        String fileW=in.nextLine();
        obj.readData(fileR);
        int size=obj.dim();
        //System.out.println(size);
        float time=0;
        Vector ans=new Vector();
        for(int i=0;i<500;i++){
            System.gc();
            tick();
            ans=sum(obj,size);
            time=time+tock();
        }
        System.out.println("Run Took: "+time/500+ " seconds");
        writeTooFile(time/500);
        wind.x=(ans.x)/obj.dim();
        wind.y=ans.y/obj.dim();
        obj.writeData(fileW, wind);
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
     * Write to parallel time to a file method
     * @param value 
     */
    public static void writeTooFile(float value){
        try{
            FileWriter file =new FileWriter("parallelTimes.txt",true);
             file.write("SC: 100000 Data: 500 Time: "+value+"");
            file.write(System.getProperty( "line.separator" ));
            file.close();
            
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
