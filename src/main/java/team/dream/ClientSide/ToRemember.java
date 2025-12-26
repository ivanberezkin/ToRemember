package team.dream.ClientSide;

import javax.swing.*;
import java.util.Scanner;

public class ToRemember {
    static void main() {

        try (Scanner sc = new Scanner(System.in)) {
            IO.println("Connect to server = 1, Offline = 2");
            String inputUser = sc.nextLine();
            if(inputUser.equals("1")){
                ClientConnection client = ClientConnection.getClientConnection();
                IO.println("Enter username:");
                client.setUsername(sc.nextLine());
                client.start();

                try{
                    client.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                //Do offline stuff
            }
        }
    }
}