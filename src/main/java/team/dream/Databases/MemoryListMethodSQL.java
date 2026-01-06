package team.dream.Databases;

import team.dream.shared.MemoryList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemoryListMethodSQL {

    private static final Connection connectionToDB = ConnectionToSQL.getInstance().getConnectionToDb();
    private static final String memoryListTableName = "memorylist";
    private static final String usersTableName = "users";
    private static int userID;

    public static void createNewMemoryList(String username, String titleForNewMemoryList){

        userID = UsersMethodSQL.getUserIDForUser(username);

        String sqlCreateNewMemoryList = String.format("INSERT INTO memorylist (ownerUserID, title) VALUES (?,?)");

        try(PreparedStatement stmt = connectionToDB.prepareStatement(sqlCreateNewMemoryList)){

            stmt.setInt(1,userID);
            stmt.setString(2, titleForNewMemoryList);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void removeMemoryList(MemoryList memoryListToDelete){


        String sqlCreateNewMemoryList = String.format("DELETE FROM memorylist WHERE memoryListID = ?");

        try(PreparedStatement stmt = connectionToDB.prepareStatement(sqlCreateNewMemoryList)){

            stmt.setInt(1,memoryListToDelete.getMemoryListID());
            stmt.executeUpdate();
            IO.print("removeMemoryList executed.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<MemoryList> showUsersMemoryLists (String usernameForUser){
        ArrayList<MemoryList> usersMemoryList = new ArrayList<>();

        String sqlShowUsersMemoryLists = String.format(
          "SELECT * FROM %s INNER JOIN %s ON %s.ownerUserID = %s.userid WHERE %s.username = '%s'",
                memoryListTableName,usersTableName,memoryListTableName,usersTableName,usersTableName,usernameForUser
        );

        try(PreparedStatement stmt = connectionToDB.prepareStatement(sqlShowUsersMemoryLists)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int memoryListID = rs.getInt(1);
                    String memoryListTitle = rs.getString(3);
                    String memoryListOwnerUsername = rs.getString(5);

                usersMemoryList.add(new MemoryList(memoryListTitle,memoryListOwnerUsername,memoryListID));
                }
            }

            IO.println("ShowUsersMemoryLists executed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersMemoryList;
    }

}
