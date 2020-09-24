import java.net.*;
import java.util.*;

public class Server {
    public static final int PORT = 3456;
    private  Set<String> userNames = new HashSet<>();
    private  Set<UserThread> userThreads = new HashSet<>();


    public static void main(String[] args) {
        Server server = new Server();
        server.execute();

    }

    public void execute () {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Started, waited for connection");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted. " + socket.getInetAddress() + " added new user.");
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }

            } catch (Exception ex) {
                System.out.println("Error in the server: " + ex.getMessage());
                ex.printStackTrace();
            }

    }

    /**
     *  Широковещательная рассылка одного абоненты всем
     */
    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Сохраняет ник нового абонента
     */
    void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * При отключении абонента удаляет его из списка и удаляет его UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Возвращает true если абонент в онлайне
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}

