package DSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static DSA.Algorithms.Hash.*;
import static DSA.Algorithms.PKC.*;

public class Server {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
            BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, client_hash, encrypted_client_hash, final_client_message;
            String server_message, server_hash, encrypted_server_hash, final_server_message;
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

            int[] server_keys = keyGen(phi_n);
            int server_private_key = server_keys[0];
            int server_public_key = server_keys[1];

            int client_public_key = Integer.parseInt(in.readLine());

            System.out.println("[INFO]\t\t> Server's Private Key: "+server_private_key);
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);
            pr.println(server_public_key);
            System.out.println("[INFO]\t\t> Server's Public Key broadcasted.");
            System.out.println("[INFO]\t\t> Client's Public Key: "+server_public_key);

            while (true) {
                final_client_message = in.readLine();
                System.out.println("[SERVER]\t> "+final_client_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Extracting message and hash...");
                TimeUnit.SECONDS.sleep(DELAY);
                extracted = extract(final_client_message);
                client_message = extracted[0];
                encrypted_client_hash = extracted[1];
                System.out.println("[INFO]\t\t> Message: " + client_message);
                System.out.println("[INFO]\t\t> Encrypted hash: " + encrypted_client_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Decrypting hash using client's public key...");
                TimeUnit.SECONDS.sleep(DELAY);
                client_hash = decrypt(encrypted_client_hash, client_public_key, n);
                System.out.println("[INFO]\t\t> Client hash: " + client_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Comparing hash values...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (compare(combine(client_message, client_hash))) {
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
                server_hash = hash(server_message);
                System.out.println("[INFO]\t> Hash of message: "+server_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t> Encrypting hash using server's private key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_server_hash = encrypt(server_hash, server_private_key, n);
                System.out.println("[INFO]\t> Encrypted hash: "+encrypted_server_hash);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t> Combining message and encrypted hash...");
                TimeUnit.SECONDS.sleep(DELAY);
                final_server_message = combine(server_message, encrypted_server_hash);
                System.out.println("[INFO]\t> Final message: "+final_server_message);
                pr.println(final_server_message);
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
