package team.dream.ClientSide.MVCPattern;

import team.dream.shared.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    private ClientModel model;
    private View view;
    List<MemoryList> bothOwnedAndSharedList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public ClientController(ClientModel model, View view) {
        this.model = model;
        this.view = view;
    }


    public MemoryList getInputFromShowMemoryLists() {
        view.showAllMemoryListsView(model.getUsersMemoryList(), model.getSharedMemoryList());

        int inputFromUser = scan.nextInt();
        scan.nextLine();

        bothOwnedAndSharedList.clear();
        bothOwnedAndSharedList.addAll(model.getUsersMemoryList());
        bothOwnedAndSharedList.addAll(model.getSharedMemoryList());

        return bothOwnedAndSharedList.get(inputFromUser - 1);

    }

    private String getTitleForNewMemoryList() {
        IO.println("Please enter name for your memorylist.");
        String title = scan.nextLine();
        //TODO lägga till felhantering
        return title;
    }

    public Message getInputFromChosenMemoryList(MemoryList memoryListToShow) {
        view.showMemoryListView(memoryListToShow);
        view.showUserOptionForChosenMemoryListView();

        int userChosenOption = scan.nextInt();
        scan.nextLine();
        return getInputFromChosenMemoryListAllOptions(userChosenOption, memoryListToShow);

    }

    private Note createNewNote() {
        IO.println("Creating new note, please enter title: ");
        String title = scan.nextLine();

        IO.println("Enter description of the note: ");
        String description = scan.nextLine();

        IO.println("Set priority index (1-5, 1 is highest priority, 5 is lowest): ");
        int priority = scan.nextInt();
        scan.nextLine();

        view.categoryEnumPrint();
        String category = scan.nextLine();
        Category chosenCategory = Category.valueOf(category.trim().toUpperCase());

        return new Note(title, description, priority, chosenCategory);
    }

    private Message getChosenNoteForUser(MemoryList chosedMemoryList) {
        view.showMemoryListView(chosedMemoryList);
        IO.println("Which note would you like to see?" +
                "\nEnter 0 to go back " +
                "\nEnter a valid index");
        int chosenNoteIndex = scan.nextInt();
        scan.nextLine();
        if (chosenNoteIndex == 0) {
            return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
        } else {
            Note chosenNote = chosedMemoryList.getNotes().get(chosenNoteIndex - 1);
            chosenNote.printNote();
            return wouldUserWantToEditNote(chosenNote, chosedMemoryList);
        }

    }

    private Message wouldUserWantToEditNote(Note note, MemoryList chosedMemoryList) {
        IO.println("If you want to edit note, enter Title/Description/Priority/Category else enter random character to go back");
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
                view.categoryEnumPrint();
                userEdit = scan.nextLine();
                note.setCategoryEnum(Category.valueOf(userEdit.trim().toUpperCase()));
            }
            default -> {
                return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, chosedMemoryList, model.getUser());
            }
        }
        return new Message(MessageType.UPDATE_NOTE, chosedMemoryList, model.getUser());
    }

    private Message getInputFromChosenMemoryListAllOptions(int userChosenOption, MemoryList chosedMemoryList) {
        switch (userChosenOption) {
            case 1 -> {
                return getChosenNoteForUser(chosedMemoryList);
            }
            case 2 -> {
                chosedMemoryList.addNoteToMemoryList(createNewNote());
                return new Message(MessageType.CREATE_NOTE, chosedMemoryList, model.getUser());
            }
            case 3 -> {
                //TODO sort notes
            }
            case 4 -> {
                //TODO remove note
            }
            case 5 -> {
                //TODO remove memory list
            }
            case 6 -> {
                return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
            }
        }
        //TODO fixa felhantering sen
        return null;
    }

    public Message getInputFromStartingMenu() {
        IO.println("Hello " + model.getUser());
        int inputFromUser;
        while (true) {
            view.showStartingMenuView();
            try {
                inputFromUser = scan.nextInt();
                scan.nextLine();
                switch (inputFromUser) {
                    case 1 -> {
                        return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
                    }
                    //Create new memoryList
                    case 2 -> {
                        String title = getTitleForNewMemoryList();
                        return new Message(MessageType.CREATE_MEMORY_LIST, title, model.getUser());
                    }
                    case 3 -> {
                        return new Message(MessageType.REMOVE_MEMORY_LIST, model.getUser());
                    }
                    case 4 -> {
                        return new Message(MessageType.CREATE_NOTE, model.getUser());
                    }
                    case 5 -> {
                        System.exit(0);
                    }

                }
            } catch (InputMismatchException e) {
                scan.next();
                IO.println("Please enter a valid digit");
            }


        }


    }

}
