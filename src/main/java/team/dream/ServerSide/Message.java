package team.dream.ServerSide;

public class Message {
    private final MessageType type;
    private final Object data;

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
