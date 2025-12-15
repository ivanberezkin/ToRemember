package team.dream.ServerSide;

import team.dream.shared.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    SingleServerProtocol serverProtocol = SingleServerProtocol.getServerProtocol();

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

                if(inputFromClient != null){
                    serverProtocol.processInputFromClient(inputFromClient);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
