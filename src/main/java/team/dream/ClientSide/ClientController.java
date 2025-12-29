package team.dream.ClientSide;

import team.dream.shared.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    private ClientModel model;
    private View view;
    Scanner scan = new Scanner(System.in);

    ClientController(ClientModel model, View view) {
        this.model = model;
        this.view = view;
    }


    public MemoryList getInputFromShowMemoryLists(){
        view.showAllMemoryListsView(model.getUsersMemoryList(), model.getSharedMemoryList());

        int inputFromUser = scan.nextInt();
        scan.nextLine();

        List<MemoryList> bothOwnedAndSharedList = new ArrayList<>();
        bothOwnedAndSharedList.addAll(model.getUsersMemoryList());
        bothOwnedAndSharedList.addAll(model.getSharedMemoryList());

        return bothOwnedAndSharedList.get(inputFromUser-1);

    }

    private String getTitleForNewMemoryList() {
        IO.println("Please enter name for your memorylist.");
        String title = scan.nextLine();
        //TODO lägga till felhantering
        return title;
    }

    public Message getInputFromChosenMemoryList(MemoryList memoryListToShow){
        view.showMemoryListsView(memoryListToShow);
        view.showUserOptionForChosenMemoryListView();

        int userChosenOption = scan.nextInt();
        scan.nextLine();
        return getInputFromChosenMemoryListAllOptions(userChosenOption, memoryListToShow);

    }

    private Note createNewNote(){
        IO.println("Creating new note, please enter title: ");
        String title = scan.nextLine();

        IO.println("Enter description of the note: ");
        String description = scan.nextLine();

        IO.println("Set priority index (1-5, 1 is highest priority, 5 is lowest): ");
        int priority = scan.nextInt();

        view.categoryEnumPrint();
        Category chosenCategory = Category.valueOf(scan.nextLine());

        return new Note(title, description,priority,chosenCategory);


    }

    private Message getInputFromChosenMemoryListAllOptions(int userChosenOption, MemoryList chosedMemoryList){
        switch (userChosenOption){
            case 1 -> {
                //TODO show note
            }
            case 2 -> {
                chosedMemoryList.addNoteToMemoryList(createNewNote());
                return new Message(MessageType.CREATE_NOTE,chosedMemoryList,model.getUser());
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
                getInputFromShowMemoryLists();
            }
        }
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
