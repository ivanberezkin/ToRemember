package shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connections {
    private final String username;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public Connections(String username, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.username = username;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public String getUsername() {
        return username;
    }


    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }
}
