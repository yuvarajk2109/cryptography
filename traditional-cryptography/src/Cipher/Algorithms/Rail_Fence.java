package Cipher.Algorithms;

public class Rail_Fence {

    static int LEVEL = 2;

    public String encrypt(String message) {
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        for (int i=0; i<LEVEL; i++) {
            int index = i;
            while (index < size) {
                char current = message.charAt(index);
                sb.append(current);
                index += LEVEL;
            }
        }
        return sb.toString();
    }

    public String decrypt(String message) {
        StringBuilder sb = new StringBuilder();
        int size = message.length();
        int split = (int) Math.ceil((double) size/LEVEL);
        for (int i=0; i<split; i++) {
            int current_index = i;
            char current = message.charAt(current_index);
            sb.append(current);
            int next_index = current_index+split;
            if (next_index == size) {
                break;
            }
            char next = message.charAt(next_index);
            sb.append(next);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Rail_Fence rf = new Rail_Fence();
        String original = "Meet me at the toga partyy!";
        String encrypted = rf.encrypt(original);
        String decrypted = rf.decrypt(encrypted);
        System.out.println("Original:\t\t" + original);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
