package team.dream.ClientSide;

import team.dream.shared.MemoryList;
import team.dream.shared.Note;

import java.util.List;

public class View {


    View() {

    }

    public void showStartingMenuView() {
        IO.println("*** ToRemember ***" +
                "\nPlease choose an option below" +
                "\n1. Show all memory lists" +
                "\n2. Create new memory list" +
                "\n3. Remove memory list" +
                "\n4. Create note" +
                "\n5. Exit");
    }

    public void showAllMemoryListsView(List<MemoryList> ownedList, List<MemoryList> sharedList) {
        StringBuilder sb = new StringBuilder();
        ViewHelperMethods.getAllOwnedMemoryListToStringBuilder(sb, ownedList);
        if(!sharedList.isEmpty()){
            sb.append(ViewHelperMethods.getAllSharedMemoryListToStringBuilder(sb, sharedList, ownedList.size()));
        }
        IO.println(sb);
    }



    public void showMemoryListsView(MemoryList memoryList) {
        IO.println(memoryList.getTitle());
        for (Note note : memoryList.getNotes()) {
            IO.println(note.getTitle());
        }
    }
}
