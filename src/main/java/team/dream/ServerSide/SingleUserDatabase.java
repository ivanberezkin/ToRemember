package team.dream.ServerSide;

public class SingleUserDatabase {
    private static final SingleUserDatabase instance = new SingleUserDatabase();

    private SingleUserDatabase(){
    }

    public static SingleUserDatabase getInstance(){
        return instance;
    }



}
