package team.dream.ClientSide;

import team.dream.shared.Message;
import team.dream.shared.MessageType;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientController {
    private ClientModel model;
    private View view;
    Scanner scan = new Scanner(System.in);

    ClientController(ClientModel model, View view){
        this.model = model;
        this.view = view;
    }

    public Message getInputFromStartingMenu(){
        IO.println("Hello " + model.getUser());
        int inputFromUser;
        while(true){
            view.showStartingMenuView();
            try{
                inputFromUser = scan.nextInt();
                switch(inputFromUser){
                    case 1 -> {
                        return new Message(MessageType.SHOW_LIST_OF_MEMORY_LISTS, model.getUser());
                    }
                    case 2 -> {
                        return new Message(MessageType.CREATE_MEMORY_LIST, model.getUser());
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
            } catch (InputMismatchException e){
                scan.next();
                IO.println("Please enter a valid digit");
            }



        }


    }

}
