package team.dream.ClientSide;

import team.dream.shared.Message;

import javax.swing.*;

public class ClientProtocol {
    private static final ClientProtocol clientProtocol = new ClientProtocol();

    ClientProtocol(){
    }


    public void processInputFromServer(Message messageFromServer){
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
    }

    public static ClientProtocol getClientProtocol(){
        return clientProtocol;
    }
}
