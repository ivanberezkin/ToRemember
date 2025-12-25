package team.dream.shared;

import java.io.Serializable;

public enum MessageType implements Serializable {
    REQUEST_LOGIN,
    USER_NOT_FOUND,
    STARTING_MENU,
    SHOW_LIST_OF_MEMORY_LISTS,
    SHOW_CHOSEN_MEMORY_LIST
}
