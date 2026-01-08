package team.dream.ServerSide;


import team.dream.Databases.*;
import team.dream.shared.MemoryList;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import team.dream.shared.Note;
import java.util.Comparator;



public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    private final String userTableName = "users";
    private final String memoryListTableName = "memorylist";
    private final String notelistTableName = "notelist";

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
                    return new Message(MessageType.STARTING_MENU, username);
                }
            }

            case UPDATE_NOTE -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof Note updatedNote) {
                    NotelistMethodSQL.updateNoteInSQL(updatedNote);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, MemoryListMethodSQL.showUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            case REMOVE_NOTE -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof Note noteToRemove) {
                    NotelistMethodSQL.removeNoteInMemoryList(noteToRemove);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, MemoryListMethodSQL.showUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            case REMOVE_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList memoryListToRemoveFromDB) {
                    MemoryListMethodSQL.removeMemoryList(memoryListToRemoveFromDB);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, MemoryListMethodSQL.showUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            case CREATE_NOTE -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof Note newNoteToAdd) {
                    NotelistMethodSQL.addNewNoteToSQL(newNoteToAdd);
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, MemoryListMethodSQL.showUsersMemoryLists(inputFromClient.getUsername()), inputFromClient.getUsername());
                }
            }

            case SHOW_CHOSEN_MEMORY_LIST -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList memoryListToShow) {
                    SQLTableFunctions.createNoteListTableIfNotExist(notelistTableName);
                    memoryListToShow.setNotes(MemoryListMethodSQL.showMemoryListWithNotes(memoryListToShow.getMemoryListID()));
                    return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, memoryListToShow, inputFromClient.getUsername());
                }
            }

            case SORT_NOTES_BY_PRIORITY -> {
                IO.println(inputFromClient.getType() + " received from client");
                if (inputFromClient.getData() instanceof MemoryList memoryListToShow) {
                    memoryListToShow.setNotes(MemoryListMethodSQL.showMemoryListWithNotes(memoryListToShow.getMemoryListID()));
                    memoryListToShow.getNotes().sort(Comparator.comparingInt(Note::getPriorityIndex));
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