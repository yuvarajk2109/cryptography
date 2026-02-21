package Asymmetric.RSA.Authentication;

import Asymmetric.RSA.Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server {

    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
            BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
            PrintWriter pr = new PrintWriter(s.getOutputStream(), true);

            String client_message, decrypted_client_message;
            String server_message, encrypted_server_message;

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

            int[] server_keys = Algorithm.keyGen(phi_n);
            int server_private_key = server_keys[0];
            int server_public_key = server_keys[1];

            System.out.println("[INFO]\t\t> Server's Private Key: "+server_private_key);
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);
            pr.println(server_public_key);
            System.out.println("[INFO]\t\t> Server's Public Key broadcasted.");

            int client_public_key = Integer.parseInt(in.readLine());

            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);


            while (true) {
                client_message = in.readLine();
                System.out.println("[CLIENT]\t> " + client_message);
                System.out.println("[INFO]\t\t> Decrypting with Client's Public Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_client_message = Algorithm.decrypt(client_message, client_public_key, n);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_client_message);
                if (decrypted_client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                System.out.print("[SYSTEM]\t> Message: ");
                server_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting with Server's Private Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_server_message = Algorithm.encrypt(server_message, server_private_key, n);
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