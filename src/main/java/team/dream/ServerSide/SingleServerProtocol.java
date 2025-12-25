package team.dream.ServerSide;

import team.dream.shared.Connections;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import team.dream.shared.User;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    SingleUserDatabase userDatabase = SingleUserDatabase.getUserDB();

    private SingleServerProtocol() {

    }

    public User verifyUserInUserDatabase(Message loginRequest){
        User user = null;
        if(loginRequest.getData() instanceof String usernameFromClient){
               user = userDatabase.findExistingUser(usernameFromClient);
       }
        return user;
    }

    public Object processInputFromClient(Object inputFromClient) {
        Message inputFromClient = (Message) inputStream.readObject();

        if(inputFromClient != null && inputFromClient.getType().equals(MessageType.REQUEST_LOGIN)){
            User existingUser = serverProtocol.verifyUserInUserDatabase(inputFromClient);
            if(existingUser != null){
                connectionsList.add(new Connections(existingUser.getUserName(),outputStream,inputStream));
                outputStream.writeObject(new Message(MessageType.STARTING_MENU, ""));
            }else{
                outputStream.writeObject(new Message(MessageType.USER_NOT_FOUND,inputFromClient.getData()));
                String newUserUsername = (String)(inputFromClient.getData());
                singleUserDatabase.addNewUser(newUserUsername);
                connectionsList.add(new Connections(newUserUsername,outputStream,inputStream));
            }
        }else if(inputFromClient != null){
            serverProtocol.processInputFromClient(inputFromClient);
        }
        return null;
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
