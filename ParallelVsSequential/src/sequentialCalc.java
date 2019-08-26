/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author George
 */
import java.util.Scanner;
public class sequentialCalc {
    
    public static void average(String fileR,String fileW){
        float xTotal=0;
        float yTotal=0;
        Vector wind=new Vector();
        CloudData obj=new CloudData();
        obj.readData(fileR);
        for(int t = 0; t < obj.dimt; t++)
            for(int x = 0; x < obj.dimx; x++)
                for(int y = 0; y < obj.dimy; y++){
                    xTotal+=obj.advection[t][x][y].x;
                    yTotal+=obj.advection[t][x][y].y;
		}
        wind.x=xTotal/obj.dim();
        wind.y=yTotal/obj.dim();
        obj.writeData(fileW, wind);
    }
    
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        System.out.println("File in");
        String fileR=in.nextLine();
        System.out.println("File out");
        String fileW=in.nextLine();
        average(fileR,fileW);
                
    }
}
