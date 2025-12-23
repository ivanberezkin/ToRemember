package ClientSide;

import shared.Message;

import javax.swing.*;

public class ClientProtocol {
    private static final ClientProtocol clientProtocol = new ClientProtocol();

    ClientProtocol(){
    }


    public void processInputFromServer(Message messageFromServer){

        switch(messageFromServer.getType()){
            case USER_NOT_FOUND -> {
                if(messageFromServer.getData() instanceof String username){
                    JOptionPane.showMessageDialog(null,"User not found, creating new user " + username);
                }
            }
        }
    }

    public static ClientProtocol getClientProtocol(){
        return clientProtocol;
    }
}
