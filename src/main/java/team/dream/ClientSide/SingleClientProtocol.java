package team.dream.ClientSide;

import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.Scanner;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();
    Scanner scanner = new Scanner(System.in);
    private ClientModel model;
    private View view;


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
                    model = new ClientModel(loggedInUsername);
                    view = new View(model);
                    ClientController cc = new ClientController(model,view);

                    return  cc.getInputFromStartingMenu();
                }
            }
            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println("ClientProtocol: Show list of memory lists");
                scanner.nextLine(); //TODO menu for choosing valid actions
                IO.println("ClientProtocol: Send list choice made");
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null); //TODO FactoryMethod
            }
            case SHOW_CHOSEN_MEMORY_LIST -> {
                IO.println("ClientProtocol: Show chosen memory list");
                scanner.nextLine(); //TODO menu for choosing valid actions
                IO.println("ClientProtocol: Send action chosen");
                return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, null); //TODO FactoryMethod
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