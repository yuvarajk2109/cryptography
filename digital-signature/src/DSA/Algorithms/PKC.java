package DSA.Algorithms;

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

    static int encryptCurrent(int M, int e, int n) {
        BigInteger bigE = BigInteger.valueOf(e);
        BigInteger bigN = BigInteger.valueOf(n);
        BigInteger bigM = BigInteger.valueOf(M);

        BigInteger C = bigM.modPow(bigE, bigN);
//        System.out.println(M+"^"+e+" mod "+n+" = "+C);
        return C.intValue();
    }

    static int decryptCurrent(int C, int d, int n) {
        BigInteger bigD = BigInteger.valueOf(d);
        BigInteger bigN = BigInteger.valueOf(n);
        BigInteger bigC = BigInteger.valueOf(C);
        BigInteger M = bigC.modPow(bigD, bigN);
//        System.out.println(C+"^"+d+" mod "+n+" = "+M);
        return M.intValue();
    }

    public static String encrypt(String message, int e, int n) {
        StringBuilder cipher = new StringBuilder();
        int msg_len = message.length();
        for (int i=0; i<msg_len; i++) {
            char m = message.charAt(i);
            int c = encryptCurrent(m, e, n);
//            System.out.println("Encrypted Number: "+c);
//            System.out.println("Encrypted Character: "+(char) c);
            cipher.append((char) c);
//            cipher.append(c);
        }
        return cipher.toString();
    }

    public static String decrypt(String cipher, int d, int n) {
        StringBuilder message = new StringBuilder();
        int cpr_len = cipher.length();
        for (int i=0; i<cpr_len; i++) {
            char c = cipher.charAt(i);
            int m = decryptCurrent(c, d, n);
//            System.out.println("Decrypted Number: "+m);
//            System.out.println("Decrypted Character: "+(char) m);
            message.append((char) m);
//            message.append(m);
        }
        return message.toString();
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
