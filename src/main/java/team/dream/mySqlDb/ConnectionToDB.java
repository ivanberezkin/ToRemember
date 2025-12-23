package team.dream.mySqlDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private Connection connectionToDb;


    public ConnectionToDB() {

        /*Eftersom vi använder Local databas så kan man sätta system variabel som sätts när man startar programmet.
            I Run -> Edit (Under Configuration) -> Välj SingleServerListener i fältet environment variable skriv följande
            DB_url="jdbc:mysql//localhost:3306/bigtomtedatabase?serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            DB_username=dinUsernameFörDB;
            DB_password=dinLösenFörDB
            jag skrev det i tre rader för synlighetens skull, men det ska vara en rad. Inga mellanslag!
            Då behöver vi inte vara oroliga att ladda upp några lösen till Github också.
         */
        String url = System.getenv("DB_url");
        String username = System.getenv("DB_username");
        String password = System.getenv("DB_password");

        {
            try {
                connectionToDb = DriverManager.getConnection(url,
                        username,
                        password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        ;
    }
}
