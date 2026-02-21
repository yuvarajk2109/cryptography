package Cipher.Algorithms;
import Cipher.Main;
import static Cipher.Main.matrixInverse;

public class Hill {

    static int DIMENSION = 2;

    static int[][] KEY = {
        {3, 2},
        {8, 5}
    };

    static int[][] KEY_INV = new int[DIMENSION][DIMENSION];

    public Hill() {
        KEY_INV = matrixInverse(KEY, DIMENSION);
    }

    static String lower = Main.lowercaseAlphabets;

    private static int getAlphabetCount(String message) {
        int count = 0;
        int size = message.length();
        for (int i=0; i<size; i++) {
            char current = message.charAt(i);
            if (Character.isAlphabetic(current)) {
                count++;
            }
        }
        return count;
    }

    public static int[][] constructMessageMatrix(String message) {
        int size = getAlphabetCount(message);
        int rows = size/2;
        if (size%2==1) {
            rows++;
        }
        int[][] message_matrix = new int[rows][DIMENSION];
        int message_index = 0;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<DIMENSION; j++) {
                if (message_index < size) {
                    char current = Character.toLowerCase(message.charAt(message_index));
                    message_matrix[i][j] = lower.indexOf(current);
                    message_index++;
                } else {
                    message_matrix[i][j] = lower.indexOf("x");
                }
            }
        }
        return message_matrix;
    }

    public static int[][] multiply(int[][] m1, int[][] m2) {
        int r1 = m1.length;
        int c1 = m1[0].length;
        int c2 = m2[0].length;
        int[][] result = new int[r1][c2];
        for (int i=0; i<r1; i++) {
            for (int j=0; j<c2; j++) {
                for (int k=0; k<c1; k++) {
                    result[i][j] += m1[i][k]*m2[k][j];
                }
            result[i][j] = Math.floorMod(result[i][j], 26);
            }
        }
        return result;
    }

    public String encrypt(String message) {
        int[][] message_matrix = constructMessageMatrix(message);
        int[][] encrypted_matrix = multiply(message_matrix, KEY);
//        System.out.println("Message Matrix: "+ Arrays.deepToString(message_matrix));
//        System.out.println("Encrypted Matrix: "+ Arrays.deepToString(encrypted_matrix));
        int rows = encrypted_matrix.length;
        int cols = encrypted_matrix[0].length;
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        int message_index = 0;
        for (int[] encryptedMatrix : encrypted_matrix) {
            for (int j = 0; j < cols; j++) {
                if (message_index == size) break;
                char current = message.charAt(message_index);
                while (!Character.isAlphabetic(current) && message_index < size) {
                    sb.append(current);
                    message_index++;
                }
                if (message_index == size) break;
                char encrypted_letter = lower.charAt(encryptedMatrix[j]);
                if (Character.isUpperCase(current)) {
                    sb.append(Character.toUpperCase(encrypted_letter));
                } else {
                    sb.append(encrypted_letter);
                }
                message_index++;
            }
        }
        return sb.toString();
    }

    public String decrypt(String message) {
        int[][] message_matrix = constructMessageMatrix(message);
        int[][] decrypted_matrix = multiply(message_matrix, KEY_INV);
//        System.out.println("Message Matrix: "+ Arrays.deepToString(message_matrix));
//        System.out.println("Decrypted Matrix: "+ Arrays.deepToString(decrypted_matrix));
        int rows = decrypted_matrix.length;
        int cols = decrypted_matrix[0].length;
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        int message_index = 0;
        for (int[] decryptedMatrix : decrypted_matrix) {
            for (int j = 0; j < cols; j++) {
                if (message_index == size) break;
                char current = message.charAt(message_index);
                while (!Character.isAlphabetic(current) && message_index < size) {
                    sb.append(current);
                    message_index++;
                }
                if (message_index == size) break;
                char decrypted_letter = lower.charAt(decryptedMatrix[j]);
                if (Character.isUpperCase(current)) {
                    sb.append(Character.toUpperCase(decrypted_letter));
                } else {
                    sb.append(decrypted_letter);
                }
                message_index++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Hill h = new Hill();
        String original = "hill!";
        String encrypted = h.encrypt(original);
        String decrypted = h.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
