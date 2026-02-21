package Cipher.Algorithms;

import Cipher.Main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Autokey {

    static String keyword = "horlicks";

    static String filename = "files\\autokey_keyword.txt";

    static String lower = Main.lowercaseAlphabets;

    private String process(String message) throws IOException {
        int message_length = message.length();
        int keyword_length = keyword.length();
        StringBuilder sb = new StringBuilder();
        int message_index = 0;
        int keyword_index = 0;
        while (message_index < message_length) {
            if (keyword_index == keyword_length) {
                break;
            }
            char message_char = message.charAt(message_index);
            if (Character.isAlphabetic(message_char)) {
                char keyword_char = keyword.charAt(keyword_index);
                sb.append(keyword_char);
                keyword_index++;
            }
            message_index++;
        }
        int message_key_index = 0;
        while (message_index < message_length) {
            char message_char = message.charAt(message_index);
            if (Character.isAlphabetic(message_char)) {
                char message_key_char = message.charAt(message_key_index);
                while (!Character.isAlphabetic(message_key_char)) {
                    message_key_index++;
                    message_key_char = message.charAt(message_key_index);
                }
                sb.append(message_key_char);
                message_key_index++;
            }
            message_index++;
        }

        FileWriter fw = new FileWriter(filename);
        String processed_keyword = sb.toString();
        fw.write(processed_keyword);
        fw.close();
        return processed_keyword;
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

    public String encrypt(String message) throws IOException {
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

    public String decrypt(String message) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileReader fr = new FileReader(filename);
        StringBuilder keyword = new StringBuilder();
        int file_character;
        while ((file_character=fr.read())!=-1) {
            keyword.append((char) file_character);
        }
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

    public static void main(String[] args) throws IOException {
        Autokey ak = new Autokey();
        String original = "terminate";
        String encrypted = ak.encrypt(original);
        String decrypted = ak.decrypt(encrypted);
        System.out.println("Keyword: "+keyword);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
