package team.dream.ServerSide;

import team.dream.shared.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private SingleServerProtocol serverProtocol = SingleServerProtocol.getServerProtocol();

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try(ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){

            Message messageFromUser;
            IO.println("ClientHandler: Waiting for client to send");
            while((messageFromUser = (Message) inputStream.readObject()) != null){
//                IO.println("ClientHandler: Received from client");
                outputStream.reset();
                outputStream.writeObject(serverProtocol.processInputFromClient(messageFromUser));
//                IO.println("ClientHandler: Sent to client");
                outputStream.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}