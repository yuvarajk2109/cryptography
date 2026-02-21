package ES.Algorithms;

public class DES {

    static int[][] IP_Table = {
        {58, 50, 42, 34, 26, 18, 10, 2},
        {60, 52, 44, 36, 28, 20, 12, 4},
        {62, 54, 46, 38, 30, 22, 14, 6},
        {64, 56, 48, 40, 32, 24, 16, 8},
        {57, 49, 41, 33, 25, 17, 9, 1},
        {59, 51, 43, 35, 27, 19, 11, 3},
        {61, 53, 45, 37, 29, 21, 13, 5},
        {63, 55, 47, 39, 31, 23, 15, 7}
    };

    static int[][] PC_1_Table = {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}
    };

    static int[][] PC_2_Table = {
        {14, 17, 11, 24, 1, 5, 3, 28},
        {15, 6, 21, 10, 23, 19, 12, 4},
        {26, 8, 16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55, 30, 40},
        {51, 45, 33, 48, 44, 49, 39, 56},
        {34, 53, 46, 42, 50, 36, 29, 32}
    };

    static int[][] E_BOX = {
        {32, 1, 2, 3, 4, 5},
        {4, 5, 6, 7, 8, 9},
        {8, 9, 10, 11, 12, 13},
        {12, 13, 14, 15, 16, 17},
        {16, 17, 18, 19, 20, 21},
        {20, 21, 22, 23, 24, 25},
        {24, 25, 26, 27, 28, 29},
        {28, 29, 30, 31, 32, 1}
    };

    static int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    static int[][] S2 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    static int[][] S3 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };

    static int[][] S4 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    static int[][] S5 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };

    static int[][] S6 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };

    static int[][] S7 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };

    static int[][] S8 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    static int[][] P_BOX = {
        {16, 7, 20, 21, 29, 12, 28, 17},
        {1, 15, 23, 26, 5, 18, 31, 10},
        {2, 8, 24, 14, 32, 27, 3, 9},
        {19, 13, 30, 6, 22, 11, 4, 25}
    };

//    static int[][] IP_Inverse_Table = {
//        {58, 50, 42, 34, 26, 18, 10, 2},
//        {60, 52, 44, 36, 28, 20, 12, 4},
//        {62, 54, 46, 38, 30, 22, 14, 6},
//        {64, 56, 48, 40, 32, 24, 16, 8},
//        {57, 49, 41, 33, 25, 17, 9, 1},
//        {59, 51, 43, 35, 27, 19, 11, 3},
//        {61, 53, 45, 37, 29, 21, 13, 5},
//        {63, 55, 47, 39, 31, 23, 15, 7}
//    };

    static int[][] IP_Inverse_Table = {
        {40, 8, 48, 16, 56, 24, 64, 32},
        {39, 7, 47, 15, 55, 23, 63, 31},
        {38, 6, 46, 14, 54, 22, 62, 30},
        {37, 5, 45, 13, 53, 21, 61, 29},
        {36, 4, 44, 12, 52, 20, 60, 28},
        {35, 3, 43, 11, 51, 19, 59, 27},
        {34, 2, 42, 10, 50, 18, 58, 26},
        {33, 1, 41, 9, 49, 17, 57, 25}
    };

    public String initialPermutation(String PT_64) {
        StringBuilder sb = new StringBuilder();
        int cols = IP_Table[0].length;
        for (int[] row : IP_Table) {
            for (int j = 0; j < cols; j++) {
                sb.append(PT_64.charAt(row[j]-1));
            }
        }
        System.out.println("\nInitial Permutation:\t"+display(sb.toString(), 4));
        return sb.toString();
    }

//    public String permutationChoice1(String KEY_64) {
//        StringBuilder KEY_56 = new StringBuilder();
//        int length = KEY_64.length();
//        for (int i=0; i<length; i++) {
//            if ((i+1)%8!=0) {
//                KEY_56.append(KEY_64.charAt(i));
//            }
//        }
//        System.out.println("Permutation Choice 1:\t"+display(KEY_56.toString()));
//        return KEY_56.toString();
//    }

    public String permutationChoice1(String KEY_64) {
        StringBuilder KEY_56 = new StringBuilder();
        int cols = PC_1_Table[0].length;
        for (int[] row : PC_1_Table) {
            for (int j=0; j<cols; j++) {
                KEY_56.append(KEY_64.charAt(row[j]-1));
            }
//            System.out.println("Current PC1: "+display(KEY_56.toString()));
        }
        System.out.println("Permutation Choice 1:\t"+display(KEY_56.toString(), 7));
        return KEY_56.toString();
    }

    public String leftShift(String KEY_56) {
        StringBuilder sb = new StringBuilder();
        int length = KEY_56.length();
        int half_length = length/2;
        for (int i=0; i<half_length; i++) {
            sb.append(KEY_56.charAt(Math.floorMod(i+1, 28)));
        }
        for (int i=half_length; i<length; i++) {
            sb.append(KEY_56.charAt(Math.floorMod(i+1, 28)+28));
        }
        return sb.toString();
    }

    public String permutationChoice2(String KEY_56) {
        StringBuilder KEY_48 = new StringBuilder();
        int cols = PC_2_Table[0].length;
        for (int[] row : PC_2_Table) {
            for (int j=0; j<cols; j++) {
                KEY_48.append(KEY_56.charAt(row[j]-1));
            }
        }
        return KEY_48.toString();
    }

    public String leftPlainText(String PT_64) {
        return PT_64.substring(0, 32);
    }

    public String rightPlainText(String PT_64) { return PT_64.substring(32, 64); }

    public String expansionPermutation(String RPT_32) {
        StringBuilder sb = new StringBuilder();
        int cols = E_BOX[0].length;
        for (int[] row : E_BOX) {
            for (int j=0; j<cols; j++) {
                sb.append(RPT_32.charAt(row[j]-1));
            }
        }
        return sb.toString();
    }

    public String xor(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        int size = s1.length();
        for (int i=0; i<size; i++) {
            int b1 =  s1.charAt(i);
            int b2 =  s2.charAt(i);
            if (b1==b2) {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        return sb.toString();
    }

    public String DecimalToBinary(int number) {
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append((char) number%2);
            number /= 2;
        }
        while (sb.length() != 4) sb.append('0');
//        System.out.println("Binary: "+sb);
        return sb.reverse().toString();
    }

    public int BinaryToDecimal(String binary) {
        int number = 0;
        int size = binary.length();
        StringBuilder sb = new StringBuilder(binary).reverse();
        for (int i=0; i<size; i++) {
            number += (int)Math.pow(2, i)*Character.getNumericValue(sb.charAt(i));
        }
        return number;
    }

    public int getSBoxRowIndex(String KEY_6) {
        String row = String.valueOf(KEY_6.charAt(0)) + KEY_6.charAt(5);
        return BinaryToDecimal(row);
    }

    public int getSBoxColIndex(String KEY_6) {
        return BinaryToDecimal(KEY_6.substring(1, 5));
    }

    public int getSBoxValue(int SBox, int row, int col) {
        int number = 0;
        if (SBox==0) {
            number = S1[row][col];
        } else if (SBox==1) {
            number = S2[row][col];
        } else if (SBox==2) {
            number = S3[row][col];
        } else if (SBox==3) {
            number = S4[row][col];
        } else if (SBox==4) {
            number = S5[row][col];
        } else if (SBox==5) {
            number = S6[row][col];
        } else if (SBox==6) {
            number = S7[row][col];
        } else if (SBox==7) {
            number = S8[row][col];
        }
        return number;
    }

    public String processInSBox(String KEY_48) {
        StringBuilder KEY_32 = new StringBuilder();
        for (int i=0; i<8; i++) {
            String KEY_6 = KEY_48.substring(i*6, (i*6)+6);
            int row = getSBoxRowIndex(KEY_6);
            int col = getSBoxColIndex(KEY_6);
            int number = getSBoxValue(i, row, col);
//            System.out.println("Number: "+number);
            KEY_32.append(DecimalToBinary(number));
        }
        return KEY_32.toString();
    }

    public String transposition(String KEY_32) {
        StringBuilder sb = new StringBuilder();
        int cols = P_BOX[0].length;
        for (int[] row : P_BOX) {
            for (int j=0; j<cols; j++) {
                sb.append(KEY_32.charAt(row[j]-1));
            }
        }
        return sb.toString();
    }

    public String circularLeftShift(String KEY_56, int round) {
        if (round==1 || round==2 || round==9 || round==16) {
            KEY_56 = leftShift(KEY_56);
        } else {
            KEY_56 = leftShift(leftShift(KEY_56));
        }
        return KEY_56;
    }

    public String mangler(String RPT_32, String KEY_48) {
        String EP_48 = expansionPermutation(RPT_32);
        String XOR_48 = xor(EP_48, KEY_48);
        String substituted_32 = processInSBox(XOR_48);
//        System.out.println("Length: "+substituted_32.length());
        return transposition(substituted_32);
    }

    public String initialPermutationInverse(String PT_64) {
        StringBuilder sb = new StringBuilder();
        int cols = IP_Inverse_Table[0].length;
        for (int[] row : IP_Inverse_Table) {
            for (int j = 0; j < cols; j++) {
                sb.append(PT_64.charAt(row[j]-1));
            }
        }
        return sb.toString();
    }

    public String encrypt(String PT_64, String KEY_64) {
        String PT = initialPermutation(PT_64);
//        String PT = PT_64;
        String KEY_56 = permutationChoice1(KEY_64);
        for (int i=1; i<=16; i++) {
            KEY_56 = circularLeftShift(KEY_56, i);
            System.out.println("\nRound:\t\t\t\t\t"+i);
            System.out.println("Key:\t\t\t\t\t"+display(KEY_56, 7));
            String KEY_48 = permutationChoice2(KEY_56);
            String LPT_32 = leftPlainText(PT);
            String RPT_32 = rightPlainText(PT);
            String manglerPT = mangler(RPT_32, KEY_48);
            String newRight = xor(manglerPT, LPT_32);
            if ((i-1)<10) {
                System.out.println("L"+(i-1)+":\t\t\t\t\t\t" + display(LPT_32, 4));
                System.out.println("R"+(i-1)+":\t\t\t\t\t\t" + display(RPT_32, 4));
            } else {
                System.out.println("L"+(i-1)+":\t\t\t\t\t" + display(LPT_32, 4));
                System.out.println("R"+(i-1)+":\t\t\t\t\t" + display(RPT_32, 4));
            }
            if (i<10) {
                System.out.println("K"+i+":\t\t\t\t\t\t"+display(KEY_48, 6));
                System.out.println("L"+i+":\t\t\t\t\t\t" + display(RPT_32, 4));
                System.out.println("R"+i+":\t\t\t\t\t\t" + display(newRight, 4));
            } else {
                System.out.println("K"+i+":\t\t\t\t\t"+display(KEY_48, 6));
                System.out.println("L" + i + ":\t\t\t\t\t" + display(RPT_32, 4));
                System.out.println("R" + i + ":\t\t\t\t\t" + display(newRight, 4));
            }
            if (i!=16) {
                PT = RPT_32;
                PT = PT.concat(newRight);
            } else {
                PT = newRight;
                PT = PT.concat(RPT_32);
            }
        }
        System.out.println("\nBefore IP Inverse:\t\t"+display(PT, 4));
        String result = initialPermutationInverse(PT);
        return result;
    }

    static String display(String text, int gap) {
        StringBuilder sb = new StringBuilder();
        int length = text.length();
        for (int i=0; i<length; i++) {
            sb.append(text.charAt(i));
            if ((i+1)%gap == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DES d = new DES();
        // https://simewu.com/des/
        String PT_64 = "0000000100100011010001010110011110001001101010111100110111101111";
        String KEY_64 = "0000000100100011010001010110011110001001101010111100110111101111";
        System.out.println("Plain Text:\t\t\t\t"+display(PT_64, 4));
        System.out.println("Key:\t\t\t\t\t"+display(KEY_64, 4));
        String encrypted = d.encrypt(PT_64, KEY_64);
        System.out.println("Cipher Text:\t\t\t"+display(encrypted, 4));
    }
}