import java.io.*;
import java.util.*;


public class main {     

     
    public static void main(String[] args) {
    //TODO:build the hash table and insert keys using the insertKeyArray function.
    	
    	int listX [] =  {79, 13, 45, 64, 32, 95, 67, 27, 78, 18, 41, 69, 15, 29, 72, 57, 81, 50, 60, 14};
    	int listY [] = {70, 54, 19, 58, 46, 14, 67, 80, 3, 93, 47, 50, 74, 72, 85, 95, 86, 91, 81, 90};
    	
    	Open_Addressing openMapX = new Open_Addressing(10, 0, 590);
    	Open_Addressing openMapY = new Open_Addressing(10,0,1023);
    	
    	Chaining chainX = new Chaining (10,0,590);
    	
    	Chaining chainY = new Chaining (10,0,1023);
    	 	
    		
    		int probCollisionX = openMapX.insertKeyArray(listX);
    		int probCollisionY = openMapY.insertKeyArray(listY);
    		
    		int chainCollisionX = chainX.insertKeyArray(listX);
    		int chainCollisionY = chainY.insertKeyArray(listY);
    		
    		
    		System.out.println("collision resolved by probing for ListX when A = 1018: "+probCollisionX);
    		System.out.println("collision resolved by probing for ListY when A = 683: "+probCollisionY);
    		
    		System.out.println("collision resolved by chaining for List X when A = 1018 : "+chainCollisionX);
    		System.out.println("collision resolved by chaining for List Y when A = 683: "+chainCollisionY);
    	
    	
    }
}