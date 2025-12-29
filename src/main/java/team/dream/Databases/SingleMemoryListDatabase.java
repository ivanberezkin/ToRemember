package team.dream.Databases;

import lombok.Data;
import team.dream.shared.MemoryList;
import team.dream.shared.User;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class SingleMemoryListDatabase {
    private static final SingleMemoryListDatabase instance = new SingleMemoryListDatabase();
    private List<MemoryList> memoryLists = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename = "src/main/resources/MemoryLists.json";

    private SingleMemoryListDatabase() {
    memoryLists = readMemoryListsFromFileJson();
    }

    public static SingleMemoryListDatabase getInstance() {
        return instance;
    }

    public void printSizeOfUsersMemoryLists(ArrayList<MemoryList> usersMemoryLists){
        for(MemoryList ml : usersMemoryLists){
            IO.println(ml.getTitle() + "    Size: " + ml.getNotes().size());
        }
    }

    private List<MemoryList> readMemoryListsFromFileJson() {
        return mapper.readValue(new File(filename), new TypeReference<List<MemoryList>>() {});
    }

    public ArrayList<MemoryList> getAllUsersMemoryLists(String username){
        ArrayList<MemoryList> usersMemoryLists = new ArrayList<>();
        for(MemoryList ml : memoryLists){
            if(username.equalsIgnoreCase(ml.getOwnerUsername())){
                usersMemoryLists.add(ml);
            }
        }
        return  usersMemoryLists;
    }

    public boolean isIDtaken(int idToCheck){
        for(MemoryList ml : memoryLists){
            if(ml.getMemoryListID() == idToCheck){
                return true;
            }
        }

        return false;
    }

    public void updateNotesInMemoryListInDB(MemoryList updatedMemoryList){
        for(MemoryList ml : memoryLists){
            if(ml.getMemoryListID() == updatedMemoryList.getMemoryListID()){
                ml.setNotes(updatedMemoryList.getNotes());
            }
        }
        writeMemoryListsToFile(memoryLists);
    }

    public void addNewMemoryListToDB(MemoryList memoryListToAdd){
        memoryLists.add(memoryListToAdd);
        writeMemoryListsToFile(memoryLists);
    }

    public void removeMemoryListFromDB(MemoryList memoryListToRemove){
        memoryLists.remove(memoryListToRemove);
        writeMemoryListsToFile(memoryLists);
    }


    private void writeMemoryListsToFile(List<MemoryList> memoryLists) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), memoryLists);
    }
}
