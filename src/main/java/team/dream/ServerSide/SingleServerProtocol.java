package team.dream.ServerSide;

import team.dream.shared.Message;
import team.dream.shared.MessageType;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();

    private SingleServerProtocol() {}

    public Message processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {
            case REQUEST_LOGIN -> {
                IO.println("SSP: Request Login");
                if (true) { //TODO change boolean value to isRegisteredUser
                    IO.println("SSP: Login Success, found user");
                    return new Message(MessageType.STARTING_MENU, "test"); //TODO FactoryMethod for Message
                } else {
                    IO.println("SSP: Login creating new user");
                    String newUserUsername = (String) (inputFromClient.getData());
                    return new Message(MessageType.STARTING_MENU, null); //TODO FactoryMethod for Message
                    //TODO what is connectionsList used for?
                }
            }
            case STARTING_MENU -> {
                IO.println("SSP: Send starting menu model to user");
                //TODO send starting menu model to client side MVC
                return new Message(MessageType.STARTING_MENU, null);

            }
            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println("SSP: Send list of memory lists model to user");
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null);
            }
        }
        return null;
    }

    public static SingleServerProtocol getServerProtocol() {return serverProtocol;}
}