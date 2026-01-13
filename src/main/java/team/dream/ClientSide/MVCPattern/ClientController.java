package team.dream.ClientSide.MVCPattern;

import lombok.Data;
import team.dream.ClientSide.MVCPattern.ConsoleView.ConsoleView;
import team.dream.shared.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Data
public class ClientController {
    private ClientModel model;
    private ConsoleView consoleView;
    List<MemoryList> bothOwnedAndSharedList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public ClientController(ClientModel model, ConsoleView consoleView) {
        this.model = model;
        this.consoleView = consoleView;
    }

    private Message getInputFromChosenMemoryListAllOptions(int userChosenOption, MemoryList chosedMemoryList) throws InputMismatchException {

        switch (userChosenOption) {
                case 1 -> {
                    return NoteHelperMethods.getChosenNoteForUser(chosedMemoryList, scan, this);
                }
                case 2 -> {
                    Note newNote = NoteHelperMethods.createNewNote(scan, this, chosedMemoryList);
                    return new Message(MessageType.CREATE_NOTE, newNote, model.getUser());
                }
                case 3 -> {
                    return new Message(MessageType.SORT_NOTES_BY_PRIORITY, chosedMemoryList, model.getUser());
                }
                case 4 -> {
                    return new Message(MessageType.REMOVE_MEMORY_LIST, chosedMemoryList, model.getUser());
                }
                case 5 -> {
                    return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
                }
                default -> {
                    throw new InputMismatchException();
                }
            }

    }

    public Message getInputFromStartingMenu() {
        IO.println("Hello " + model.getUser());
        int inputFromUser;
        while (true) {
            consoleView.showStartingMenuView();
            try {
                inputFromUser = scan.nextInt();
                scan.nextLine();
                switch (inputFromUser) {
                    case 1 -> {
                        return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
                    }

                    case 2 -> {
                        String title = MemoryListHelperMethods.getTitleForNewMemoryList(scan);
                        return new Message(MessageType.CREATE_MEMORY_LIST, title, model.getUser());
                    }

                    case 4 -> {
                        //TODO show all notes for a category
                    }
                    case 3 -> {
                        System.exit(0);
                    }

                    default -> {
                        throw new InputMismatchException();
                    }

                }
            } catch (InputMismatchException e) {
                scan.next();
                IO.println("Please enter a valid digit");
            }
        }
    }

    public Message getInputFromChosenMemoryList(MemoryList memoryListToShow) {
        while (true) {
            try {

                consoleView.showMemoryListView(memoryListToShow);
                consoleView.showUserOptionForChosenMemoryListView();
                int userChosenOption = scan.nextInt();
                scan.nextLine();
                return getInputFromChosenMemoryListAllOptions(userChosenOption, memoryListToShow);

            } catch (InputMismatchException e) {
                IO.println("Invalid index, please try again");
                scan.nextLine();
            }
        }
    }

    public Message getInputFromShowMemoryLists() {

        while (true) {

            try {
                consoleView.showAllMemoryListsView(model.getUsersMemoryList(), model.getSharedMemoryList());

                int inputFromUser = scan.nextInt();

                if (inputFromUser == 0) {
                    return new Message(MessageType.STARTING_MENU, model.getUser());
                } else {
                    bothOwnedAndSharedList.clear();
                    bothOwnedAndSharedList.addAll(model.getUsersMemoryList());
                    bothOwnedAndSharedList.addAll(model.getSharedMemoryList());

                    if (inputFromUser <= bothOwnedAndSharedList.size()) {
                        return new Message(MessageType.SHOW_CHOSEN_MEMORY_LIST, bothOwnedAndSharedList.get(inputFromUser - 1), model.getUser());
                    } else {
                        throw new InputMismatchException();
                    }
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                IO.println("Please enter a valid index");
            }
        }

    }

}
