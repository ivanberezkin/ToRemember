package team.dream.ClientSide;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import javax.swing.*;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();

    SingleClientProtocol(){

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
            case LOGIN_SUCCESSFUL -> {
                if(messageFromServer.getData() instanceof String loggedInUsername)
                IO.println("Hello " + loggedInUsername + "!");
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

    public static SingleClientProtocol getClientProtocol(){
        return clientProtocol;
    }
}
