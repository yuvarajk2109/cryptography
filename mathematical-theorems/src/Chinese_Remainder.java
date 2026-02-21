import java.util.Scanner;

public class Chinese_Remainder {

    static Scanner input = new Scanner(System.in);

    static int[] getA(int k) {
        int a[] = new int[k];
        System.out.println("\nPositive Integers a1, a2, ... , ak");
        for (int i=0; i<k; i++) {
            while (true) {
                System.out.print("a" + (i + 1) + ": ");
                int c = input.nextInt();
                if (c<0) {
                    System.out.println("Enter a positive integer.");
                    continue;
                }
                a[i] = c;
                break;
            }
        }
        return a;
    }

    static int[] getM(int k) {
        int m[] = new int[k];
        System.out.println("\nPairwise Relatively Prime Integers m1, m2, ... , mk");
        for (int i=0; i<k; i++) {
            System.out.print("m"+(i+1)+": ");
            int c = input.nextInt();
            m[i] = c;
        }
        return m;
    }

    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static boolean pairwiseRelativelyPrime(int k, int[] m) {
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                if (i!=j) {
                    if (gcd(m[i], m[j]) != 1)
                        return false;
                }
            }
        }
        return true;
    }

    static void printCongruence(int k, int[] a, int[] m) {
        System.out.println("\nSimultaneous Congruences");
        for (int i=0; i<k; i++) {
            System.out.println("x ≅ "+a[i]+" mod "+m[i]);
        }
    }

    static int findM(int k, int[] m) {
        int M = 1;
        for (int i=0; i<k; i++) {
            M*=m[i];
        }
        return M;
    }

    static int[] findZ(int k, int M, int[] m) {
        int[] z = new int[k];
        for (int i=0; i<k; i++) {
            z[i] = M/m[i];
        }
        return z;
    }

    public static int findInverse(int a, int m) {
        int a_inv = 0;
        for (int i = 0; i < m; i++) {
            if ((a*i)%m == 1) {
                a_inv = i;
                break;
            }
        }
        return a_inv;
    }

    static int[] findY(int k, int[] z, int[] m) {
        int[] y = new int[k];
        for (int i=0; i<k; i++) {
            y[i] = findInverse(z[i], m[i]);
        }
        return y;
    }

    static int[] findW(int k, int M, int[] y, int[] z) {
        int[] w = new int[k];
        for (int i=0; i<k; i++) {
            w[i] = Math.floorMod(y[i]*z[i], M);
        }
        return w;
    }

    static int findX(int k, int M, int[] a, int[] w) {
        int x = 0;
        for (int i=0; i<k; i++) {
            x += (a[i]*w[i]);
        }
        return Math.floorMod(x, M);
    }

    static int ChineseRemainderTheorem(int k, int[] a, int[] m) {
        int M = findM(k, m);
        int[] z = findZ(k, M, m);
        int[] y = findY(k, z, m);
        int[] w = findW(k, M, y, z);
        int x = findX(k, M, a, w);
        return x;
    }

    public static void main(String[] args) {
        System.out.print("No. of Integers: ");
        int k = input.nextInt();
        int a[] = getA(k);
        int m[] = getM(k);
        if (pairwiseRelativelyPrime(k, m)) {
            printCongruence(k, a, m);
            int x = ChineseRemainderTheorem(k, a, m);
            System.out.println("\nx = "+x);
        } else {
            System.out.println("\nChinese Remainder Theorem is NOT applicable, as m1, m2, ..., mk are NOT pariwise relatively prime.");
        }

    }
}