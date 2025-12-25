package team.dream.ServerSide;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try(ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){

            SingleServerProtocol serverProtocol = SingleServerProtocol.getServerProtocol();
            Object object;

            while((object = inputStream.readObject()) != null){
                outputStream.writeObject(serverProtocol.processInputFromClient(object));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
