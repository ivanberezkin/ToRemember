package team.dream.ClientSide;

import javax.swing.*;
import java.util.Scanner;

public class ToRemember {
    static void main() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Connect to server = 1, Offline = 2");
        String inputUser = sc.nextLine();
        if(inputUser.equals("1")){
            ClientConnection client = ClientConnection.getClientConnection();
            client.start();
        }else{
            //Do offline stuff
        }

        }
}
