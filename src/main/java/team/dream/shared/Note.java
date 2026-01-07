package team.dream.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note implements Serializable {
    private int noteID;
    private int memoryListID;
    private String title;
    private String description;
    private int priorityIndex;
    private Category categoryEnum;
    private boolean isDone;

    public Note(int memoryListID, String title, String description, int priorityIndex, Category categoryEnum) {
        this.memoryListID = memoryListID;
        this.title = title;
        this.description = description;
        this.priorityIndex = priorityIndex;
        this.categoryEnum = categoryEnum;
        this.isDone = false;
    }

    public Note(int noteID, int memoryListID, String title, String description, int priorityIndex, Category categoryEnum) {
        this.noteID = noteID;
        this.memoryListID = memoryListID;
        this.title = title;
        this.description = description;
        this.priorityIndex = priorityIndex;
        this.categoryEnum = categoryEnum;
        this.isDone = false;
    }

    public void printNote(){
        IO.println("Title: " + title);
        IO.println("Description: " + description);
        IO.println("Priority: " + priorityIndex);
        IO.println("Category: " + categoryEnum.toString());
    }

}