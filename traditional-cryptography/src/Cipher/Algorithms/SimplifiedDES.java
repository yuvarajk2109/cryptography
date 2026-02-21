package Cipher.Algorithms;


import java.util.HashMap;

public class SimplifiedDES {

    static String KEY = "1010110001";
    static String k1 = "";
    static String k2 = "";

    static int[][] s0 =  {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 2}
    };

    static int[][] s1 = {
            {0, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 0},
            {2, 1, 0, 3}
    };

    HashMap<String, Integer> number = new HashMap<>();
    HashMap<Integer, String> binary = new HashMap<>();

    public SimplifiedDES() {
        String[] keys = generateKeys();
        k1 = keys[0];
        k2 = keys[1];
        number.put("00", 0);
        number.put("01", 1);
        number.put("10", 2);
        number.put("11", 3);
        binary.put(0, "00");
        binary.put(1, "01");
        binary.put(2, "10");
        binary.put(3, "11");
    }

    public String P10(String text) {
        int[] index = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
        StringBuilder sb = new StringBuilder();
        sb.setLength(10);
        for (int i=0; i<10; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String P8(String text) {
        int[] index = {6, 3, 7, 4, 8, 5, 10, 9};
        StringBuilder sb = new StringBuilder();
        sb.setLength(8);
        for (int i=0; i<8; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String P4(String text) {
        int[] index = {2, 4, 3, 1};
        StringBuilder sb = new StringBuilder();
        sb.setLength(4);
        for (int i=0; i<4; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String leftShift(String text) {
        StringBuilder sb = new StringBuilder(text);
        for (int i=0; i<5; i++) {
            sb.setCharAt(i, text.charAt(Math.floorMod(i+1, 5)));
        }
        for (int i=5; i<10; i++) {
            sb.setCharAt(i, text.charAt(Math.floorMod(i+1, 5)+5));
        }
        return sb.toString();
    }

    public String[] generateKeys() {
        String p10 = P10(KEY);
        String ls1= leftShift(p10);
        String k1 = P8(ls1);
        String ls2 = leftShift(leftShift(ls1));
        String k2 = P8(ls2);
//        System.out.println("P10:\t\t\t"+p10);
//        System.out.println("Left Shift 1:\t"+ls1);
//        System.out.println("K1:\t\t\t\t"+k1);
//        System.out.println("Left Shift 2:\t"+ls2);
//        System.out.println("K2:\t\t\t\t"+k2);
        return new String[]{k1, k2};
    }

    public String leftNibble(String text) {
        return text.substring(0, 4);
    }

    public String rightNibble(String text) {
        return text.substring(4);
    }

    public String xor(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        int size = s1.length();
        for (int i=0; i<size; i++) {
            int b1 = s1.charAt(i);
            int b2 = s2.charAt(i);
            if (b1==b2) {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        return sb.toString();
    }

    public String IP(String text) {
        int[] index = {2, 6, 3, 1, 4, 8, 5, 7};
        StringBuilder sb = new StringBuilder(text);
        for (int i=0; i<8; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String EP(String text) {
        int[] index = {4, 1, 2, 3, 2, 3, 4, 1};
        StringBuilder sb = new StringBuilder();
        sb.setLength(8);
        for (int i=0; i<8; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String generateS(String text) {
        StringBuilder sb = new StringBuilder();

        StringBuilder s0_row = new StringBuilder();
        StringBuilder s0_col = new StringBuilder();
        StringBuilder s1_row = new StringBuilder();
        StringBuilder s1_col = new StringBuilder();

        String left = leftNibble(text);
        s0_row.append(left.charAt(0));
        s0_row.append(left.charAt(3));
        s0_col.append(left.charAt(1));
        s0_col.append(left.charAt(2));

        sb.append(binary.get(s0[number.get(s0_row.toString())][number.get(s0_col.toString())]));

        String right = rightNibble(text);
        s1_row.append(right.charAt(0));
        s1_row.append(right.charAt(3));
        s1_col.append(right.charAt(1));
        s1_col.append(right.charAt(2));

        sb.append(binary.get(s1[number.get(s1_row.toString())][number.get(s1_col.toString())]));

        return sb.toString();
    }

    public String swap(String text)  {
        String left = leftNibble(text);
        String right = rightNibble(text);
        return right + left;
    }

    public String IP_INV(String text) {
        int[] index = {4, 1, 3, 5, 7, 2, 8, 6};
        StringBuilder sb = new StringBuilder(text);
        for (int i=0; i<8; i++) {
            sb.setCharAt(i, text.charAt(index[i]-1));
        }
        return sb.toString();
    }

    public String fk(String text, int stage) {
        String right = rightNibble(text);
        String ep = EP(right);
        String fk1;
        if (stage==1) {
            fk1 = xor(ep, k1);
        } else {
            fk1 = xor(ep, k2);
        }
        String fk2 = generateS(fk1);
        String p4 = P4(fk2);
        String left = xor(p4, leftNibble(text));
        return left + right;
    }

    public String DecimalToBinary(int ascii) {
        StringBuilder sb = new StringBuilder();
        while (ascii != 0) {
            sb.append((char) ascii%2);
            ascii /= 2;
        }
        while (sb.length() < 8) {
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    public int BinaryToDecimal(String binary) {
        int ascii = 0;
        int size = binary.length();
        StringBuilder sb = new StringBuilder(binary).reverse();
        for (int i=0; i<size; i++) {
            ascii += (int)Math.pow(2, i)*Character.getNumericValue(sb.charAt(i));
        }
        return ascii;
    }

    public String sdesEncrypt(String pt_key) {
        String ip = IP(pt_key);
        String fk1 = fk(ip, 1);
        String swapped = swap(fk1);
        String fk2 = fk(swapped, 2);
        String ip_inv = IP_INV(fk2);
        return ip_inv;
    }

    public String sdesDecrypt(String ct_key){
        String ip = IP(ct_key);
        String fk1 = fk(ip, 2);
        String swapped = swap(fk1);
        String fk2 = fk(swapped, 1);
        String ip_inv = IP_INV(fk2);
        return ip_inv;
    }

    public String encrypt(String pt) {
        StringBuilder sb = new StringBuilder();
        int size = pt.length();
        for (int i=0; i<size; i++) {
            int ascii = pt.charAt(i);
            String binary = DecimalToBinary(ascii);
            String encrypted = sdesEncrypt(binary);
            int encrypted_ascii = BinaryToDecimal(encrypted);
            sb.append((char) encrypted_ascii);
//            System.out.println("Binary: "+binary);
//            System.out.println("Encrypted: "+encrypted);
//            System.out.println("Encrypted Ascii: "+encrypted_ascii);
//            System.out.println("String: "+sb);
        }
        return sb.toString();
    }

    public String decrypt(String ct) {
        StringBuilder sb = new StringBuilder();
        int size = ct.length();
        for (int i=0; i<size; i++) {
            int ascii = ct.charAt(i);
            String binary = DecimalToBinary(ascii);
            String encrypted = sdesDecrypt(binary);
            int encrypted_ascii = BinaryToDecimal(encrypted);
            sb.append((char) encrypted_ascii);
//            System.out.println("Binary: "+binary);
//            System.out.println("Encrypted: "+encrypted);
//            System.out.println("Encrypted Ascii: "+encrypted_ascii);
//            System.out.println("String: "+sb);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SimplifiedDES sdes = new SimplifiedDES();
        String original = "Hello!";
        String encrypted = sdes.encrypt(original);
        String decrypted = sdes.decrypt(encrypted);
        System.out.println("Plain Text:\t\t" + original);
        System.out.println("Key:\t\t\t"+KEY);
        System.out.println("Encrypt Test:\t" + encrypted);
        System.out.println("Decrypt Test:\t" + decrypted);
    }
}
