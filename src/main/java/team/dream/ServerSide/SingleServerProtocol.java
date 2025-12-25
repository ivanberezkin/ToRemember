package team.dream.ServerSide;

import team.dream.shared.Connections;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import team.dream.shared.User;

import java.util.ArrayList;
import java.util.List;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    private SingleUserDatabase userDatabase = SingleUserDatabase.getUserDB();
    private List<Connections> connectionsList = new ArrayList<>();

    private SingleServerProtocol() {}

    public Message processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {
            case REQUEST_LOGIN -> {
                    User existingUser = serverProtocol.verifyUserInUserDatabase(inputFromClient);
                    if (existingUser != null) {
                        //TODO FactoryMethod for Message
                        return new Message(MessageType.STARTING_MENU, null);
                    } else {
                        String newUserUsername = (String) (inputFromClient.getData());
                        userDatabase.addNewUser(newUserUsername);
                        //TODO what is connectionsList used for?
                    }
            }
            case STARTING_MENU -> {
                IO.println("Show starting menu");
                return new Message(MessageType.STARTING_MENU, null);

            }
            case SHOW_LIST_OF_MEMORY_LISTS -> {
                IO.println("show list of memory lists");
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, null);
            }
        }
        return null;
    }

    public User verifyUserInUserDatabase(Message loginRequest){
        User user = null;
        if(loginRequest.getData() instanceof String usernameFromClient){
               user = userDatabase.findExistingUser(usernameFromClient);
       }
        return user;
    }


    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
