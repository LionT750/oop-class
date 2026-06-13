package events;

import java.time.LocalDateTime;

public abstract class Event {
    private LocalDateTime timestamp;
    private String type;

    public Event(String type) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getType() { return type; }
}
