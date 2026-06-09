import java.time.*;
import java.util.UUID;

public class BaseEntity {
    public UUID id;
    public Instant createdAt;
    public Instant updatedAt;
    public Boolean isActive;


    public BaseEntity(UUID id, Instant createdAt, Instant updatedAt, Boolean isActive) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

     public String toString(){
        return "dumb method";
     }
    
}
