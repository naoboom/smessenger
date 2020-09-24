import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Бот Bender содан для тестирования
 * Пишет каждый три секунды сообщение в чат
 */
public class Bender {

    public static void main(String[] args) {
        Client client = new Client("localhost", 3456);
        client.setUserName("Bender");
        client.execute();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(
                () -> {
                    client.setMessage("I am Going To Build My Own Theme Park! With Blackjack! And Hookers!");
                },
                1, 3,
                TimeUnit.SECONDS);
    }
}
