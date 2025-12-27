package team.dream.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemoryList implements Serializable {
    private List<Note> notes = new ArrayList<>();
    private String title;
    private String userID;
    private List<User> users = new ArrayList<>();

    public MemoryList() {}

    public MemoryList(String title, String userID) {
        this.title = title;
        this.userID = userID;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
