package team.dream.ClientSide;

import team.dream.shared.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection extends Thread {

    private int port = 44444;
    private static final ClientConnection client = new ClientConnection();
    private SingleClientProtocol clientProtocol = SingleClientProtocol.getClientProtocol();

    private ClientConnection() {}

    public static ClientConnection getClientConnection() {return client;}

    @Override
    public void run() {
        while (true) {
            try (Socket socket = new Socket("127.0.0.1", port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){
                Message messageFromServer;
                IO.println("ClientConnection: Waiting for server to send");
                while((messageFromServer = (Message) inputStream.readObject()) != null) {
                    IO.println("ClientConnection: Received from server");
                    outputStream.writeObject(clientProtocol.processInputFromServer(messageFromServer));
                    IO.println("ClientConnection: Sent to server");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
