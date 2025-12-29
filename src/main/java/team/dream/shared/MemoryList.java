package team.dream.shared;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemoryList implements Serializable {
    private List<Note> notes = new ArrayList<>();
    private String title;
    private String ownerUsername;
    private List<User> users = new ArrayList<>();
    private int memoryListID;

    public MemoryList() {}

    public MemoryList(String title, String ownerUsername, int memoryListID) {
        this.title = title;
        this.ownerUsername = ownerUsername;
        this.memoryListID = memoryListID;
    }

    public void addNoteToMemoryList(Note noteToAdd){
        notes.add(noteToAdd);
        IO.println("Note " + noteToAdd.getTitle() + " successfully added to " + title);
    }

}
