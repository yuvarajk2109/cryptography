package Asymmetric.El_Gamal;

import java.math.BigInteger;
import java.util.Arrays;
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

    public static int[] encryptCurrent(int m, int y_other, int a, int q) {
        int k = (int) ((Math.random() * (q - 1)) + 1);
        BigInteger M = BigInteger.valueOf(m);
        BigInteger Y = BigInteger.valueOf(y_other);
        BigInteger K = BigInteger.valueOf(k);
        BigInteger A = BigInteger.valueOf(a);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger Kc = Y.modPow(K, Q);
        BigInteger C1 = A.modPow(K, Q);
        BigInteger C2 = Kc.multiply(M).mod(Q);
        return new int[]{C1.intValue(), C2.intValue()};
    }

    public static int getInverse(int a, int m) {
        return BigInteger.valueOf(a).modInverse(BigInteger.valueOf(m)).intValue();
    }

    public static int decryptCurrent(int[] CT, int x_mine, int q) {
        BigInteger C1 = BigInteger.valueOf(CT[0]);
        BigInteger C2 = BigInteger.valueOf(CT[1]);
        BigInteger X = BigInteger.valueOf(x_mine);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger Kc = C1.modPow(X, Q);
        int kc = Kc.intValue();
        int kc_inv = getInverse(kc, q);
        BigInteger Kc_inv = BigInteger.valueOf(kc_inv);
        BigInteger M = C2.multiply(Kc_inv).mod(Q);
        return M.intValue();
    }

    public static int[][] encrypt(String message, int a, int q, int public_key) {
        int msg_len = message.length();
        int[][] cipher = new int[2][msg_len];
        for (int i=0; i<msg_len; i++) {
            char m = message.charAt(i);
            int[] c = encryptCurrent(m, public_key, a, q);
            cipher[0][i] = c[0];
            cipher[1][i] = c[1];
        }
        return cipher;
    }

    public static String decrypt(int[][] cipher, int q, int private_key) {
        StringBuilder message = new StringBuilder();
        int cpr_len = cipher[0].length;
        for (int i=0; i<cpr_len; i++) {
            int[] CT = {cipher[0][i], cipher[1][i]};
            int m = decryptCurrent(CT, private_key, q);
            message.append((char) m);
        }
        return message.toString();
    }

    public static void main(String[] args) {
//        System.out.println(findPrimitive(1009));
        System.out.println(isPrimitiveRoot(11, 1009));
        int a = 11;
        int q = 1009;
        int x = 5;
        int m = 17;
        int y = generateY(x, a, q);
        int[] CT = encryptCurrent(m, y, a, q);
        System.out.println("Message: "+m);
        System.out.println("Cipher: "+Arrays.toString(CT));
        System.out.println("Message: "+decryptCurrent(CT, x, q));
        String msg = "Hello";
        int[][] encrypted = encrypt(msg, a, q, y);
        System.out.println("\nMessage: "+msg);
        System.out.println("After encryption\n"+ Arrays.toString(encrypted[0]) +"\t\t"+ Arrays.toString(encrypted[1]));
//        System.out.println(encrypted[0].length() + "\t" + encrypted[1].length());
        String decrypted = decrypt(encrypted, q, x);
        System.out.println("After decryption\n"+decrypted);
    }
}