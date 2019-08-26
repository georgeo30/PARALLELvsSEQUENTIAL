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
                    localAverage(t,x,y,obj);
		}
        wind.x=xTotal/obj.dim();
        wind.y=yTotal/obj.dim();
        obj.writeData(fileW, wind);
    }
    public static void localAverage(int t,int x,int y,CloudData obj){
        float localAvgX=0;
        float localAvgY=0;
        if(x==0&&y==0){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y+1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y+1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y+1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y+1].y)/4;
        }
        else if(x==obj.dimx&&y==obj.dimy){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y-1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y-1].y)/4;
        }
        else if(x==0&&y==obj.dimy){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x)/4;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y)/4;
        }
        else if(x==obj.dimx&&y==0){
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
        else if(x==obj.dimx){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x-1][y+1].x+obj.advection[t][x-1][y].x+obj.advection[t][x][y+1].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x-1][y+1].y+obj.advection[t][x-1][y].y+obj.advection[t][x][y+1].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y)/6;
        }
        else if(y==obj.dimy){
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x)/6;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y)/6;
        }
        else{
            localAvgX=(obj.advection[t][x][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x+1][y].x+obj.advection[t][x][y-1].x+obj.advection[t][x-1][y-1].x+obj.advection[t][x-1][y].x+obj.advection[t][x+1][y-1].x+obj.advection[t][x][y+1].x+obj.advection[t][x+1][y+1].x)/9;
            localAvgY=(obj.advection[t][x][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x+1][y].y+obj.advection[t][x][y-1].y+obj.advection[t][x-1][y-1].y+obj.advection[t][x-1][y].y+obj.advection[t][x+1][y-1].y+obj.advection[t][x][y+1].y+obj.advection[t][x+1][y+1].y)/9;

        }
        
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
