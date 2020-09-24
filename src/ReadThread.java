import java.io.*;
import java.net.*;

/**
 * Этот поток отвечает за чтение с сервера и печать в консоль
 * Отключается если абонент отключился от сервера
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println(response);
                client.messages.add(response);

                // prints the username after displaying the server's message
                // if (client.getUserName() != null) {
                 //   System.out.print(">");
                //    System.out.print("[" + client.getUserName() + "]: ");
                // }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}