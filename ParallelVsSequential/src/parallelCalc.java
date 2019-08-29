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
import java.util.concurrent.ForkJoinPool;
public class parallelCalc {
    static final ForkJoinPool fjPool = new ForkJoinPool();
	static Vector sum(CloudData obj,int size){
	  return fjPool.invoke(new Parallel(obj,0,size));
	}
    public static void main(String[] args) {
        CloudData obj=new CloudData();
        Scanner in =new Scanner(System.in);
        System.out.println("File in");
        String fileR=in.nextLine();
        System.out.println("File out");
        String fileW=in.nextLine();
        obj.readData(fileR);
        int size=obj.dim();
        //System.out.println(size);
        Vector ans=sum(obj,size);
        System.out.println((ans.x)/obj.dim()+"and "+ans.y/obj.dim());
        
    }
}
