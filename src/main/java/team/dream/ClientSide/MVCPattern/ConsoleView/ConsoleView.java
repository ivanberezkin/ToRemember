package team.dream.ClientSide.MVCPattern.ConsoleView;

import team.dream.shared.Category;
import team.dream.shared.MemoryList;
import team.dream.shared.Note;

import java.util.List;

public class ConsoleView {


    public ConsoleView() {

    }

    public void showStartingMenuView() {
        IO.println("*** ToRemember ***" +
                "\nPlease choose an option below" +
                "\n1. Show all memory lists" +
                "\n2. Create new memory list" +
                "\n3. Exit");
    }

    public void showAllMemoryListsView(List<MemoryList> ownedList, List<MemoryList> sharedList) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        ViewHelperMethods.getAllOwnedMemoryListToStringBuilder(sb, ownedList);
        if (!sharedList.isEmpty()) {
            sb.append(ViewHelperMethods.getAllSharedMemoryListToStringBuilder(sb, sharedList, ownedList.size()));
        }

        sb.append("Please enter index of MemoryList you would like to see, enter 0 to go back to start menu.");
        IO.println(sb);
    }


    public void categoryEnumPrint() {
        IO.print("Available categories ");
        for (Category c : Category.values()) {
            IO.print(c.toString());
            IO.print(" ");
        }
        IO.println("\nPlease choose one of those categories: ");
    }

    public void showUserOptionForChosenMemoryListView() {
        StringBuilder userOptionsSb = new StringBuilder();
        userOptionsSb.append("What would you like to do with this list.").append("\n").
                append("1. Show note").append("\n").
                append("2. Create new note").append("\n").
                append("3. Sort notes based on priority").append("\n").
                append("4. Remove memory list").append("\n").
                append("5. Return to view all memory lists").append("\n\n").
                append("Please enter valid index: ");

        IO.println(userOptionsSb);
    }

    public void showMemoryListView(MemoryList memoryList) {
        StringBuilder memoryListSb = new StringBuilder();
        memoryListSb.append("*** ").append(memoryList.getTitle()).append(" ***").append("\n");
        List<Note> notesInMemoryList = memoryList.getNotes();
        int titleLength = 0;
        Note currentNote;
        for (int i = 0; i < notesInMemoryList.size(); i++) {
            currentNote = notesInMemoryList.get(i);
            memoryListSb.append(i + 1).
                    append(": ").
                    append(currentNote.getTitle());

            addFormattingToTable(memoryListSb,currentNote, 25);
            memoryListSb.append("\tPriority: ").
                    append(currentNote.getPriorityIndex());
            memoryListSb.append(" ".repeat(5));
            memoryListSb.append("\tStatus: ");
            if(currentNote.isDone()){
                memoryListSb.append("Finished").append("\n");
            }else{
                memoryListSb.append("Ongoing").append("\n");
            }

        }

        IO.println(memoryListSb);

    }

    private StringBuilder addFormattingToTable(StringBuilder sb, Note note, int spaces){
        int titleLength = 0;
        if ((titleLength = note.getTitle().length()) < spaces) {
            sb.append(" ".repeat(spaces - titleLength + 1));
        }
        return sb;
    }

}
