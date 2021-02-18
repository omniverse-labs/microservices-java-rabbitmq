package messaging.models;

public class Event {
    private String eventType;
    private Object eventInfo;

    public Event(String eventType, Object eventInfo) {
        this.eventInfo = eventInfo;
        this.eventType = eventType;
    }

    public Event(String eventType) {
        this.eventInfo = null;
        this.eventType = eventType;
    }

    public String getEventType() {
        return this.eventType;
    }

    public Object getEventInfo() {
        return this.eventInfo;
    }

    public String toString() {
        return String.format("event (%s, %s)", eventType, eventInfo);
    }
}
