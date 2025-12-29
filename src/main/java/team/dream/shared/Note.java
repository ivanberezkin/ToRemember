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
    private String title;
    private String description;
    private int priorityIndex;
    private Category categoryEnum;
    private boolean isDone;


    public Note(String title, String description, int priorityIndex, Category categoryEnum) {
        this.title = title;
        this.description = description;
        this.priorityIndex = priorityIndex;
        this.categoryEnum = categoryEnum;
        this.isDone = false;
    }
}