package team.dream.ClientSide;

import team.dream.shared.MemoryList;
import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientController {
    private ClientModel model;
    private View view;
    Scanner scan = new Scanner(System.in);

    ClientController(ClientModel model, View view) {
        this.model = model;
        this.view = view;
    }


    public void getInputFromShowMemoryLists(){
        view.showAllMemoryListsView(model.getUsersMemoryList());


        //TODO har inget vettigt att skicka tillbaka än, så pausar programmet här genom att vänta på input
        int inputFromUser = scan.nextInt();
        scan.nextLine();
    }

    private String getTitleForNewMemoryList() {
        IO.println("Please enter name for your memorylist.");
        String title = scan.nextLine();
        //TODO lägga till felhantering
        return title;
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
