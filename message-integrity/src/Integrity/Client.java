package Integrity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import static Integrity.Algorithm.combine;
import static Integrity.Algorithm.hash;

public class Client {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, hashed_client_message;
            String server_message, hashed_server_message;
            String[] extracted;

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Hashing...");
                TimeUnit.SECONDS.sleep(DELAY);
                hashed_client_message = combine(client_message, hash(client_message));
                System.out.println("[SYSTEM]\t> Hashed Message: "+hashed_client_message);
                pr.println(hashed_client_message);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                hashed_server_message = in.readLine();
                System.out.println("[SERVER]\t> "+hashed_server_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Extracting message...");
                TimeUnit.SECONDS.sleep(DELAY);
                extracted = Algorithm.extract(hashed_server_message);
                server_message = extracted[0];
                System.out.println("[INFO]\t\t> Message: " + server_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Comparing hash values...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (Algorithm.compare(hashed_server_message)) {
                    System.out.println("[INFO]\t\t> Message integrity is secure.");
                } else {
                    System.out.println("[CAUTION]\t> MESSAGE HAS BEEN TAMPERED!");
                }
                TimeUnit.SECONDS.sleep(DELAY);
                if (server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
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
