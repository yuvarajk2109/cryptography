package Cipher.Algorithms;

import Cipher.Main;

public class Vigner {

    static String keyword = "horlicks";

    static String lower = Main.lowercaseAlphabets;

    private String process(String message) {
        int message_length = message.length();
        int keyword_length = keyword.length();
        StringBuilder sb = new StringBuilder();
        int keyword_index = 0;
        for (int i=0; i<message_length; i++) {
            char current = message.charAt(i);
            if (Character.isAlphabetic(current)) {
                sb.append(keyword.charAt(keyword_index % keyword_length));
                keyword_index++;
            }
        }
        return sb.toString();
    }

    private char handleCase(char current, char secret) {
        if (Character.isUpperCase(current)) {
            return Character.toUpperCase(secret);
        }
        return secret;
    }

    public int handleEncryptIndex(int index, int keyword_index) {
        return Math.floorMod(index+keyword_index, 26);
    }

    public String encrypt(String message) {
        keyword = process(message);
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        int keyword_index = 0;
        for (int i=0; i<size; i++) {
            char current = message.charAt(i);
            if (Character.isAlphabetic(current)) {
                char key_letter = keyword.charAt(keyword_index);
                int index = handleEncryptIndex(lower.indexOf(Character.toLowerCase(current)), lower.indexOf(key_letter));
                char encrypted = lower.charAt(index);
                sb.append(handleCase(current, encrypted));
                keyword_index++;
            } else {
                sb.append(current);
            }
        }
        return sb.toString();
    }

    public int handleDecryptIndex(int index, int keyword_index) {
        return Math.floorMod(index-keyword_index, 26);
    }

    public String decrypt(String message) {
        keyword = process(message);
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        int keyword_index = 0;
        for (int i=0; i<size; i++) {
            char current = message.charAt(i);
            if (Character.isAlphabetic(current)) {
                char key_letter = keyword.charAt(keyword_index);
                int index = handleDecryptIndex(lower.indexOf(Character.toLowerCase(current)), lower.indexOf(key_letter));
                char decrypted = lower.charAt(index);
                sb.append(handleCase(current, decrypted));
                keyword_index++;
            } else {
                sb.append(message.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Vigner pa = new Vigner();
//        String original = "annAnna university!";
        String original = "This is for Vignere cipher.";
        String encrypted = pa.encrypt(original);
        String decrypted = pa.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
