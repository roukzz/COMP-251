import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {
      int[] resultArray = new int[2];
       
     // correct bits extraction
     x = bitExtracted(x,size);
     y = bitExtracted(y,size);
       
    // case with size 0
    if (size == 0) {
        return new int [] {0,0};
        }
   //base case 
    if (size == 1) {
        resultArray[0] = x * y;
        resultArray[1]++;
    } else {
        int m = (int) Math.ceil(size / 2.0);
        int a = (int) (x/Math.pow(2,m)); // left half of x
        int b = (int)(x%Math.pow(2, m)); // right half of x
        int c = (int) (y/ Math.pow(2, m)); // left half of y
        int d = (int)(y%Math.pow(2, m));// right half of y
        // parameters for the computation of the product
        int[] e = naive(m, a, c);
        int[] f = naive(m, b, d);
        int[] g = naive(m, b, c);
        int[] h = naive(m, a, d);
        // compute the product
        resultArray[0] = (e[0] << 2 * m) + ((g[0] + h[0]) << m) + f[0];
        // cost of operation of size m
        resultArray[1] += e[1] + f[1] + g[1] + h[1] + 3 * m;
    }
    return resultArray;
}
   
    public static int[] karatsuba(int size, int x, int y) {
        // array containing the product and the cost
        int[] resultArray = new int[2];
        // correct number of bits extracted
        x = bitExtracted(x, size);
        y = bitExtracted(y, size);
        
        if (size == 0) {
            return new int [] {0,0};
        }

    // base case
    if (size == 1) {
        resultArray[0] = x * y;
        resultArray[1] += 1;// cost of base case is 1
        
    } else {
        int m = (int) Math.ceil(size / 2.0);
        int a = (int) (x/Math.pow(2,m)); // left half of x
        int b = (int)(x%Math.pow(2, m)); // right half of x
        int c = (int) (y/ Math.pow(2, m)); // left half of y
        int d = (int)(y%Math.pow(2, m));// right half of y
        
        // computing the parameters useful to get the product
        int[] e = karatsuba(m, a, c);
        int[] f = karatsuba(m, b, d);
        int[] g = karatsuba(m, a - b, c - d);
        
        resultArray[0] = (e[0] << 2 * m) + ((e[0] + f[0] - g[0]) << m) + f[0];// product 
        resultArray[1] += e[1] + f[1] + g[1] + 6 * m;// cost of operations of size m
    }
    return resultArray;
        
    }
    
    // extract number of bits k from the least significant bit of a number
    static int bitExtracted(int number, int k) 
    { 
        return ((int)(number % Math.pow(2, k))) ; 
    } 
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
