package ServerSide;

import shared.Connections;
import shared.Message;
import shared.MessageType;
import shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private SingleServerProtocol serverProtocol = SingleServerProtocol.getServerProtocol();
    private static final ArrayList<Connections> connectionsList = new ArrayList<>();


    ClientHandler(Socket socket) {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                Message inputFromClient = (Message) inputStream.readObject();

                if(inputFromClient != null){
                    serverProtocol.processInputFromClient(inputFromClient);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
