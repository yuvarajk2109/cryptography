package DSA_Schnorr.Algorithms;

import static DSA_Schnorr.Algorithms.Hash.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

    public static int generateA(int p, int q) {
        BigInteger A = BigInteger.TWO;
        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);
        while (!A.modPow(Q, P).equals(BigInteger.ONE)) {
            A = A.add(BigInteger.ONE);
        }
        return A.intValue();
    }

    public static int generateS(int q) {
        return (int) ((Math.random() * (q-1)) + 1);
    }

    public static int computeV(int a, int s, int p) {
        BigInteger A = BigInteger.valueOf(a);
        BigInteger S = BigInteger.valueOf(s);
        BigInteger P = BigInteger.valueOf(p);
        BigInteger V = A.modPow(S, P).modInverse(P);
        return V.intValue();
    }

    public static int generateR(int q) {
        return (int) ((Math.random() * (q-1)) + 1);
    }

    public static int computeX(int a, int r, int p, int q) {
        BigInteger A = BigInteger.valueOf(a);
        BigInteger R = BigInteger.valueOf(r);
        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger X = A.modPow(R, P).mod(Q);
        return X.intValue();
    }

    public static int[] generateSignature(String M, int x, int r, int s, int q) throws NoSuchAlgorithmException {
        String M_x = M + x;
//        System.out.println("Message: "+M_x);
        String hexHash = hash(M_x);
        BigInteger e = new BigInteger(hexHash, 16);
//        System.out.println("E1: "+e);
        BigInteger Q = BigInteger.valueOf(q);
        e = e.mod(Q);
//        System.out.println("E1: "+e);
//        System.out.println("X: "+x);

        BigInteger R = BigInteger.valueOf(r);
        BigInteger S = BigInteger.valueOf(s);
        BigInteger Y = R.add(S.multiply(e)).mod(Q);

        return new int[]{e.intValue(), Y.intValue()};
    }

    public static boolean verifySignature(String M, int a, int y, int v, int e, int p, int q) throws NoSuchAlgorithmException {
        BigInteger A = BigInteger.valueOf(a);
        BigInteger Y = BigInteger.valueOf(y);
        BigInteger V = BigInteger.valueOf(v);
        BigInteger E = BigInteger.valueOf(e);
        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);

        BigInteger Xp = (A.modPow(Y, P).multiply(V.modPow(E, P))).mod(P).mod(Q);

        String Mx = M + Xp.toString();
        BigInteger E2 = new BigInteger(hash(Mx), 16).mod(Q);

//        System.out.println("\nX': " + Xp);
//        System.out.println("E : " + E);
//        System.out.println("E2: " + E2);

        return E.equals(E2);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String M = "Hello";
        int p = 11;
        int q = 5;
        int a = generateA(p, q);
        int s  = generateS(q);
        int v = computeV(a, s, p);
        int r = generateR(q);
        int x = computeX(a, r, p, q);
        int[] signature = generateSignature(M, x, r, s, q);
        System.out.println(Arrays.toString(signature));
        System.out.println(verifySignature(M, a, signature[1], v, signature[0], p, q));
    }
}