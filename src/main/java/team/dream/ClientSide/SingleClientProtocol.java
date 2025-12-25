package team.dream.ClientSide;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import javax.swing.*;
import java.util.Scanner;

public class SingleClientProtocol {
    private static final SingleClientProtocol clientProtocol = new SingleClientProtocol();

    SingleClientProtocol(){

    }


    public void processInputFromServer(Message messageFromServer, ClientConnection client){

        switch(messageFromServer.getType()){
            case USER_NOT_FOUND -> {
                IO.println("SSL: USER_NOT_FOUND reached");
                if(messageFromServer.getData() instanceof String username){
                    getInputFromUserForUserNotFoundMessage(username, client);
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

    private static void getInputFromUserForUserNotFoundMessage(String username, ClientConnection client ){
        Scanner scan = new Scanner(System.in);

        IO.println("User not found, would you like to create " + username + "?" +
                "\nEnter 'Yes' or 'No' to try with different username.");

        String inputFromUser = scan.next();
        if(inputFromUser.equalsIgnoreCase("Yes")){
            client.sendMessageToServer(new Message(MessageType.CREATE_NEW_USER, username));
        }else if (inputFromUser.equalsIgnoreCase("No")){
            client.getUsernameFromUser();
        }else{
            IO.println("Unknown input, try again.");
        }
    }

    public static SingleClientProtocol getClientProtocol(){
        return clientProtocol;
    }
}
