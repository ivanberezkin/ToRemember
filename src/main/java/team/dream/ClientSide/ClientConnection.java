package team.dream.ClientSide;

import team.dream.shared.Message;
import team.dream.shared.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientConnection extends Thread {

    private int port = 44444;
    private static final ClientConnection client = new ClientConnection();
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    private final SingleClientProtocol singleClientProtocol = SingleClientProtocol.getClientProtocol();

    private ClientConnection() {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            getUsernameFromUser();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUsernameFromUser(){
        Scanner scan = new Scanner(System.in);
        IO.println("Please enter your username");
        boolean usernameConfirmation = false;
        while (!usernameConfirmation) {
            String username = scan.nextLine();
            if (!username.isEmpty()) {
                this.sendMessageToServer(new Message(MessageType.REQUEST_LOGIN, username));
                usernameConfirmation = true;
            } else{
                IO.println("Wrong format on username, try again");
            }
        }
    }

    public void sendMessageToServer(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message messageFromServer = (Message) inputStream.readObject();

                if (messageFromServer != null) {
                    singleClientProtocol.processInputFromServer(messageFromServer, this);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ClientConnection getClientConnection() {
        return client;
    }

}
