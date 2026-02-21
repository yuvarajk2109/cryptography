package Cipher.Algorithms;
import Cipher.Main;

import static Cipher.Main.getInverse;

public class Affine {
    static int m = 26;
    static int b = 3;
    static int a = 11;

    static String lower = Main.lowercaseAlphabets;
    static String upper = Main.uppercaseAlphabets;

    private int handleEncryptIndex(int index) {
        return Math.floorMod((a*index)+b, m);
    }

    public String encrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = sb.length();
        int index;
        for (int i=0; i<size; i++) {
            char current = sb.charAt(i);
            if (Character.isLowerCase(current)) {
                index = handleEncryptIndex(lower.indexOf(current));
                sb.setCharAt(i, lower.charAt(index));
            } else if (Character.isUpperCase(current)) {
                index = handleEncryptIndex(upper.indexOf(current));
                sb.setCharAt(i, upper.charAt(index));
            }
        }
        return sb.toString();
    }

    private int handleDecryptIndex(int index) {
        int a_inv = getInverse(a, m);
        return Math.floorMod((index-b)*a_inv, m);
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = sb.length();
        int index=0;
        for (int i=0; i<size; i++) {
            char current = sb.charAt(i);
            if (Character.isLowerCase(current)) {
                index = handleDecryptIndex(lower.indexOf(current));
                sb.setCharAt(i, lower.charAt(index));
            } else if (Character.isUpperCase(current)) {
                index = handleDecryptIndex(upper.indexOf(current));
                sb.setCharAt(i, upper.charAt(index));
            }
//            System.out.println("Character: "+current+"\tIndex: "+index+"\t"+"String: "+sb);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Affine a = new Affine();
        String original = "zebra!";
        String encrypted = a.encrypt(original);
        String decrypted = a.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}