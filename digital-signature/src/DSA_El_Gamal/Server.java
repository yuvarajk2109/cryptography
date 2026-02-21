package DSA_El_Gamal;

import DSA_El_Gamal.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static DSA_El_Gamal.Algorithms.PKC.*;

public class Server {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));

            String client_message;
            String server_message;

            int[] server_signature, client_signature;

            Scanner input = new Scanner(System.in);

            int q;

            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }

            int alpha = findPrimitiveRoot(q);

            int server_private_key = generateX(q);
            int server_public_key = computeY(server_private_key, alpha, q);

            int client_public_key = (int) in.readObject();

            System.out.println("[INFO]\t\t> Server's Private Key: "+server_private_key);
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);
            out.writeObject(server_public_key);
            System.out.println("[INFO]\t\t> Server's Public Key broadcasted.");
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);

            while (true) {
                client_message = (String) in.readObject();
                client_signature = (int[]) in.readObject();
                System.out.println("[CLIENT]\t> Message: "+client_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[CLIENT]\t> Signature: "+ Arrays.toString(client_signature));
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Verifying signature...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (verify(client_message, client_signature[0], client_signature[1], q, alpha, client_public_key)) {
                    System.out.println("[INFO]\t\t> Signature valid.");
                } else {
                    System.out.println("[INFO]\t\t> INVALID signature!");
                }
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
                    break;
                }
                System.out.print("[SYSTEM]\t> Message: ");
                server_message = br.readLine();
                System.out.println("[INFO]\t\t> Signing...");
                TimeUnit.SECONDS.sleep(DELAY);
                server_signature = sign(server_message, q, alpha, server_private_key);
                System.out.println("[INFO]\t\t> Signature: "+ Arrays.toString(server_signature));
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Sending message and signature...");
                out.writeObject(server_message);
                out.writeObject(server_signature);
                if (server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: "+e.getMessage());
        } catch (NoSuchAlgorithmException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
