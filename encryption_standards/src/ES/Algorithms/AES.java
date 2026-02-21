package ES.Algorithms;

import javax.crypto.*;
import java.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES {

    public Cipher cipher;
    public SecretKey secretKey;

    public static int KEYSIZE = 128;

    public AES() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("AES");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEYSIZE);
        secretKey = keyGen.generateKey();
    }

    public String encrypt(String message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(message.getBytes());
        String encrypted = Base64.getEncoder().encodeToString(encryptedData);
        return encrypted;
    }

    public String decrypt(String encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        String decrypted = new String(decryptedData);
        return decrypted;
    }

    public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        AES aes = new AES();
        String message = "Yuvaraj";
        String encrypted = aes.encrypt(message);
        String decrypted = aes.decrypt(encrypted);
        System.out.println("Plain Text Message:\t"+message);
        System.out.println("Encrypted Message:\t"+encrypted);
        System.out.println("Decrypted Message:\t"+decrypted);
    }
}
