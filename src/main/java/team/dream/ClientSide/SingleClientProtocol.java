package team.dream.ClientSide;

import team.dream.shared.Message;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();

    private SingleClientProtocol(){}

    public static SingleClientProtocol getClientProtocol(){return clientProtocol;}

    public Message processInputFromServer(Message messageFromServer){
        switch(messageFromServer.getType()){
            case USER_NOT_FOUND -> {
                if(messageFromServer.getData() instanceof String username){
                    IO.println("ClientProtocol: User not found. Username: " + username);
                }
            }
            case STARTING_MENU -> {
                //TODO create model view controll
                IO.println("""
                        1. See memory lists
                        2. Create memory list
                        3. Delete memory list
                        4. Quit""");

            }
        }
        return null;
    }

}
