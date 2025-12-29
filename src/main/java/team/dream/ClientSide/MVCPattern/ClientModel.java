package team.dream.ClientSide.MVCPattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import team.dream.shared.MemoryList;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientModel {


    private String user;
    private List<MemoryList> usersMemoryList = new ArrayList<>();
    private List<MemoryList> sharedMemoryList = new ArrayList<>();


    ClientModel(String user){
        this.user = user;
        //TODO här behöver vi göra så att en funktion i singleMemoryListDatabase
        // bara returnerar memory lists som tillhör user.
    }

    public void updateUsersMemoryList(ArrayList<MemoryList> updatedList){
        usersMemoryList.clear();
        usersMemoryList.addAll(updatedList);

    }

}
