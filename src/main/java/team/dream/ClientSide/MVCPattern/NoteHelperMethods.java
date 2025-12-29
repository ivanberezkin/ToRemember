package team.dream.ClientSide.MVCPattern;

import team.dream.shared.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class NoteHelperMethods {


    protected static Message removeNote(MemoryList chosenMemoryList, Scanner scan, ClientController clientController){



        return new Message(MessageType.UPDATE_NOTE, chosenMemoryList, clientController.getModel().getUser());
    }

    protected static Message sortNotesByPriority(MemoryList chosenMemoryList, ClientController clientController) {

        chosenMemoryList.getNotes().sort(Comparator.comparingInt(Note::getPriorityIndex));
        return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, chosenMemoryList, clientController.getModel().getUser());
    }

    protected static Message wouldUserWantToEditNote(Note note, MemoryList chosedMemoryList, Scanner scan, ClientController clientController) {
        IO.println("If you want to edit note, enter Title/Description/Priority/Category." +
                "\nIf you want to remove note enter 'Remove'" +
                "\nIf you want to mark note as done enter 'Done'" +
                "\nEnter random character to go back");
        String userEditInput = scan.nextLine();
        String userEdit = null;
        int userEditPriority;
        switch (userEditInput.trim().toLowerCase()) {
            case "title" -> {
                IO.println("Current title: " + note.getTitle());
                IO.println("Change title to: ");
                userEdit = scan.nextLine();
                note.setTitle(userEdit);
            }
            case "description" -> {
                IO.println("Current description: " + note.getDescription());
                IO.println("Change description to: ");
                userEdit = scan.nextLine();
                note.setDescription(userEdit);
            }
            case "priority" -> {
                IO.println("Current priority: " + note.getPriorityIndex());
                IO.println("Change priority to (1-5 where 1 is highest priority and 5 is lowest): ");
                userEditPriority = scan.nextInt();
                scan.nextLine();
                note.setPriorityIndex(userEditPriority);
            }
            case "category" -> {
                IO.println("Current Category: " + note.getCategoryEnum().toString());
                clientController.getView().categoryEnumPrint();
                userEdit = scan.nextLine();
                note.setCategoryEnum(Category.valueOf(userEdit.trim().toUpperCase()));
            }
            case "remove" -> {
                chosedMemoryList.getNotes().remove(note);
            }
            case "done" -> {
                note.setDone(true);
            }
            default -> {
                return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, chosedMemoryList, clientController.getModel().getUser());
            }
        }
        return new Message(MessageType.UPDATE_NOTE, chosedMemoryList, clientController.getModel().getUser());
    }

    protected static Message getChosenNoteForUser(MemoryList chosedMemoryList, Scanner scan, ClientController clientController) {
        clientController.getView().showMemoryListView(chosedMemoryList);
        IO.println("Which note would you like to see?" +
                "\nEnter 0 to go back " +
                "\nEnter a valid index");
        int chosenNoteIndex = scan.nextInt();
        scan.nextLine();
        if (chosenNoteIndex == 0) {
            return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, clientController.getModel().getUser());
        } else {
            Note chosenNote = chosedMemoryList.getNotes().get(chosenNoteIndex - 1);
            chosenNote.printNote();
            return wouldUserWantToEditNote(chosenNote, chosedMemoryList, scan, clientController);
        }

    }

    protected static Note createNewNote(Scanner scan, ClientController clientController) {
        IO.println("Creating new note, please enter title: ");
        String title = scan.nextLine();

        IO.println("Enter description of the note: ");
        String description = scan.nextLine();

        IO.println("Set priority index (1-5, 1 is highest priority, 5 is lowest): ");
        int priority = scan.nextInt();
        scan.nextLine();

        clientController.getView().categoryEnumPrint();
        String category = scan.nextLine();
        Category chosenCategory = Category.valueOf(category.trim().toUpperCase());

        return new Note(title, description, priority, chosenCategory);
    }


}
