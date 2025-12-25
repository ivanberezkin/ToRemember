package shared;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private final MessageType type;
    private final Object data;

    public Message(MessageType type, Object data ) {
        this.type = type;
        this.data = data;
    }
}