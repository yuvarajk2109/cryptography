package DSA_El_Gamal.Algorithms;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static DSA_El_Gamal.Algorithms.Hash.hash;

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


    public static BigInteger modInverse(BigInteger k, BigInteger mod) {
        return k.modInverse(mod);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

    public static int findPrimitiveRoot(int q) {
        for (int a = 2; a < q; a++) {
            boolean isPrimitive = true;
            for (int i = 1; i < q - 1; i++) {
                if (BigInteger.valueOf(a).modPow(BigInteger.valueOf(i), BigInteger.valueOf(q)).equals(BigInteger.ONE)) {
                    if (i < q - 1) {
                        isPrimitive = false;
                        break;
                    }
                }
            }
            if (isPrimitive) return a;
        }
        throw new RuntimeException("No primitive root found for q=" + q);
    }

    public static int generateX(int q) {
        return (int) ((Math.random() * (q - 2)) + 1);
    }

    public static int computeY(int x, int alpha, int q) {
        return BigInteger.valueOf(alpha).modPow(BigInteger.valueOf(x), BigInteger.valueOf(q)).intValue();
    }


    public static int[] sign(String M, int q, int alpha, int Xa) throws NoSuchAlgorithmException {
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger alphaBI = BigInteger.valueOf(alpha);
        BigInteger XaBI = BigInteger.valueOf(Xa);
        String h = hash(M);

        BigInteger H = new BigInteger(h.getBytes());
        BigInteger qMinus1 = Q.subtract(BigInteger.ONE);

        int k;
        BigInteger K;
        do {
            k = (int) ((Math.random() * (q - 2)) + 1);
            K = BigInteger.valueOf(k);
        } while (!gcd(K, qMinus1).equals(BigInteger.ONE));

        BigInteger S1 = alphaBI.modPow(K, Q);
        BigInteger kInv = K.modInverse(qMinus1);
        BigInteger S2 = kInv.multiply(H.subtract(XaBI.multiply(S1))).mod(qMinus1);

        return new int[]{S1.intValue(), S2.intValue()};
    }

    public static boolean verify(String M, int s1, int s2, int q, int alpha, int Ya) throws NoSuchAlgorithmException {
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger alphaBI = BigInteger.valueOf(alpha);
        BigInteger YaBI = BigInteger.valueOf(Ya);
        BigInteger S1 = BigInteger.valueOf(s1);
        BigInteger S2 = BigInteger.valueOf(s2);
        String h = hash(M);

        BigInteger H = new BigInteger(h.getBytes());

        BigInteger v1 = alphaBI.modPow(H, Q);
        BigInteger v2 = (YaBI.modPow(S1, Q).multiply(S1.modPow(S2, Q))).mod(Q);

//        System.out.println("v1 = " + v1);
//        System.out.println("v2 = " + v2);

        return v1.equals(v2);
    }

    public static void main(String[] args) throws Exception {
        int q = 17;

        int alpha = findPrimitiveRoot(q);

        int Xa = generateX(q);

        BigInteger Ya = BigInteger.valueOf(computeY(Xa, alpha, q));

        System.out.println("Public key: (q=" + q + ", α=" + alpha + ", Ya=" + Ya + ")");
        System.out.println("Private key: Xa=" + Xa);

        String M = "Hello";
        int[] signature = sign(M, q, alpha, Xa);
        int r = signature[0];
        int s = signature[1];

        System.out.println("\nMessage: " + M);
        System.out.println("Signature (r, s): (" + r + ", " + s + ")");

        boolean valid = verify(M, r, s, q, alpha, Ya.intValue());
        System.out.println("\nVerification result: " + (valid));
    }
}