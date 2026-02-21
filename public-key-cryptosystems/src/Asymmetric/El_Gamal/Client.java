package Asymmetric.El_Gamal;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String client_message, decrypted_server_message;
            int[][] encrypted_client_message, server_message;

            Scanner input = new Scanner(System.in);

            int q, a;

            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (Algorithm.isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }
            while (true) {
                System.out.print("[INFO]\t\t> a: ");
                a = input.nextInt();
                if (a<q && Algorithm.isPrimitiveRoot(a, q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a primitive root of "+q+".");
                }
            }

            int[] client_keys = Asymmetric.El_Gamal.Algorithm.generateKeys(a, q);
            int client_private_key = client_keys[0];
            int client_public_key = client_keys[1];

            System.out.println("[INFO]\t\t> Client's Private Key: "+client_private_key);
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);
            out.writeObject(client_public_key);
            System.out.println("[INFO]\t\t> Client's Public Key broadcasted.");

            int server_public_key = (int) in.readObject();
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting with Server's Public Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_client_message = Algorithm.encrypt(client_message, a, q, server_public_key);
                System.out.println("[SYSTEM]\t> Encrypted Message: "+ Arrays.deepToString(encrypted_client_message));
                out.writeObject(encrypted_client_message);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                server_message = (int[][]) in.readObject();
                System.out.println("[SERVER]\t> "+ Arrays.deepToString(server_message));
                System.out.println("[INFO]\t\t> Decrypting with Client's Private Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_server_message = Algorithm.decrypt(server_message, q, client_private_key);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_server_message);
                if (decrypted_server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}