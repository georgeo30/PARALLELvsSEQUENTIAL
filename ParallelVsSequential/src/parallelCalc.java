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
	static Vector sum(CloudData obj,int size){
	  return fjPool.invoke(new Parallel(obj,0,size));
	}
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
        for(int i=0;i<5;i++){
            System.gc();
            tick();
            ans=sum(obj,size);
            time=time+tock();
        }
        System.out.println("Run Took: "+time/5+ " seconds");
        writeTooFile(time/5);
        wind.x=(ans.x)/obj.dim();
        wind.y=ans.y/obj.dim();
        //obj.writeData(fileW, wind);
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
     * Write to sequential time to a file method
     * @param value 
     */
    public static void writeTooFile(float value){
        try{
            FileWriter file =new FileWriter("parallelTimes.txt",true);
             file.write("SC: 100 Time: "+value+"");
            file.write(System.getProperty( "line.separator" ));
            file.close();
            
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
