package team.dream.ServerSide;



import team.dream.Databases.SingleUserDatabase;
import team.dream.shared.Message;
import team.dream.shared.MessageType;


public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    private static final SingleUserDatabase userDatabase = SingleUserDatabase.getInstance();

    private SingleServerProtocol() {
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

    public Message processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {
            case REQUEST_LOGIN -> {
                if (inputFromClient.getData() instanceof String usernameToCheck){
                    if(userDatabase.findExistingUser(usernameToCheck) != null){
                        IO.println("User found, Login Successful");
                        return new Message(MessageType.STARTING_MENU, usernameToCheck);
                    }else{
                        IO.println("User not found, sending User Not Found Message");
                        return new Message(MessageType.USER_NOT_FOUND, usernameToCheck);
                    }
                }
            }

            case CREATE_NEW_USER -> {
                if (inputFromClient.getData() instanceof String usernameToAddToDB) { //TODO change boolean value to isRegisteredUser
                       userDatabase.addNewUser(usernameToAddToDB);
                        IO.println("SSP: New User created");
                        return new Message(MessageType.STARTING_MENU, usernameToAddToDB);
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

    /*
    // kommenterar ur koden för den bryter separation of concern då min tanke är
    // att klassen ClientConnection ansvarar för att skicka och ta emor data
    // Jag går att övertalas om ni anser det här vara en bättre lösning

    public void processLoginFromClient(Message inputFromClient, ObjectOutputStream oos, ObjectInputStream ois) {
        if (inputFromClient.getData() instanceof String usernameToCheck) {
            Connections connectionToClient = new Connections(usernameToCheck, oos, ois);

            if (inputFromClient.getType().equals(MessageType.CREATE_NEW_USER)) {

                    connectionToClient.addToConnectionList(usernameToCheck, connectionToClient);
                    ClientHandler.sendMessageToClient(connectionToClient, new Message(MessageType.LOGIN_SUCCESSFUL, usernameToCheck));
            } else {

                if (UsersMethodSQL.checkIfUserExistsInDB(usernameToCheck)) {
                    connectionToClient.addToConnectionList(usernameToCheck, connectionToClient);
                    ClientHandler.sendMessageToClient(connectionToClient, new Message(MessageType.LOGIN_SUCCESSFUL, usernameToCheck));
                } else {
                    ClientHandler.sendMessageToClient(connectionToClient, new Message(MessageType.USER_NOT_FOUND, usernameToCheck));

                }
            }
        }
    }
     */
}