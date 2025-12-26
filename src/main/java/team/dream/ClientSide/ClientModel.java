package team.dream.ClientSide;

import team.dream.Databases.SingleMemoryListDatabase;
import team.dream.shared.MemoryList;
import team.dream.shared.User;

import java.util.ArrayList;
import java.util.List;

public class ClientModel {

    SingleMemoryListDatabase singleMemoryListDatabase = SingleMemoryListDatabase.getInstance();
    private User user;
    private List<MemoryList> usersMemoryList = new ArrayList<>();


    ClientModel(User user){
        this.user = user;
        //TODO här behöver vi göra så att en funktion i singleMemoryListDatabase
        // bara returnerar memory lists som tillhör user.
        usersMemoryList = singleMemoryListDatabase.getMemoryLists();
    }

}
