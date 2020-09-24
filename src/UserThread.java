import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Этот обработчик потоков для каждого абонента
 * сервер общается одновременно с каждым абонентом в отдельном потоке
 */
public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    DBCon db = new DBCon();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime startDateTime;
    {
        startDateTime = LocalDateTime.now();
    }
    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            db.connect();

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                startDateTime = LocalDateTime.now();
                serverMessage = dtf.format(startDateTime) + " [" + userName + "]: " + clientMessage;

                server.broadcast(serverMessage, this);
                db.writeMessageToDB(dtf.format(startDateTime), userName, clientMessage);

            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);

            db.close();

        } catch (Exception ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *  Печатает список подключеных абонентов
     */
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    /**
     *  Отправляет сообщение в поток
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}