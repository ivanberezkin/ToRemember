package team.dream.shared;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private final MessageType type;
    private final Object data;
    private String username;

    public Message(MessageType type, Object data, String username) {
        this.type = type;
        this.data = data;
        this.username = username;
    }

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

}