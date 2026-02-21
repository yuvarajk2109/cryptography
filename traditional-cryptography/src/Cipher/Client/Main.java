package Cipher.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Main {
    static long DELAY = Cipher.Main.DELAY;

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, encrypted_client_message;
            String server_message, decrypted_server_message;

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_client_message = Cipher.Main.encrypt(client_message);
                System.out.println("[SYSTEM]\t> Encrypted Message: "+encrypted_client_message);
                pr.println(encrypted_client_message);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                server_message = in.readLine();
                System.out.println("[SERVER]\t> "+server_message);
                System.out.println("[INFO]\t\t> Decrypting...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_server_message = Cipher.Main.decrypt(server_message);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_server_message);
                if (decrypted_server_message.equalsIgnoreCase("terminate") || decrypted_server_message.equalsIgnoreCase("terminatk")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
