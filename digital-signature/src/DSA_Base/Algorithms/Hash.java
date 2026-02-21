package DSA_Base.Algorithms;

import DSA_Base.Main;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Main.ALGORITHM);
        byte[] messageDigest = md.digest(message.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder hashtext = new StringBuilder(no.toString(16));
        while (hashtext.length() < Main.LENGTH) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }

    public static String combine(String message, String hash) {
        return message + hash;
    }

    public static String[] extract(String hashed_message) {
        int length = hashed_message.length();
        int offset = length - Main.LENGTH;
        return new String[] {hashed_message.substring(0, offset), hashed_message.substring(offset, length)};
    }

    public static boolean compare(String hashed_message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Main.ALGORITHM);
        String[] extracted = extract(hashed_message);
        String message = extracted[0];
        String current_hash = extracted[1];
        return hash(message).equals(current_hash);
    }
}
