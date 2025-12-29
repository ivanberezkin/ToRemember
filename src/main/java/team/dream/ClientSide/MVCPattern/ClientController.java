package team.dream.ClientSide.MVCPattern;

import lombok.Data;
import team.dream.shared.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Data
public class ClientController {
    private ClientModel model;
    private View view;
    List<MemoryList> bothOwnedAndSharedList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public ClientController(ClientModel model, View view) {
        this.model = model;
        this.view = view;
    }

    private Message getInputFromChosenMemoryListAllOptions(int userChosenOption, MemoryList chosedMemoryList) {
        switch (userChosenOption) {
            case 1 -> {
                return NoteHelperMethods.getChosenNoteForUser(chosedMemoryList, scan, this);
            }
            case 2 -> {
                chosedMemoryList.addNoteToMemoryList(NoteHelperMethods.createNewNote(scan, this));
                return new Message(MessageType.CREATE_NOTE, chosedMemoryList, model.getUser());
            }
            case 3 -> {
                return NoteHelperMethods.sortNotesByPriority(chosedMemoryList, this);
            }
            case 4 -> {
                return new Message(MessageType.REMOVE_MEMORY_LIST,chosedMemoryList, model.getUser());
            }
            case 5 -> {
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
                        String title = MemoryListHelperMethods.getTitleForNewMemoryList(scan);
                        return new Message(MessageType.CREATE_MEMORY_LIST, title, model.getUser());
                    }
                    case 3 -> {
                        return new Message(MessageType.REMOVE_MEMORY_LIST, model.getUser());
                    }
                    case 4 -> {
                        System.exit(0);
                    }

                }
            } catch (InputMismatchException e) {
                scan.next();
                IO.println("Please enter a valid digit");
            }
        }
    }

    public Message getInputFromChosenMemoryList(MemoryList memoryListToShow) {
        view.showMemoryListView(memoryListToShow);
        view.showUserOptionForChosenMemoryListView();

        int userChosenOption = scan.nextInt();
        scan.nextLine();
        return getInputFromChosenMemoryListAllOptions(userChosenOption, memoryListToShow);
    }

    public Message getInputFromShowMemoryLists() {
        view.showAllMemoryListsView(model.getUsersMemoryList(), model.getSharedMemoryList());

        int inputFromUser = scan.nextInt();
        scan.nextLine();

        if(inputFromUser == 0){
            return new Message(MessageType.STARTING_MENU, model.getUser());
        }else{
            bothOwnedAndSharedList.clear();
            bothOwnedAndSharedList.addAll(model.getUsersMemoryList());
            bothOwnedAndSharedList.addAll(model.getSharedMemoryList());

            return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, bothOwnedAndSharedList.get(inputFromUser - 1), model.getUser());
        }
    }


}
