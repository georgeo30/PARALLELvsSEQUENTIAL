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
	static Float sum(CloudData obj,int size){
	  return fjPool.invoke(new Parallel(obj,0,size));
	}
    public static void main(String[] args) {
        CloudData obj=new CloudData();
        Scanner in =new Scanner(System.in);
        System.out.println("File in");
        String fileR=in.nextLine();
        System.out.println("File out");
        String fileW=in.nextLine();
        obj.readData(fileW);
        int size=obj.dim();
        
    }
}
