package team.dream.ServerSide;

import team.dream.mySqlDb.ConnectionToSQL;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServerListener {
    private static final SingleServerListener instance = new SingleServerListener();
    private int port = 55555;
    private boolean running = true;
    private ConnectionToSQL connectionToDBClass = ConnectionToSQL.getInstance();

    private SingleServerListener() {}

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(running){
                Socket socket = serverSocket.accept();
                System.out.println("SSL: Client Connected");

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

    static void main(String [] args) {
        SingleServerListener SSL = SingleServerListener.getInstance();
        SSL.start();
    }


}
