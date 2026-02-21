package Integrity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import static Integrity.Algorithm.combine;
import static Integrity.Algorithm.hash;

public class Server {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
            BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, hashed_client_message;
            String server_message, hashed_server_message;
            String[] extracted;

            while (true) {
                hashed_client_message = in.readLine();
                System.out.println("[CLIENT]\t> "+hashed_client_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Extracting message...");
                TimeUnit.SECONDS.sleep(DELAY);
                extracted = Algorithm.extract(hashed_client_message);
                client_message = extracted[0];
                System.out.println("[INFO]\t\t> Message: " + client_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Comparing hash values...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (Algorithm.compare(hashed_client_message)) {
                    System.out.println("[INFO]\t\t> Message integrity is secure.");
                } else {
                    System.out.println("[CAUTION]\t> MESSAGE HAS BEEN TAMPERED!");
                }
                TimeUnit.SECONDS.sleep(DELAY);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
                System.out.print("[SYSTEM]\t> Message: ");
                server_message = br.readLine();
                System.out.println("[INFO]\t\t> Hashing...");
                TimeUnit.SECONDS.sleep(DELAY);
                hashed_server_message = combine(server_message, hash(server_message));
                System.out.println("[SYSTEM]\t> Hashed Message: "+hashed_server_message);
                pr.println(hashed_server_message);
                if (server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
