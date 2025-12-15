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
    public void run(){
        try{
            while(true){
                Message inputFromClient = (Message) inputStream.readObject();

                if(inputFromClient != null && inputFromClient.getType().equals(MessageType.REQUEST_LOGIN)){
                   User existingUser = serverProtocol.verifyUserInUserDatabase(inputFromClient);
                    if(existingUser != null){
                        connectionsList.add(new Connections(existingUser.getUserName(),outputStream,inputStream));
                        IO.println("CLIENTHANDLER: New Connection added. Total connections: " + connectionsList.size());
                    }else{
                        outputStream.writeObject(new Message(MessageType.USER_NOT_FOUND,null));
                    }
                }else if(inputFromClient != null){
                    serverProtocol.processInputFromClient(inputFromClient);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
