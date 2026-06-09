import java.util.UUID;
import java.time.*;

public class App {
    public static void main(String[] args) throws Exception {
        User user = new User(UUID.randomUUID(), Instant.now(), Instant.now(), true, "Lucas", "LK100@gmail.com", "48999999999", "Um belo dia de praia no marrocos");
    
        System.out.println(user.toString());
    
    }
}
