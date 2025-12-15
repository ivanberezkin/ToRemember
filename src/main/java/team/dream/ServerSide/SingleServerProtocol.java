package team.dream.ServerSide;

import team.dream.shared.Message;
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

    public void processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {

            }
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
