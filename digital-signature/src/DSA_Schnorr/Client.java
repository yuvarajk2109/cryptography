package DSA_Schnorr;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static DSA_Schnorr.Algorithms.PKC.*;

public class Client {
    static long DELAY = Main.DELAY;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String client_message;
            String server_message;

            int[] client_signature, server_signature;

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
                if ((p-1)%q==0 && isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime factor of "+(p-1)+".");
                }
            }

            int alpha = generateA(p, q);
            int r = generateR(q);
            int x = computeX(alpha, r, p, q);

            int client_private_key = generateS(q);
            int client_public_key = computeV(alpha, client_private_key, q);

            System.out.println("[INFO]\t\t> Client's Private Key: "+client_private_key);
            System.out.println("[INFO]\t\t> Client's Public Key: "+client_public_key);
            out.writeObject(client_public_key);
            System.out.println("[INFO]\t\t> Client's Public Key broadcasted.");
            int server_public_key = (int) in.readObject();
            System.out.println("[INFO]\t\t> Server's Public Key: "+server_public_key);

            while (true) {
                System.out.print("[SYSTEM]\t> Message: ");
                client_message = br.readLine();
                System.out.println("[INFO]\t\t> Signing...");
                TimeUnit.SECONDS.sleep(DELAY);
                client_signature = generateSignature(client_message, x, r, client_private_key, q);
                System.out.println("[INFO]\t\t> Signature: "+ Arrays.toString(client_signature));
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Sending message and signature...");
                TimeUnit.SECONDS.sleep(DELAY);
                out.writeObject(client_message);
                out.writeObject(client_signature);
                if (client_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Client terminated connection.");
                    break;
                }
                server_message = (String) in.readObject();
                server_signature = (int[]) in.readObject();
                System.out.println("[SERVER]\t> Message: " + server_message);
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[SERVER]\t> Signature: " + Arrays.toString(server_signature));
                TimeUnit.SECONDS.sleep(DELAY);
                System.out.println("[INFO]\t\t> Verifying signature...");
                TimeUnit.SECONDS.sleep(DELAY);
                if (verifySignature(server_message, alpha, server_signature[1], server_public_key, server_signature[0], p, q)) {
                    System.out.println("[INFO]\t\t> Signature valid.");
                } else {
                    System.out.println("[INFO]\t\t> INVALID signature!");
                }
                if (server_message.equalsIgnoreCase("terminate")) {
                    System.out.println("[INFO]\t\t> Server terminated connection.");
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
