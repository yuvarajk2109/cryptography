package Cipher.Algorithms;

import java.util.HashMap;
import java.util.Map;

public class Monoalphabetic {
    Map<String, String> encryptTable = new HashMap<>();
    Map<String, String> decryptTable = new HashMap<>();

    public Monoalphabetic() {
        encryptTable.put("a", "b");
        encryptTable.put("b", "c");
        encryptTable.put("c", "d");
        encryptTable.put("d", "e");
        encryptTable.put("e", "f");
        encryptTable.put("f", "g");
        encryptTable.put("g", "h");
        encryptTable.put("h", "i");
        encryptTable.put("i", "j");
        encryptTable.put("j", "k");
        encryptTable.put("k", "l");
        encryptTable.put("l", "m");
        encryptTable.put("m", "n");
        encryptTable.put("n", "o");
        encryptTable.put("o", "p");
        encryptTable.put("p", "q");
        encryptTable.put("q", "r");
        encryptTable.put("r", "s");
        encryptTable.put("s", "t");
        encryptTable.put("t", "u");
        encryptTable.put("u", "v");
        encryptTable.put("v", "w");
        encryptTable.put("w", "x");
        encryptTable.put("x", "y");
        encryptTable.put("y", "z");
        encryptTable.put("z", "a");

        for (Map.Entry<String, String> entry : encryptTable.entrySet()) {
            decryptTable.put(entry.getValue(), entry.getKey());
        }
    }

    public String encrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = sb.length();
//        int index;
        for (int i=0; i<size; i++) {
            char current = sb.charAt(i);
            if (Character.isAlphabetic(current)) {
                char encrypted = encryptTable.get(String.valueOf(Character.toLowerCase(current))).charAt(0);
                if (Character.isUpperCase(current)) {
                    encrypted = Character.toUpperCase(encrypted);
                }
                sb.setCharAt(i, encrypted);
            }
        }
        return sb.toString();
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = sb.length();
//        int index;
        for (int i=0; i<size; i++) {
            char current = sb.charAt(i);
            if (Character.isAlphabetic(current)) {
                char decrypted = decryptTable.get(String.valueOf(Character.toLowerCase(current))).charAt(0);
                if (Character.isUpperCase(current)) {
                    decrypted = Character.toUpperCase(decrypted);
                }
                sb.setCharAt(i, decrypted);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Monoalphabetic m = new Monoalphabetic();
        String original = "balAAloon!";
        String encrypted = m.encrypt(original);
        String decrypted = m.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }



}
