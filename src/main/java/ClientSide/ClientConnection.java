package ClientSide;

import shared.Message;
import shared.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection extends Thread {

    private int port = 55555;
    private static final ClientConnection client = new ClientConnection();

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private boolean usernameConfirmation = false;
    private final ClientProtocol clientProtocol = ClientProtocol.getClientProtocol();

    private ClientConnection() {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());


            while (!usernameConfirmation) {
                String username = JOptionPane.showInputDialog("Please enter your username: ");
                if (!username.isEmpty()) {
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
                Message messageFromServer = (Message) inputStream.readObject();

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
