package mySqlDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersMethod {

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

}
