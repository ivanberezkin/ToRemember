package team.dream.ClientSide;

import javax.swing.*;

public class Main {
    static void main() {


        String inputUser = JOptionPane.showInputDialog("Connect to server = 1, Offline = 2");
        if(inputUser.equals("1")){
            ClientConnection client = ClientConnection.getClientConnection();
            client.start();
        }else{
            //Do offline stuff
        }

        }
}
