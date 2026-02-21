package DSA_Base.Algorithms;

import java.math.BigInteger;

public class PKC {
    public static boolean isPrime(int n) {
        if (n<2) return false;
        else if (n==2) return true;
        else {
            for (int i=2; i<n; i++)
                if (n%i==0) return false;
            return true;
        }
    }

    public static boolean isDivisor(int p, int q) {
        return (p-1)%q == 0;
    }

    public static int generateH(int p) {
        int h = (int) ((Math.random() * (p - 3)) + 2);
        return h;
    }

    public static int computeG(int p, int q, int h) {
        BigInteger H = BigInteger.valueOf(h);
        BigInteger G = H.pow((p-1)/q);
        return G.intValue();
    }

    public static int generateK(int p) {
        return (int) ((Math.random() * (p - 3)) + 2);
    }

    public static int generateX(int q) {
        return (int) ((Math.random() * (q - 1)) + 1);
    }

    public static int generateSignature(int g, int k, int p, int q, int x) {
        BigInteger G = BigInteger.valueOf(g);
        BigInteger K = BigInteger.valueOf(k);
        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger R = G.modPow(K, P).mod(Q);
        return R.intValue();
    }

    public static int verifySignature() {
        return 1;
    }

    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static int getInverse(int a, int m) {
        int a_inv = 0;
        for (int i = 0; i < m; i++) {
            if ((a*i)%m == 1) {
                a_inv = i;
                break;
            }
        }
        return a_inv;
    }

    public static int[] keyGen(int phi_n) {
        int private_key;
        while (true) {
            private_key = (int) ((Math.random() * (phi_n - 1)) + 1);
            if (gcd(private_key, phi_n) == 1) {
                break;
            } else {
                continue;
            }
        }
        int public_key = getInverse(private_key, phi_n);
        return new int[]{private_key, public_key};
    }
}
