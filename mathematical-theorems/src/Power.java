import java.math.BigInteger;
import java.util.Scanner;

public class Power {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Number: ");
        BigInteger n = BigInteger.valueOf(input.nextInt());
        System.out.print("Power: ");
        BigInteger p = BigInteger.valueOf(input.nextInt());
        System.out.print("Modulus: ");
        BigInteger m = BigInteger.valueOf(input.nextInt());
        System.out.println("Result: "+n.modPow(p, m));
    }
}
