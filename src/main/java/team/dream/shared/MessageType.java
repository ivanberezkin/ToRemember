package team.dream.shared;

import java.io.Serializable;

public enum MessageType implements Serializable {
    REQUEST_LOGIN,
    USER_NOT_FOUND,
    CREATE_NEW_USER,
    LOGIN_SUCCESSFUL
}
