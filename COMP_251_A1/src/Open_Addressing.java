import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
            
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
            //TODO: implement this function and change the return statement.
        	
        	int towPowerW = power2(this.w) ; // 2^w
        	
        	int offset = (this.A*key)% towPowerW >> (w-r); // h(k) mod 2^w
        	
        	int tableSize= this.m; //2^r
        	
        	int index = (offset+ i) % tableSize; //g(k,i)
        
        	return index;
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            //TODO : implement this and change the return statement.
            int i=0;
            //starter index
            int index = probe(key,i);
            int collision = 0;
            // since it doesn't find an empty slot keep iterating by linear probing until to find one 
            while (this.Table[index] != -1) {
            	
            	collision++; // collision number for one key
            	
            	i++;
            	index = probe(key,i);
            	
            }
            
            this.Table[index]=key;
        	
            return collision;  
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            //TODO
            int collision = 0;
            for (int key: keyArray) {//  For each integer key in the array called KeyArray(keyArray give us the keys to insert)
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
            //TODO: implement this and change the return statement
            int i = 0;
        	int index =  probe(key,i); 
        	
        	while (Table[index] != key) {
        		i++;
        		index = probe(key,i);
        	}
        	
        	Table[index] = -1; 
        	
            return i;
        }
}
