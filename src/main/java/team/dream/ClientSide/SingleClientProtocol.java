package team.dream.ClientSide;

import team.dream.shared.Message;
import team.dream.shared.MessageType;
import java.util.Scanner;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();
    Scanner scanner = new Scanner(System.in);

    private SingleClientProtocol(){}

    public static SingleClientProtocol getClientProtocol(){return clientProtocol;}

    public Message processInputFromServer(Message messageFromServer) {
        switch (messageFromServer.getType()) {
            case USER_NOT_FOUND -> {
                IO.println("ClientProtocol: User not found");
                //TODO create model view control
                return null;
            }
            case LOGIN_SUCCESSFUL -> {
                if(messageFromServer.getData() instanceof String loggedInUsername)
                    IO.println("Hello " + loggedInUsername + "!");
            }
            case STARTING_MENU -> {
                IO.println("ClientProtocol: Show starting menu");
                scanner.nextLine(); //TODO menu for choosing valid actions
                IO.println("ClientProtocol: Send menu choice made");
                //TODO create model view control
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null); //TODO FactoryMethod
            }
            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println("ClientProtocol: Show list of memory lists");
                scanner.nextLine(); //TODO menu for choosing valid actions
                IO.println("ClientProtocol: Send list choice made");
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null); //TODO FactoryMethod
            }
            case SHOW_CHOSEN_MEMORY_LIST ->  {
                IO.println("ClientProtocol: Show chosen memory list");
                scanner.nextLine(); //TODO menu for choosing valid actions
                IO.println("ClientProtocol: Send action chosen");
                return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, null); //TODO FactoryMethod
            }
        }
        IO.println("ClientProtocol: No return from switch triggered");
        return null;
    }
    private static void getInputFromUserForUserNotFoundMessage(String username, ClientConnection client ){
        Scanner scan = new Scanner(System.in);

        IO.println("User not found, would you like to create " + username + "?" +
                "\nEnter 'Yes' or 'No' to try with different username.");

        String inputFromUser = scan.next();
        if(inputFromUser.equalsIgnoreCase("Yes")){
            client.sendMessageToServer(new Message(MessageType.CREATE_NEW_USER, username));
        }else if (inputFromUser.equalsIgnoreCase("No")){
            client.getUsernameFromUser();
        }else{
            IO.println("Unknown input, try again.");
        }
    }
}