import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *  Этот поток отвечает за чтение и отправку на сервер того что абонент печатает.
 *  Прекращает преоцеесс если напечатать bye
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {


        String userName = client.getUserName();
        Scanner scan = new Scanner(System.in);

        client.setUserName(userName);
        writer.println(userName);

        boolean nextMessage = client.getNextMessage();
        String newText = "";
        do {
         //    String msg = scan.nextLine();
         //   newText = (msg);
            System.out.print("");

            if (client.getNextMessage() != nextMessage) {
                newText = client.getMessage();
                writer.println(newText);
                nextMessage = client.getNextMessage();
            }

        } while (!newText.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}