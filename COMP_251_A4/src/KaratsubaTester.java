
public class KaratsubaTester {
    public static void main(String[] args){
        //int product = Multiply.karatsuba(1, 8, 7)[0];
        //int cost = Multiply.karatsuba(1, 8, 7)[1];
        
       int product = Multiply.naive(3, 6, 6)[0];
        int cost = Multiply.naive(3, 6, 6)[1];
       
      System.out.println("product of naive is = "+ product);
        System.out.println("cost of naive is = " + cost);
        
        int Kproduct = Multiply.karatsuba(3, 6, 6)[0];
        int Kcost = Multiply.karatsuba(3, 6, 6)[1];
        
       System.out.println("product of Karatsuba is = "+ Kproduct);
       System.out.println("cost of Karatsuba is = " + Kcost);
       
        
        
        
//        if (product == 0) {
//            System.out.println("Product is a success!");
//        } else {
//            System.out.println("Product has error!");
//        }
//
//        if (cost == 1) {
//            System.out.println("Cost is a success!");
//        } else {
//            System.out.println("Cost has error!");
//        }
   }
}
