//package ES.Client;
//
//import ES.Algorithms.AES;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.security.InvalidKeyException;
//import java.util.concurrent.TimeUnit;
//
//import static ES.Main.DELAY;
//
//public class Main {
//
//    public static void main(String[] args) {
//        try (Socket s = new Socket("localhost", 8080)) {
//            System.out.println("[INFO]\t\t> Server connection established.");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);
//
//            String client_message, encrypted_client_message;
//            String server_message, decrypted_server_message;
//
//            while (true) {
//                System.out.print("[SYSTEM]\t> Message: ");
//                client_message = br.readLine();
//                System.out.println("[INFO]\t\t> Encrypting...");
//                TimeUnit.SECONDS.sleep(DELAY);
//                encrypted_client_message = AES.encrypt(client_message);
//                System.out.println("[SYSTEM]\t> Encrypted Message: "+encrypted_client_message);
//                pr.println(encrypted_client_message);
//                if (client_message.equalsIgnoreCase("terminate")) {
//                    System.out.println("[INFO]\t\t> ES.Client terminated connection.");
//                    break;
//                }
//                server_message = in.readLine();
//                System.out.println("[SERVER]\t> "+server_message);
//                System.out.println("[INFO]\t\t> Decrypting...");
//                TimeUnit.SECONDS.sleep(DELAY);
//                decrypted_server_message = AES.decrypt(server_message);
//                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_server_message);
//                if (decrypted_server_message.equalsIgnoreCase("terminate") || decrypted_server_message.equalsIgnoreCase("terminatex")) {
//                    System.out.println("[INFO]\t\t> ES.Server terminated connection.");
//                    break;
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Error: "+e.getMessage());
//        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
