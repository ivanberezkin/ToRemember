package team.dream.Databases;

import javax.xml.transform.Result;
import java.sql.*;

public class UsersMethodSQL {

    private static final Connection connectionToDB = ConnectionToSQL.getInstance().getConnectionToDb();
    private static final String usersTableName = "users";

    
    
    public static int getUserIDForUser(String username){
        Integer userID = null;
        String sqlgetUserID = String.format("select userid from %s where %s.username = '%s'"
                , usersTableName, usersTableName, username);


        try(PreparedStatement stmt = connectionToDB.prepareStatement(sqlgetUserID)){

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    userID = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userID;
    }
    
    public static void addUserToDB(String usernameToAdd){
       try(PreparedStatement stmt = connectionToDB.prepareStatement(
               "INSERT INTO "+ usersTableName +" (username) VALUES (?)"
       )) {

           stmt.setString(1, usernameToAdd);
           IO.println(stmt.executeUpdate() + " rows executed");

       } catch (SQLException e) {
           throw new RuntimeException(e);
       } ;
    }

    public static boolean checkIfUserExistsInDB(String usernameToCheck){

        String sql = "SELECT count(*) from "+usersTableName+" WHERE username = ?";

        try(PreparedStatement stmt = connectionToDB.prepareStatement(sql)
        ) {

            stmt.setString(1,usernameToCheck);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int count = rs.getInt(1);
                    return count != 0;
                }
            }

        } catch (SQLException e) {
            IO.println("Runtime i checkIfUserbal bal");
            throw new RuntimeException(e);
        }

        return false;
    }

}
