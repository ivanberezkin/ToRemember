package team.dream.ClientSide.MVCPattern;

import team.dream.shared.*;

import java.util.*;

public class NoteHelperMethods {


    protected static Message removeNote(MemoryList chosenMemoryList, Scanner scan, ClientController clientController) {


        return new Message(MessageType.UPDATE_NOTE, chosenMemoryList, clientController.getModel().getUser());
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
                while (true) {
                    IO.println("Current title: " + note.getTitle());
                    IO.println("Change title to: ");
                    userEdit = scan.nextLine();

                    if (!userEdit.isEmpty()) {
                        note.setTitle(userEdit);
                        break;
                    } else {
                        IO.println("---- Title can't be empty.");
                    }

                }
            }
            case "description" -> {
                while (true) {
                    IO.println("Current description: " + note.getDescription());
                    IO.println("Change description to: ");
                    userEdit = scan.nextLine();
                    if (!userEdit.isEmpty()) {
                        note.setDescription(userEdit);
                        break;
                    } else {
                        IO.println("---- Description can't be empty.");
                    }
                }
            }
            case "priority" -> {
                while (true) {

                    IO.println("Current priority: " + note.getPriorityIndex());
                    IO.println("Change priority to (1-5 where 1 is highest priority and 5 is lowest): ");
                    try {

                        userEditPriority = scan.nextInt();
                        scan.nextLine();

                        if (userEditPriority > 0 && userEditPriority <= 5) {
                            note.setPriorityIndex(userEditPriority);
                            break;
                        } else {
                            throw new IllegalArgumentException();
                        }

                    } catch (InputMismatchException e) {
                        IO.println("---- Please enter a number between 1 - 5. ----");
                        scan.nextLine();
                    } catch (IllegalArgumentException e) {
                        IO.println("---- Please enter a valid priority between 1 - 5. ----");
                    }
                }
            }
            case "category" -> {
                while (true) {
                    IO.println("Current Category: " + note.getCategoryEnum().toString());
                    clientController.getView().categoryEnumPrint();
                    userEdit = scan.nextLine();
                    if (!userEdit.isEmpty()) {
                        try {
                            note.setCategoryEnum(Category.valueOf(userEdit.trim().toUpperCase()));
                            break;
                        } catch (IllegalArgumentException e) {
                            IO.println("---- Please enter a valid category. ----");
                        }
                    } else {
                        IO.println("---- Category can't be empty.");
                    }

                }
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
        return new Message(MessageType.UPDATE_NOTE, note, clientController.getModel().getUser());
    }

    protected static Message getChosenNoteForUser(MemoryList chosedMemoryList, Scanner scan, ClientController clientController) {

        while (true) {
            try {

                clientController.getView().showMemoryListView(chosedMemoryList);
                IO.println("Which note would you like to see?" +
                        "\nEnter 0 to go back " +
                        "\nEnter a valid index");
                int chosenNoteIndex = scan.nextInt();
                scan.nextLine();
                if (chosenNoteIndex == 0) {
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, clientController.getModel().getUser());
                } else {
                    if (chosenNoteIndex <= chosedMemoryList.getNotes().size()) {
                        Note chosenNote = chosedMemoryList.getNotes().get(chosenNoteIndex - 1);
                        chosenNote.printNote();
                        return wouldUserWantToEditNote(chosenNote, chosedMemoryList, scan, clientController);
                    } else {
                        throw new IllegalArgumentException();

                    }
                }

            } catch (InputMismatchException e) {
                IO.println("---- Index doesn't exist, please enter a valid index. ----");
                scan.nextLine();
            } catch (IllegalArgumentException e) {
                IO.println("---- Note index doesn't exist, please enter a valid index. ----");
            }
        }
    }

    protected static Note createNewNote(Scanner scan, ClientController clientController, MemoryList chosedMemoryList) {
        Category chosenCategory = null;

        while (true) {
            try {
                IO.println("Creating new note, please enter title: ");
                String title = scan.nextLine();

                IO.println("Enter description of the note: ");
                String description = scan.nextLine();

                IO.println("Set priority index (1-5, 1 is highest priority, 5 is lowest): ");
                int priority = scan.nextInt();
                scan.nextLine();

                clientController.getView().categoryEnumPrint();
                String category = scan.nextLine();
                try {
                    chosenCategory = Category.valueOf(category.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    IO.println("---- Category doesn't exist, please try again. ----");
                }

                return new Note(chosedMemoryList.getMemoryListID(),title, description, priority, chosenCategory);
            } catch (InputMismatchException e) {
                IO.println("---- Please enter valid values. ----");
            }
        }

    }

}
