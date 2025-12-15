package team.dream.ClientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection extends Thread{

    private int port = 55555;
    private static final ClientConnection client = new ClientConnection();

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;


    private ClientConnection() {
        try{
            Socket socket = new Socket("127.0.0.1", port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            //Test skicka vid skapande
            outputStream.writeObject(new String("test"));

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        while(true){
            try {
                Object objectFromServer = inputStream.readObject();

                if(objectFromServer != null){
                    IO.println("Svar från server received");

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ClientConnection getClientConnection(){
        return client;
    }

}
