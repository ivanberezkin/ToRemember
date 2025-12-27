package team.dream.ClientSide;

import javax.swing.*;
import java.util.Scanner;

public class ToRemember {
    static void main() {

        try (Scanner sc = new Scanner(System.in)) {
            ClientConnection client = ClientConnection.getClientConnection();
            IO.println("Enter username:");
            client.setUsername(sc.nextLine());
            client.start();

            try {
                client.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
