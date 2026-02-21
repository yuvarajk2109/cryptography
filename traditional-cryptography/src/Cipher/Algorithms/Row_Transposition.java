package Cipher.Algorithms;

public class Row_Transposition {

    static String KEY = "4312567";
    static int KEY_SIZE = KEY.length();

    public String[][] encryptProcess(String message) {
        int message_size = message.length();
        int split = (int) Math.ceil((double) message_size/KEY_SIZE);
        String[][] key_array = new String[split][KEY_SIZE];
        int message_index = 0;
        for (int i=0; i<split; i++) {
            for (int j=0; j<KEY_SIZE; j++) {
                if (message_index == message_size) {
                    key_array[i][j] = "-";
                } else {
                    char current = message.charAt(message_index);
                    key_array[i][j] = String.valueOf(current);
                    message_index++;
                }
            }
        }
        return key_array;
    }

    public String encrypt(String message) {
        StringBuilder sb = new StringBuilder();
        String[][] key_array = encryptProcess(message);
        int message_size = message.length();
        int split = (int) Math.ceil((double) message_size/KEY_SIZE);
        int key_index = 1;
        while (key_index <= KEY_SIZE) {
            int current_key_index = KEY.indexOf(Integer.toString(key_index).charAt(0));
            for (int i=0; i<split; i++) {
                sb.append(key_array[i][current_key_index]);
            }
            key_index++;
        }
        return sb.toString();
    }

    public String[][] decryptProcess(String message) {
        int message_size = message.length();
        int split = (int) Math.ceil((double) message_size/KEY_SIZE);
        String[][] key_array = new String[split][KEY_SIZE];
        int message_index = 0;
        int key_index = 1;
        while (key_index <= KEY_SIZE) {
            int current_key_index = KEY.indexOf(Integer.toString(key_index).charAt(0));
            for (int i=0; i<split; i++) {
                key_array[i][current_key_index] = String.valueOf(message.charAt(message_index));
                message_index++;
            }
            key_index++;
        }
        return key_array;
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder();
        int message_size = message.length();
        int split = (int) Math.ceil((double) message_size/KEY_SIZE);
        String[][] key_array = decryptProcess(message);
        for (int i=0; i<split; i++) {
            for (int j=0; j<KEY_SIZE; j++) {
                char current = key_array[i][j].charAt(0);
                if (current != '-') {
                    sb.append(current);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Row_Transposition rt = new Row_Transposition();
        String original = "Attack postponed until 2 AM!";
        String encrypted = rt.encrypt(original);
        String decrypted = rt.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
