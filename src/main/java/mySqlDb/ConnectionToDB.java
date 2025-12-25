package mySqlDb;

import lombok.Data;
import shared.Connections;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Data
public class ConnectionToDB {
    private static final ConnectionToDB instance = new ConnectionToDB();
    private Connection connectionToDb;
    private final String pathToConfigProperties = "src/main/resources/dbConfig.properties";
    private String usernameLogon = null;
    private String passwordLogon = null;
    private String url = null;
    private String url2 = null;
    private String dbName = null;


    public ConnectionToDB() {

        assignPropertiesToVariables();
        {
            try {
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
        try(InputStream input = ConnectionToDB.class.getClassLoader().getResourceAsStream("dbConfig.properties")){

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

    public static ConnectionToDB getInstance(){
        return instance;
    }
}

