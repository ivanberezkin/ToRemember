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
    private final Scanner sc;
    private boolean usernameConfirmation = false;
    private final ClientProtocol clientProtocol = ClientProtocol.getClientProtocol();

    private ClientConnection() {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            sc = new Scanner(System.in);


            while (!usernameConfirmation) {
                IO.println("Please enter your username: ");
                String username = sc.nextLine();
                if (!username.isEmpty()) {
                    //TODO FactoryMethod for message type ??
                    outputStream.writeObject(new Message(MessageType.REQUEST_LOGIN, username));
                    usernameConfirmation = true;
                }
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                IO.println("ClientConnection: Waiting for server to send");
                Message messageFromServer = (Message) inputStream.readObject();
                IO.println("ClientConnection: received from server");

                if (messageFromServer != null) {
                    clientProtocol.processInputFromServer(messageFromServer);
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
