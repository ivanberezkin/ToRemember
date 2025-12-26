package team.dream.ClientSide;

public class View {

    private ClientModel model;

    View (ClientModel model){
        this.model = model;
    }

    public void showStartingMenuView(){
        IO.println("*** ToRemember ***" +
                "\nPlease choose an option below" +
                "\n1. Show my notelists" +
                "\n2. Create new notelist"+
                "\n3. Remove notelist" +
                "\n4. Create note" +
                "\n5.Exit");
    }


}
