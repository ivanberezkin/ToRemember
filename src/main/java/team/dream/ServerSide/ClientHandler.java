package team.dream.ServerSide;

import team.dream.shared.Message;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
            while((messageFromUser = (Message) inputStream.readObject()) != null){
                outputStream.reset();
                outputStream.writeObject(serverProtocol.processInputFromClient(messageFromUser));
                outputStream.flush();
            }
        }catch (EOFException |SocketException e){
            IO.println("Client disconnected.");
        } catch (Exception e){
            IO.println("Unexpected error.");
        }
    }
}