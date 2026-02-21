package Cipher.Attacks;

import Cipher.Algorithms.Caesar;
import Cipher.Main;

public class Caesar_Brute_Force {

    static String lower = Main.lowercaseAlphabets;
    static String upper = Main.uppercaseAlphabets;

    static void bruteForce(String message) {
        int message_size = message.length();
        System.out.println("\nKey\t\tWord");
        for (int i=0; i<26; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j=0; j<message_size; j++) {
                char current = message.charAt(j);
                if (Character.isAlphabetic(current)) {
                    if (Character.isLowerCase(current)) {
                        int current_index = lower.indexOf(current);
                        int modified_index = Math.floorMod(current_index-i, 26);
                        sb.append(lower.charAt(modified_index));
                    } else if (Character.isUpperCase(current)) {
                        int current_index = upper.indexOf(current);
                        int modified_index = Math.floorMod(current_index-i, 26);
                        sb.append(upper.charAt(modified_index));
                    }
                } else {
                    sb.append(current);
                }
            }
            System.out.println(i+"\t\t"+sb);
        }
    }

    public static void main(String[] args) {
        String original = "hello";
        Caesar c = new Caesar();
        String encrypted = c.encrypt(original);
        System.out.println("Original Message: "+original);
        System.out.println("Encrypted Message: "+encrypted);
        bruteForce(encrypted);
//        System.out.println("Caesar Cipher Key: "+findKey(original, encrypted));
    }
}
