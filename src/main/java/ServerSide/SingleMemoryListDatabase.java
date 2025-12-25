package ServerSide;

import shared.MemoryList;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SingleMemoryListDatabase {
    private static final SingleMemoryListDatabase instance = new SingleMemoryListDatabase();
    private List<MemoryList> memoryLists = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename = "src/main/resources/MemoryLists.json";

    private SingleMemoryListDatabase() {
    }

    public static SingleMemoryListDatabase getInstance() {
        return instance;
    }

    private List<MemoryList> readMemoryListsFromFileJson() {
        return mapper.readValue(new File(filename), new TypeReference<List<MemoryList>>() {});
    }

    private void writeMemoryListsToFile(List<MemoryList> memoryLists) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), memoryLists);
    }


    public List<MemoryList> getMemoryLists() {
        return memoryLists;
    }

    public void setMemoryLists(List<MemoryList> memoryLists) {
        this.memoryLists = memoryLists;
    }
}
