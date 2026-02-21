package Asymmetric.El_Gamal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server {
    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String server_message, decrypted_client_message;
            int[][] encrypted_server_message, client_message;

            Scanner input = new Scanner(System.in);

            int q, a;

            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (Asymmetric.El_Gamal.Algorithm.isPrime(q)) {
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

            int[] server_keys = Algorithm.generateKeys(a, q);
            int server_private_key = server_keys[0];
            int server_public_key = server_keys[1];

            int client_public_key = (int) in.readObject();
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);

            System.out.println("[INFO]\t\t> Server's Private Key: "+server_private_key);
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);
            out.writeObject(server_public_key);
            System.out.println("[INFO]\t\t> Server's Public Key broadcasted.");

            while (true) {
                client_message = (int[][]) in.readObject();
                System.out.println("[SERVER]\t> "+ Arrays.deepToString(client_message));
                System.out.println("[INFO]\t\t> Decrypting with Server's Private Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                decrypted_client_message = Algorithm.decrypt(client_message, q, server_private_key);
                System.out.println("[INFO]\t\t> Decrypted Message: " + decrypted_client_message);
                if (decrypted_client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                System.out.print("[SYSTEM]\t> Message: ");
                server_message = br.readLine();
                System.out.println("[INFO]\t\t> Encrypting with Client's Public Key...");
                TimeUnit.SECONDS.sleep(DELAY);
                encrypted_server_message = Algorithm.encrypt(server_message, a, q, client_public_key);
                System.out.println("[SYSTEM]\t> Encrypted Message: "+ Arrays.deepToString(encrypted_server_message));
                out.writeObject(encrypted_server_message);
                if (server_message.equalsIgnoreCase("terminate")) {
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