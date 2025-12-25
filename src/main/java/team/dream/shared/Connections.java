package team.dream.shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Connections {
    private final String username;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private static final ArrayList<Connections> connectionsList = new ArrayList<>();


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

    public void addToConnectionList(String usernameToCheck, Connections connectionToAdd){
        if(!checkIfConnectionExist(usernameToCheck)){
            connectionsList.add(connectionToAdd);
            IO.println("CONNECTIONS: Connection added succesfully, total connections: "+ connectionsList.size());
        }
    }

    public static boolean checkIfConnectionExist(String username){
        for(Connections c : connectionsList){
            if(username.equalsIgnoreCase(c.username)){
                return true;
            }
        }
        return false;
    }
}
