import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Euler {
    static Scanner input = new Scanner(System.in);
    static int DELAY = 1;
    static int getNumber() {
        System.out.print("Number: ");
        return input.nextInt();
    }
    static int getPower() {
        System.out.print("Power: ");
        return input.nextInt();
    }
    static int getModulus() {;
        System.out.print("Modulus: ");
        return input.nextInt();
    }
    static boolean notDivisible(int a, int p) {
        return a%p!=0;
    }

    static int gcd(int a, int b) {
        if (b==0) {
            return a;
        }
        return gcd(b, a%b);
    }

    static int totient(int n) {
        int count = 0;
        for (int i=1; i<n; i++) {
            if (gcd(i, n) == 1) count++;
        }
        return count;
    }
    public static void main(String[] args) throws InterruptedException {
        int a = getNumber();
        int power = getPower();
        int n = getModulus();
        if (gcd(a, n) == 1) {
            int tf = totient(n);
            int lost_power = power/tf;
            int new_power = power%tf;
            int result = (int) Math.pow(a, new_power);
            System.out.println("\nTotient Function: "+tf);
            System.out.println(a+"^"+tf+" is congruent to 1 mod "+n+".");
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+n+" = ("+a+"^"+tf+")^"+lost_power+" * "+a+"^"+new_power);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println("("+a+"^"+tf+")^"+lost_power+" = 1");
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+n+" = "+a+"^"+new_power+" mod "+n);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+n+" = "+result+" mod "+n);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+n+" = "+Math.floorMod(result, n));
        } else {
            System.out.println("Euler's Theorem can't be applied.");
        }
    }
}