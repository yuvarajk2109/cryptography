package Cipher.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Main {

    static long DELAY = Cipher.Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
            BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, decrypted_client_message;
            String server_message, encrypted_server_message;
            while (true) {
                client_message = in.readLine();
                System.out.println("[CLIENT]\t> " + client_message);
                System.out.println("[INFO]\t\t> Decrypting...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_client_message = Cipher.Main.decrypt(client_message);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_client_message);
                if (decrypted_client_message.equalsIgnoreCase("terminate") || decrypted_client_message.equalsIgnoreCase("terminatex")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                System.out.print("[SYSTEM]\t> Message: ");
                server_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_server_message = Cipher.Main.encrypt(server_message);
                System.out.println("[SYSTEM]\t> Encrypted Message: "+encrypted_server_message);
                pr.println(encrypted_server_message);
                if (server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
