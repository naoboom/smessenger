import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Бот Droid содан для тестирования
 * Пишет каждый три секунды сообщение в чат
 */
public class Droid {

    public static void main(String[] args) throws  Exception {
        Client client = new Client("localhost", 3456);
        client.setUserName("Droid");
        client.execute();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(
                () -> {
                     client.setMessage("Hello world! I am Droid!");
                    },
                0, 3,
                TimeUnit.SECONDS);

    }
}
