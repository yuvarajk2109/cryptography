import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Fermat {
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
    public static void main(String[] args) throws InterruptedException {
        int a = getNumber();
        int power = getPower();
        int p = getModulus();
        if (Prime.isPrime(p) && a>0 && notDivisible(a, p)) {
            int p_minus = p-1;
            int lost_power = power/p_minus;
            int new_power = power%p_minus;
            int result = (int) Math.pow(a, new_power);
            System.out.println("\n"+a+"^"+p_minus+" is congruent to 1 mod "+p+".");
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+p+" = ("+a+"^"+p_minus+")^"+lost_power+" * "+a+"^"+new_power);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println("("+a+"^"+p_minus+")^"+lost_power+" = 1");
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+p+" = "+a+"^"+new_power+" mod "+p);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+p+" = "+result+" mod "+p);
            TimeUnit.SECONDS.sleep(DELAY);
            System.out.println(a+"^"+power+" mod "+p+" = "+Math.floorMod(result, p));
        } else {
            System.out.println("Fermat's Theorem can't be applied.");
        }
    }
}
