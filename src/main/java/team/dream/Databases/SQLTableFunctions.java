package team.dream.Databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLTableFunctions {

    private static final Connection connectionToDB = ConnectionToSQL.getInstance().getConnectionToDb();

    public static void createTableIfNotExist(String tableName){

        String createTableIfNotExistSQL = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(userid int not null auto_increment primary key," +
                " username varchar(50) not null UNIQUE)";

        try {
            Statement createTableIfNotExistStatement = connectionToDB.createStatement();
            createTableIfNotExistStatement.executeUpdate(createTableIfNotExistSQL);
            IO.println("Table " + tableName + " exists or is created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createMemoryListTableIfNotExist(String tableName){

        String createTableIfNotExistSQL = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(memoryListID int not null auto_increment primary key," +
                " ownerUserID int not null," +
                "foreign key(ownerUserID) references Users(userid))";

        try {
            Statement createTableIfNotExistStatement = connectionToDB.createStatement();
            createTableIfNotExistStatement.executeUpdate(createTableIfNotExistSQL);
            IO.println("Table " + tableName + " exists or is created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
