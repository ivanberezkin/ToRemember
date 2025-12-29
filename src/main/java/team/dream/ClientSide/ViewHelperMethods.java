package team.dream.ClientSide;

import team.dream.shared.MemoryList;

import java.util.List;

public class ViewHelperMethods {


    public static StringBuilder getAllOwnedMemoryListToStringBuilder(StringBuilder sb, List<MemoryList> ownedLists){
        sb.append("*** Owned Lists ***").append("\n");
        for (int i = 0; i < ownedLists.size(); i++) {
            sb.append(i + 1).append(": ").
                    append(ownedLists.get(i).getTitle()).
                    append(", notes in list: ").
                    append(ownedLists.get(i).getNotes().size()).
                    append("\n");
        }
        return sb;
    }

}
