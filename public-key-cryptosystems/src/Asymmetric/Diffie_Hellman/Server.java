package Asymmetric.Diffie_Hellman;

import static Asymmetric.Diffie_Hellman.Algorithm.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static long DELAY = Asymmetric.Main.DELAY;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("[INFO]\t\t> Client connection established.");

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

            int[] server_keys = generateKeys(a, q);
            int server_x = server_keys[0];
            int server_y = server_keys[1];

            int client_y = (int) in.readObject();
            System.out.println("[INFO]\t\t> Client's Y: "+client_y);

            System.out.println("[INFO]\t\t> Server's X: "+server_x);
            System.out.println("[INFO]\t\t> Server's Y: "+server_y);
            out.writeObject(server_y);
            System.out.println("[INFO]\t\t> Server's Y broadcasted.");

            int k = generateSharedKey(q, server_x, client_y);
            System.out.println("[INFO]\t\t> Shared Diffie-Hellman Key: "+k);

        } catch (IOException e) {
            System.err.println("Error: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
