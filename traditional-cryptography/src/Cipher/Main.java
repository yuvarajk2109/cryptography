package Cipher;

import Cipher.Algorithms.*;

import java.io.IOException;

public class Main {
    public static long DELAY = 1;
    public static int METHOD = 10;

    /*
    ENCRYPTION METHODS
    1: Caesar Cipher
    2: Affine Cipher
    3: Playfair Cipher
    4: Monoalphabetic Cipher
    5: Vigner Cipher
    6: Autokey Cipher
    7: Hill Cipher
    8: Rail Fence Cipher
    9: Row Transposition Cipher
    10: SDES
     */

    public static String lowercaseAlphabets = "abcdefghijklmnopqrstuvwxyz";
    public static String uppercaseAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static int getInverse(int a, int m) {
        int a_inv = 0;
        for (int i = 0; i < m; i++) {
            if ((a*i)%m == 1) {
                a_inv = i;
                break;
            }
        }
        return a_inv;
    }

    public static int[][] matrixInverse(int[][] matrix, int dimension) {
        int det = (matrix[0][0]*matrix[1][1]) - (matrix[0][1]*matrix[1][0]);
        int det_inv = getInverse(Math.floorMod(det, 26), 26);
//        System.out.println("Det: "+det);
//        System.out.println("Det Inv: "+det_inv);
        int[][] inverse = new int[dimension][dimension];
        inverse[0][0] = det_inv * matrix[1][1];
        inverse[1][1] = det_inv * matrix[0][0];
        inverse[0][1] = det_inv * -matrix[0][1];
        inverse[1][0] = det_inv * -matrix[1][0];
        return inverse;
    }

    public static String encrypt(String message) throws IOException {
        String encrypted = switch (METHOD) {
            case 1 -> {
                Caesar c = new Caesar();
                yield c.encrypt(message);
            }
            case 2 -> {
                Affine a = new Affine();
                yield a.encrypt(message);
            }
            case 3 -> {
                Playfair p = new Playfair();
                yield p.encrypt(message);
            }
            case 4 -> {
                Monoalphabetic m = new Monoalphabetic();
                yield m.encrypt(message);
            }
            case 5 -> {
                Vigner pa = new Vigner();
                yield pa.encrypt(message);
            }
            case 6 -> {
                Autokey ak = new Autokey();
                yield ak.encrypt(message);
            }
            case 7 -> {
                Hill h = new Hill();
                yield h.encrypt(message);
            }
            case 8 -> {
                Rail_Fence rf = new Rail_Fence();
                yield rf.encrypt(message);
            }
            case 9 -> {
                Row_Transposition rt = new Row_Transposition();
                yield rt.encrypt(message);
            }
            case 10 -> {
                SimplifiedDES des = new SimplifiedDES();
                yield des.encrypt(message);
            }
            default -> "";
        };
        return encrypted;
    }

    public static String decrypt(String message) throws IOException {

        return switch (METHOD) {
            case 1 -> {
                Caesar c = new Caesar();
                yield c.decrypt(message);
            }
            case 2 -> {
                Affine a = new Affine();
                yield a.decrypt(message);
            }
            case 3 -> {
                Playfair p = new Playfair();
                yield p.decrypt(message);
            }
            case 4 -> {
                Monoalphabetic m = new Monoalphabetic();
                yield m.decrypt(message);
            }
            case 5 -> {
                Vigner pa = new Vigner();
                yield pa.decrypt(message);
            }
            case 6 -> {
                Autokey ak = new Autokey();
                yield ak.decrypt(message);
            }
            case 7 -> {
                Hill h = new Hill();
                yield h.decrypt(message);
            }
            case 8 -> {
                Rail_Fence rf = new Rail_Fence();
                yield rf.decrypt(message);
            }
            case 9 -> {
                Row_Transposition rt = new Row_Transposition();
                yield rt.decrypt(message);
            }
            case 10 -> {
                SimplifiedDES des = new SimplifiedDES();
                yield des.decrypt(message);
            }
            default -> "";
        };
    }
}
