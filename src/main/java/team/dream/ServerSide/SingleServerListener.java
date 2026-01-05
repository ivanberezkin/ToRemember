package team.dream.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServerListener {
    private static final SingleServerListener instance = new SingleServerListener();
    private int port = 44444;

    private SingleServerListener() {}

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(true){
                Socket socket = serverSocket.accept();
                IO.println("SSL: Client Connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SingleServerListener getInstance() {return instance;}

    public static void main(String[] args) {
        SingleServerListener SSL = SingleServerListener.getInstance();
        SSL.start();
    }
}