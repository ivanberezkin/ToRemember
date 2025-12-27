package team.dream.shared;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Note {
    private String title;
    private String description;
    private int priorityIndex;
    private int categoryEnum;
    private boolean isDone;


    public Note(String title, String description, int priorityIndex, int categoryEnume) {
        this.title = title;
        this.description = description;
        this.priorityIndex = priorityIndex;
        this.categoryEnum = categoryEnum;
        this.isDone = false;
    }
}