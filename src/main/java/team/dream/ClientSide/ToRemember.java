package team.dream.ClientSide;

import team.dream.ClientSide.Network.ClientConnection;

import java.util.Scanner;

public class ToRemember {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ClientConnection client = ClientConnection.getClientConnection();
            String usernameInputFromUser;
            while(true){
                IO.println("Enter username:");
                usernameInputFromUser = sc.nextLine();
                if(usernameInputFromUser.isEmpty()){
                    IO.println("Username can't be empty. Try again!");
                }else{
                    client.setUsername(usernameInputFromUser);
                    client.start();
                    break;
                }
            }

            try {
                client.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
