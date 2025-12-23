package ServerSide;


import mySqlDb.UsersMethod;
import shared.Message;
import shared.MessageType;
import shared.User;

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

    public void processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {

            case MessageType.REQUEST_LOGIN -> {
                if(inputFromClient.getData() instanceof String usernameToCheck){
                    if(UsersMethod.checkIfUserExistsInDB(usernameToCheck)){
                        IO.println("User exists");
                    }else{
                        IO.println("User not found");
                    }
                }
            }




            }
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
