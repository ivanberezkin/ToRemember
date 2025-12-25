package team.dream.ServerSide;


import team.dream.mySqlDb.SQLTableFunctions;
import team.dream.mySqlDb.UsersMethodSQL;
import team.dream.shared.Connections;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import team.dream.unusedClasses.SingleUserDatabase;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();

    private SingleServerProtocol() {

    }

    public void processInputFromClient(Message messageFromClient) {

        if (messageFromClient != null) {
            switch (messageFromClient.getType()) {
                case MessageType.CREATE_NEW_USER -> {
                    if (messageFromClient.getData() instanceof String userNameToAddToSQL)
                        UsersMethodSQL.addUserToDB(userNameToAddToSQL);
                }
                case STARTING_MENU -> {
                    IO.println("Show starting menu");
//                    return new Message(MessageType.STARTING_MENU, null);

                }
                case SHOW_LIST_OF_MEMORY_LISTS -> {
                    IO.println("show list of memory lists");
//                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null);
                }
            }

        }

    }

    public void processLoginFromClient(Message inputFromClient, ObjectOutputStream oos, ObjectInputStream ois) {
        if (inputFromClient.getData() instanceof String usernameToCheck) {
            Connections connectionToClient = new Connections(usernameToCheck, oos, ois);

            SQLTableFunctions.createTableIfNotExist("users");
            if (UsersMethodSQL.checkIfUserExistsInDB(usernameToCheck)) {
                connectionToClient.addToConnectionList(usernameToCheck, connectionToClient);
                ClientHandler.sendMessageToClient(connectionToClient, new Message(MessageType.LOGIN_SUCCESSFUL, usernameToCheck));
            } else {
                ClientHandler.sendMessageToClient(connectionToClient, new Message(MessageType.USER_NOT_FOUND, usernameToCheck));

            }
        }
    }


    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
