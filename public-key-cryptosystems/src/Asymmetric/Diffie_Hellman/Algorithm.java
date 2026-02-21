package Asymmetric.Diffie_Hellman;

import java.math.BigInteger;
import java.util.HashSet;

public class Algorithm {
    public static boolean isPrime(int n) {
        if (n<2) return false;
        else if (n==2) return true;
        else {
            for (int i=2; i<n; i++)
                if (n%i==0) return false;
            return true;
        }
    }

    static void findPrimefactors(HashSet<Integer> s, int n) {
        for (int i = 2; i <= n; i++) {
            if (isPrime(i) && n % i == 0) {
                s.add(i);
                while (n % i == 0) {
                    n /= i;
                }
            }
        }
    }

    static boolean isPrimitiveRoot(int a, int q) {
        if (!isPrime(q)) return false;
        int phi = q - 1;
        HashSet<Integer> s = new HashSet<>();
        findPrimefactors(s, phi);

        for (Integer factor : s) {
            if (BigInteger.valueOf(a).modPow(BigInteger.valueOf(phi / factor), BigInteger.valueOf(q))
                    .equals(BigInteger.ONE)) {
                return false;
            }
        }

        return true;
    }

    public static int generateX(int q) {
        return (int) ((Math.random() * (q - 1)) + 1);
    }

    public static int generateY(int x, int a, int q) {
        BigInteger bigX = BigInteger.valueOf(x);
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigQ = BigInteger.valueOf(q);
        BigInteger Y = bigA.modPow(bigX, bigQ);
        return Y.intValue();
    }

    public static int[] generateKeys(int a, int q) {
        int x = generateX(q);
        int y = generateY(x, a, q);
        return new int[]{x, y};
    }

    public static int generateSharedKey(int q, int x, int y) {
        BigInteger bigQ = BigInteger.valueOf(q);
        BigInteger bigX = BigInteger.valueOf(x);
        BigInteger bigY = BigInteger.valueOf(y);
        BigInteger K = bigY.modPow(bigX, bigQ);
        return K.intValue();
    }
}