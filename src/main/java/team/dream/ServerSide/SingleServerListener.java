package team.dream.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServerListener {
    private static final SingleServerListener instance = new SingleServerListener();
    private int port = 55555;
    private boolean running = true;


    private SingleServerListener() {

    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(running){
                Socket socket = serverSocket.accept();
                IO.println("SSL: Client Connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SingleServerListener getInstance() {
        return instance;
    }

    static void main() {
        SingleServerListener SSL = SingleServerListener.getInstance();
        SSL.start();
    }


}
