package Cipher.Algorithms;
import Cipher.Main;

public class Caesar {

    static int KEY = 1;

    static String lower = Main.lowercaseAlphabets;
    static String upper = Main.uppercaseAlphabets;

    private int handleEncryptIndex(int index) {
        return Math.floorMod(index+KEY, 26);
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
        return Math.floorMod(index-KEY, 26);
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = sb.length();
        int index;
        for (int i=0; i<size; i++) {
            char current = sb.charAt(i);
            if (Character.isLowerCase(current)) {
                index = handleDecryptIndex(lower.indexOf(current));
                sb.setCharAt(i, lower.charAt(index));
            } else if (Character.isUpperCase(current)) {
                index = handleDecryptIndex(upper.indexOf(current));
                sb.setCharAt(i, upper.charAt(index));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Caesar c = new Caesar();
        String original = "Hello!";
        String encrypted = c.encrypt(original);
        String decrypted = c.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}