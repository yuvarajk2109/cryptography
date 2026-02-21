package Cipher.Attacks;

import Cipher.Algorithms.Caesar;
import Cipher.Main;

public class Caesar_Attack {

    static String lower = Main.lowercaseAlphabets;

    static int findKey(String original, String encrypted) {
        int size = original.length();
        int key = -1;
        for (int i=0; i<size; i++) {
            char current_original = original.charAt(i);
            if (Character.isAlphabetic(current_original)) {
                char current_encrypted = encrypted.charAt(i);
                int original_index = lower.indexOf(current_original);
                int encrypted_index = lower.indexOf(current_encrypted);
                key = Math.floorMod(encrypted_index-original_index, 26);
                break;
            }
        }
        return key;
    }

    public static void main(String[] args) {
        String original = "hello";
        Caesar c = new Caesar();
        String encrypted = c.encrypt(original);
        System.out.println("Original Message: "+original);
        System.out.println("Encrypted Message: "+encrypted);
        System.out.println("Caesar Cipher Key: "+findKey(original, encrypted));
    }
}
