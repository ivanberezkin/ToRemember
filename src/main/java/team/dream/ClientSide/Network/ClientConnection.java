package team.dream.ClientSide.Network;

import team.dream.shared.Message;
import team.dream.shared.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection extends Thread {
    private int port = 44444;
    private static final ClientConnection client = new ClientConnection();
    private SingleClientProtocol clientProtocol = SingleClientProtocol.getClientProtocol();
    private String username;

    private ClientConnection() {}

    public static ClientConnection getClientConnection() {return client;}

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){

            outputStream.writeObject(new Message(MessageType.REQUEST_LOGIN, username));
            outputStream.flush();

            Message messageFromServer;

            while(true) {
                try{
                    messageFromServer = (Message) inputStream.readObject();
//                    IO.println("ClientConnection: Received from server");
                    outputStream.reset();
                    outputStream.writeObject(clientProtocol.processInputFromServer(messageFromServer));
                    outputStream.flush();
//                    IO.println("ClientConnection: Sent to server");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void getUsernameFromUser(){
        Scanner scan = new Scanner(System.in);
        IO.println("Please enter your username");
        boolean usernameConfirmation = false;
        while (!usernameConfirmation) {
            String username = scan.nextLine();
            if (!username.isEmpty()) {
                this.sendMessageToServer(new Message(MessageType.REQUEST_LOGIN, username));
                usernameConfirmation = true;
            } else{
                IO.println("Wrong format on username, try again");
            }
        }

 */
/*
        public void sendMessageToServer(Message message){
            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

 */
}