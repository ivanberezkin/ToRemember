package shared;

import lombok.Data;

import java.io.Serializable;
import java.sql.Connection;

@Data
public class Message implements Serializable {
    private final MessageType type;
    private final Object data;
    private final Connections clientConnection;

    public Message(MessageType type, Object data, Connections clientConnection) {
        this.type = type;
        this.data = data;
        this.clientConnection = clientConnection;
    }
}