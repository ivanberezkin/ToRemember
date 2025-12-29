package team.dream.ServerSide;


import team.dream.Databases.SingleMemoryListDatabase;
import team.dream.Databases.SingleUserDatabase;
import team.dream.shared.MemoryList;
import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.ArrayList;
import java.util.Random;


public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    private static final SingleUserDatabase userDatabase = SingleUserDatabase.getInstance();
    private static final SingleMemoryListDatabase singleMemoryListDatabase = SingleMemoryListDatabase.getInstance();

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
                    if (userDatabase.findExistingUser(usernameToCheck) != null) {
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
                    userDatabase.addNewUser(usernameToAddToDB);
                    IO.println("SSP: New User created");
                    return new Message(MessageType.STARTING_MENU, usernameToAddToDB);
                }
            }

            case CREATE_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof String titleOfNewMemoryList) {
                    Random randomID = new Random();
                    int assignedIdToNewMemoryList = randomID.nextInt(100);
                    while (singleMemoryListDatabase.isIDtaken(assignedIdToNewMemoryList)) {
                        assignedIdToNewMemoryList = randomID.nextInt(100);
                    }
                    MemoryList memoryListToAddToDB = new MemoryList(
                            titleOfNewMemoryList,
                            inputFromClient.getUsername(),
                            assignedIdToNewMemoryList);

                    singleMemoryListDatabase.addNewMemoryListToDB(memoryListToAddToDB);
                    IO.println("SSP: Memorylist added to DB succesfully");
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
                    singleMemoryListDatabase.removeMemoryListFromDB(memoryListToRemoveFromDB);
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
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, singleMemoryListDatabase.getAllUsersMemoryLists(ownerUsername), ownerUsername);
                }

            }
        }
        return null;
    }
}