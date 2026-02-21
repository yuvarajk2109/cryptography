package DSA_RSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static DSA_RSA.Algorithms.Hash.*;
import static DSA_RSA.Algorithms.PKC.*;

public class Client {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, client_hash, encrypted_client_message, final_client_message;
            String server_message, server_hash, encrypted_server_message, final_server_message;
            String[] extracted;

            Scanner input = new Scanner(System.in);

            int p, q;

            while (true) {
                System.out.print("[INFO]\t\t> p: ");
                p = input.nextInt();
                if (isPrime(p)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }
            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }

            int n = p*q;
            int phi_n = (p-1)*(q-1);

            int[] client_keys = keyGen(phi_n);
            int client_private_key = client_keys[0];
            int client_public_key = client_keys[1];

            System.out.println("[INFO]\t\t> Client's Private Key: "+client_private_key);
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);
            pr.println(client_public_key);
            System.out.println("[INFO]\t\t> Client's Public Key broadcasted.");
            int server_public_key = Integer.parseInt(in.readLine());
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Hashing...");
                TimeUnit.SECONDS.sleep(DELAY);
                client_hash = hash(client_message);
                System.out.println("[INFO]\t\t> Hash of message: "+client_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Encrypting message using client's private key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_client_message = encrypt(client_message, client_private_key, n);
                System.out.println("[INFO]\t\t> Encrypted message: "+encrypted_client_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Combining encrypted message and hash...");
                TimeUnit.SECONDS.sleep(DELAY);
                final_client_message = combine(encrypted_client_message, client_hash);
                System.out.println("[INFO]\t\t> Final message: "+final_client_message);
                pr.println(final_client_message);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                final_server_message = in.readLine();
                System.out.println("[SERVER]\t> "+final_server_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Extracting encrypted message and hash...");
                TimeUnit.SECONDS.sleep(DELAY);
                extracted = extract(final_server_message);
                encrypted_server_message = extracted[0];
                server_hash = extracted[1];
                System.out.println("[INFO]\t\t> Encrypted message: " + encrypted_server_message);
                System.out.println("[INFO]\t\t> Hash: " + server_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Decrypting message using server's public key...");
                TimeUnit.SECONDS.sleep(DELAY);
                server_message = decrypt(encrypted_server_message, server_public_key, n);
                System.out.println("[INFO]\t\t> Server message: " + server_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Comparing hash values...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (compare(combine(server_message, server_hash))) {
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
