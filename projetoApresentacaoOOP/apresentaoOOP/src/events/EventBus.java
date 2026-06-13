package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private Map<String, List<EventListener>> listeners;

    public EventBus() {
        this.listeners = new HashMap<>();
    }

    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        if (list != null) {
            list.remove(listener);
            if (list.isEmpty()) {
                listeners.remove(eventType);
            }
        }
    }

    public void publish(Event event) {
        List<EventListener> list = listeners.get(event.getType());
        if (list != null) {
            for (EventListener l : list) {
                l.onEvent(event);
            }
        }
    }

    public int getListenerCount() {
        return listeners.values().stream().mapToInt(List::size).sum();
    }

    public int getEventTypeCount() {
        return listeners.size();
    }
}
