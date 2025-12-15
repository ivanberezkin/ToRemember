package team.dream.ServerSide;

import team.dream.shared.Message;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();
    SingleUserDatabase userDatabase = SingleUserDatabase.getUserDB();

    private SingleServerProtocol() {

    }

    public void processInputFromClient(Message inputFromClient) {
        switch (inputFromClient.getType()) {
            case REQUEST_LOGIN -> {
                IO.println("User requesting login");
            }
        }
    }

    public static SingleServerProtocol getServerProtocol() {
        return serverProtocol;
    }

}
