package ServerSide;


import mySqlDb.UsersMethod;
import shared.Connections;
import shared.Message;
import shared.MessageType;
import shared.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static shared.MessageType.USER_NOT_FOUND;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();

    private SingleServerProtocol() {

    }

    public void processInputFromClient(Message messageFromClient) {

    }

    public void processLoginFromClient(Message inputFromClient, ObjectOutputStream oos, ObjectInputStream ois){
            if (inputFromClient.getData() instanceof String usernameToCheck) {
                Connections connectionToClient = new Connections(usernameToCheck,oos,ois);
                if (UsersMethod.checkIfUserExistsInDB(usernameToCheck)) {
                    connectionToClient.addToConnectionList(usernameToCheck, connectionToClient);
                } else {
                    ClientHandler.sendMessageToClient(connectionToClient,new Message(MessageType.USER_NOT_FOUND,usernameToCheck));

                }
            }
        }


    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
