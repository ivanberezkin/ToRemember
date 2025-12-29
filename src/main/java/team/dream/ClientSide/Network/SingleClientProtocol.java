package team.dream.ClientSide.Network;

import team.dream.ClientSide.MVCPattern.ClientController;
import team.dream.ClientSide.MVCPattern.ClientModel;
import team.dream.ClientSide.MVCPattern.View;
import team.dream.shared.MemoryList;
import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.ArrayList;
import java.util.Scanner;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();
    Scanner scanner = new Scanner(System.in);
    private ClientModel model = new ClientModel();
    ClientController cc = new ClientController(model, new View());

    private SingleClientProtocol() {

    }

    public static SingleClientProtocol getClientProtocol() {
        return clientProtocol;
    }


    public Message processInputFromServer(Message messageFromServer) {
        switch (messageFromServer.getType()) {
            case USER_NOT_FOUND -> {
                if (messageFromServer.getData() instanceof String notFoundUsername) {
                    IO.println("ClientProtocol: User not found");
                    return getInputFromUserForUserNotFoundMessage(notFoundUsername);
                }
            }

            case STARTING_MENU -> {
                if (messageFromServer.getData() instanceof String loggedInUsername) {
                    model.setUser(loggedInUsername);
                    return cc.getInputFromStartingMenu();
                }
            }
            case CREATE_MEMORY_LIST -> {
                IO.println("ClientProtocol: Create Memory List");
                IO.println("ClientProtocol: Enter title of new memory list");
                String title = scanner.nextLine();
                String user = model.getUser();
//                MemoryList memoryList = new MemoryList(title, user);
//                model.getUsersMemoryList().add(memoryList);
//                return new Message(MessageType.STARTING_MENU, model);
            }
            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println("ClientProtocol: Show list of memory lists");
                model.updateUsersMemoryList((ArrayList<MemoryList>) messageFromServer.getData());

                return cc.getInputFromShowMemoryLists();

            }
            case SHOW_CHOSEN_MEMORY_LIST -> {
                if (messageFromServer.getData() instanceof MemoryList memoryListToShow) {
                    return cc.getInputFromChosenMemoryList(memoryListToShow);

                }
            }
        }
        IO.println("ClientProtocol: No return from switch triggered");
        return null;
    }

    private Message getInputFromUserForUserNotFoundMessage(String username) {
        IO.println("User not found, would you like to create " + username + "?" +
                "\nEnter 'Yes' or 'No' to try with different username.");
        while (true) {
            String inputFromUser = scanner.nextLine().trim();
            if (inputFromUser.equalsIgnoreCase("Yes")) {
                return new Message(MessageType.CREATE_NEW_USER, username);
            } else if (inputFromUser.equalsIgnoreCase("No")) {
                IO.println("Please try with a different username:");
                username = scanner.nextLine().trim();
                return new Message(MessageType.REQUEST_LOGIN, username);
            } else {
                IO.println("Unknown input, try again.");
            }
        }
    }

}