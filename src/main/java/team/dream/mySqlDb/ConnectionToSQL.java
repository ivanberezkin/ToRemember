package team.dream.mySqlDb;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Data
public class ConnectionToSQL {
    private static final ConnectionToSQL instance = new ConnectionToSQL();
    private Connection connectionToDb;
    private final String pathToConfigProperties = "src/main/resources/dbConfig.properties";
    private String usernameLogon = null;
    private String passwordLogon = null;
    private String url = null;
    private String url2 = null;
    private String dbName = null;


    public ConnectionToSQL() {

        assignPropertiesToVariables();
        {
            try {
                //Först ansluter man mot själva SQL servern för att kolla ifall din dbName finns, om den inte finns så skapar den en.
                //Det här kan man göra i förhand i databashanterare som MySQL Workbench, men för att underlätta för er så gör vi såhär.
                try(Connection initialConnect = DriverManager.getConnection(url+url2,usernameLogon,passwordLogon)){
                    Statement createDatabaseIfNotExistStatement = initialConnect.createStatement();
                    String createDatabaseIfNotExistSQL = "CREATE DATABASE IF NOT EXISTS " + dbName;
                    createDatabaseIfNotExistStatement.executeUpdate(createDatabaseIfNotExistSQL);
                    IO.println("DB exists or new is created");
                }

                //Här ansluter man mot själva databasen som fanns eller som skapades.
                connectionToDb = DriverManager.getConnection(url+dbName+url2,
                        usernameLogon,
                        passwordLogon);
                IO.println("CONNECTION TO DB SUCCESFUL!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void assignPropertiesToVariables(){
        Properties properties = loadProperties();
        this.usernameLogon = properties.getProperty("db.username");
        this.passwordLogon = properties.getProperty("db.password");
        this.url = properties.getProperty("db.url");
        this.url2 = properties.getProperty("db.url2");
        this.dbName = properties.getProperty("db.name");
    }

    private Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream input = ConnectionToSQL.class.getClassLoader().getResourceAsStream("dbConfig.properties")){

            if(input == null){
                IO.println("Config file not found.");
            }else{
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static ConnectionToSQL getInstance(){
        return instance;
    }
}

