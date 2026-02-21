package Cipher.Attacks;

import Cipher.Algorithms.Affine;
import Cipher.Main;

import static Cipher.Main.getInverse;

public class Affine_Attack {

    static int m = 26;

    static String lower = Main.lowercaseAlphabets;

    static int findKeyA(char o1, char e1, char o2, char e2) {
        int o_index1 = lower.indexOf(o1);
        int e_index1 = lower.indexOf(e1);
        int o_index2 = lower.indexOf(o2);
        int e_index2 = lower.indexOf(e2);

        int x = o_index1-o_index2;
        int y = e_index1-e_index2;

        if (y>0 && x<0) {
            y = -y;
            x = -x;
        }

        int x_inv = getInverse(x, m);

        return Math.floorMod(y*x_inv, m);
    }

    static int findKeyB(char o, char e, int a) {
        int x = lower.indexOf(o);
        int y = lower.indexOf(e);
        int b = Math.floorMod(y-(x*a), m);
        return b;
    }

    static int[] findKeys(String original, String encrypted) {
        int size = original.length();
        int[] keys = new int[2];
//        int key = -1;
        char original_1 = '~', encrypted_1 = '~', original_2 = '~', encrypted_2 = '~';
        for (int i=0; i<size; i++) {
            if (original_1=='~') {
                original_1 = original.charAt(i);
                if (Character.isAlphabetic(original_1)) {
                    encrypted_1 = encrypted.charAt(i);
                } else {
                    original_1 = '~';
                }
            } else {
                original_2 = original.charAt(i);
                if (Character.isAlphabetic((original_2))) {
                    encrypted_2 = encrypted.charAt(i);
                    break;
                }
            }
        }
        original_1 = Character.toLowerCase(original_1);
        encrypted_1 = Character.toLowerCase(encrypted_1);
        original_2 = Character.toLowerCase(original_2);
        encrypted_2 = Character.toLowerCase(encrypted_2);
        keys[0] = findKeyA(original_1, encrypted_1, original_2, encrypted_2);
        keys[1] = findKeyB(original_1, encrypted_1, keys[0]);
        return keys;
    }

    public static void main(String[] args) {
        String original = "hello";
        Affine a = new Affine();
        String encrypted = a.encrypt(original);
        System.out.println("Original Message: "+original);
        System.out.println("Encrypted Message: "+encrypted);
        int[] keys = findKeys(original, encrypted);
        System.out.println("Affine Cipher Key A: "+keys[0]);
        System.out.println("Affine Cipher Key B: "+keys[1]);
    }
}
