package ClientSide;
import shared.Connections;
import shared.Message;
import shared.MessageType;
import javax.swing.*;

public class ClientProtocol {
    private static final ClientProtocol clientProtocol = new ClientProtocol();

    ClientProtocol(){

    }


    public void processInputFromServer(Message messageFromServer, ClientConnection client){

        switch(messageFromServer.getType()){
            case USER_NOT_FOUND -> {
                if(messageFromServer.getData() instanceof String username){
                    int result = JOptionPane.showConfirmDialog(null,"" +
                            "User not found, would you like to create " + username + "?", "Create User?", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        client.sendMessageToServer(new Message(MessageType.CREATE_NEW_USER, username));
                    }else if (result == JOptionPane.NO_OPTION){
                        client.getUsernameFromUser();
                    }
                }
            }
        }
    }

    public static ClientProtocol getClientProtocol(){
        return clientProtocol;
    }
}
