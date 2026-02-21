package Asymmetric.RSA.Confidentiality;

import Asymmetric.RSA.Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, encrypted_client_message;
            String server_message, decrypted_server_message;

            Scanner input = new Scanner(System.in);

            int p, q;

            while (true) {
                System.out.print("[INFO]\t\t> p: ");
                p = input.nextInt();
                if (Algorithm.isPrime(p)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }
            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (Algorithm.isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }

            int n = p*q;
            int phi_n = (p-1)*(q-1);

            int[] client_keys = Algorithm.keyGen(phi_n);
            int client_private_key = client_keys[0];
            int client_public_key = client_keys[1];
            int server_public_key = Integer.parseInt(in.readLine());

            System.out.println("[INFO]\t\t> Client's Private Key: "+client_private_key);
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);

            pr.println(client_public_key);
            System.out.println("[INFO]\t\t> Client's Public Key broadcasted.");

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting with Server's Public Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_client_message = Algorithm.encrypt(client_message, server_public_key, n);
                System.out.println("[SYSTEM]\t> Encrypted Message: "+encrypted_client_message);
                pr.println(encrypted_client_message);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                server_message = in.readLine();
                System.out.println("[SERVER]\t> "+server_message);
                System.out.println("[INFO]\t\t> Decrypting with Client's Private Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_server_message = Algorithm.decrypt(server_message, client_private_key, n);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_server_message);
                if (decrypted_server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
