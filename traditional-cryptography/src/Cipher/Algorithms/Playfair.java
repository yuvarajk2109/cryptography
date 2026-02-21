package Cipher.Algorithms;

import Cipher.Main;

public class Playfair {

    static String KEYWORD = "apple";
    static String lower = Main.lowercaseAlphabets;
    static StringBuilder[][] keyword_matrix = new StringBuilder[5][5];

    public Playfair() {
        String keyword = getUnique();
        buildKeywordMatrix(keyword);
    }

    static String getUnique() {
        StringBuilder keyword = new StringBuilder();
        int size = KEYWORD.length();
        for (int i=0; i<size; i++) {
            String current = String.valueOf(KEYWORD.charAt(i));
            if (keyword.indexOf(current) == -1) {
                keyword.append(current);
            }
        }
        return keyword.toString();
    }

    static void buildKeywordMatrix(String keyword) {
        StringBuilder already_added = new StringBuilder();
        boolean keyword_used = false;
        int keywordIndex = 0;
        int alphabetsIndex = 0;
        int keyword_size = keyword.length();
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (!keyword_used) {
                    String current = String.valueOf(keyword.charAt(keywordIndex));
                    keyword_matrix[i][j] = new StringBuilder(current);
                    already_added.append(current);
                    keywordIndex++;
                    if (keywordIndex == keyword_size) {
                        keyword_used = true;
                    }
                } else {
                    boolean inserted = false;
                    while (!inserted) {
                        String current = String.valueOf(lower.charAt(alphabetsIndex));
                        if (already_added.indexOf(current) != -1) {
                            alphabetsIndex++;
                        } else {
                            keyword_matrix[i][j] = new StringBuilder(current);
                            already_added.append(current);
                            inserted = true;
                        }
                    }
                }
            }
        }
    }

    public String process(String message) {
        StringBuilder filled_message = new StringBuilder();
        int filled_message_index = 0;
        int size = message.length();
        int alphabetic_count = 0;
        for (int i=0; i<size; i++) {
            String current = String.valueOf(message.charAt(i));
            if (Character.isAlphabetic(current.charAt(0))) {
                alphabetic_count++;
            }
            filled_message.append(current);
            if ((i!=size-1) && (filled_message_index%2==0)) {
                String next = String.valueOf(message.charAt(i+1));
                if (current.equalsIgnoreCase(next)) {
                    filled_message.append("x");
                    alphabetic_count++;
                    filled_message_index++;
                }
            }
            filled_message_index++;
        }
        if (alphabetic_count%2==1) {
            filled_message.append("x");
        }
        return filled_message.toString();
    }

    private int getRow(String character) {
        int row = -1;
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (keyword_matrix[i][j].toString().equalsIgnoreCase(character)) {
                    row = i;
                    break;
                }
            }
        }
        return row;
    }

    private int getCol(String character) {
        int col = -1;
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (keyword_matrix[i][j].toString().equalsIgnoreCase(character)) {
                    col = j;
                }
            }
        }
        return col;
    }

    private int getAlphabetCount(String message) {
        int size = message.length();
        int count = 0;
        for (int i=0; i<size; i++) {
            char current = message.charAt(i);
            if (Character.isAlphabetic(current)) {
                count++;
            }
        }
//        System.out.println("Count:\t\t\t"+count);
        return count;
    }

    public String encrypt(String message) {
        message = process(message);
//        System.out.println("Processed:\t\t"+message);
        StringBuilder sb = new StringBuilder(message);
        int size = getAlphabetCount(message);
//        int index = 0;
        int current_index = 0;
        int next_index = 1;
        while (current_index<size) {
            char current = message.charAt(current_index);
            while (!Character.isAlphabetic(current)) {
                current_index++;
                current = message.charAt(current_index);
            }
            next_index = current_index+1;
            char next = message.charAt(next_index);
//            System.out.println("Size: "+size);
//            System.out.println("Current Block: "+current+next);
//            System.out.println("Current Indices: "+current_index+" "+next_index);
            while (!Character.isAlphabetic(next)) {
                next_index++;
                next = message.charAt(next_index);
            }
            int current_row = getRow(String.valueOf(current));
            int current_col = getCol(String.valueOf(current));
            int next_row = getRow(String.valueOf(next));
            int next_col = getCol(String.valueOf(next));

            char current_encrypted = current;
            char next_encrypted = next;

            if (Character.isAlphabetic(current)) {
                if (current_row == next_row) {
                    current_encrypted = keyword_matrix[current_row][Math.floorMod(current_col+1,5)].charAt(0);
                    next_encrypted = keyword_matrix[next_row][Math.floorMod(next_col+1,5)].charAt(0);
                } else if (current_col == next_col) {
                    current_encrypted = keyword_matrix[Math.floorMod(current_row+1,5)][current_col].charAt(0);
                    next_encrypted = keyword_matrix[Math.floorMod(next_row+1,5)][next_col].charAt(0);
                } else {
                    current_encrypted = keyword_matrix[current_row][next_col].charAt(0);
                    next_encrypted = keyword_matrix[next_row][current_col].charAt(0);
                }

                if (Character.isUpperCase(current)) {
                    current_encrypted = Character.toUpperCase(current_encrypted);
                }
                if (Character.isUpperCase(next)) {
                    next_encrypted = Character.toUpperCase(next_encrypted);
                }
            }

            sb.setCharAt(current_index, current_encrypted);
            sb.setCharAt(next_index, next_encrypted);
            current_index = next_index+1;
        }
        return sb.toString();
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder(message);
        int size = getAlphabetCount(message);
//        int index = 0;
        int current_index = 0;
        int next_index = 1;
        while (current_index<size) {
            char current = message.charAt(current_index);
            while (!Character.isAlphabetic(current)) {
                current_index++;
                current = message.charAt(current_index);
            }
            next_index = current_index+1;
            char next = message.charAt(next_index);
//            System.out.println("Size: "+size);
//            System.out.println("Current Block: "+current+next);
//            System.out.println("Current Indices: "+current_index+" "+next_index);
            while (!Character.isAlphabetic(next)) {
                next_index++;
                next = message.charAt(next_index);
            }
            int current_row = getRow(String.valueOf(current));
            int current_col = getCol(String.valueOf(current));
            int next_row = getRow(String.valueOf(next));
            int next_col = getCol(String.valueOf(next));

            char current_decrypted = current;
            char next_decrypted = next;

            if (Character.isAlphabetic(current)) {
                if (current_row == next_row) {
                    current_decrypted = keyword_matrix[current_row][Math.floorMod(current_col-1,5)].charAt(0);
                    next_decrypted = keyword_matrix[next_row][Math.floorMod(next_col-1,5)].charAt(0);
                } else if (current_col == next_col) {
                    current_decrypted = keyword_matrix[Math.floorMod(current_row-1,5)][current_col].charAt(0);
                    next_decrypted = keyword_matrix[Math.floorMod(next_row-1,5)][next_col].charAt(0);
                } else {
                    current_decrypted = keyword_matrix[current_row][next_col].charAt(0);
                    next_decrypted = keyword_matrix[next_row][current_col].charAt(0);
                }

                if (Character.isUpperCase(current)) {
                    current_decrypted = Character.toUpperCase(current_decrypted);
                }
                if (Character.isUpperCase(next)) {
                    next_decrypted = Character.toUpperCase(next_decrypted);
                }
            }
            sb.setCharAt(current_index, current_decrypted);
            sb.setCharAt(next_index, next_decrypted);
            current_index = next_index+1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Playfair p = new Playfair();
        String original = "Hello";
        String encrypted = p.encrypt(original);
        String decrypted = p.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}