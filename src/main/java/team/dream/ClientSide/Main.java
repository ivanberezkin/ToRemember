package team.dream.ClientSide;

public class Main {
    static void main() {
       ClientConnection client = ClientConnection.getClientConnection();
       client.start();
        }
}
