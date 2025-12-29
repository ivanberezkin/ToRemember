package team.dream.shared;

import java.io.Serializable;

public enum MessageType implements Serializable {
    REQUEST_LOGIN,
    USER_NOT_FOUND,
    CREATE_NEW_USER,
    LOGIN_SUCCESSFUL,
    STARTING_MENU,
    SHOW_LIST_OF_MEMORY_LISTS,
    SHOW_CHOSEN_MEMORY_LIST,
    CREATE_MEMORY_LIST,
    REMOVE_MEMORY_LIST,
    CREATE_NOTE,
    UPDATE_NOTE,
}
