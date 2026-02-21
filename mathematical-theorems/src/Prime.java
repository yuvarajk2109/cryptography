import java.util.Scanner;

public class Prime {
    static Scanner input = new Scanner(System.in);
    static int getMin() {
        System.out.print("Min: ");
        return input.nextInt();
    }
    static int getMax() {
        System.out.print("Max: ");
        return input.nextInt();
    }
    public static boolean isPrime(int n) {
        if (n<2) {
            return false;
        } else if (n==2) {
            return true;
        } else {
            for (int i=2; i<n; i++) {
                if (n%i==0) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println("Prime Numbers");
        System.out.println("Specify Range");
        int min = getMin();
        int max = getMax();
        int count = 0;
        System.out.println("\nPrime Numbers from "+min+" to "+max);
        for (int i=min; i<=max; i++) {
            if (isPrime(i)) {
                System.out.print(i+" ");
                count++;
                if (count%10==0) {
                    System.out.println();
                }
            }
        }
    }
}
