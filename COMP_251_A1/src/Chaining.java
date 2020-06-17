import java.io.*;
import java.util.*;

public class Chaining {
    
     public int m; // number of SLOTS 
     public int A; // the default random number
     int w;
     int r;
     public ArrayList<ArrayList<Integer>>  Table;

    // if A==-1, then a random A is generated. else, input A is used.
    protected Chaining(int w, int seed, int A){
         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         this.Table = new ArrayList<ArrayList<Integer>>(m); // table is an ArrayList of ArrayList of size m
         for (int i=0; i<m; i++) {
             Table.add(new ArrayList<Integer>()); // append new arrayList to the master ArrayList(table)
         }
         if (A==-1){
         this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
        }
        else{
            this.A = A;
        }

     }
    /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     //generate a random number in a range (for A)
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;     
    }




    /**Implements the hash function h(k)*/
    public int chain (int key) {
        // TODO: implement this and change the return statement
    	
    	int twoPowerW = power2(this.w) ; // 2^w
    	int index = ((A*key) % twoPowerW) >> (w-r);
        return index;
    }
        
    
    /**Inserts key k into hash table. Returns the number of collisions encountered*/
    public int insertKey(int key){
        //TODO: implement this and change the return statement
    	int collision;
    	// find the the index where to store the key
    	int index= chain(key);
    	// if the index is larger than the master arrayList size, find another index less than m(size of the )
    	while (index >= m) {
    		index = chain (key);	
    	}
    	// get the appropriate list from the master arrayList
    	ArrayList<Integer> slot = this.Table.get(index);
    	slot.add(key);
    	// if the the size is 1 then, it was the first insertion which means no collision
    	if (slot.size() == 1 ) {
    		return 0;
    	} else {
    		
    	collision = slot.size() - 1;
        return collision; // return collisions based on the size of the list
    	}
    }

    
    
    /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
    public int insertKeyArray (int[] keyArray){
        int collision = 0;
        for (int key: keyArray) {
            collision += insertKey(key);
        }
        return collision;
    }


}