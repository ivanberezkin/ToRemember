package team.dream.mySqlDb;

import java.sql.*;

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
        String usernameLogon = System.getenv("DB_username");
        String passwordLogon = System.getenv("DB_password");

        {
            try {
                connectionToDb = DriverManager.getConnection(url,
                        usernameLogon,
                        passwordLogon);

                PreparedStatement stmt = connectionToDb.prepareStatement("select id, username from users");
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String username_db = rs.getString("username");
                    int id = rs.getInt("id");
                    IO.println(id + "  " + username_db);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
