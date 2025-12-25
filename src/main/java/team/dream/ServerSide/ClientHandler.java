package team.dream.ServerSide;

import team.dream.shared.Connections;
import team.dream.shared.Message;
import team.dream.shared.MessageType;
import team.dream.shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private SingleServerProtocol serverProtocol = SingleServerProtocol.getServerProtocol();


    ClientHandler(Socket socket) {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessageToClient(Connections conn, Message message){
        try {
            conn.getOutputStream().writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                Message inputFromClient = (Message) inputStream.readObject();

                if(inputFromClient.getType().equals(MessageType.REQUEST_LOGIN)){
                    serverProtocol.processLoginFromClient(inputFromClient, outputStream,inputStream);
                }else{
                    serverProtocol.processInputFromClient(inputFromClient);
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
