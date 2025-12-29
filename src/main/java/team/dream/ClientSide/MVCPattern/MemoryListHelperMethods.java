package team.dream.ClientSide.MVCPattern;

import java.util.Scanner;

public class MemoryListHelperMethods {
    protected static String getTitleForNewMemoryList(Scanner scan) {
        IO.println("Please enter name for your memorylist.");
        String title = scan.nextLine();
        //TODO lägga till felhantering
        return title;
    }

}
