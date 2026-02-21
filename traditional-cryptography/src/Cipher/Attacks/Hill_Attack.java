package Cipher.Attacks;

import Cipher.Algorithms.Hill;
import static Cipher.Algorithms.Hill.constructMessageMatrix;
import static Cipher.Algorithms.Hill.multiply;
import static Cipher.Main.matrixInverse;

import java.util.Arrays;

public class Hill_Attack {

    static int[][] findKey(String original, String encrypted) {
        int[][] pt_matrix = constructMessageMatrix(original);
        int[][] ct_matrix = constructMessageMatrix(encrypted);
        int[][] pt_inverse = matrixInverse(pt_matrix, 2);
//        System.out.println("Plain Text Matrix: "+Arrays.deepToString(pt_matrix));
//        System.out.println("Cipher Text Matrix: "+ Arrays.deepToString(ct_matrix));
//        System.out.println("Plain Text Inverse: "+ Arrays.deepToString(pt_inverse));
        return multiply(pt_inverse, ct_matrix);
    }

    public static void main(String[] args) {
        String original = "hill";
        Hill h = new Hill();
        String encrypted = h.encrypt(original);
        System.out.println("Original Message: "+original);
        System.out.println("Encrypted Message: "+encrypted);
        int[][] key = findKey(original, encrypted);
        System.out.println("Hill Cipher Key: "+ Arrays.deepToString(key));
    }
}
