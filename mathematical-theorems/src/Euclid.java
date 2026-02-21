import java.util.Scanner;

public class Euclid {
    static void extended_euclid(int m, int b) {
        int A1 = 1, A2 = 0, A3 = m;
        int B1 = 0, B2 = 1, B3 = b;
        int iteration = 0;

        System.out.printf("%n%-10s %-8s %-8s %-8s %-8s %-8s %-8s %-8s%n",
                "Iteration", "Q", "A1", "A2", "A3", "B1", "B2", "B3");
        System.out.printf("%-10d %-8s %-8d %-8d %-8d %-8d %-8d %-8d%n",
                iteration, "-", A1, A2, A3, B1, B2, B3);

        while (true) {
            if (B3 == 0) {
                System.out.println("\nGCD = "+ A3);
                System.out.println("No inverse.");
                return;
            } else if (B3 == 1) {
                System.out.println("\nGCD = "+ B3);
                System.out.println("Inverse = "+Math.floorMod(B2, m));
                return;
            }
            iteration++;
            int Q = A3/B3;
            int T1 = A1-(Q*B1);
            int T2 = A2-(Q*B2);
            int T3 = A3-(Q*B3);
            A1 = B1;
            A2 = B2;
            A3 = B3;
            B1 = T1;
            B2 = T2;
            B3 = T3;

            System.out.printf("%-10d %-8d %-8d %-8d %-8d %-8d %-8d %-8d%n",
                    iteration, Q, A1, A2, A3, B1, B2, B3);
        }
    }

    static Scanner input = new Scanner(System.in);

    static int getNumber() {
        System.out.print("Number: ");
        return input.nextInt();
    }

    static int getModulus() {
        System.out.print("Modulus: ");
        return input.nextInt();
    }

    public static void main(String[] args) {
        int b = getNumber();
        int m = getModulus();
        extended_euclid(m, b);
    }
}
