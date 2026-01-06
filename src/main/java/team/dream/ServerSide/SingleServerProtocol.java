package team.dream.ServerSide;


import team.dream.Databases.ConnectionToSQL;
import team.dream.Databases.MemoryListMethodSQL;
import team.dream.Databases.SQLTableFunctions;
import team.dream.Databases.UsersMethodSQL;
import team.dream.oldDatabases.SingleMemoryListDatabase;
import team.dream.oldDatabases.SingleUserDatabase;
import team.dream.shared.MemoryList;
import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.Random;


public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    private static final SingleUserDatabase userDatabase = SingleUserDatabase.getInstance();
    private static final SingleMemoryListDatabase singleMemoryListDatabase = SingleMemoryListDatabase.getInstance();
    private static final ConnectionToSQL connToSQL = ConnectionToSQL.getInstance();
    private final String userTableName = "users";
    private final String memoryListTableName = "memorylist";

    private SingleServerProtocol() {
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

    public Message processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {
            case REQUEST_LOGIN -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof String usernameToCheck) {
                    SQLTableFunctions.createTableIfNotExist(userTableName);
                    if (UsersMethodSQL.checkIfUserExistsInDB(usernameToCheck)) {
                        IO.println("User found, Login Successful");
                        return new Message(MessageType.STARTING_MENU, usernameToCheck);
                    } else {
                        IO.println("User not found, sending User Not Found Message");
                        return new Message(MessageType.USER_NOT_FOUND, usernameToCheck);
                    }
                }
            }

            case CREATE_NEW_USER -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof String usernameToAddToDB) {
                    UsersMethodSQL.addUserToDB(usernameToAddToDB);
                    IO.println("SSP: New User created");
                    return new Message(MessageType.STARTING_MENU, usernameToAddToDB);
                }
            }

            case CREATE_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof String titleOfNewMemoryList) {
                    SQLTableFunctions.createMemoryListTableIfNotExist(memoryListTableName);
                    MemoryListMethodSQL.createNewMemoryList(inputFromClient.getUsername(), titleOfNewMemoryList);
                    return new Message(MessageType.STARTING_MENU, inputFromClient.getUsername());
                }
            }

            case STARTING_MENU -> {
                if (inputFromClient.getData() instanceof String username) {

                    IO.println(inputFromClient.getType() + " received from client");
                    //TODO send starting menu model to client side MVC
                    return new Message(MessageType.STARTING_MENU, username);
                }
            }

            case UPDATE_NOTE -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList updatedMemoryListWithUpdatedNote) {
                    singleMemoryListDatabase.updateNotesInMemoryListInDB(updatedMemoryListWithUpdatedNote);
                    return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, updatedMemoryListWithUpdatedNote, inputFromClient.getUsername());
                }
            }

            case REMOVE_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList memoryListToRemoveFromDB) {
                    MemoryListMethodSQL.removeMemoryList(memoryListToRemoveFromDB);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, singleMemoryListDatabase.getAllUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            case CREATE_NOTE -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList updatedMemoryListFromClient) {
                    singleMemoryListDatabase.updateNotesInMemoryListInDB(updatedMemoryListFromClient);

                    //For Troubleshooting purposes.
//                    singleMemoryListDatabase.printSizeOfUsersMemoryLists(singleMemoryListDatabase.getAllUsersMemoryLists(inputFromClient.getUsername()));
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, singleMemoryListDatabase.getAllUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            //TODO detta steg känns egentligen lite onödig, men tar det för att det ska vara tydligare tillsvidare.
            case SHOW_CHOSEN_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList memoryListToShow) {
                    return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, memoryListToShow, inputFromClient.getUsername());
                }
            }

            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof String ownerUsername) {
                    SQLTableFunctions.createMemoryListTableIfNotExist(memoryListTableName);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, MemoryListMethodSQL.showUsersMemoryLists(ownerUsername), ownerUsername);
                }

            }
        }
        return null;
    }
}