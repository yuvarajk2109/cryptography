package Asymmetric.Diffie_Hellman;

import static Asymmetric.Diffie_Hellman.Algorithm.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 8080)) {
            System.out.println("[INFO]\t\t> Server connection established.");

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            Scanner input = new Scanner(System.in);

            int q, a;

            while (true) {
                System.out.print("[INFO]\t\t> q: ");
                q = input.nextInt();
                if (isPrime(q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a prime integer.");
                }
            }
            while (true) {
                System.out.print("[INFO]\t\t> a: ");
                a = input.nextInt();
                if (a<q && isPrimitiveRoot(a, q)) {
                    break;
                } else {
                    System.out.println("[INFO]\t\t> Invalid. Please enter a primitive root of "+q+".");
                }
            }

            int[] client_keys = generateKeys(a, q);
            int client_x = client_keys[0];
            int client_y = client_keys[1];

            System.out.println("[INFO]\t\t> Client's X: "+client_x);
            System.out.println("[INFO]\t\t> Client's Y: "+client_y);
            out.writeObject(client_y);
            System.out.println("[INFO]\t\t> Client's Y broadcasted.");

            int server_y = (int) in.readObject();
            System.out.println("[INFO]\t\t> Server's Y: "+server_y);
            
            int k = generateSharedKey(q, client_x, server_y);
            System.out.println("[INFO]\t\t> Shared Diffie-Hellman Key: "+k);

            
        } catch (IOException e) {
            System.err.println("Error: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
