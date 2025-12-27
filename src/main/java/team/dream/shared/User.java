package team.dream.shared;

import lombok.Data;

@Data
public class User {

    private String userName;
    private String userID;

    public User(String userName) {
        this.userName = userName;
        this.userID = userID;
    }

    public User() {
    }
}
