package mySqlDb;

import javax.xml.transform.Result;
import java.sql.*;

public class UsersMethod {

    private static final Connection connectionToDB = ConnectionToDB.getInstance().getConnectionToDb();


    public static void addUserToDB(Connection connectionToDb, String usernameToAdd){
       try(PreparedStatement stmt = connectionToDb.prepareStatement(
               "INSERT INTO users (username) VALUES (?)"
       )) {

           stmt.setString(1, usernameToAdd);
           IO.println(stmt.executeUpdate() + " rows executed");

       } catch (SQLException e) {
           throw new RuntimeException(e);
       } ;
    }

    public static boolean checkIfUserExistsInDB(String usernameToCheck){

        String sql = "SELECT count(*) from user WHERE username = ?";

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
