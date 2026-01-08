package team.dream.ClientSide.MVCPattern;

import java.util.Scanner;

public class MemoryListHelperMethods {
    protected static String getTitleForNewMemoryList(Scanner scan) {
        String title;

        while(true){
            IO.println("Please enter name for your memorylist.");
            title = scan.nextLine();

            if(!title.isEmpty()){
                return title;
            }else{
                IO.println("Title can't be empty. ");
            }
        }
    }

}
