package Asymmetric.RSA;

import java.math.BigInteger;

public class Algorithm {

//    static int p = 7;
//    static int q = 3;
//    public static int n = p*q;
//    static int phi_n = (p-1)*(q-1);

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
        BigInteger bigM = BigInteger.valueOf(M);
        BigInteger bigE = BigInteger.valueOf(e);
        BigInteger bigN = BigInteger.valueOf(n);

        BigInteger bigC = bigM.modPow(bigE, bigN);

//        System.out.println(M+"^"+e+" mod "+n+" = "+bigC);
        return bigC.intValue();
    }

    static int decryptCurrent(int C, int d, int n) {
        BigInteger bigC = BigInteger.valueOf(C);
        BigInteger bigD = BigInteger.valueOf(d);
        BigInteger bigN = BigInteger.valueOf(n);

        BigInteger bigM = bigC.modPow(bigD, bigN);

//        System.out.println(C+"^"+d+" mod "+n+" = "+bigM);
        return bigM.intValue();
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
            }
        }
        int public_key = getInverse(private_key, phi_n);
        return new int[]{private_key, public_key};
    }

    public static void main(String[] args) {

        int p = 97;
        int q = 101;
        int n = p*q;
        int phi_n = (p-1)*(q-1);

        int[] sender_keys = keyGen(phi_n);
        int[] receiver_keys = keyGen(phi_n);

        String message = "Hello";
        String encrypted, decrypted;

        System.out.println("\nConfidentiality");
        System.out.println("Receiver's Public Key: "+receiver_keys[1]);
        System.out.println("Receiver's Private Key: "+receiver_keys[0]);
        encrypted = encrypt(message, receiver_keys[1], n);
        decrypted = decrypt(encrypted, receiver_keys[0], n);
        System.out.println("Message: "+message);
        System.out.println("Encrypted: "+encrypted);
        System.out.println("Decrypted: "+decrypted);
    }
}