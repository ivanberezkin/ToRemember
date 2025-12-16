package team.dream.shared;

import java.util.ArrayList;
import java.util.List;

public class MemoryList {
    private List<Note> notes = new ArrayList<>();

    public MemoryList(List<Note> notes) {
        this.notes = notes;
    }

    public MemoryList() {
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
