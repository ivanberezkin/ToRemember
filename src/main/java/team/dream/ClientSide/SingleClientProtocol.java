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
}