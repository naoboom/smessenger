import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.Socket;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Клиентская часть чата.
 * Напечатайте bye для преращения программы
 */
public class Client {
    private String hostname;
    private int port;
    private String userName = "Anonim";
    private String message ="";
    private boolean nextMessage = false;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime startDateTime;
    {
        startDateTime = LocalDateTime.now();
    }
    public ObservableList<String> messages = FXCollections.observableArrayList("Клиент стартовал: " + dtf.format(startDateTime));

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String execute() {
        try {
            Socket socket = new Socket(hostname, port);
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
            return "Connected to the chat server. To disconnect, type \"bye\"";
        } catch (UnknownHostException ex) {
            return "Server not found: " + ex.getMessage();
        } catch (IOException ex) {
            return "I/O Error: " + ex.getMessage();
        }

    }


    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

    void setMessage(String msg) {
        this.nextMessage = !this.nextMessage;
        this.message = msg;
    }

    String getMessage() {
        return this.message;
    }

    boolean getNextMessage(){
        return this.nextMessage;
    }
    void setHostname(String name) {
        this.hostname = name;
    }

    void setPort(String name) {
        this.port = Integer.parseInt(name);
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 3456);
        client.execute();
    }
}
