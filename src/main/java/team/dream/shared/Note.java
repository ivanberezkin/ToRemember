package team.dream.shared;

import java.time.LocalDateTime;

public class Note {
    private String title;
    private LocalDateTime deadline;

    public Note(String title, LocalDateTime deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
