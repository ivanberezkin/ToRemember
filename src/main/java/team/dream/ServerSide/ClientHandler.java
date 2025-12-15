package team.dream.ServerSide;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;


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
                Message inputFromClient = null;
                inputFromClient = (Message) inputStream.readObject();

                if(inputFromClient != null){
                    //TODO lägga till logik som kollar vad det är för objekt.

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
